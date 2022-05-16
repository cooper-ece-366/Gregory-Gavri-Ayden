// Written by Gavri Kepets
import React from 'react';
import { useState } from 'react';

const styleSheet = {
    container: {
        width: "100%",
        display: "flex",
        justifyContent: "center",
        textAlign: "center",
        marginTop: "20px"
    },
    input: {
        fontSize: "1.25em",
        width: "25%",
        marginRight: "20px",
        background: "rgba(100, 100, 100, 0.25)",
        color: "white",
        borderRadius: "10px",
        padding: "0px 10px",
        border: "2px solid #0014C4"
    },
    days: {
        fontSize: "1.25em",
    }
}

const DaysInput = (props) => {
    const [text, setText] = useState(props.text);

    return (
        <div style={styleSheet.container}>
            <input style={styleSheet.input} autoFocus type="number" value={text} onChange={props.onChange}></input>
            <div style={styleSheet.days}>Days</div>
        </div>
    )
}

export default DaysInput;