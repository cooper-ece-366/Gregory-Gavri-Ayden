import { useState, useRef, useLayoutEffect} from 'react'; 
import { useParams } from "react-router-dom"; 
import { useUserContext } from '../../Contexts/UserContext';
import { getTripById } from "../../utils/Trip";
import Map from "../Utils/Map"; 
import FloatingMenu from "../Utils/FloatingMenu";
import TripMenu from "./TripMenu"; 

const styleSheet = {

    fullPage: {
        width: "100%",
        height:"100%",
        display: "flex",
    }

}; 
const TripViewer = ()=>{
    const params = useParams(); 
    const [trip, setTrip] = useState(null);
    const {user,getIdToken} = useUserContext();
    const mapRef = useRef(null); 

    const swapStops = (stop1,stops2)=>{
        setTrip(t=>{

        })
    }

    useLayoutEffect(()=>{
        if(user)
            (async()=>setTrip(await getTripById(params.id,getIdToken())))(); 
    },[user]); 
    return (
        <div style={styleSheet.fullPage}>
            { trip ? (
                <div style={styleSheet.fullPage} >
                    <Map ref={mapRef} addMarkerArgs={
                        [trip.tripData.startLocation, ...trip.tripData.stops, trip.tripData.endLocation]
                    }/>
                    <FloatingMenu>
                        <TripMenu trip={trip} swapStops={swapStops}/>
                    </FloatingMenu>

                </div>
                
            ): (<h1>No trip selected</h1>)}  
        </div>
    ); 
}

export default TripViewer; 