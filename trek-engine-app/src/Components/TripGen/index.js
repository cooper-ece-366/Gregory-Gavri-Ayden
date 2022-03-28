import TripForm from "./TripForm"
import {useRef} from "react"; 
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
        height:"100%",
        display: "flex",
    },
    contentContainer: {
        paddingTop:"70px",
        width:"25%"
    }
}

const TripGen = (props) => {
    //ref for all map related functions 
    const mapRef = useRef(null);
    return (
        <div style={styleSheet.fullPage}>
            <div style={styleSheet.contentContainer}>
                <div onClick={()=>{mapRef.current.addPath("NY","LA","NY-LA")}}>NY-LA Trip</div>
            </div>
            <Map ref={mapRef}/>
        </div>
    );
}

export default TripGen;