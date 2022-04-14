// Written by Gavri Kepets
import React from 'react';
import { useState } from 'react';
import AutoComplete from "../../../Utils/AutoComplete";
import ListItem from './ListItem';

const styleSheet = {
    container: {
        width: "100%",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        textAlign: "center",
        marginTop: "20px"
    },
    input: {
        fontSize: "1.15em",
        width: "25%",
        marginRight: "20px",
        background: "#ffffff",
        color: "black",
        borderRadius: "10px",
        padding: "0px 10px",
        border: "2px solid #0014C4"
    },
    scroll: {
        overflow: "auto",
        maxHeight: "200px",
        minHeight: "100px",
        background: "rgba(100, 100, 100, 0.25)",
    },
    mybutton: {
        width: "25%",
        minWidth: "100px",
        background: "#0055ff",
        borderRadius: "10px",
        color: "white",
        border: "none",
        fontSize: "0.75em",
    },
    buttonContainer: {
        margin: "10px",
    }
}

const AutoList = ({ items = [], setItems, addItem }) => {
    const [text, setText] = useState('');

    const handleTextChange = (e) => {
        setText(e);
    }

    const localAddItem = (e) => {
        if (!items.includes(e) && e != "" && e != null) {
            setItems([...items, e]);
        }
        setText('');
        inputRef.current.value = '';
    }

    const localRemoveItem = (e) => {
        setItems(items.filter(item => item !== e));
    }

    let inputRef = React.createRef();


    return (
        <div style={styleSheet.container}>
            <AutoComplete setName={handleTextChange} inputColor="#0000ff" inputRef={inputRef} setText={handleTextChange} />
            <div style={styleSheet.buttonContainer}><button style={styleSheet.mybutton} onClick={() => { localAddItem(text); setText(''); }}>Add</button></div>
            <div style={styleSheet.scroll}>
                {items.map((item, index) => {
                    return (
                        <ListItem key={index} name={item} removeItem={localRemoveItem} />
                    )
                })}
            </div>
        </div>
    )
}

export default AutoList;
