// Written By Gavri Kepets
import { useCallback, useState } from 'react'
import DraggableItem from './DraggableItem'
import DnDList from 'react-dnd-list';

const style = {
    width: "85%",
    userSelect: "none",
    listStyle: "none",
    padding: "0",
    backgroundColor: "rgba(255,255,255,0.25)",
}

const DraggableList = ({ stops, swapStops }) => {
    return (
        <ul style={style}>
            <DnDList
                items={stops}
                itemComponent={DraggableItem}
                setList={swapStops}
                disableTransitions={true}
            />
        </ul>
    );
}

export default DraggableList;