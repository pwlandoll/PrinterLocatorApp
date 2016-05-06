package edu.jcu.plandoll16.printerlocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AllPrinterFilterActivity extends AppCompatActivity {
    private Button allBuildingsButton, adminButton, bolerButton,
            dolanButton, libraryButton, lscButton, omalleyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_printer_filter);

        allBuildingsButton = (Button)findViewById(R.id.allBuildingsButton);
        adminButton = (Button)findViewById(R.id.adminButton);
        bolerButton = (Button)findViewById(R.id.bolerButton);
        dolanButton = (Button)findViewById(R.id.dolanButton);
        libraryButton = (Button)findViewById(R.id.libraryButton);
        lscButton = (Button)findViewById(R.id.lscButton);
        omalleyButton = (Button)findViewById(R.id.omalleyButton);
    }

    private Button.OnClickListener buildingSelectorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String buildingName = "all";
            int id = v.getId();
            switch (id) {
                case R.id.allBuildingsButton: buildingName = "all"; break;
                case R.id.adminButton: buildingName = "admin"; break;
                case R.id.bolerButton: buildingName = "boler"; break;
                case R.id.dolanButton: buildingName = "dolan"; break;
                case R.id.libraryButton: buildingName = "library"; break;
                case R.id.lscButton: buildingName = "lsc"; break;
                case R.id.omalleyButton: buildingName = "omalley"; break;
                default: buildingName = "all"; break;
            }
            Intent mIntent = new Intent(getApplicationContext(), ListAllPrintersActivity.class);
            mIntent.putExtra("edu.jcu.plandoll16.PrinterLocator.buildingName", buildingName);
            startActivity(mIntent);
        }
    };
}
