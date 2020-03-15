/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Adam
 */
public class ABMDevelopTestQ2 {

    //2.	Taking the following XML document, write code to extract the RefText values for the following RefCodes:   ‘MWB’, ‘TRV’ and ‘CAR’
    //The XML Layout provided in the Word document was missing the closing Declaration Tag, so I added it in
    public static void main(String[] args) {
        //Users can check any XML file so long as it is in the 'src/main/' path
        List<String> refTexts = getRefTexts("inputdocument.xml");
        for (String ref : refTexts) {
            System.out.println(ref);    //Loop through List and print value to console
        }
        System.out.println("TEST " + test(refTexts));
    }

    public static List<String> getRefTexts(String fileName) {
        List<String> results = new ArrayList<>();
        try {
            File xmlFile = new File("src/main/" + fileName); //Generate File object using provided filename 
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder(); //Create new builder and generate a document from the file
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();  //Normalise Document
            NodeList nodeList = doc.getElementsByTagName("Reference"); //Search for all Reference Tags
            for (int i = 0; i < nodeList.getLength(); i++) {
                //Loop through Reference tags and store value of RefCode attribute
                String nodeValue = nodeList.item(i).getAttributes().getNamedItem("RefCode").getNodeValue();
                //if Refcode matches required codes, find the value of the child node Reftext.
                if (nodeValue.equals("MWB") || nodeValue.equals("TRV") || nodeValue.equals("CAR")) {
                    //Child node RefText was of type text, not node and getTextContent was required instead
                    String refTextValue = nodeList.item(i).getTextContent().replaceAll("\\s+", "");
                    //Lots of whitespace in the RefText values required the use of replacing all of it using regex
                    results.add(refTextValue);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println("An Error has occurred, Try again");
        }
        return results; //return the arraylist of refText values
    }

    public static String test(List<String> refTexts) {
        //This method compares a given List with a list of the expected results and returns either PASSED or FAILED based on the result.
        List<String> expectedResults = new ArrayList<>();
        expectedResults.add("586133622");
        expectedResults.add("71Q0019681");
        expectedResults.add("1");
        if (expectedResults.equals(refTexts)) {
            return "PASSED";
        }
        return "FAILED";
    }
}
