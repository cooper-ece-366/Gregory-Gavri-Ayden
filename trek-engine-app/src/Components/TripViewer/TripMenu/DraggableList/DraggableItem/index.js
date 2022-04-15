import {useState} from 'react'; 

const styleSheet = {
    container: (dragging)=> ({
        display: "flex",
        flexDirecton: "row",
        justifyContent: "space-between",
        border: `1px ${dragging ? "dashed":"solid"} black`,
        paddingTop:"1px",
        marginTop:"5px",
        cursor: "grab"
    }), 
    clicker: {
        cursor: "pointer",
    }
}; 


const DraggableItem = ({children, onDragStart,onDrop,onDragOver, onDragEnd,remove,index})=>{

    const [dragging, setDragging] = useState(false); 
    return (
        <div style = {styleSheet.container(dragging)}>
            <div draggable={true}
                className={`draggable ${index}`}
                onDragStart={(e)=>{
                    if(e.currentTarget.classList[1] === "draggable") return; 
                    setTimeout(()=>setDragging(true),1); // cheat to just change div without the dragged div
                    onDragStart(e); 
                }}
                onDragEnd={(e)=>{
                    setDragging(false); 
                    onDragEnd(e);
                }}
                onDragOver={(e)=>{
                    if(e.currentTarget.classList[1] === "draggable") return; 
                    onDragOver(e); 
                }}
                onDrop={e=>{
                    onDrop(e); 
                }}>
                    {!dragging ? children : <div></div>}
            </div>
            <div style = {styleSheet.clicker} onClick = {()=>remove(index)}>
                X
            </div>
        </div>
    );  
};


export default DraggableItem; 