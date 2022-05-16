// import { MongoClient } from 'mongodb'
const { MongoClient } = require("mongodb"); 
const stops = require("./stops.json"); 

let uri = "mongodb+srv://testuser:1d41u9z868I6RYPKqCjW@cluster0.ob7pt.mongodb.net/TrekEngine?retryWrites=true&w=majority"
let dbName = "TrekEngine"

let cachedClient = null
let cachedDb = null

if (!uri) {
  throw new Error(
    'Please define the MONGODB_URI environment variable inside .env.local'
  )
}

if (!dbName) {
  throw new Error(
    'Please define the MONGODB_DB environment variable inside .env.local'
  )
}

async function connectToDatabase() {
  if (cachedClient && cachedDb) {
    return { client: cachedClient, db: cachedDb }
  }

  const client = await MongoClient.connect(uri, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  })

  const db = await client.db(dbName)

  cachedClient = client
  cachedDb = db

  return { client, db }
}

(async ()=>{
    const {client, db} = await connectToDatabase();
    const formatStops = stops.map( ({info: {lat,lng,name,type}, stops}) => ({info: {cords: [Number(lng),Number(lat)], name, type, isCurated:true}, stops})); 
    console.log("starting insert");

    for(let i = 0; i<formatStops.length; i++){
      const {info,stops} = formatStops[i]; 
      console.log(`starting insert ${info.name}`); 
        const {insertedId:bigStop, acknowledged  } = await db.collection("bigStops").insertOne(info); 
        console.log(`finished inserting ${info.name} id= ${bigStop} ack=${acknowledged }`);
        const smallStops = stops.map(({lat,lng,name,tag:type})=>({cords:[Number(lng),Number(lat)],name,type,bigStop}));
        if(smallStops.length > 0){
          console.log(smallStops.length)
          await db.collection("smallStops").insertMany(smallStops); 
        }
        console.log(`Big Stops# = ${i}`)
    }

    client.close(); 
})()