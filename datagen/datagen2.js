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
    "resorts"
    ];

const nature = ["view_points",
    "farms",
    "picnic_site",
    "beaches",
    "geological_formations",
    "nature_reserves",
    "natural_springs",
    "water"
];

const nightlife = ["bars","pubs","nightclubs"];

const food = ["restaurants",
    "cafes",
    "wineries",
    "biergartens"
    ];

const shopping = ["marketplaces","malls"];

const sports = ["sport"];

const entertainment = ["casino",
    "amusements",
];

const museums_and_zoos= ["museums"];

const hist = ["historical_places",
    "fortifications",
    "monuments_and_memorials",
    "archaeology",
    "bridges",
    "lighthouses",
    "towers",
    "historic_architecture"
];

const cult = ["urban_environment"];

const city_types = [].concat(lodging,nature,nightlife,museums_and_zoos,shopping,sports,entertainment,hist,cult,food);
const park_types = [].concat(lodging,nature,museums_and_zoos,sports,hist,cult,food);

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
    if (museums_and_zoos.includes(t)) return 'museums_and_zoos';
    if (hist.includes(t)) return 'hist';
    if (cult.includes(t)) return 'culture';
}

const getStop = (obj,tag)=>{
    
    const stop = {
        name: obj.properties.name,
        lat: obj.geometry.coordinates[1],
        lng: obj.geometry.coordinates[0],
        types: obj.properties.kinds.split(','),
        tag: getTag(tag)
    }
    return stop;
}

const test = async(obj)=>{
    const {data} = await nearby2(obj[0].lng,obj[0].lat,'gardens_and_parks','3h',8000);
    const s = getStop(data.features[0],'gardens_and_parks');
    console.log(s);
}

const generateStops = async (obj)=>{
    let total = 0;

    for (let i = 0; i < obj.length; i++) {

        let radius = 50000;
        let types = park_types;
        const stops = [];
        const stopSet = new Set();
        console.log(i," Starting: ", obj[i].name);
        
        if(obj[i].type == 'city'){
            radius = 15000;
            types = city_types;
        }

        for(let j = 0; j < types.length; j++){

            let rating = '3';
            if(getTag(types[j]) == 'nature' || getTag(types[j]) == 'hist') rating = '3h';

            const {data} = await nearby2(obj[i].lng,obj[i].lat,types[j],rating,radius);

            console.log("places in ", types[j], data.features.length);
            for(let k = 0; k < data.features.length; k++){
                stop = getStop(data.features[k],types[j]);
                if(stopSet.has(stop.name)) continue; 
                stopSet.add(stop.name); 
                stops.push(stop);
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

(async ()=>{
    const jsonObj = await csv().fromFile(csvFilePath); 
    fs.writeFileSync(stopsFilePath,JSON.stringify(await generateStops(jsonObj))); 
})();
