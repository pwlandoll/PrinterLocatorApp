package edu.jcu.plandoll16.printerlocator;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Printer class.
 *
 * Each printer has variables corresponding to the fields of the SQLite database.
 *
 * @author Peter Landoll <plandoll16@jcu.edu>
 * @version 1.0
 * @since 2016-4-12
 */
public class Printer implements Comparable<Printer> {
    private double locationLatitude, locationLongitude, distance;
    private int id, statusCode;
    private String name, description, building;

    /**
     * Constructor.
     *
     * Default name is "printer", location is the middle of the Admin building
     */
    public Printer() {
        this(0, "printer", 41.490150, -81.531329);
    }

    /**
     * Constructor.
     *
     * @param name name of the printer
     * @param locationLatitude latitude of the location of the printer
     * @param locationLongitude longitude of the location of the printer
     */
    public Printer(int id, String name, double locationLatitude, double locationLongitude) {
        this.id = id;
        this.name = name;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        // Default status code is 0
        this.statusCode = 0;
        this.description = "";
        this.building = "";
    }

    /**
     * method from Comparable to compare two printers by name, alphabetically
     *
     * @param another Printer to compare against
     * @return comparison for which Printer's name comes first alphabetically
     */
    @Override
    public int compareTo(Printer another) {
        return name.compareToIgnoreCase(another.getName());
    }

    /**
     * creates a LinearLayout with the printer's name and a button that can be set to launch an activity
     * @param context used for creating views
     * @return LinearLayout containing a TextView and Button
     */
    public LinearLayout getPrinterLayout(Context context) {
        LinearLayout layout = new LinearLayout(context);
        // Create layout to fill width of screen, but to only be as tall as necessary
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        layout.setLayoutParams(layoutParams);
        // Create view with a TextView for the name, and a Button to show more info
        TextView nameText = new TextView(context);
        nameText.setText(name);
        nameText.setLayoutParams(layoutParams);
        Button moreButton = new Button(context);
        moreButton.setText(R.string.more);
        moreButton.setLayoutParams(layoutParams);
        layout.addView(nameText, 0);
        layout.addView(moreButton, 1);
        return layout;
    }

    // Getters and Setters follow

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }
}
