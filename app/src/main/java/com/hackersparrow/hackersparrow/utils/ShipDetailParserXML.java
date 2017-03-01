package com.hackersparrow.hackersparrow.utils;


import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import com.hackersparrow.hackersparrow.model.Ship;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ShipDetailParserXML extends AsyncTask<String, Object, Ship> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Ship doInBackground(String... Url) {
        Ship newShip = new Ship();
        try {
            URL url = new URL(Url[0]);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            getImages(newShip, doc, Url);
            getBasicInfo(newShip, doc, Url);
            getInfo(newShip, doc, Url);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        showTestInfo(newShip);
        return newShip;
    }

    @Override
    protected void onPostExecute(Ship result) {
        super.onPostExecute(result);
    }

    private void showTestInfo(Ship newShip) {
        System.out.println(newShip.getDetailImages().toString()); //--- IMAGE URLs ARE OK
        System.out.println("Nombre: " + newShip.getName());
        System.out.println("Tipo: " + newShip.getType());
        System.out.println("Patron: " + newShip.getPatron());
        System.out.println("Pasajeros: " + newShip.getCapability());
        System.out.println("Cabinas: " + newShip.getRooms());
        System.out.println("Eslora: " + newShip.getMeters());
        System.out.println("WC: " + newShip.getWc());
        System.out.println("Precio: " + newShip.getPrice());
        System.out.println("Equipo: " + newShip.getEquip());
        System.out.println("Especificaciones: " + newShip.getEspecifications());
        System.out.println("Extras: " + newShip.getExtras());
        System.out.println("Extras opcionales: " + newShip.getOptionalExtras());
    }

    // getNode function
    private String getNode(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();

    }

    public void getImages(Ship ship, Document doc, String... Url) {
        NodeList nodeList = doc.getElementsByTagName("img");

        List<String> urls = new LinkedList<>();
        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node nNode = nodeList.item(temp);
            urls.add(nNode.getTextContent());
        }
        ship.setDetailImages(urls);
    }

    public void getBasicInfo(Ship ship, Document doc, String... Url) {
        NodeList nodeList = doc.getElementsByTagName("barco");
        for (int temp = 0; temp < nodeList.getLength(); temp++) {

            Node nNode = nodeList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                ship.setName(getNode("nombre", eElement));
                ship.setType(getNode("tipo", eElement));
                ship.setPatron(getNode("patron", eElement));
                ship.setCapability(getNode("pasajeros", eElement));
                ship.setRooms(getNode("cabinas", eElement));
                ship.setMeters(getNode("eslora", eElement));
                ship.setWc(getNode("wc", eElement));
                ship.setPrice(getNode("precio", eElement));
            }
        }
    }

    public void getInfo(Ship ship, Document doc, String... Url) {
        NodeList nodeList = doc.getElementsByTagName("info");
        for (int temp = 0; temp < nodeList.getLength(); temp++) {

            Node nNode = nodeList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String especifications = Html.fromHtml(getNode("especificaciones", eElement)).toString();

                ship.setEquip(getNode("equipamiento", eElement));
                ship.setEspecifications(especifications);
                ship.setExtras(getNode("extras", eElement));
                ship.setOptionalExtras(getNode("extras_opcionales", eElement));

            }
        }
    }

}