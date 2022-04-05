import {useState} from 'react'; 

const styleSheet = {
    container: {
        display: "flex",
        flexDirecton: "row",
        justifyContent: "space-between",
    }, 
    clicker: {
        cursor: "pointer",
    }

}; 


const DraggableItem = ({children, onDragStart,onDrop,onDragOver, onDragEnd,remove,index})=>{
    


    return (
        <div style = {styleSheet.container}>
            <div draggable={true}
                className={`draggable ${index}`}
                onDragStart={(e)=>{
                    if(e.currentTarget.classList[1] === "draggable") return; 
                    onDragStart(e); 
                }}
                onDragEnd={(e)=>{
                    onDragEnd(e);
                }}
                onDragOver={(e)=>{
                    if(e.currentTarget.classList[1] === "draggable") return; 
                    onDragOver(e); 
                }}
                onDrop={e=>{
                    onDrop(e); 
                }}>
                    {children}
            </div>
            <div style = {styleSheet.clicker} onClick = {()=>remove(index)}>
                -
            </div>
        </div>
    );  
};


export default DraggableItem; 