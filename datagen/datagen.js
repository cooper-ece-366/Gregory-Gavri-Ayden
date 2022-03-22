// convert data.csv to json
const fs = require('fs');
const csv = require('csvtojson');

const csvFilePath = './data.csv';
const tripsFilePath = './trips.json';

const generateTrips = (places, amount) => {
    const trips = [];

    for (let i = 0; i < amount; i++) {
        let locations = [];
        let amount = Math.floor(Math.random() * 7);

        for (let j = 0; j < amount; j++) {
            let index = Math.floor(Math.random() * places.length);

            while (locations.includes(places[index])) {
                index = Math.floor(Math.random() * places.length);
            }

            locations.push(places[index]);
        }


        // find two furthest locations
        let maxDistance = 0;
        let maxDistanceIndex = 0;
        let maxDistanceIndex2 = 0;

        for (let j = 0; j < locations.length; j++) {
            for (let k = 0; k < locations.length; k++) {
                if (j !== k) {
                    let distance = Math.sqrt(Math.pow(locations[j].lat - locations[k].lat, 2) + Math.pow(locations[j].lng - locations[k].lng, 2));

                    if (distance > maxDistance) {
                        maxDistance = distance;
                        maxDistanceIndex = j;
                        maxDistanceIndex2 = k;
                    }
                }
            }
        }

        let start = locations[maxDistanceIndex];
        let end = locations[maxDistanceIndex2];

        locations.splice(maxDistanceIndex, 1);
        locations.splice(maxDistanceIndex2 - 1, 1);

        locations.sort((a, b) => {
            let distanceA = Math.sqrt(Math.pow(a.lat - start.lat, 2) + Math.pow(a.lng - start.lng, 2));
            let distanceB = Math.sqrt(Math.pow(b.lat - start.lat, 2) + Math.pow(b.lng - start.lng, 2));

            return distanceA - distanceB;
        });


        let trip = {
            start: start,
            locations: locations,
            end: end
        }

        trips.push(trip);
    }

    return trips;
};

const getDistance = (lat1, lon1, lat2, lon2) => {
    var R = 6371; // Radius of the earth in km
    var dLat = deg2rad(lat2 - lat1); // deg2rad below
    var dLon = deg2rad(lon2 - lon1);
    var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    var d = R * c; // Distance in km
    return d;
};

csv().fromFile(csvFilePath).then((jsonObj) => {
    fs.writeFileSync(tripsFilePath, JSON.stringify(generateTrips(jsonObj, 100)));
});