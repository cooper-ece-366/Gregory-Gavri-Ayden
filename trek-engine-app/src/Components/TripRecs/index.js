import React, { useEffect, useState, useRef } from 'react';
import Map from "../Utils/Map";
import FloatingMenu from "../Utils/FloatingMenu";
import { useUserContext } from '../../Contexts/UserContext';
import { useParams } from "react-router-dom";
import { getTripById, getStopById } from "../../utils/Trip";
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

    useEffect(async () => {
        let data = await getTripById(params.id);
        let rawTrip = data.trip;
        let stops = data.stops;
        console.log(data);

        let enrichedStops = rawTrip.tripData.stops.map(stop => {
            console.log(stop);
            let bigStop = stops.find(s => s.id === stop.bigStop);
            console.log(bigStop);
            return { ...bigStop };
        });

        rawTrip.tripData.stops = enrichedStops;
        setTrip(rawTrip);
        setTripRecs([rawTrip])
    }, [params.id]);

    useEffect(async () => {
        console.log(tripRecs)
        let i = 0;
        for (let rec of tripRecs) {
            for (let stop of rec.tripData.stops) {
                mapRef.current.addMarkerLngLat(stop.lng, stop.lat, stop.id);
            }

            mapRef.current.addPath(rec.tripData.stops, "trip" + i++, tinycolor.random().toString());
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