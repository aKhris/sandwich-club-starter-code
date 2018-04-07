package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String KEY_NAME = "name";
    private final static String KEY_NAME_MAIN = "mainName";
    private final static String KEY_NAME_AKA = "alsoKnownAs";
    private final static String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    private final static String KEY_DESCRIPTION = "description";
    private final static String KEY_IMAGE = "image";
    private final static String KEY_INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject jsonSandwich = new JSONObject(json);

            JSONObject jsonSandwichName = jsonSandwich.getJSONObject(KEY_NAME);
            String mainName = jsonSandwichName.getString(KEY_NAME_MAIN);
            List<String> alsoKnownAs = getListFromJSONArray(jsonSandwichName.getJSONArray(KEY_NAME_AKA));

            String placeOfOrigin = jsonSandwich.getString(KEY_PLACE_OF_ORIGIN);
            String description = jsonSandwich.getString(KEY_DESCRIPTION);
            String image = jsonSandwich.getString(KEY_IMAGE);

            List<String> ingredients = getListFromJSONArray(jsonSandwich.getJSONArray(KEY_INGREDIENTS));

            return new Sandwich(
                    mainName,
                    alsoKnownAs,
                    placeOfOrigin,
                    description,
                    image,
                    ingredients
            );
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Function that makes a List of Strings from JSONArray
     * @param array - JSONArray that looks like: ["Sliced bread","Cheese","Ham"]
     * @return List of strings or just empty list with size=0
     * @throws JSONException when there is an error during JSONArray.getString() call
     */
    private static List<String> getListFromJSONArray(JSONArray array) throws JSONException{
        List<String> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.getString(i));
        }
        return list;
    }
}