// Written by Gavri Kepets and Greg Presser
import { useState, useRef, useLayoutEffect } from 'react';
import { useParams } from "react-router-dom";
import { useUserContext } from '../../Contexts/UserContext';
import { getTripById, updateTrip } from "../../utils/Trip";
import Map from "../Utils/Map";
import FloatingMenu from "../Utils/FloatingMenu";
import TripMenu from "./TripMenu";

const styleSheet = {

    fullPage: {
        width: "100%",
        height: "100%",
        display: "flex",
    },
    invalid: {
        position: "absolute",
        top: "50%",
        left: "50%",
        transform: "translate(-50%,-50%)",
        width: "100%",
        height: "100%",
    },
    removeModal: {
        position: "absolute",
        width: "50%",
        height: "50%",
        backgroundColor: "white",
        top: "50%",
        left: "50%",
        minWidth: "200px",
        minHeight: "150px",
        maxWidth: "400px",
        maxHeight: "300px",
        transform: "translate(-50%,-50%)",
        zIndex: "100",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        textAlign: "center",
        borderRadius: "10px",
        boxShadow: "0px 0px 50px 5px black",
    },
    modalButton: {
        margin: "10px",
        padding: "10px",
        borderRadius: "5px",
        border: "none",
        cursor: "pointer",
    }
};

const TripViewer = () => {
    const params = useParams();
    const [trip, setTrip] = useState(null);
    const { user, getIdToken } = useUserContext();
    const mapRef = useRef(null);
    const [isRemove, setIsRemove] = useState(false);
    const [removeKey, setRemoveKey] = useState(null);

    const swapStops = (newOrder) => {
        let startLocation = newOrder[0];
        let endLocation = newOrder[newOrder.length - 1];
        let newTrip = { ...trip, tripData: { startLocation, endLocation, stops: newOrder } };
        setTrip(newTrip);
    }

    const addTrip = loc => setTrip(t => ({ ...t, tripData: { ...t.tripData, stops: [...t.tripData.stops, loc] } }));

    const changeName = name => setTrip(t => ({ ...t, meta: { ...t.meta, name } }));

    const changeDescription = description => setTrip(t => ({ ...t, meta: { ...t.meta, description } }));

    const remove = (key) => {
        setIsRemove(true);
        setRemoveKey(key);
    };

    const removeLocationByKey = () => {
        setTrip(t => {
            const newStops = t.tripData.stops;
            newStops.splice(removeKey, 1);
            return { ...t, tripData: { startLocation: newStops[0], endLocation: newStops[newStops.length - 1], stops: newStops } };
        });
        setIsRemove(false);
        setRemoveKey(null);
    }

    const closeModal = () => {
        setIsRemove(false);
        setRemoveKey(null);
    }

    const submit = async () => {
        const id_token = await getIdToken();
        const res = await updateTrip(trip, id_token);
        alert(res);
    }

    const getBigStops = () => {
        return trip.tripData.stops.map(stop => stop.bigStop);
    }


    useLayoutEffect(() => {
        if (user)
            (async () => setTrip(await getTripById(params.id, getIdToken())))();
        else
            (async () => setTrip(await getTripById(params.id)))();
    }, [user]);


    return (
        <div style={styleSheet.fullPage}>
            {trip ? (
                <div style={styleSheet.fullPage} >
                    {isRemove && <div style={styleSheet.removeModal}><h1>Are you sure you want to remove this stop?</h1><div><button style={styleSheet.modalButton} onClick={removeLocationByKey}>Yes</button><button style={styleSheet.modalButton} onClick={closeModal}>No</button></div></div>}
                    <Map ref={mapRef} addMarkerArgs={trip.tripData.stops}
                        addPathArgs={
                            [{
                                stops: getBigStops(),
                                id: trip.meta.name
                            }]
                        } />
                    <FloatingMenu>
                        <TripMenu
                            trip={trip}
                            swapStops={swapStops}
                            addTrip={addTrip}
                            changeName={changeName}
                            changeDescription={changeDescription}
                            remove={remove}
                            editable={user?.email === trip.meta.user || !trip.meta.private}
                            submit={submit} />
                    </FloatingMenu>

                </div>

            ) : (<h1>Looking for Trip...</h1>)}
        </div>
    );
}

export default TripViewer; 