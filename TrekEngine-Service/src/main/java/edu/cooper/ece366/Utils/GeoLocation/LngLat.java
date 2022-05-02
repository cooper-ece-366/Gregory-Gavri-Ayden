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

    public String getDirStr(){
        return lat + " " + lng; 
    }

    public double abs(){
        return Math.sqrt(Math.pow(lng, 2) + Math.pow(lat, 2)); 
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
        return convertToRad(this.lng); 
    }

    public double getPhi(){
        return convertToRad(this.lat); 
    }
    public double getPhiD(LngLat p2){
        return convertToRad(p2.getLat() - this.lat);
    }

    public double  getBearing(LngLat p2){

        final double y = Math.sin(this.getGammaD(p2)) * Math.cos(p2.getPhi());
        final double x = Math.cos(this.getPhi()) * Math.sin(p2.getPhi()) - 
            Math.sin(this.getPhi()) * Math.cos(p2.getPhi()) * Math.cos(this.getGammaD(p2));
        
        return Math.atan2(y, x); // returns bearing in radians 
    }

    public LngLat getDest(long d, double brng) {

        final double phi1 = this.getPhi();
        final double gamma1 = this.getGamma(); 
        final double sPhi1 = Math.sin(phi1);
        final double cPhi1 = Math.cos(phi1);

        final double dr = ((double)d) / ((double)R);

        final double cDR = Math.cos(dr);
        final double sDR = Math.sin(dr);

        final double phi = Math.asin(sPhi1*cDR +
                      cPhi1*sDR*Math.cos(brng) );


        final double gamma = gamma1 + 
            Math.atan2(Math.sin(brng)*sDR*cPhi1,
                        cDR-sPhi1*Math.sin(phi));

        return new LngLat(convertFromRad(gamma), convertFromRad(phi));
    }

    public LngLat getIntermidiatePoint(LngLat p2, long d, double f){
        final double del = ((double)d)/((double)R);
        final double a = Math.sin((1.0-f)*del) / Math.sin(del);
        final double b = Math.sin(f*del) / Math.sin(del);
        final double x = a*Math.cos(this.getPhi()) * Math.cos(this.getGamma()) + 
            b*Math.cos(p2.getPhi()) * Math.cos(p2.getGamma());
        final double y = a*Math.cos(this.getPhi()) * Math.sin(this.getGamma()) +
            b*Math.cos(p2.getPhi()) * Math.sin(p2.getGamma());
        final double z = a*Math.sin(this.getPhi()) + b*Math.sin(p2.getPhi());
        final double phi = Math.atan2(z, Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
        final double gamma = Math.atan2(y, x);

        return new LngLat(convertFromRad(gamma), convertFromRad(phi));
    }

    public LngLat sub(LngLat p2){
        return new LngLat(this.lng - p2.getLng(), this.lat - p2.getLat());
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

    private static double convertFromRad(double rad){
        return rad * 180 / Math.PI; 
    }

    private static double convertToRad(double deg){
        return deg * Math.PI / 180; 
    }



    
}
