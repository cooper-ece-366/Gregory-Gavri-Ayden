// Written by Gavri Kepets
import React, { useState } from 'react';
import { WithContext as ReactTags } from 'react-tag-input';
import Multiselect from 'multiselect-react-dropdown';
import AutoComplete from "./AutoComplete"
import EditableText from './EditableText';
import CustomSelect from '../../Utils/CustomSelect';
import CustomInput from '../../Utils/CustomInput';

const styleSheet = {
    container: {
        width: "100%",
        background: "linear-gradient(180deg, #050D2B 0%, #010514 100%)",
        borderRadius: "10px",
        color: "white",
    },
    form: {
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        width: "100%",
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
    },
    text: {
        fontSize: "25px",
        marginTop: "20px",
        marginBottom: "5px",
    }
}

const TripForm = (props) => {
    const [duration, setDuration] = useState(0); // 0: duration, 1: dates, 2: indefinite
    const [from, setFrom] = useState("");
    const [to, setTo] = useState("");
    const [days, setDays] = useState(0);
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [required, setRequired] = useState([]);
    const [prefs, setPrefs] = useState([]);
    const [tripName, setTripName] = useState("Trip 1");

    const options = [
        { name: "National Parks", value: 0 },
        { name: "National Forests / Monuments", value: 0 },
        { name: "Big Cities", value: 1 },
        { name: "Famous Restaraunts", value: 2 },
        { name: "Museums", value: 3 },
    ];

    const handleDurationChange = (e) => {
        setDuration(e.target.value);
    }

    const getDurationMenu = () => {
        if (duration == 0) {
            return (
                <CustomInput type="number" onChange={handleDayChange}></CustomInput>
            )
        }
        else if (duration == 1) {
            return (
                <div>
                    <input type="date" onChange={handleStartDateChange}></input>
                    <div>and end on...</div>
                    <input type="date" onChange={handleEndDateChange}></input>
                </div>
            )
        }
    }

    const handleFormSubmit = async (e) => {
        e.preventDefault();

        let finalDays = days;

        if (duration === 1) {
            finalDays = Math.round((new Date(endDate) - new Date(startDate)) / (1000 * 60 * 60 * 24));
            setDays(finalDays);
        }

        let finalRequired = required.map(r => r.text);

        let data = {
            from,
            to,
            days: parseInt(finalDays),
            startDate,
            endDate,
            required: finalRequired,
            prefs
        }
    }

    const handleDayChange = (e) => setDays(e.target.value);

    const handleStartDateChange = (e) => setStartDate(e.target.value);

    const handleEndDateChange = (e) => setEndDate(e.target.value);

    const handleDelete = i => setRequired(required.filter((requirement, index) => index !== i));

    const handleAddition = requirement => {
        setRequired([...required, requirement]);
    };

    const handlePrefChange = (e) => {
        setPrefs(e.map(p => p.name));
    }

    return (
        <div style={styleSheet.container}>
            <EditableText text="Trip 1" />
            <form style={styleSheet.form} onSubmit={handleFormSubmit}>
                <div style={styleSheet.text}>From</div>
                <AutoComplete setName={setFrom} inputColor="#00ff00" />

                <div style={styleSheet.text}>To</div>
                <AutoComplete setName={setTo} inputColor="#ff0000" />
                <br />
                <CustomSelect handleChange={handleDurationChange} options={["My trip will be...", "My trip will start on..."]} />
                <div>
                    {getDurationMenu()}
                </div>

                Make sure I visit...
                <ReactTags
                    tags={required}
                    delimiters={[188, 13]}
                    handleDelete={handleDelete}
                    handleAddition={handleAddition}
                    inputFieldPosition="bottom"
                    allowDragDrop={false}
                    placeholder="Press enter to add!"
                />
                I prefer to visit...
                <Multiselect
                    options={options}
                    onSelect={handlePrefChange} // Function will trigger on select event
                    onRemove={handlePrefChange} // Function will trigger on remove event
                    displayValue="name" // Property name to display in the dropdown options
                />

                <button onClick={handleFormSubmit}>Make my Trip!</button>
            </form>
        </div>
    )
}

export default TripForm;