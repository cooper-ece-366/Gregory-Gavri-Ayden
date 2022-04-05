// Written by Gavri Kepets
import React from 'react';

const styleSheet = {
    select: {
        fontSize: "1.5em",
        height: "30px",
        borderRadius: "10px",
        background: `linear-gradient(90deg, #050D2B 0%, #050D2B 100%)`,
        border: "2px solid rgba(255, 255, 255, 0.1)",
        color: "white",
        height: "100%",
        lineHeight: "30px",
        width: "auto%",
    },
    container: {
        height: "30px",
        width: "auto",
    },
    option: {
        fontSize: "16px",
        borderRadius: "10px",
        backgroundColor: "#000000",
        border: "2px solid #00ecff",
    }
}

const CustomSelect = (props) => {
    return (
        <div style={styleSheet.container}>
            <select style={styleSheet.select} onChange={props.handleChange}>
                {
                    props.options.map((option, index) => {
                        return (
                            <option style={styleSheet.option} key={index} value={index}>{option}</option>
                        )
                    })
                }
            </select>
        </div>
    )
}

export default CustomSelect;