import {useState} from 'react'; 
import DraggableItem from "./DraggableItem";
const DraggableList = ({stops, swapStops, remove})=>{

    const [startPos,setStartPos] = useState(null); 
    const [endPos, setEndPos] = useState(null); 

    const onDragStart = (e)=>{
        console.log("drag start");
        console.log(parseInt(e.target.classList[1]));
        setStartPos(parseInt(e.target.classList[1]));  
    }

    const onDragOver = (e)=>{
        e.preventDefault();
        setEndPos(parseInt(e.target.classList[1]));
    }

    const onDrop = (e)=>{
    }


    const onDragEnd = (e)=>{
        swapStops(startPos,endPos);
        setStartPos(null);
        setEndPos(null);
    }

    return (
        <div>
            {stops.map(({name},i)=>(<DraggableItem index = {i} 
                onDragStart={onDragStart} 
                onDrop={onDrop} 
                onDragOver={onDragOver}
                onDragEnd={onDragEnd}
                remove={remove}>{name}</DraggableItem>))}
        </div>
    )
}

export default DraggableList; 