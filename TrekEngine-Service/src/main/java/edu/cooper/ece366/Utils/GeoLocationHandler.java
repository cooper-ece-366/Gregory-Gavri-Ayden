package edu.cooper.ece366.Utils;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.maps.GeoApiContext;

import static edu.cooper.ece366.Utils.BodyParser.parseJSON;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GeoLocationHandler {
    private class Data {
        public String name; 
        public double lng; 
        public double lat; 
        public String[] types = new String[0];

        public Data(String name, double lng, double lat, String[] types){
            this.name = name;
            this.lng = lng;
            this.lat = lat;
            this.types = types;
        }
    }

    private final String API_KEY; 
    private GeoApiContext context;

    public GeoLocationHandler(){
        API_KEY = System.getenv("GOOGLE_API_KEY"); 
        context = new GeoApiContext.Builder()
            .apiKey(API_KEY)
            .build();
    }

    public String[] getTypes(JsonArray J_types){
        
        String types[] = new String[J_types.size()];
        for(int i = 0; i < J_types.size(); i++){
            types[i] = J_types.get(i).getAsString();
        }
        return types;
    }

    public String search(String address) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        Request request = new Request.Builder()
            .url("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=" + address +"&inputtype=textquery&fields=formatted_address%2Cname%2Crating%2Cgeometry%2Ctypes&key=" + API_KEY)
            .method("GET", null)
            .build();
        Response response = client.newCall(request).execute();
        String res = response.body().string().trim();
        JsonObject obj = parseJSON(res).get("candidates").getAsJsonArray().get(0).getAsJsonObject();

        Data temp = new Data(obj.get("name").getAsString(), 
                            obj.get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lng").getAsDouble(),
                            obj.get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lat").getAsDouble(),
                            getTypes(obj.get("types").getAsJsonArray()));

        return new Gson().toJson(temp); 
    }

    public String nearby(String location, String type, String radius) throws IOException{ 

        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        Request request = new Request.Builder()
            .url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + location + "&type=" + type + "&radius=" + radius + "&key=" + API_KEY)
            .method("GET", null)
            .build();
        Response response = client.newCall(request).execute();
        String res = response.body().string().trim();
        JsonObject obj = parseJSON(res); 
        JsonArray results = obj.get("results").getAsJsonArray();

        Data places[] = new Data[results.size()]; 

        String name;
        JsonObject local;
        Data temp;
        String types[];

        for(int i = 0; i < results.size(); i++){
            name = results.get(i).getAsJsonObject().get("name").getAsString();
            local = results.get(i).getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject();
            types = getTypes(results.get(i).getAsJsonObject().get("types").getAsJsonArray());
            
            temp = new Data(name, local.get("lng").getAsDouble(), local.get("lat").getAsDouble(), types);
            places[i] = temp;
        }
        return new Gson().toJson(places); 
    }

    public String directions(String start, String end) throws IOException{ 
        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        Request request = new Request.Builder()
            .url("https://maps.googleapis.com/maps/api/directions/json?origin=" + start + "&destination=" + end + "&key=" + API_KEY)
            .method("GET", null)
            .build();
        Response response = client.newCall(request).execute();
        String res = response.body().string().trim();
        JsonObject obj = parseJSON(res); 
        JsonArray steps = obj.get("routes").getAsJsonArray()
                            .get(0).getAsJsonObject()
                            .get("legs").getAsJsonArray().get(0).getAsJsonObject()
                            .get("steps").getAsJsonArray(); 

        String polylines[] = new String[steps.size()]; 

        for(int i = 0; i<steps.size(); i++)
            polylines[i] = steps.get(i).getAsJsonObject().get("polyline").getAsJsonObject().get("points").getAsString();
        
        return new Gson().toJson(polylines); 
    }
}
