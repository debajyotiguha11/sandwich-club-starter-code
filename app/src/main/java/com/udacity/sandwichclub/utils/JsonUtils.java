package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject name = jsonObject.getJSONObject("name");
        String Name = name.getString("mainName");
        String place = jsonObject.getString("placeOfOrigin");
        String description = jsonObject.getString("description");
        String image = jsonObject.getString("image");

        List<String> lstAKA = new ArrayList<>();
        if (name.has("alsoKnownAs")) {
            JSONArray aka = name.getJSONArray("alsoKnownAs");

            for (int i = 0; i < aka.length(); i++) {
                String strAKA = aka.getString(i);
                lstAKA.add(strAKA);
            }
        }

        JSONArray ingredientArray = jsonObject.getJSONArray("ingredients");

        List<String> lstIngredients = new ArrayList<>();
        for (int i = 0; i < ingredientArray.length(); i++) {
            lstIngredients.add(ingredientArray.getString(i));
        }

        return new Sandwich(Name, lstAKA, place, description, image, lstIngredients);
    }
}
