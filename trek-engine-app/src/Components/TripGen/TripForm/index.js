// Written by Gavri Kepets
import React, { useEffect, useState } from 'react';
import Multiselect from 'multiselect-react-dropdown';
import AutoComplete from "../../Utils/AutoComplete";
import EditableText from './EditableText';
import CustomSelect from '../../Utils/FormUtils/CustomSelect';
import DaysInput from './DaysInput';
import AutoList from './AutoList';
import { insertNewTrip } from '../../../utils/Trip';
import "./styles.css";
// import AutoComplete from "../../Utils/AutoComplete"
import axios from "axios";
import { insertTrip } from '../../../utils/Trip';
import { useUserContext } from '../../../Contexts/UserContext';
import { useNavigate } from 'react-router-dom';


const styleSheet = {
    container: {
        width: "100%",
        margin: "5px",
        color: "white",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
    },
    form: {
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        width: "100%",
        overflow: "auto",
        overflowX: "hidden",
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
        fontSize: "1.5em",
        marginTop: "20px",
        marginBottom: "5px",
    },
    submit: {
        minWidth: "200px",
        maxWidth: "300px",
        margin: "20px",
        width: "80%",
        backgroundColor: "rgba(222, 45, 22, 0.5)",
        color: "white",
        background: "#DE2D16",
        border: "4px solid #781C10",
        boxSizing: "border-box",
        boxShadow: "0px 4px 4px rgba(0, 0, 0, 0.25)",
        borderRadius: "25px",
        fontSize: "1.25em",
        fontFamily: "'Sen', sans-serif",
        cursor: "pointer",
        padding: "10px",
    },
    greysubmit: {
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
        cursor: "not-allowed",
        padding: "10px",
        opacity: "0.5",
        padding: "5px",
    }
}

const TripForm = (props) => {
    const { user, getIdToken } = useUserContext();
    const [duration, setDuration] = useState(0); // 0: duration, 1: dates, 2: indefinite
    const [from, setFrom] = useState("");
    const [to, setTo] = useState("");
    const [days, setDays] = useState(0);
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [required, setRequired] = useState([]);
    const [prefs, setPrefs] = useState([]);
    const [name, setName] = useState("Trip 1");
    const [submittable, setSubmittable] = useState(false);
    const navigate = useNavigate();
    const mapRef = props.mapRef;

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

    useEffect(() => {
        console.log("HERER");
        if (days > 0 && from != "" && to != "") {
            setSubmittable(true);
        }
        else {
            setSubmittable(false);
        }
    }, [days, from, to]);

    const handleFormSubmit = async (e) => {
        e.preventDefault();
        console.log(user);
        let finalDays = days;

        if (duration === 1) {
            finalDays = Math.round((new Date(endDate) - new Date(startDate)) / (1000 * 60 * 60 * 24));
            setDays(finalDays);
        }
        let startLocation = from.split(",")[0];
        let endLocation = to.split(",")[0];
        let stops = required.map(stop => stop.split(",")[0]);

        let data = {
            details: {
                tripLength: days,
                tags: prefs.map(pref => {
                    return { tag: pref, weight: 1 };
                }),
            },
            meta: {
                name: name,
                description: "",
                private: false,
            },
            trip: {
                endLocation: endLocation,
                startLocation: startLocation,
                stops: [
                    startLocation,
                    ...stops,
                    endLocation
                ]
            }
        }
        console.log("SUBMITTED");
        console.log(data);

        let id_token = await getIdToken();

        let response = await insertTrip(data, id_token);
        navigate("/triprecs/" + response.data);
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
                    onSelect={handlePrefChange}
                    onRemove={handlePrefChange}
                    displayValue="name"
                />
                <button style={submittable ? styleSheet.submit : styleSheet.greysubmit} onClick={handleFormSubmit}>Make my Trip!</button>
            </form>
        </div>
    )
}

export default TripForm;