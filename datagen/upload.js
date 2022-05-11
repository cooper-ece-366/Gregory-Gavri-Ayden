// NOTE ALL DATES SHOULD BE IN LONG FORMAT 
const axios = require("axios"); 

// is private is a boolean 
// created & updaetd are longs (dates)
const metaMaker = (name,user,description,isPrivate,created,updated)=>({
        name,
        user,
        description,
        private:isPrivate,
        created,
        updated
}); 

// tags is a list of tags 
// startDate is a long 
// tripLength is an int
const detailMaker = (startDate,tripLength,tags)=>({startDate,tripLength,tags}); 


// tag is a string
// weight is a double
const tagMaker = (tag,weight)=>({tag,weight}); 

// name is a string
// lat & lng is a double
// type is a string 
const locationMaker = (name,lat,lng,type)=>({name,lat,lng,type});

// all are locations 
// stops is a list of locations 
const tripDataMaker = (startLocaiton,endLocation,stops)=>({startLocaiton,endLocation,stops});

const uploadData = (details,meta,trip)=>{
    const data = {
        trip: {
            details, 
            meta,
            trip,
        }
    }
    axios.post("http://localhost:4567/api/v1/tripgen/unsafeInsert", data)
}

module.exports = {
    metaMaker,
    detailMaker,
    tagMaker,
    locationMaker,
    tripDataMaker,
    uploadData
}


