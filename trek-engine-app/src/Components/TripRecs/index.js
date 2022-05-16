// Written by Gavri Kepets
import React, { useEffect, useState, useRef } from 'react';
import Map from "../Utils/Map";
import FloatingMenu from "../Utils/FloatingMenu";
import { useUserContext } from '../../Contexts/UserContext';
import { useParams } from "react-router-dom";
import { getTripById, getAutoRecs } from "../../utils/Trip";
import AutoRec from './AutoRec';
import tinycolor from 'tinycolor2';
import { updateTrip } from '../../utils/Trip';
import { useNavigate } from 'react-router-dom';

const styleSheet = {
    fullPage: {
        width: "100%",
        height: "100%",
        display: "flex",
    },
    contentContainer: {
        width: "100%",
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
    },
    recsContainer: {
        width: "100%",
    }
};

const TripRecs = () => {
    const params = useParams();
    const { user, getIdToken } = useUserContext();
    const [trip, setTrip] = useState(null);
    const [tripRecs, setTripRecs] = useState(null);
    let mapRef = useRef(null);
    const navigate = useNavigate();

    useEffect(() => {
        (async () => {
            console.log(user)
            if (user === null) return;
            let id_token = await getIdToken();
            console.log("id_token: ", id_token);

            let data = await getTripById(params.id);

            setTrip(data);
            console.log("HERE!");
            let tripRecs = await getAutoRecs(params.id, id_token);
            console.log(tripRecs)
            setTripRecs([tripRecs])
        })();
    }, [params.id, user]);

    useEffect(async () => {
        let i = 1;
        for (let rec of tripRecs) {
            for (let { bigStop: stop } of rec.tripData.stops) {
                mapRef.current.addMarkerLngLat(stop.lng, stop.lat, stop.id);
            }

            mapRef.current.addPath(rec.tripData.stops.map(stop => stop.bigStop), "trip" + (i++), tinycolor.random().toString());
        }
    }, [tripRecs]);

    const overrideTrip = async (otrip) => {
        console.log("overrideTrip w:", otrip);
        let id_token = await getIdToken();
        await updateTrip(otrip, id_token);
        navigate("/viewTrip/" + otrip._id);
    }

    return (
        <div style={styleSheet.fullPage}>
            <Map
                ref={mapRef}
            />
            <FloatingMenu>
                <div style={styleSheet.contentContainer}>
                    <h1>{trip ? trip.meta.name : "Loading..."}</h1>
                    <div style={styleSheet.recsContainer}>
                        {
                            tripRecs ?
                                tripRecs.map(trip => {
                                    return (
                                        <AutoRec trip={trip} override={overrideTrip} />
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