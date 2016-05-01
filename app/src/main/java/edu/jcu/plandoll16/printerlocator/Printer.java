package edu.jcu.plandoll16.printerlocator;

/**
 * Printer class.
 *
 * Each printer has variables corresponding to the fields of the SQLite database.
 *
 * @author Peter Landoll <plandoll16@jcu.edu>
 * @version 0.1
 * @since 2016-4-12
 */
public class Printer {
    private double locationLatitude, locationLongitude;
    private int statusCode;
    private String name;

    /**
     * Constructor.
     *
     * Default name is "printer", location is the middle of the Admin building
     */
    public Printer() {
        this("printer", 41.490150, -81.531329);
    }

    /**
     * Constructor.
     *
     * Default location is the middle of the Admin building.
     * @param name name of the printer
     */
    public Printer(String name) {
        this(name, 41.490150, -81.531329);
    }

    /**
     * Constructor.
     *
     * @param name name of the printer
     * @param locationLatitude latitude of the location of the printer
     * @param locationLongitude longitude of the location of the printer
     */
    public Printer(String name, double locationLatitude, double locationLongitude) {
        this.name = name;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
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
}
