import {useState, useEffect} from 'react'; 
import AutoComplete from '../../Utils/AutoComplete';
import EditableText from '../../Utils/EditableText';
import DraggableList from './DraggableList';    
import {getLoc} from "../../../utils/GeoLocation";

const TripMenu = ({trip, swapStops,addTrip,changeName,changeDescription,submit,remove, editable})=>{
    const [addLocation, setAddLocation] = useState("");

    const styleSheet = {
        container: {
            width: "100%",
            height: "100%",
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            margin: "5px",
        },
        add: {
            width: "100%",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
        },
        addButton: {
            margin: "5px",
            padding: "5px",
            borderRadius: "5px",
            border: "none",
            cursor: "pointer",
        },
    }

    // TODO: In backend, check if user added a curated stop or a custom stop.
    const addLoc = async () => {
        let location = await getLoc(addLocation);
        console.log(location);
        addTrip({bigStop:{name:addLocation, lat:location.lat, lng:location.lng}});
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
            <br/>
            <div style={styleSheet.add}>{editable && <AutoComplete inputColor="grey" placeholder="Add a Stop" setName={setAddLocation} clearOnSelect={true}/> }<button style={styleSheet.addButton} onClick={addLoc}>Add Place</button></div>
            {editable && <button onClick={submit}>Update Button</button>}
        </div>
    );    
}

export default TripMenu; 