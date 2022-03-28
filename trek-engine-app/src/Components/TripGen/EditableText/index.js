// Written by Gavri Kepets
import React, { useEffect } from 'react';
import { useRef, useState } from 'react';

const styleSheet = {
    container: {
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-between",
        fontSize: "15px",
        margin: "10px",
    },
    input: {
        width: "100%",
        fontSize: "30px",
        height: "30px",
    },
    text: {
        fontSize: "30px",
        margin: "0px"
    },
    edit:{
        fontSize: "10px",
    }
}

const EditableText = (props) => {
    const wrapperRef = useRef(null);
    const [isEdit, setIsEdit] = useState(false);
    const [text, setText] = useState(props.text);

    const handleEditClick = () => {
        setIsEdit(true);
        console.log("edit clicked");
    }

    useEffect(() => {
        document.addEventListener("click", handleClickOutside, false);
        // add listerner for enter key
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
            return <input style={styleSheet.input} type="text" value={text} onChange={changeText}></input>
        } else {
            return <h1 style={styleSheet.text}>{text}</h1>
        }
    }

    return (
        <div style={styleSheet.container} ref={wrapperRef}>
            <div>{getTextComponent()}</div>
            <div><p style={styleSheet.edit} onClick={handleEditClick}>edit</p></div>
        </div>
    )
}

export default EditableText;