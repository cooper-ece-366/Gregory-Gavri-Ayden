// Written by Gavri Kepets
import React, { useState } from 'react';
import Multiselect from 'multiselect-react-dropdown';
import AutoComplete from "../../Utils/AutoComplete";
import EditableText from './EditableText';
import CustomSelect from '../../Utils/FormUtils/CustomSelect';
import DaysInput from './DaysInput';
import AutoList from './AutoList';
import "./styles.css";
// import AutoComplete from "../../Utils/AutoComplete"
import axios from "axios";
import { insertTrip } from '../../../utils/Trip';
import { useUserContext } from '../../../Contexts/UserContext';

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
    text: {
        fontSize: "25px",
        marginTop: "20px",
        marginBottom: "5px",
    },
    submit: {
        position: "absolute",
        bottom: "0",
        marginBottom: "20px",
        minWidth: "100px",
        maxWidth: "300px",
        width: "80%",
        fontSize: "1.5em",
        backgroundColor: "rgba(222, 45, 22, 0.5)",
        color: "white",
        background: "#DE2D16",
        border: "4px solid #781C10",
        boxSizing: "border-box",
        boxShadow: "0px 4px 4px rgba(0, 0, 0, 0.25)",
        borderRadius: "25px",
        fontSize: "1.5em",
        fontFamily: "'Sen', sans-serif",
        cursor: "pointer",
        padding: "10px",
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
    const [name, setName] = useState("Trip 1");
    const { user, getIdToken } = useUserContext();

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
                <DaysInput type="number" onChange={handleDayChange}></DaysInput>
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

        // let data = {
        //     details: {
        //         tripLength: days,
        //         tags: prefs,
        //         startDate
        //     },
        //     meta: {
        //         name,
        //         description: "",
        //         private: false,
        //     },
        //     trip: {
        //         endLocation: to,
        //         startLocation: from,
        //         stops: [
        //             from,
        //             ...required,
        //             to
        //         ]
        //     }
        // };

        let data = {
            details: {
                tripLength: 10,
                tags: [{ tag: "national_parks", weight: 1 }],
            },
            meta: {
                name: "trip" + Math.floor(Math.random() * 1000),
                description: "",
                private: false,
            },
            trip: {
                endLocation: "FAKELA",
                startLocation: "FAKENew York",
                stops: [
                    "FAKENew York",
                    "FAKEChicago",
                    "FAKELA"
                ]
            }
        }

        let id_token = await getIdToken();

        insertTrip(data, id_token);
    }

    const handleDayChange = (e) => setDays(e.target.value);

    const handleStartDateChange = (e) => setStartDate(e.target.value);

    const handleEndDateChange = (e) => setEndDate(e.target.value);

    const handlePrefChange = (e) => {
        setPrefs(e.map(p => p.name));
    }

    const addRequired = (e) => {
        setRequired(e);
    }

    return (
        <div style={styleSheet.container}>
            <EditableText startText="Trip 1" onChange={setName} />
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
                <br />
                <div style={styleSheet.text}>Make sure I visit...</div>
                <AutoList items={required} setItems={addRequired}></AutoList>
                <div style={styleSheet.text}>I prefer to visit...</div>
                <Multiselect
                    options={options}
                    onSelect={handlePrefChange} // Function will trigger on select event
                    onRemove={handlePrefChange} // Function will trigger on remove event
                    displayValue="name" // Property name to display in the dropdown options
                />

                <button style={styleSheet.submit} onClick={handleFormSubmit}>Make my Trip!</button>
            </form>
        </div>
    )
}

export default TripForm;