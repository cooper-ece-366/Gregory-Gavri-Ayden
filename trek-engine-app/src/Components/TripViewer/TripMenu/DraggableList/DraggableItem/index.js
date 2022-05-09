import { useRef } from 'react'
import { useDrag, useDrop } from 'react-dnd'

const style = {
    paddingTop: '0.5rem',
    paddingBottom: '0.5rem',
    cursor: 'move',
    width: '100%',
    borderBottom: '1px solid #eee',
}

const DraggableItem = ({ id, text, index, moveCard, dnd, item }) => {
    return (
        <li
            style={{ ...dnd.item.styles, ...dnd.handler.styles, ...style }}
            className={dnd.item.classes}
            ref={dnd.item.ref}
            {...dnd.handler.listeners}
            key={item.bigStop.name}
        >
            {item.bigStop.name}
        </li>
    )
}

export default DraggableItem;