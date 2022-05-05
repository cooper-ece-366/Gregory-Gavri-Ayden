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
        let trip = await getTripById(params.id);
        console.log(trip);
        trip = JSON.parse(trip);
        setTrip(trip);
        console.log(trip);
    }, [params.id]);

    return (
        <div style={styleSheet.fullPage}>
            <Map />
            <FloatingMenu>
                <h1>{trip ? "HERE" : "Loading..."}</h1>
            </FloatingMenu>
        </div>
    );
};

export default About;