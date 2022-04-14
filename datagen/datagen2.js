const fs = require('fs');
const stopsFilePath = './stops.json';

const axios = require("axios"); 
const csv = require('csvtojson');
const csvFilePath = './data.csv';

const stops = [];
const stopSet = new Set();

const nearby = async (location,type,radius)=>axios.get(`http://localhost:4567/api/v1/geo/nearby?location=${location}&type=${type}&radius=${radius}`);
const search = async (address)=>axios.get(`http://localhost:4567/api/v1/geo/search?address=${address}`);
const nearby2 = async (lng,lat,kinds,rate,radius)=>axios.get(`http://api.opentripmap.com/0.1/en/places/radius?lon=-73.989563&lat=40.726589&kinds=gardens_and_parks&rate=3h&radius=12000&apikey=5ae2e3f221c38a28845f05b61bac2d9ae3fa1ac023ab5025d99abd3c`)

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
    "roman_bridges",
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
    "triumphal_archs",
    "amphitheatres",
    "pyramids",
    "fortifications"
];

const cult = ["sculptures",
    "fountains",
    "installation",
    "squares",
    "wall_painting"
];

const types = []

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

const test = async(obj)=>{
    const {data} = await nearby2(obj[0].lng,obj[0].lat,'parks_and_gardens','3h',12000); 
    console.log(data);
}

const generateStops = async (obj)=>{
    
    for (let i = 0; i < 1; i++) {
        console.log("Starting: ", obj[i].name);
        const {data} = await search(obj[i].name); 
        insert(getStop(data, obj[i].type));

        let latlng = obj[i].lat.toString().concat(" ").concat(obj[i].lng.toString());

        for(let j = 0; j < p_types.length; j++){
            const {data} = await nearby(latlng, p_types[j],"50000"); 

            for(let k = 0; k < data.length; k++){
                insert(getStop(data[k],p_types[j])); 
            }
            console.log("finished: ", p_types[j]);
        }
    }
    return(stops);
}
(async ()=>{
    const jsonObj = await csv().fromFile(csvFilePath); 
    fs.writeFileSync(stopsFilePath,JSON.stringify(await test(jsonObj))); 
})();

/*(async ()=>{
    const jsonObj = await csv().fromFile(csvFilePath); 
    fs.writeFileSync(stopsFilePath,JSON.stringify(await generateStops(jsonObj))); 
})();*/
