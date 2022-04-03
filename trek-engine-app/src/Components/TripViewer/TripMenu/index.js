import {useState} from 'react'; 
import DraggableList from './DraggableList';    
const TripMenu = ({trip, swapStops})=>{

    return (
        <div>
            <h1> {trip.meta.name}</h1>
            <h2> {trip.meta.description}</h2>
            <DraggableList swapStops = {swapStops} stops={[trip.tripData.startLocation, ...trip.tripData.stops,trip.tripData.endLocation]}/>
            
        </div>
    );    
}

export default TripMenu; 