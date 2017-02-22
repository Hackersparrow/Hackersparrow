package com.hackersparrow.hackersparrow.model;

public class Ship {
    /*
    <barco id="59">
        <img>http://www.spanishcharters.com/imagenes/barcos/59/20-exterior_04-g.png</img>
        <nombre>Bavaria 34</nombre>
        <tipo>Velero</tipo>
        <patron>Obligatorio</patron>
        <pasajeros>6+2</pasajeros>
        <eslora>10.40</eslora>
    </barco>
     */
    private int id;
    private String imgURL;
    private boolean patron;
    private String name;
    private String type;
    private String capability;
    private float meters;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public boolean isPatron() {
        return patron;
    }

    public void setPatron(boolean patron) {
        this.patron = patron;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCapability() {
        return capability;
    }

    public void setCapability(String capability) {
        this.capability = capability;
    }

    public float getMeters() {
        return meters;
    }

    public void setMeters(float meters) {
        this.meters = meters;
    }
}
