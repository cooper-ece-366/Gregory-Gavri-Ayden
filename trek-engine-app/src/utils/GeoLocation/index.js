import axios from "axios"; 
import polyline from "polyline"; 

// TODO implement backend
export const getDirection = async (origin, destination)=> {
    const {data} = await axios.get(`http://localhost:4567/api/v1/geo/direction?start=${origin}&end=${destination}`);  
    return polyline.decode(data).reverse();
}

export const getLatLng = async (address)=> {
    const {data: {lng, lat}} = await axios.get(`http://localhost:4567/api/v1/geo/search?search=${address}`);  
    return [lng, lat];
}