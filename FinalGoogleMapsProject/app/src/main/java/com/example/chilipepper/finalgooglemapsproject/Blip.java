package com.example.chilipepper.finalgooglemapsproject;

/**
 * Created by delshawnkirksey on 12/9/15.
 */
public class Blip {

    public String _inspectionType, _violationDescr, _businessName;
    public double _longitude, _latitude;

    public Blip (String inspectionType, String violationDescr, String businessName, double longitude, double latitude)
    {
        _inspectionType = inspectionType;
        _violationDescr = violationDescr;
        _businessName = businessName;
        _longitude = longitude;
        _latitude = latitude;
    }

    public String get_inspectionType() { return _inspectionType; }
    public String get_violationDescr() { return _violationDescr; }
    public String get_businessName() { return _businessName; }
    public double get_longitude() { return _longitude; }
    public double get_latitude() { return _latitude; }

}
