package com.chsra.photosearch;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class JSONUtility {
    public static class ImageJSONParser {
        public static ArrayList < String > getSearchImage(String jsonData) {
            ArrayList < String > resultList = new ArrayList < > ();
            String key = null;
            try {
                JSONObject obj = new JSONObject(jsonData);
                JSONObject pageId = obj.getJSONObject("query").getJSONObject("pages");
                Iterator i = pageId.keys();
                while (i.hasNext()) {
                    key = (String) i.next();
                }
                resultList.add(pageId.getJSONObject(key.toString()).getJSONObject("thumbnail").getString("source"));
                resultList.add(pageId.getJSONObject(key.toString()).getString("extract"));
                resultList.add(pageId.getJSONObject(key.toString()).getString("title"));
                return resultList;

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}