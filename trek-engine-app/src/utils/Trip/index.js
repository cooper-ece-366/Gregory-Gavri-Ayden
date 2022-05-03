import axios from "axios";
import { useUserContext } from "../../Contexts/UserContext";

export const getTripById = async (trip_id, id_token) => {
    if (id_token) {
        console.log("getTripById: trip_id: ", trip_id);
        const { data: trip } = await axios.post("http://localhost:4567/api/v1/tripgen/getById", { trip_id, id_token });
        console.log(trip);
        return trip;
    }
    const { data: trip } = await axios.post("http://localhost:4567/api/v1/tripgen/getById", { trip_id });
    return trip;
};


export const updateTrip = async (trip, id_token) => {
    console.log(trip);
    trip.meta.updated = new Date(trip.meta.updated).getTime();
    trip.meta.created = new Date(trip.meta.created).getTime();
    trip.details.startDate = new Date(trip.details.startDate).getTime();
    const { data: response } = await axios.post("http://localhost:4567/api/v1/tripgen/update", { trip, id_token });
    return response;
}

export const insertTrip = async (trip, id_token) => {
    trip.meta.updated = new Date().getTime();
    trip.meta.created = new Date().getTime();
    trip.details.startDate = new Date().getTime();
    console.log(trip);
    const response = await axios.post("http://localhost:4567/api/v1/tripgen/insert", { trip, id_token });
    console.log(response);
    return response;
}