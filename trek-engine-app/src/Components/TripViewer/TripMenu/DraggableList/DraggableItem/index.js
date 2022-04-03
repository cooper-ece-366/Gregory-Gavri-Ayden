import {useState} from 'react'; 

const DraggableItem = ({children, onDragStart,onDrop,onDragOver,index:key})=>{
    
    const [isVisible, setIsVisible] = useState(true); 
    return <div draggable={true}
        className={`draggable ${key}`}
        onDragStart={(e)=>{
            setIsVisible(false);
           onDragStart(e,key); 
        }}
        onDrop={(e)=>onDrop(e,key)}
        onDragOver={(e)=>{
            setIsVisible(true);
            onDragOver(e,key); 
        }}>
            {children}
    </div>
};


export default DraggableItem; 