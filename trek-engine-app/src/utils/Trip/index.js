import axios from "axios"; 
export const getTripById = async (trip_id, id_token) =>{
    if(id_token){
        console.log("getTripById: trip_id: ", trip_id);
        const {data: trip} = await axios.post("http://localhost:4567/api/v1/tripgen/getById",{trip_id,id_token});  
        console.log(trip); 
        return trip; 
    }
    const {data: trip} = await axios.post("http://localhost:4567/api/v1/tripgen/getById",{trip_id});   
    return trip; 
}; 