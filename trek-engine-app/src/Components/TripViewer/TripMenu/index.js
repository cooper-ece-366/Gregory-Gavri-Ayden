import {useState, useEffect} from 'react'; 
import AutoComplete from '../../Utils/AutoComplete';
import EditableText from '../../Utils/EditableText';
import DraggableList from './DraggableList';    
import {getLoc} from "../../../utils/GeoLocation";

const TripMenu = ({trip, swapStops,addTrip,changeName,changeDescription,submit,remove, editable})=>{

    const styleSheet = {
        container: {
            width: "100%",
            height: "100%",
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            margin: "5px",
        }
    }

    const addLoc = async (address)=>{
        const loc = await getLoc(address); 
        addTrip(loc); 
    }

    const getBigStops = () => {
        return trip.tripData.stops.map(stop => stop.bigStop);
    }


    return (
        <div style={styleSheet.container}>
            {editable ? <EditableText text={trip.meta.name} setText={changeName}/> : <h1>{trip.meta.name}</h1>}
            {editable ? <EditableText fontSize="10px" text={trip.meta.description} setText={changeDescription}/> : <h1>{trip.meta.description}</h1>}
            {editable ? 
                <DraggableList remove = {remove} swapStops = {swapStops} stops={getBigStops()}/> 
                : 
                <ol>{[getBigStops()].map(({
                    name,
                })=><li>{name}</li>)}</ol>
            }
            {editable && <AutoComplete placeholder="Add a Stop" setName={addLoc} clearOnSelect={true}/> }
            {editable && <button onClick={submit}>Update Button</button>}
        </div>
    );    
}

export default TripMenu; 