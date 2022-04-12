const fs = require('fs');
const stopsFilePath = './stops.json';

const axios = require("axios"); 
const csv = require('csvtojson');
const csvFilePath = './data.csv';

const stops = [];
const stopSet = new Set();

const nearby = async (location,type,radius)=>axios.get(`http://localhost:4567/api/v1/geo/nearby?location=${location}&type=${type}&radius=${radius}`);
const search = async (address)=>axios.get(`http://localhost:4567/api/v1/geo/search?address=${address}`);

const p_types = ["park",
                    "natural_feature",
                    "landmark",
                    "museum",
                    "art_gallery",
                    "lodging",
                    "rv_park",
                    "campground",
                    "casino",
                    "zoo",
                    "tourist_attraction",
                    "stadium",
                    "night_club",
                    "amusement_park",
                    "restaurant"
                ];

const insert = (obj)=>{
    if (stopSet.has(obj.name)) return; 
    stopSet.add(obj.name); 
    stops.push(obj);
}

const getTag = (p_type)=>{
    if (p_type == 'natural_feature' || p_type == 'park' || p_type == 'national_park') return 'nature';
    if (p_type == 'museum' || p_type == 'art_gallery') return 'landmark';
    if (p_type == 'rv_park' || p_type == 'campground') return 'lodging';
    if (p_type == 'casino' || p_type == 'zoo' || p_type == 'tourist_attraction' || p_type == 'stadium' || p_type == 'night_club' || p_type == 'amusement_park') return 'entertainment';
    return p_type;
}

const getStop = (obj,tag1)=>{
    const stop = {
        name: obj.name,
        lat: obj.lat,
        lng: obj.lng,
        g_types: obj.types,
        tag: getTag(tag1)
    }
    return stop;
}

const generateStops = async (obj)=>{
    
    for (let i = 0; i < 1; i++) {
        const {data} = await search(obj[i].name); 
        insert(getStop(data, obj[i].type));

        let latlng = obj[i].lat.toString().concat(" ").concat(obj[i].lng.toString());

        for(let j = 0; j < p_types.length; j++){
            const {data} = await nearby(latlng, p_types[j],"50000"); 

            for(let k = 0; k < data.length; k++){
                insert(getStop(data[k],p_types[j])); 
            }
        }
    }
    return(stops);
}

(async ()=>{
    const jsonObj = await csv().fromFile(csvFilePath); 
    fs.writeFileSync(stopsFilePath,JSON.stringify(await generateStops(jsonObj))); 
})();
