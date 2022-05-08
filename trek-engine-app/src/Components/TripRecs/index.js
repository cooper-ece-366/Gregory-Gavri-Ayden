import React, { useEffect, useState } from 'react';
import Map from "../Utils/Map";
import FloatingMenu from "../Utils/FloatingMenu";
import { useUserContext } from '../../Contexts/UserContext';
import { useParams } from "react-router-dom";
import { getTripById, getStopById } from "../../utils/Trip";

const styleSheet = {
    fullPage: {
        width: "100%",
        height: "100%",
        display: "flex",
    },
    contentContainer: {
        paddingTop: "70px",
        width: "25%"
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

const About = () => {
    const params = useParams();
    const { user, getIdToken } = useUserContext();
    const [trip, setTrip] = useState(null);

    useEffect(async () => {
        console.log("HERE!")
        let data = await getTripById(params.id);
        let rawTrip = data.trip;
        let stops = data.stops;

        let enrichedStops = rawTrip.tripData.stops.map(stop => {
            let bigStop = stops.find(s => s.bigStop === stop.id);
            return { ...bigStop };
        });

        rawTrip.stops = enrichedStops;

        setTrip(rawTrip);
    }, [params.id]);

    return (
        <div style={styleSheet.fullPage}>
            <Map />
            <FloatingMenu>
                <h1>{trip ? trip.meta.name : "Loading..."}</h1>
            </FloatingMenu>
        </div>
    );
};

export default About;