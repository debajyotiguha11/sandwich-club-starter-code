package com.udacity.sandwichclub;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView SandwichIv = findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null)
            closeOnError();


        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(SandwichIv);
            setTitle(sandwich.getMainName());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView origin = findViewById(R.id.origin_tv);
        TextView description = findViewById(R.id.description_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);
        TextView alsoKnown =  findViewById(R.id.also_known_tv);
        TextView originLabel = findViewById(R.id.org_tv_label);
        TextView alsoKnownLabel = findViewById(R.id.aka_tv_label);

        if(sandwich.getAlsoKnownAs().size() == 0){
            alsoKnown.setVisibility(View.GONE);
            alsoKnownLabel.setVisibility(View.GONE);
        }
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            originLabel.setVisibility(View.GONE);
            origin.setVisibility(View.GONE);
        }

        alsoKnown.setText(sandwich.getAlsoKnownAs().toString().replace("]", "").replace("[", ""));
        origin.setText(sandwich.getPlaceOfOrigin());
        ingredients.setText(sandwich.getIngredients().toString().replace("]", "").replace("[", ""));
        description.setText(sandwich.getDescription());
    }
}
