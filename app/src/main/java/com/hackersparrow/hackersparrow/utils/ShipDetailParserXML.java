package com.hackersparrow.hackersparrow.utils;


import android.os.AsyncTask;
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

    NodeList nodeList;

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

            getImages(newShip, nodeList, doc, Url);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        //System.out.println(newShip.getDetailImages().toString()); --- IMAGE URLs ARE OK
        return newShip;
    }

    @Override
    protected void onPostExecute(Ship result) {
        super.onPostExecute(result);
    }

    // getNode function
    private static String getNode(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();

    }

    public static void getImages(Ship ship, NodeList nodeList, Document doc, String... Url){
        nodeList = doc.getElementsByTagName("img");

        List<String> urls = new LinkedList<>();
        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node nNode = nodeList.item(temp);
            urls.add(nNode.getTextContent());
        }
        ship.setDetailImages(urls);
    }


}