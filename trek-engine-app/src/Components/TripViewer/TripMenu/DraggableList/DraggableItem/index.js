import { useRef } from 'react'
import { useDrag, useDrop } from 'react-dnd'

const style = {
    border: '1px dashed gray',
    marginBottom: '.5rem',
    cursor: 'move',
    width: '100%',
}

const DraggableItem = ({ id, text, index, moveCard, dnd, item }) => {
    return (
        <li
            style={{ ...dnd.item.styles, ...dnd.handler.styles, ...style }}
            className={dnd.item.classes}
            ref={dnd.item.ref}
            {...dnd.handler.listeners}
        >
            {item.bigStop.name}
        </li>
    )
}

export default DraggableItem;