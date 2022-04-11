import axios from "axios"; 
// import polyline from "@mapbox/polyline"; 
import { decode } from "@googlemaps/polyline-codec";


export const getDirection = async (origin, destination)=> {
    const {data} = await axios.get(`http://localhost:4567/api/v1/geo/direction?start=${origin}&end=${destination}`);  
    const result = data.map(x=>decode(x,5)).reduce((acc,x)=>[...acc,...x],[]).map(([first,second])=>[second,first]); 
    return result;
}

export const getLatLng = async (address)=> {
    const {data: {lng, lat}} = await axios.get(`http://localhost:4567/api/v1/geo/search?search=${address}`);  
    return [lng, lat];
}

export const getNearby = async (location,type,radius)=> {
    const {data} = await axios.get(`http://localhost:4567/api/v1/geo/nearby?locaton=${location}&type=${type}&radius=${radius}`);  
    return data;
}