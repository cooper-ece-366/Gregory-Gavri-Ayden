import { useState, useRef, useLayoutEffect} from 'react'; 
import { useParams } from "react-router-dom"; 
import { useUserContext } from '../../Contexts/UserContext';
import { getTripById , updateTrip} from "../../utils/Trip";
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

    const swapStops = (stops1,stops2)=>{
        console.log(stops1,stops2); 
        setTrip(t=>{
            const newStops = [t.tripData.startLocation, ...(t.tripData.stops), t.tripData.endLocation];
            const [temp] = newStops.splice(stops1,1); 
            newStops.splice(stops2,0,temp); 
            return {...t, tripData:{startLocation: newStops[0], endLocation: newStops[newStops.length-1], stops: newStops.slice(1,-1)}};
        }); 
    }

    const addTrip = loc=>setTrip(t=>({...t, tripData: {...t.tripData, stops:[...t.tripData.stops, loc]}})); 

    const changeName = name=>setTrip(t=>({...t, meta:{...t.meta, name}}));

    const changeDescription = description=>setTrip(t=>({...t, meta:{...t.meta, description}}));

    const remove = (key)=>setTrip(t=>{
        const newStops = [t.tripData.startLocation, ...t.tripData.stops, t.tripData.endLocation]; 
        newStops.splice(key,1); 
        return {...t, tripData:{startLocation: newStops[0], endLocation: newStops[newStops.length-1], stops: newStops.slice(1,-1)}};
    }); 

    const submit = async ()=>{
        const id_token = await getIdToken(); 
        const res = await updateTrip(trip,id_token); 
        alert(res); 
    }
    

    useLayoutEffect(()=>{
        if(user)
            (async()=>setTrip(await getTripById(params.id,getIdToken())))(); 
        else
            (async()=>setTrip(await getTripById(params.id)))(); 
    },[user]); 
    return (
        <div style={styleSheet.fullPage}>
            { trip ? (
                <div style={styleSheet.fullPage} >
                    <Map ref={mapRef} addMarkerArgs={
                        // [trip.tripData.startLocation, ...trip.tripData.stops, trip.tripData.endLocation]
                        trip.tripData.stops.map(({bigStop})=>bigStop)
                    }
                    addPathArgs={
                        [{
                            stops: trip.tripData.stops.map(({bigStop})=>bigStop),
                            id: trip.meta.name
                        }]
                    }/>
                    <FloatingMenu>
                        <TripMenu 
                            trip={trip}
                            swapStops={swapStops}
                            addTrip={addTrip}
                            changeName={changeName}
                            changeDescription={changeDescription}
                            remove={remove}
                            editable={user?.email === trip.meta.user}
                            submit={submit}/>
                    </FloatingMenu>

                </div>
                
            ): (<h1>No trip selected</h1>)}  
        </div>
    ); 
}

export default TripViewer; 