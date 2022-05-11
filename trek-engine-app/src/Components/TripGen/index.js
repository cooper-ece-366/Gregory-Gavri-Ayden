// Written By Gavri Kepets
import TripForm from "./TripForm"
import { useRef } from "react";
import Map from "../Utils/Map";

const styleSheet = {
    fullPage: {
        width: "100%",
        height: "100%",
        display: "flex",
    },
    floatingMenu: {
        position: "fixed",
        zIndex: "2",
        height: "80%",
        width: "15%",
        maxWidth: "450px",
        minWidth: "200px",
        backgroundColor: "#ffffff",
        borderRadius: "10px",
        bottom: "10%",
        left: "2.5%",
        minWidth: "250px",
        boxShadow: "0px 0px 50px #000000",
        display: "flex",
        background: "linear-gradient(180deg, #050D2B 0%, #010514 100%)",
    }
}

const TripGen = (props) => {
    //ref for all map related functions 
    const mapRef = useRef(null);
    return (
        <div style={styleSheet.fullPage}>
            <Map ref={mapRef} />
            <div style={styleSheet.floatingMenu}>
                <TripForm />
            </div>
        </div>
    );
}

export default TripGen;