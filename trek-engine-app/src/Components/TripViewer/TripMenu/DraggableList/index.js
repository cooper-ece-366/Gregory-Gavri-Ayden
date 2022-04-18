import { useCallback, useState } from 'react'
import DraggableItem from './DraggableItem'
import DnDList from 'react-dnd-list';

const style = {
    // width: "100%",
    userSelect: "none",
}

const DraggableList = ({ stops, swapStops }) => {
    console.log(stops);
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