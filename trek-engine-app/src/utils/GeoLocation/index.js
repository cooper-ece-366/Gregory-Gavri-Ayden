import axios from "axios"; 
import { decode } from "@googlemaps/polyline-codec";


export const getDirection = async (stops)=> {
    console.log("stops", stops); 
    const stuff = await axios.post(`http://localhost:4567/api/v1/geo/directions`,{stops});  
    console.log(stuff); 
    const {data: {polylines}} = stuff; 
    const result = polylines.map(x=>decode(x,5)).reduce((acc,x)=>[...acc,...x],[]).map(([first,second])=>[second,first]); 
    console.log(result); 
    return result;
}

export const getLatLng = async (address)=> {
    const {data: {lng, lat}} = await axios.get(`http://localhost:4567/api/v1/geo/search?address=${address}`);  
    return [lng, lat];
}

export const getLoc = async (address)=> {
    const {data: {lng, lat,types="Unknown", name}} = await axios.get(`http://localhost:4567/api/v1/geo/search?address=${address}`);  
    console.log(address, lng, lat,types,name);
    return {lng, lat, type: types[0], name:address};
}