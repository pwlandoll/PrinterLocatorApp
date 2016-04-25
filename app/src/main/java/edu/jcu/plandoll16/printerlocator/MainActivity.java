package edu.jcu.plandoll16.printerlocator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void fetchPrinterList() {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        try {
            url = new URL("http://stuweb.jcu.edu/printerstatus.html");
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                
            }
        } catch (Exception ex) {

        }
    }
}
