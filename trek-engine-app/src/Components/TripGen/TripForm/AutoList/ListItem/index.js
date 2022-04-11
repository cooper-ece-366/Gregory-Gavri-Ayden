// Written by Gavri Kepets
import React from 'react';

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
        background: "#ffffff",
        color: "black",
        borderRadius: "10px",
        padding: "0px 10px",
        border: "2px solid #0014C4"
    },
    days: {
        fontSize: "1.25em",
    }
}

const ListItem = ({ name, removeItem }) => {

    const removeName = () => {
        removeItem(name);
    }

    return (
        <div key={name} style={styleSheet.container}>
            <div><p>{name}</p></div>
            <div><button onClick={removeName}>X</button></div>
        </div>
    )
}

export default ListItem;
