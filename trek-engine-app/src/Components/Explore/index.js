import {useRef} from "react"; 
import Map from "../Map"; 

const styleSheet = {
    fullPage: {
        width: "100%",
        height:"100%",
        display: "flex",
    },
    contentContainer: {
        paddingTop:"70px",
        width:"25%"
    }
};


const Explore = () => {
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
}; 

export default Explore;