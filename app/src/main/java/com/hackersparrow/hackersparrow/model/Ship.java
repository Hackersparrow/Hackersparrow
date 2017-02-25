package com.hackersparrow.hackersparrow.model;

import java.io.Serializable;
import java.util.List;

public class Ship implements Serializable {
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
    private String id;
    private String imgURL;
    private String patron;
    private String name;
    private String type;
    private String capability;
    private String meters;

    private List<String> detailImages;

    public List<String> getDetailImages() {
        return detailImages;
    }

    public void setDetailImages(List<String> detailImages) {
        this.detailImages = detailImages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getPatron() {
        return patron;
    }

    public void setPatron(String patron) {
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

    public String getMeters() {
        return meters;
    }

    public void setMeters(String meters) {
        this.meters = meters;
    }
}
