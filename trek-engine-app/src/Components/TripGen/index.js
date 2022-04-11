import TripForm from "./TripForm"
import { useRef } from "react";
import Map from "../Map";

const styleSheet = {
    container: {
        display: "flex",
        paddingTop: "50px",
        height: "100%",
        width: "100%",
    },
    fullPage: {
        width: "100%",
        height: "100%",
        display: "flex",
    },
    contentContainer: {
        paddingTop: "70px",
        width: "25%"
    },
    floatingMenu: {
        position: "fixed",
        zIndex: "2",
        height: "80%",
        width: "20%",
        maxWidth: "450px",
        minWidth: "200px",
        backgroundColor: "#ffffff",
        borderRadius: "10px",
        bottom: "10%",
        left: "2.5%",
        minWidth: "250px",
        boxShadow: "0px 0px 50px #000000",
        display: "flex",
    }
}

const TripGen = (props) => {
    //ref for all map related functions 
    const mapRef = useRef(null);
    return (
        <div style={styleSheet.fullPage}>
            {/* <div style={styleSheet.contentContainer}>
                <div onClick={()=>{mapRef.current.addPath("NY","LA","NY-LA")}}>NY-LA Trip</div>
            </div> */}
            <Map ref={mapRef} />
            <div style={styleSheet.floatingMenu}>
                <TripForm />
            </div>
        </div>
    );
}

export default TripGen;