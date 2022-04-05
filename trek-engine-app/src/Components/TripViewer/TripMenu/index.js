import {useState, useEffect} from 'react'; 
import AutoComplete from '../../Utils/AutoComplete';
import EditableText from '../../Utils/EditableText';
import DraggableList from './DraggableList';    
import {getLoc} from "../../../utils/GeoLocation";
const TripMenu = ({trip, swapStops,addTrip,changeName,changeDescription,submit,remove, editable})=>{

    const addLoc = async (address)=>{
        const loc = await getLoc(address); 
        addTrip(loc); 
    }


    return (
        <div>
            {editable ? <EditableText text={trip.meta.name} setText={changeName}/> : <h1>{trip.meta.name}</h1>}
            {editable ? <EditableText text={trip.meta.description} setText={changeDescription}/> : <h1>{trip.meta.description}</h1>}
            {editable ? 
                <DraggableList remove = {remove} swapStops = {swapStops} stops={[trip.tripData.startLocation, ...trip.tripData.stops,trip.tripData.endLocation]}/> 
                : 
                <ol>{[trip.tripData.startLocation, ...trip.tripData.stops, trip.tripData.endLocation].map(({
                    name,
                })=><li>{name}</li>)}</ol>
            }
            {editable && <AutoComplete placeholder="Add a Stop" setName={addLoc} clearOnSelect={true}/> }
            {editable && <button onClick={submit}>Update Button</button>}
        </div>
    );    
}

export default TripMenu; 