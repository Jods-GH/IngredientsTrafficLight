package com.example.intolerancetrafficlight;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView productTextView;
    TextView brandTextView;

    ImageView trafficLight;
    ScrollView ingredients;
    LinearLayout ingredientsLinearLayout;
    Button scanButton;
    Resources res;
    Spinner intoleranceSpinner;

    IntoleranceInfo intolerances;
    IntoleranceEnum currentIntolerance;

    LocaleList currentLocale;

    Drawable redCirle;
    Drawable greenCircle;
    Drawable orangeCircle;

    Drawable noCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        productTextView = findViewById(R.id.ProductText);
        scanButton = findViewById(R.id.ScanButton);
        brandTextView = findViewById(R.id.BrandText);
        ingredients = findViewById(R.id.IngredientsScrollView);
        ingredientsLinearLayout = findViewById(R.id.IngredientsLinearLayout);
        intoleranceSpinner = findViewById(R.id.intoleranceSpinner);
        res = getResources();
        currentLocale = res.getConfiguration().getLocales();
        trafficLight = findViewById(R.id.trafficLight);
        redCirle = ResourcesCompat.getDrawable(res,R.drawable.circle_red,null);
        greenCircle = ResourcesCompat.getDrawable(res,R.drawable.circle_green,null);
        orangeCircle = ResourcesCompat.getDrawable(res,R.drawable.circle_orange,null);
        noCircle = ResourcesCompat.getDrawable(res,R.drawable.circle_none,null);
        GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                        Barcode.FORMAT_EAN_8,
                        Barcode.FORMAT_EAN_13)
                .enableAutoZoom()
                .build();
        GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(this);
        Context ctx = this;
        FoodIntoleranceRequestor intoleranceRequestor = new FoodIntoleranceRequestor(ctx);
        intoleranceRequestor.execute(IntoleranceEnum.SORBITOL);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trafficLight.setForeground(noCircle);
                scanner
                        .startScan()
                        .addOnSuccessListener(
                                barcode -> {
                                    RestDataRequestor requestor = new RestDataRequestor(ctx);
                                    requestor.execute(barcode.getDisplayValue());
                                })
                        .addOnCanceledListener(
                                () -> {
                                    System.out.println("canceled scan");
                                })
                        .addOnFailureListener(
                                e -> {
                                    System.out.println(e);
                                });
            }
        });
    }

    public void handleDataReturn(FoodInfo info) {
        ingredientsLinearLayout.removeAllViews();
        productTextView.setText("Product");
        brandTextView.setText("Brand");
        if (info != null) {
            if (info.getName() != null)
                productTextView.setText(info.getName());
            if (info.getBrand() != null)
                brandTextView.setText(info.getBrand());
            if (info.getException() != null)
                Toast.makeText(this, info.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
            if (info.getIngredients() != null) {
                Intolerance intolerated = intolerances.getIntolerances().get(currentIntolerance);
                TrafficLightColors colorToSet = null;
                for (Ingredient ingredient : info.getIngredients()) {
                    Integer foodCode = ingredient.getFoodCode()!=null? Integer.parseInt(ingredient.getFoodCode()) : 0;
                    ToleratedIngredient ingredientToCheck = new ToleratedIngredient(ingredient.getText().toLowerCase().trim(), foodCode, Boolean.FALSE);
                    if (intolerated.getIntoleratedIngredients().contains(ingredientToCheck)) {
                        colorToSet = TrafficLightColors.NOT_TOLERATED;
                        break;
                    }
                    if (!intolerated.getToleratedIngredients().contains(ingredientToCheck)) {
                        TextView textView = new TextView(this);
                        String text;
                        if (ingredient.getPercentEstimate() > 0 && ingredient.getFoodCode() != null){
                            text = String.format(res.getString(R.string.ingredient_text_three), ingredient.getText(), ingredient.getFoodCode(), ingredient.getPercentEstimate().toString());
                        }
                        else if (ingredient.getPercentEstimate() > 0)
                            text = String.format(res.getString(R.string.ingredient_text_percent), ingredient.getText(), ingredient.getPercentEstimate().toString());
                        else if (ingredient.getFoodCode() !=null)
                            text = String.format(res.getString(R.string.ingredient_text), ingredient.getText(), ingredient.getFoodCode());
                        else
                            text = ingredient.getText();
                        textView.setText(text);
                        ingredientsLinearLayout.addView(textView);
                        if (colorToSet == null)
                            colorToSet = TrafficLightColors.NOT_FOUND;
                    }

                }
                ;

                if (colorToSet == TrafficLightColors.NOT_TOLERATED) {
                    trafficLight.setForeground(redCirle);
                } else if (colorToSet == TrafficLightColors.NOT_FOUND) {
                    trafficLight.setForeground(orangeCircle);
                } else {
                    trafficLight.setForeground(greenCircle);
                }

            }


            if (info.getAdditives() != null) {
                info.getAdditives().forEach(additive -> {
                    TextView textView = new TextView(this);
                    textView.setText(additive.substring(3));
                    ingredientsLinearLayout.addView(textView);
                });
            }
        }
    }
    public void handleDataReturn(IntoleranceInfo info){
        intolerances = info;
        ArrayList<String> options=new ArrayList<String>();
        Map<String, IntoleranceEnum> intoleranceMap = new HashMap<>();
        for(IntoleranceEnum intolerance:intolerances.getIntolerances().keySet()) {
            Locale localeToUse = currentLocale.getFirstMatch(info.supportecLocales.get(intolerance));
            String localizedName = info.localizedNames.get(intolerance).get(localeToUse) != null ? info.localizedNames.get(intolerance).get(localeToUse) : intolerance.name();
            intoleranceMap.put(localizedName, intolerance);
            options.add(localizedName);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,options);
        intoleranceSpinner.setAdapter(adapter);

        intoleranceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // when the user selects an author we request the authors book Data to choose from
                currentIntolerance = intoleranceMap.get(intoleranceSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}