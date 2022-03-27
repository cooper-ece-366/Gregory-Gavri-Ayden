import axios from "axios"; 
// import polyline from "@mapbox/polyline"; 
import { decode, encode } from "@googlemaps/polyline-codec";


// TODO implement backend
export const getDirection = async (origin, destination)=> {
    const {data} = await axios.get(`http://localhost:4567/api/v1/geo/direction?start=${origin}&end=${destination}`);  
    const result = data.map(decode).reduce((acc,x)=>[...acc,...x],[]).map(([first,second])=>[second/100000,first/100000]); 
    console.log(result); 
    return result;
}

export const getLatLng = async (address)=> {
    const {data: {lng, lat}} = await axios.get(`http://localhost:4567/api/v1/geo/search?search=${address}`);  
    return [lng, lat];
}