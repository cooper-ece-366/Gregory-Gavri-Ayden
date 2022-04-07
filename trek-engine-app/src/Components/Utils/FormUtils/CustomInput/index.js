// Written by Gavri Kepets
import TinyColor from 'tinycolor2';
import "./styles.css";

const styleSheet = {
    input: (color, width, height, fontSize) => {
        let darkColor = TinyColor(color).darken(40).toString();
        let lightColor = TinyColor(color).darken(20).toString();

        return ({
            width: width,
            fontSize: fontSize,
            background: "#ffff00",
            height: height,
            border: 0,
            outline: 0,
            color: "black",
            borderRadius: "8px",
        })
    }
}

const CustomInput = ({ type, onChange, inputColor = "red", width = "85%", fontSize = "16px", height = "30px" }) => {
    return (
        <input onChange={onChange} type={type} style={styleSheet.input(inputColor, width, height, fontSize)} className="customInput" />
    )
}

export default CustomInput; 