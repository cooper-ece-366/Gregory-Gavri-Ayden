package edu.cooper.ece366.Utils;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.maps.GeoApiContext;

import static edu.cooper.ece366.Utils.BodyParser.parseJSON;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GeoLocationHandler {

    private final String API_KEY; 
    private GeoApiContext context;


    public GeoLocationHandler(){
        API_KEY = System.getenv("GOOGLE_API_KEY"); 
        context = new GeoApiContext.Builder()
            .apiKey(API_KEY)
            .build();
    }

    public String search(String search) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        Request request = new Request.Builder()
            .url("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=" + search +"&inputtype=textquery&fields=formatted_address%2Cname%2Crating%2Cgeometry&key=" + API_KEY)
            .method("GET", null)
            .build();
        Response response = client.newCall(request).execute();
        String res = response.body().string().trim();
        return res; 
    }

    public String searchMultiple(String search) throws IOException{ //throws ApiException, InterruptedException,IOException

        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        Request request = new Request.Builder()
            .url("https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + search +"&inputtype=textquery&fields=formatted_address%2Cname%2Crating%2Cgeometry&key=" + API_KEY)
            .method("GET", null)
            .build();
        Response response = client.newCall(request).execute();
        String res = response.body().string().trim();
        return res; 
    }

    public String directions(String start, String end) throws IOException{ //throws ApiException, InterruptedException,IOException
        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        Request request = new Request.Builder()
            .url("https://maps.googleapis.com/maps/api/directions/json?origin=" + start + "&destination=" + end + "&key=" + API_KEY)
            .method("GET", null)
            .build();
        Response response = client.newCall(request).execute();
        String res = response.body().string().trim();
        JsonObject obj = parseJSON(res); 
        String polyPoints = obj.get("routes").getAsJsonArray()
                            .get(0).getAsJsonObject()
                            .get("overview_polyline").getAsJsonObject()
                            .get("points").getAsString();
        
        return polyPoints; 
    }

    
}
