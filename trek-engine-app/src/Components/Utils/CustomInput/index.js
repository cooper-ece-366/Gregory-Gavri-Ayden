// Written by Gavri Kepets
import TinyColor from 'tinycolor2';
import { useState } from 'react';

const styleSheet = {
    dropdown: {
        position: "absolute",
        zIndex: 1000,
        borderRadius: "10px",
        boxShadow: "0 4px 10px -2px #000000",
        width: "100%",
        marginTop: "40px",
        width: "85%"
    },
    dropdownElement: {
        width: "5%",
    },
    input: (color, width) => {
        let darkColor = TinyColor(color).darken(40).toString();
        let lightColor = TinyColor(color).darken(20).toString();

        return ({
            width: width,
            fontSize: "16px",
            background: `linear-gradient(91.28deg, ${lightColor} 0%, ${darkColor} 100%)`,
            borderRadius: "10px",
            height: "30px",
            border: `2px solid ${color}`,
            color: "white",
        })
    },
    container: {
        width: "100%",
        display: "flex",
        justifyContent: "center",
        textAlign: "center",
    },
    parent: {
        width: "100%",
    }
}

const AutoComplete = ({ type, setName, onChange, inputColor = "red", width = "85%" }) => {

    const [address, setAddress] = useState('');

    const handleChange = ad => setAddress(ad);


    const handleSelect = async ad => {
        setAddress(ad);
        setName(ad);
    }

    return (
        <div style={styleSheet.parent}>
            <input onChange={onChange} type={type} style={styleSheet.input(inputColor, width)} />
            <div>Days</div>
        </div>
    )
}

export default AutoComplete; 