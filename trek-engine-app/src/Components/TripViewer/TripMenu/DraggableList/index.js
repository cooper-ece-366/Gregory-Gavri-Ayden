import DraggableItem from "./DraggableItem";
const DraggableList = ({stops, swapStops})=>{

    const onDragStart = (e,key)=>{
        console.log("drag start");
        console.log(e); 
    }

    const onDragOver = (e,key)=>{
        e.preventDefault();
        
    }

    const onDrop = (e,key)=>{
        console.log("drop"); 
        if(e.currentTarget.className.includes("draggable")){
            console.log(e.currentTarget); 
            swapStops(parseInt(e.currentTarget.className.split(" ")[1]),key);
        }
    }

    return (
        <div>
            {stops.map(({name},i)=>(<DraggableItem index = {i} onDragStart={onDragStart} onDrop={onDrop} onDragOver={onDragOver}>{name}</DraggableItem>))}
        </div>
    )
}

export default DraggableList; 