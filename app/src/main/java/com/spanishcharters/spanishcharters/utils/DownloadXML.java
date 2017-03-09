package com.spanishcharters.spanishcharters.utils;


import android.os.AsyncTask;
import android.util.Log;

import com.spanishcharters.spanishcharters.model.Ship;

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

public class DownloadXML extends AsyncTask<String, Object, List<Ship>> {

    public List<Ship> shipList = new LinkedList<>();
    NodeList nodeList;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Ship> doInBackground(String... Url) {
        try {
            URL url = new URL(Url[0]);
            DocumentBuilderFactory dbf = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Download the XML file
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();
            // Locate the Tag Name
            nodeList = doc.getElementsByTagName("barco");

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node nNode = nodeList.item(temp);

            Ship newShip = new Ship();

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                newShip.setId(eElement.getAttribute("id"));
                newShip.setImgURL(getNode("img", eElement));
                newShip.setName(getNode("nombre", eElement));
                newShip.setType(getNode("tipo", eElement));
                newShip.setPatron(getNode("patron", eElement));
                newShip.setCapability(getNode("pasajeros", eElement));
                newShip.setMeters(getNode("eslora", eElement));

            }
            shipList.add(newShip);
        }

        return shipList;
    }

    @Override
    protected void onPostExecute(List<Ship> result) {
        super.onPostExecute(result);
    }

    // getNode function
    private static String getNode(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
                .getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();

    }
}