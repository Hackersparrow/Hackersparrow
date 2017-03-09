package com.spanishcharters.spanishcharters.utils;


import android.os.AsyncTask;
import android.util.Log;

import com.spanishcharters.spanishcharters.model.Port;

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

public class PortsParserXML extends AsyncTask<String, Object, List<Port>> {

    public List<Port> portList = new LinkedList<>();
    NodeList nodeList;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Port> doInBackground(String... Url) {
        if (Url[0]==null){
            return portList;
        }else {
            try {
                URL url = new URL(Url[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                // Download the XML file
                Document doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();
                // Locate the Tag Name
                nodeList = doc.getElementsByTagName("destino");

                for (int temp = 0; temp < nodeList.getLength(); temp++) {
                    Node nNode = nodeList.item(temp);

                    Port newPort = new Port();

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        newPort.setId(eElement.getAttribute("id"));
                        newPort.setName(getNode("nombre", eElement));

                        String geolocation = getNode("geolocalizacion", eElement);

                        String[] separated = geolocation.split(",");
                        newPort.setLatitude(Float.parseFloat(separated[0]));
                        newPort.setLongitude(Float.parseFloat(separated[1]));

                    }
                    portList.add(newPort);
                }

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return portList;
        }
    }

    @Override
    protected void onPostExecute(List<Port> result) {
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