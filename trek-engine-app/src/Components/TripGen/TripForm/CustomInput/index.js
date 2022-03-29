// Written by Gavri Kepets
import React from 'react';
import {  useState } from 'react';

const styleSheet = {
    input: {
        width: "100%",
        fontSize: "30px",
        height: "30px",
    }
}

const CustomInput = (props) => {
    const [text, setText] = useState(props.text);

    return (
        <input style={styleSheet.input} autoFocus type="text" value={text} onChange={(e) => {setText(e.target.value)}}></input>
    )
}

export default CustomInput;