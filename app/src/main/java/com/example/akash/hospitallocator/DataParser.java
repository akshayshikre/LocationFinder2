package com.example.akash.hospitallocator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by akash on 22-10-2017.
 */

public class DataParser {

    private HashMap<String,String> getPlace(JSONObject googlePlacejson)
    {
        HashMap<String,String> googlePlacesMap=new HashMap<>();
        String placeName="-NA-";
        String vicinity="-NA-";
        String latitude="";
        String longitude="";
        String reference="";

        try {

            if (!googlePlacejson.isNull("name"))
            {

                placeName = googlePlacejson.getString("name");

            }
            if (!googlePlacejson.isNull("vicinity"))
            {

                vicinity = googlePlacejson.getString("vicinity");

            }

            latitude=googlePlacejson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude=googlePlacejson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference=googlePlacejson.getString("reference");

            googlePlacesMap.put("place_name",placeName);
            googlePlacesMap.put("vicinity",vicinity);
            googlePlacesMap.put("lat",latitude);
            googlePlacesMap.put("lng",longitude);
            googlePlacesMap.put("reference",reference);

        }
     catch (JSONException e)
     {
        e.printStackTrace();
     }

     return googlePlacesMap;

    }

    private List<HashMap<String,String>> getPlaces(JSONArray jsonArray)
    {
        int count=jsonArray.length();
        List<HashMap<String,String>> placesList=new ArrayList<>();
        HashMap<String,String> placesMap=null;

        for(int i=0;i<count;i++)
        {
            try {
                placesMap=getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placesMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return placesList;
    }

    public List<HashMap<String,String>> parse(String jsonData)
    {
        JSONArray jsonArray=null;
        JSONObject jsonObject;

        try {
            jsonObject=new JSONObject(jsonData);
            jsonArray=jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getPlaces(jsonArray);

    }
}
