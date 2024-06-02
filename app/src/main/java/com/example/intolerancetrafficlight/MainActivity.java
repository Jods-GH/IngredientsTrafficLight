package com.example.intolerancetrafficlight;

import android.os.Bundle;
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
        GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                        Barcode.FORMAT_EAN_8,
                        Barcode.FORMAT_EAN_13)
                .enableAutoZoom()
                .build();
        GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(this);

        scanner
                .startScan()
                .addOnSuccessListener(
                        barcode -> {
                            System.out.println(barcode.getDisplayValue());
                            RestDataRequestor requestor = new RestDataRequestor(this);
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

    public void handleDataReturn(FoodInfo info){
        productTextView.setText(info.Name);
        System.out.println("handlemainstuff");
        System.out.println(info.Name);
    }
}