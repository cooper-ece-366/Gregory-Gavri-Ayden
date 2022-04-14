const fs = require('fs');
const csv = require('csvtojson');
const Upload = require('./upload');

const csvFilePath = './data.csv';
const tripsFilePath = './trips.json';

const generateTrips = (places, amount) => {
    let trips = [];

    for (let i = 0; i < amount; i++) {
        let trip = {};
        trip.meta = Upload.randomMetaMaker();

    }
}

generateTrips([], 1);