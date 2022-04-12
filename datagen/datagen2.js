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
    if (p_type == 'natural_feature' || p_type == 'park') return 'nature';
    if (p_type == 'museum' || p_type == 'art_gallery') return 'landmark';
    if (p_type == 'rv_park' || p_type == 'campground') return 'lodging';
    if (p_type == 'casino' || p_type == 'zoo' || p_type == 'tourist_attraction' || p_type == 'stadium' || p_type == 'night_club' || p_type == 'amusement_park') return 'entertainment';
    return p_type;
}

const generateStops = async (obj)=>{
    
    for (let i = 0; i < 2; i++) {
        console.log(obj[i].name);
        const {d} = await search(obj[i].name); 
        city_park_stop = {
            name: obj[i].name,
            lat: obj[i].lat,
            lng: obj[i].lng,
            g_types: d.types,
            tag: obj[i].tag
        }
        insert(city_park_stop);
        console.log(city_park_stop);
        let latlng = obj[i].lat.toString().concat(" ").concat(obj[i].lng.toString());

        for(let j = 0; j < p_types.length; j++){
            const {data} = await nearby(latlng, p_types[j],"50000"); 

            for(let k = 0; k < data.length; k++){

                
                let stop = {
                    name: data[k].name,
                    lat: data[k].lat,
                    lng: data[k].lng,
                    g_types: data[k].types,
                    tag: getTag(p_types[j])
                }
                insert(stop); 
            }
        }
    }
    return(stops);
}

(async ()=>{
    const jsonObj = await csv().fromFile(csvFilePath); 
    fs.writeFileSync(stopsFilePath,JSON.stringify(await generateStops(jsonObj))); 
})();
