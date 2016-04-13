package edu.jcu.plandoll16.printerlocator;

/**
 * Created by Peter Landoll on 4/12/16.
 */
public class Printer {
    private String name, status;
    private double locationLatitude, locationLongitude;

    public Printer() {
        // Default name is "printer", location is middle of the Admin building
        this("printer", 41.490150, -81.531329);
    }

    public Printer(String name) {
        this(name, 41.490150, -81.531329);
    }

    public Printer(String name, double locationLatitude, double locationLongitude) {
        this.name = name;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
}
