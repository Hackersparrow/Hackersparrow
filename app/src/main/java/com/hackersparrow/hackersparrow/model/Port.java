package com.hackersparrow.hackersparrow.model;

import com.hackersparrow.hackersparrow.utils.MapPinsAdder;

public class Port implements MapPinsAdder.MapPinnable<Port>{
    private String id;
    private String name;
    private float latitude;
    private float longitude;

    public Port() {
    }

    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public float getLatitude() {
        return this.latitude;
    }

    public void setLatitude(float latitudes) {
        this.latitude = latitudes;
    }

    public void setLongitude(float longitudes) {
        this.longitude = longitudes;
    }

    @Override
    public float getLongitude() {
        return this.longitude;
    }

    @Override
    public String getPinDescription() {
        return this.getName();
    }

    @Override
    public String getPinImageUrl() {
        return this.getUrl();
    }

    @Override
    public Port getRelatedModelObject() {
        return this;
    }
}
