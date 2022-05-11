// Written By Gregory Presser
package edu.cooper.ece366.Utils.GeoLocation;

import java.util.ArrayList;

import com.google.gson.Gson;

import edu.cooper.ece366.Mongo.SerializingInterface;

public class DirData implements SerializingInterface{
    private ArrayList<String> polylines; 
    private long durtaionS; 
    private long durationM; 

    public DirData(long durationS, long durationM){
        this.polylines = new ArrayList<String>();
        this.durtaionS = durationS;
        this.durationM = durationM;
    }

    public DirData() {
        this(0,0); 
    }

    public long getDurationM() {
        return durationM;
    }
    public void addDurationM(long durationM) {
        this.durationM += durationM;
    }

    public void addDurationM(int durationM) {
        this.addDurationM((long)durationM);
    }
    public long getDurtaionS() {
        return durtaionS;
    }
    public void addDurtaionS(long durtaionS) {
        this.durtaionS += durtaionS;
    }
    
    public void addDurtaionS(int durtaionS) {
        this.addDurtaionS((long)durtaionS);
    }

    public String getPolylineString(){
        return new Gson().toJson(this.polylines); 
    }

    public void addPolyine(String polyline){
        this.polylines.add(polyline);
    }
    
}
