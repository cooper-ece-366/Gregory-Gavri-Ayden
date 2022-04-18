// Written by Gavri Kepets
import { useEffect, useRef, useState } from 'react';

const styleSheet = (fontSize) => ({
    container: {
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-between",
        fontSize,
        margin: "10px",
        width: "100%",
        overflow: "hidden",
    },
    input: {
        width: "100%",
        fontSize: fontSize,
        height: fontSize,
    },
    text: {
        fontSize: fontSize,
        margin: "0px",
        overflow: "hidden",
    },
    edit: {
        fontSize: "10px",
        position: "absolute",
        right: "10px",
    }
});

const EditableText = ({ text, setText, fontSize = "30px" }) => {
    const wrapperRef = useRef(null);
    const [isEdit, setIsEdit] = useState(false);
    // const [text, setText] = useState(text);

    const handleEditClick = () => {
        setIsEdit(true);
    }

    useEffect(() => {
        document.addEventListener("click", handleClickOutside, false);
        document.addEventListener("keydown", handleKeyDown, false);
    }, []);

    const handleClickOutside = (event) => {
        if (wrapperRef.current && !wrapperRef.current.contains(event.target)) {
            setIsEdit(false);
        }
    };

    const handleKeyDown = (e) => {
        if (e.key === "Enter") {
            setIsEdit(false);
        }
    }

    const changeText = (e) => {
        setText(e.target.value);
    }

    const getTextComponent = () => {
        if (isEdit) {
            return <input style={styleSheet(fontSize).input} autoFocus type="text" value={text} onChange={changeText}></input>
        } else {
            return <h1 style={styleSheet(fontSize).text}>{text}</h1>
        }
    }

    return (
        <div style={styleSheet(fontSize).container} ref={wrapperRef}>
            <div>{getTextComponent()}</div>
            <div><p style={styleSheet(fontSize).edit} onClick={handleEditClick}>edit</p></div>
        </div>
    )
}

export default EditableText;