import React, {useState} from 'react';

const styleSheet = {
    container : {
        width: "250px",
    },
    form : {
        display: "flex",
        flexDirection: "column",
    },
    duration: {
        display: "flex",
        flexDirection: "row"
    },
    textInput: {
        margin: "10px",
        padding: "10px",
    },
    startInput: {
        borderRadius: "10px",
        borderWidth: "1px",
        background: "linear-gradient(to right, #005000, #00ff00)",
        borderImageSlice: "1",
        color: "white",
    },
    endInput: {
        borderRadius: "10px",
        borderWidth: "1px",
        background: "linear-gradient(to right, #500000, #ff0000)",
        borderImageSlice: "1",
        color: "white",
    }    
}

const TripForm = (props) => {
    const [duration, setDuration] = useState(0); // 0: duration, 1: dates, 2: indefinite

    const handleDurationChange = (e) => {
        setDuration(e.target.value);
    }

    const getDurationMenu = () => {
        if (duration == 0) {
            return (
                <div>
                    <input type="number"placeholder='10'></input> days
                </div>
            )
        }
        else if (duration == 1) {
            return (
                <div>
                    <input type="date"></input>
                    <div>and end on...</div>
                    <input type="date"></input>
                </div>
            )
        }
    }

    return (
        <div style = {styleSheet.container}>
            <h1>Build Your Trip Here!</h1>
            <form style={styleSheet.form}>
                From
                <input type="text" placeholder="Start Location" style={{...styleSheet.startInput, ...styleSheet.textInput}}></input>
                To
                <input type="text" placeholder="End Location" style={{...styleSheet.endInput, ...styleSheet.textInput}}></input>
                <hr/>
                <select onChange={handleDurationChange}>
                    <option value="0">My Trip will take...</option>
                    <option value="1">My trip will start on...</option>
                </select>
                <div>
                    {getDurationMenu()}
                </div>
                
                Make sure I visit...
                <textarea placeholder="Type mandatory locations here"></textarea>
                I prefer to visit...
                <textarea placeholder="National Parks, Big Cities, Famous restaraunts..."></textarea>
                
                <button>Make my Trip!</button>
            </form>
            {/* Start Location | End Location | Trip Length | Advanced Options     */}
        </div>
    )
}

export default TripForm;