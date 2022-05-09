import React, { useEffect, useState, useRef } from 'react';
import Map from "../Utils/Map";
import FloatingMenu from "../Utils/FloatingMenu";
import { useUserContext } from '../../Contexts/UserContext';
import { useParams } from "react-router-dom";
import { getTripById, getAutoRecs } from "../../utils/Trip";
import AutoRec from './AutoRec';
import tinycolor from 'tinycolor2';

const styleSheet = {
    fullPage: {
        width: "100%",
        height: "100%",
        display: "flex",
    },
    contentContainer: {
        width: "25%",
        display: "flex",
        flexDirection: "column",
        height: "100%",
    },
    about: {
        width: "80%",
        height: "80%",
        position: "absolute",
        top: "10%",
        left: "10%",
        backgroundColor: "rgba(100,100,100,0.75)",
        borderRadius: "10px",
        textAlign: "center",
        color: "white",
    }
};

const TripRecs = () => {
    const params = useParams();
    const { user, getIdToken } = useUserContext();
    const [trip, setTrip] = useState(null);
    const [tripRecs, setTripRecs] = useState(null);
    let mapRef = useRef(null);

    useEffect(() => {
        (async () => {
            if (user === null) return;
            let id_token = await getIdToken();
            console.log("id_token: ", id_token);

            let data = await getTripById(params.id);
            let rawTrip = data.trip;
            let stops = data.stops;

            let enrichedStops = rawTrip.tripData.stops.map(stop => {
                let bigStop = stops.find(s => s.id === stop.bigStop);
                return { ...bigStop };
            });

            rawTrip.tripData.stops = enrichedStops;
            setTrip(rawTrip);
            console.log("HERE!");
            let tripRecs = await getAutoRecs(params.id, id_token);
            setTripRecs([tripRecs])
        })();
    }, [params.id, user]);

    useEffect(async () => {
        let i = 1;
        for (let rec of tripRecs) {
            for (let { bigStop: stop } of rec.tripData.stops) {
                console.log("stop: ", stop);
                mapRef.current.addMarkerLngLat(stop.lng, stop.lat, stop.id);
            }

            mapRef.current.addPath(rec.tripData.stops.map(stop => stop.bigStop), "trip" + (i++), tinycolor.random().toString());
        }
    }, [tripRecs]);

    return (
        <div style={styleSheet.fullPage}>
            <Map
                ref={mapRef}
            />
            <FloatingMenu>
                <div style={styleSheet.contentContainer}>
                    <h1>{trip ? trip.meta.name : "Loading..."}</h1>
                    <div>
                        {
                            tripRecs ?
                                tripRecs.map(trip => {
                                    return (
                                        <AutoRec trip={trip} />
                                    )
                                })
                                :
                                <div>Loading...</div>
                        }
                    </div>
                </div>
            </FloatingMenu>
        </div>
    );
};

export default TripRecs;