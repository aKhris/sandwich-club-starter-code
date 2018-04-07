package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView alsoKnownAsTextView;
    private TextView descriptionTextView;
    private TextView ingredientsTextView;
    private TextView originTextView;
    private TextView alsoKnownAsTitle;
    private TextView descriptionTitle;
    private TextView ingredientsTitle;
    private TextView originTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        descriptionTextView = findViewById(R.id.description_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        originTextView = findViewById(R.id.origin_tv);
        alsoKnownAsTitle = findViewById(R.id.also_known_title_tv);
        descriptionTitle= findViewById(R.id.description_title_tv);
        ingredientsTitle = findViewById(R.id.ingredients_title_tv);
        originTitle = findViewById(R.id.origin_title_tv);




        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
//      Checking if there is a "Place of origin" in a sandwich item
//      if not - making corresponding TextViews "GONE"
        if(sandwich.getPlaceOfOrigin()==null || sandwich.getPlaceOfOrigin().equals("")){
            originTitle.setVisibility(View.GONE);
            originTextView.setVisibility(View.GONE);
        }
        else {
            originTextView.setText(sandwich.getPlaceOfOrigin());
        }


//      Checking if there is a "description" in a sandwich item
//      if not - making corresponding TextViews "GONE"
        if(sandwich.getDescription()==null || sandwich.getDescription().equals("")){
            descriptionTitle.setVisibility(View.GONE);
            descriptionTextView.setVisibility(View.GONE);
        }
        else {
            descriptionTextView.setText(sandwich.getDescription());
        }

//      Checking if there is a "Also known as" in a sandwich item
//      if not - making corresponding TextViews "GONE"
        if(sandwich.getAlsoKnownAs()==null || sandwich.getAlsoKnownAs().size()==0){
            alsoKnownAsTextView.setVisibility(View.GONE);
            alsoKnownAsTitle.setVisibility(View.GONE);
        }
        else{
            String akaString = sandwich.getAlsoKnownAs().toString();
            alsoKnownAsTextView.setText(
                    akaString.substring(1, akaString.length()-1)
            );
        }


//      Checking if there is a "Ingredients" in a sandwich item
//      if not - making corresponding TextViews "GONE"
        if(sandwich.getIngredients()==null || sandwich.getIngredients().size()==0){
            ingredientsTextView.setVisibility(View.GONE);
            ingredientsTitle.setVisibility(View.GONE);
        }
        else{
            String ingredientsString = sandwich.getIngredients().toString();
            ingredientsTextView.setText(
                    ingredientsString.substring(1, ingredientsString.length()-1)
            );
        }


    }
}
