package edu.cooper.ece366.Utils.GeoLocation;

import java.util.ArrayList;
import java.util.Arrays;

import edu.cooper.ece366.Exceptions.InvalidLngLatException;
import edu.cooper.ece366.Mongo.Stops.Loacation;

public class LngLat {
    private double lng;
    private double lat; 
    private static final int R = 6371000; // radius of earth in meters

    public LngLat(double lng, double lat) {
        if (lng < -180 || lng > 180 || lat < -90 || lat > 90) {
            throw new InvalidLngLatException();
        }
        this.lng = lng;
        this.lat = lat;
    }

    public LngLat(Loacation loc){
        this(loc.getLng(), loc.getLat()); 
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double deltaAsMeters(LngLat p2){
        final double phi1 = this.lat * Math.PI/180;
        final double phi2 = p2.getLat() * Math.PI/180;
        final double deltaPhi = (p2.getLat() - this.lat) * Math.PI/180;
        final double deltaGamma = (p2.getLng() - this.lng) * Math.PI/180; 

        final double sinDP = Math.sin(deltaPhi/2); 
        final double sinDG = Math.sin(deltaGamma/2); 
        final double a = sinDP * sinDP + 
            Math.cos(phi1) * Math.cos(phi2) * 
            sinDG*sinDG; 

        final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
        return R * c;
    }

    public double getGammaD(LngLat p2){
        return (p2.getLng() - this.lng) *Math.PI/180; 
    }

    public double getGamma(){
        return this.lng * Math.PI / 180; 
    }

    public double getPhi(){
        return this.lat * Math.PI / 180; 
    }
    public double getPhiD(LngLat p2){
        return (p2.getLat() - this.lat) * Math.PI / 180; 
    }

    public double  getBearing(LngLat p2){
        final double y = Math.sin(this.getGammaD(p2)) * Math.cos(p2.getPhi());
        final double x = Math.cos(this.getPhi()) * Math.sin(p2.getPhi()) - 
            Math.sin(this.getPhi()) * Math.cos(p2.getPhi()) * Math.cos(this.getGammaD(p2));
        
        return Math.atan2(y, x); // returns bearing in radians 
    }

    public LngLat getDest(long d, double brng) {
        final double phi = Math.asin( Math.sin(this.getPhi())*Math.cos(d/R) +
                      Math.cos(this.getPhi())*Math.sin(d/R)*Math.cos(brng) );

        final double gamma = this.getGamma() + 
            Math.atan2(Math.sin(brng)*Math.sin(d/R)*Math.cos(this.getPhi()),
                        Math.cos(d/R)-Math.sin(this.getPhi())*Math.sin(phi));

        return new LngLat((convertFromRad(gamma)+540)%360-180, convertFromRad(phi));
    }

    private static double convertFromRad(double rad){
        return rad * 180 / Math.PI; 
    }

    public ArrayList<Double> getList (){
        return new ArrayList<Double>(){{
            add(lng);
            add(lat);
        }};
    }

    public String toString() {
        return "LngLat{" +
                "lng=" + lng +
                ", lat=" + lat +
                '}';
    }



    
}
