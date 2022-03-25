import React, { useRef, useEffect, useState,useImperativeHandle,forwardRef } from 'react';
import mapboxgl from 'mapbox-gl';
import env from "../../env";
import {getDirection,getLatLng} from "../../utils/GeoLocation";

mapboxgl.accessToken = env.MAP_BOX_ACCESS_TOKEN; 

const styleSheet = {
    mapContainer: {
        width: "100%",
        height: "100%",
    },
}

const Map = ({lng_i=-87.65,lat_i=41.84},ref) => {
  const mapContainerRef = useRef(null);
  const map = useRef(null);

  const [lng, setLng] = useState(lng_i);
  const [lat, setLat] = useState(lat_i);
  const [zoom, setZoom] = useState(3);
  const markers = useRef({}); 
  const paths = useRef({}); 

    const addMarker = async (loc,id) => {
        const [lng,lat] = await getLatLng(loc);
        addMarkerLngLat(lng,lat,id); 
    }
    const addMarkerLngLat = (lng,lat,id) => {
        const marker = new mapboxgl.Marker().setLngLat([lng, lat]).addTo(map.current);
        markers.current[id] = marker;
    }

    const removeMarker = (id) => {
        if(!markers.current[id]) return;
        markers.current[id].remove()
        delete markers.current[id];
    }

    const addPath = async (start,end,id)=>{
        const coordinates = await getDirection(start,end);
        addMarkerLngLat(coordinates[0][0],coordinates[0][1],`start-${id}`);
        addMarkerLngLat(coordinates[coordinates.length-1][0],coordinates[coordinates.length-1][1],`end-${id}`);
        paths.current[id] = map.current.addLayer({
            id,
            type:"line",
            source:{
                type:"geojson",
                data:{
                    type:"Feature",
                    properties:{},
                    geometry:{
                        type:"LineString",
                        coordinates
                    }
                }
            },
            layout:{
                "line-join":"round",
                "line-cap":"round"
            },
            paint:{
                "line-color":"#888",
                "line-width":8
            }
        })

    }

    const removePath = id => {
        if (!paths.current[id]) return; 
        map.current.removeLayer(id);
        delete paths.current[id];
    }



  // Initialize map when component mounts
  useEffect(() => {
    map.current = new mapboxgl.Map({
      container: mapContainerRef.current,
      style: 'mapbox://styles/mapbox/streets-v11',
      center: [lng, lat],
      zoom: zoom,
    });


    map.current.on('move', () => {
      setLng(map.current.getCenter().lng.toFixed(4));
      setLat(map.current.getCenter().lat.toFixed(4));
      setZoom(map.current.getZoom().toFixed(2));
    });

    // Clean up on unmount
    return () => map.current.remove();
  }, []);

  // Functions exposed to the parent component 
  useImperativeHandle(ref,()=>({
    addMarker,
    addMarkerLngLat,
    removeMarker,
    addPath,
    removePath
  })); 

  return (
    <div style={styleSheet.mapContainer}>
      <div style={styleSheet.mapContainer} className="map-container" ref={mapContainerRef} />
    </div>
  );
};

export default forwardRef(Map);
