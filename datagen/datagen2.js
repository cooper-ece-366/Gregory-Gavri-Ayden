const fs = require('fs');
const stopsFilePath = './stops.json';

const axios = require("axios"); 
const csv = require('csvtojson');
const csvFilePath = './data.csv';

const big_stops = [];

const nearby = async (location,type,radius)=>axios.get(`http://localhost:4567/api/v1/geo/nearby?location=${location}&type=${type}&radius=${radius}`);
const search = async (address)=>axios.get(`http://localhost:4567/api/v1/geo/search?address=${address}`);
const nearby2 = async (lng,lat,kinds,rate,radius)=>axios.get(`http://api.opentripmap.com/0.1/en/places/radius?lon=${lng}&lat=${lat}&kinds=${kinds}&rate=${rate}&radius=${radius}&limit=200&apikey=5ae2e3f221c38a28845f05b61bac2d9ae3fa1ac023ab5025d99abd3c`)

const lodging = ["alpine_hut",
    "campsites",
    "hostels",
    "other_hotels",
    "motels",
    "resorts",
    "villas_and_chalet"
    ];

const nature = ["view_points",
    "gardens_and_parks",
    "farms",
    "picnic_site",
    "glaciers",
    "nature_reserves",
    "beaches",
    "other_lakes",
    "lagoons",
    "waterfalls",
    "canals",
    "rivers",
    "reservoirs",
    "dry_lakes",
    "salt_lakes",
    "rift_lakes",
    "crater_lakes",
    "rock_formations",
    "canyons",
    "caves"
];

const nightlife = ["bars","pubs","nightclubs"];

const food = ["cafes",
    "restaurants",
    "foods",
    "bakeries",
    "wineries",
    "biergartens"
    ];

const shopping = ["marketplaces","malls"];

const sports = ["bicycle_rental",
    "stadiums",
    "kitesurfing",
    "surfing",
    "climbing",
    "diving",
    "pools"
    ];

const entertainment = ["casino",
    "other_amusement_rides",
    "ferris_wheels",
    "roller_coasters",
    "water_parks",
    "miniature_parks",
    "amusement_parks",
    "circuses"
];

const museums = ["other_museums",
    "art_galleries",
    "historic_house_museums",
    "children_museums",
    "fashion_museums",
    "open_air_museums",
    "biographical_museums",
    "archaeological_museums",
    "history_museums",
    "military_museums",
    "planetariums",
    "science_museums",
    "museums_of_science_and_technology",
    "local_museums",
    "national_museums"
];

const zoos = ["zoos","aquariums"];

const relig = ["religion"];

const hist = ["historical_places",
    "monuments_and_memorials",
    "castles",
    "bunkers",
    "war_memorials",
    "archaeology",
    "unclassified_objects",
    "aqueducts",
    "footbridges",
    "viaducts",
    "stone_bridges",
    "lighthouses",
    "bell_towers",
    "clock_towers",
    "observation_towers",
    "watchtowers",
    "destroyed_objects",
    "manor_houses",
    "palaces",
    "fortifications"
];

const cult = ["sculptures",
    "fountains",
    "installation",
    "squares",
    "wall_painting"
];

const types = [].concat(lodging,nature,nightlife,zoos,museums,shopping,sports,entertainment,relig,hist,cult,food);

const insert = (obj,stops,stopSet)=>{
    if (stopSet.has(obj.name)) return; 
    stopSet.add(obj.name); 
    stops.push(obj);
    return 
}

const getTag = (t)=>{
    if (lodging.includes(t)) return 'lodging';
    if (nature.includes(t)) return 'nature';
    if (nightlife.includes(t)) return 'nightlife';
    if (food.includes(t)) return 'food';
    if (shopping.includes(t)) return 'shopping';
    if (sports.includes(t)) return 'sports';
    if (entertainment.includes(t)) return 'entertainment';
    if (museums.includes(t)) return 'museums';
    if (zoos.includes(t)) return 'zoos';
    if (relig.includes(t)) return 'religion';
    if (hist.includes(t)) return 'hist';
    if (cult.includes(t)) return 'culture';
}

const getStop = (obj,tag)=>{
    //console.log(obj);
    if(tag == 'city' || tag == 'national_park' || tag == 'state_park' || tag == 'national_forest'){
        const stop = {
            name: obj.name,
            lat: obj.lat,
            lng: obj.lng,
            types: [tag],
            tag: tag
        }
        return stop;
    }

    else{
        const stop = {
            name: obj.properties.name,
            lat: obj.geometry.coordinates[1],
            lng: obj.geometry.coordinates[0],
            types: obj.properties.kinds.split(','),
            tag: getTag(tag)
        }
        return stop;
    }
}

const test = async(obj)=>{
    const {data} = await nearby2(obj[0].lng,obj[0].lat,'gardens_and_parks','3h',8000);

    //console.log(data.features[0].properties);
    const s = getStop(data.features[0],'gardens_and_parks');
    console.log(s);
}

const generateStops = async (obj)=>{
    let total = 0;
    for (let i = 0; i < obj.length; i++) {
        const stops = [];
        const stopSet = new Set();

        console.log(i," Starting: ", obj[i].name);
        //const {data} = await search(obj[i].name);
        //insert(getStop(obj[i], obj[i].type));

        //let latlng = obj[i].lat.toString().concat(" ").concat(obj[i].lng.toString());

        for(let j = 0; j < types.length; j++){
            //const {data} = await nearby(latlng, ts[j],"50000");
            let rating = '3';
            if(getTag(types[j]) == 'nature' || getTag(types[j]) == 'religion' || getTag(types[j]) == 'hist') rating = '3h';

            const {data} = await nearby2(obj[i].lng,obj[i].lat,types[j],rating, 15000);
            console.log("places in ", types[j], data.features.length);
            for(let k = 0; k < data.features.length; k++){
                stop = getStop(data.features[k],types[j]);
                if(stopSet.has(stop.name)) continue; 
                stopSet.add(stop.name); 
                stops.push(stop);
                //insert(getStop(data.features[k],types[j]), stops, stopSet); 
            }
            console.log("finished: ", types[j], " #", j);
        }
        total += stops.length;
        console.log("Total stops: ", total);
        let big_stop = {
            info: obj[i],
            stops: stops
        }

        big_stops.push(big_stop);
    }
    return(big_stops);
}
/*(async ()=>{
    const jsonObj = await csv().fromFile(csvFilePath); 
    fs.writeFileSync(stopsFilePath,JSON.stringify(await test(jsonObj))); 
})();
*/
(async ()=>{
    const jsonObj = await csv().fromFile(csvFilePath); 
    fs.writeFileSync(stopsFilePath,JSON.stringify(await generateStops(jsonObj))); 
})();
