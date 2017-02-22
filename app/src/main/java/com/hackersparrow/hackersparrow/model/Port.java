package com.hackersparrow.hackersparrow.model;

import com.hackersparrow.hackersparrow.utils.MapPinsAdder;

public class Port implements MapPinsAdder.MapPinnable<Port>{
    private String id;
    private String name;
    private float latitudes;
    private float longitudes;

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

    public float getLatitudes() {
        return latitudes;
    }

    public void setLatitudes(float latitudes) {
        this.latitudes = latitudes;
    }

    public float getLongitudes() {
        return longitudes;
    }

    public void setLongitudes(float longitudes) {
        this.longitudes = longitudes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public float getLatitude() {
        return this.getLatitudes();
    }

    @Override
    public float getLongitude() {
        return this.getLongitudes();
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
