package com.idata365.col.util;
public class Gps {

private double lng;
private double lat;

public Gps(double lng, double lat) {
this.lng = lng;
this.lat = lat;
}


public double getLng() {
return lng;
}


public void setLng(double lng) {
this.lng = lng;
}


public double getLat() {
return lat;
}


public void setLat(double lat) {
this.lat = lat;
}


@Override
public String toString() {
return lng + "," + lat;
}

}