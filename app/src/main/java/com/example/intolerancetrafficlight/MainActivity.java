package com.example.intolerancetrafficlight;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

public class MainActivity extends AppCompatActivity {
    TextView productTextView;
    TextView brandTextView;

    ScrollView ingredients;
    LinearLayout ingredientsLinearLayout;
    Button scanButton;
    Resources res;

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
        res = getResources();
        GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                        Barcode.FORMAT_EAN_8,
                        Barcode.FORMAT_EAN_13)
                .enableAutoZoom()
                .build();
        GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(this);
        Context ctx = this;
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanner
                        .startScan()
                        .addOnSuccessListener(
                                barcode -> {
                                    System.out.println(barcode.getDisplayValue());
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

    public void handleDataReturn(FoodInfo info){
        ingredientsLinearLayout.removeAllViews();
        productTextView.setText("Product");
        brandTextView.setText("Brand");
        if(info != null){
            productTextView.setText(info.Name);
            brandTextView.setText(info.brand);
            if(info.getIngredients()!=null)
                info.getIngredients().forEach(ingredient ->{
                    TextView textView = new TextView(this);
                    String text;
                    if(ingredient.getPercentEstimate()>0)
                        text= String.format(res.getString(R.string.ingredient_text), ingredient.getText(), ingredient.getPercentEstimate().toString());
                    else
                        text = ingredient.getText();
                    textView.setText(text);
                    ingredientsLinearLayout.addView(textView);
                });
            if(info.getAdditives()!=null){
                info.getAdditives().forEach(additive ->{
                    TextView textView = new TextView(this);
                    textView.setText(additive.substring(3));
                    ingredientsLinearLayout.addView(textView);
                });
            }
        }
    }
}