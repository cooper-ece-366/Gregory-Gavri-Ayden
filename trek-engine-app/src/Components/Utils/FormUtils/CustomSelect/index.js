// Written by Gavri Kepets
import React from 'react';

const styleSheet = {
    select: (fontSize) => {
        return ({
            fontSize: fontSize,
            height: "30px",
            borderRadius: "10px",
            background: `linear-gradient(90deg, #050D2B 0%, #050D2B 100%)`,
            border: "2px solid rgba(255, 255, 255, 0.1)",
            color: "white",
            height: "100%",
            lineHeight: "30px",
            width: "auto%",
        })
    },
    container: {
        height: "30px",
        width: "auto",
    },
    option: (fontSizeOption) => {
        return ({
            fontSize: fontSizeOption,
            borderRadius: "10px",
            backgroundColor: "#000000",
            border: "2px solid #00ecff",
        })
    }
}

const CustomSelect = ({ handleChange, options, fontSize = "1.25em", fontSizeOption = "1em", }) => {
    return (
        <div style={styleSheet.container}>
            <select style={styleSheet.select(fontSize)} onChange={handleChange}>
                {
                    options.map((option, index) => {
                        return (
                            <option style={styleSheet.option(fontSizeOption)} key={index} value={index}>{option}</option>
                        )
                    })
                }
            </select>
        </div>
    )
}

export default CustomSelect;