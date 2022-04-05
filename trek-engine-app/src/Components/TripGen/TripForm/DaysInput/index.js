// Written by Gavri Kepets
import React from 'react';
import { useState } from 'react';

const styleSheet = {
    container: {
        width: "100%",
        display: "flex",
        justifyContent: "center",
        textAlign: "center",
    },
    input: {
        fontSize: "16px",
        width: "50%",
        marginRight: "20px",
    }
}

const DaysInput = (props) => {
    const [text, setText] = useState(props.text);

    return (
        <div style={styleSheet.container}>
            <input style={styleSheet.input} autoFocus type="number" value={text} onChange={props.onChange}></input>
            <div>Days</div>
        </div>
    )
}

export default DaysInput;