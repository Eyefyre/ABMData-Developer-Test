/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abmdeveloptestq1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adam
 */
public class ABMDevelopTestQ1 {

    public static String edifact = "UNA:+.?'"
            + "UNB+UNOC:3+2021000969+4441963198+180525:1225+3VAL2MJV6EH9IX+KMSV7HMD+CUSDECU-IE++1++1'"
            + "UNH+EDIFACT+CUSDEC:D:96B:UN:145050'"
            + "BGM+ZEM:::EX+09SEE7JPUV5HC06IC6+Z'"
            + "LOC+17+IT044100'"
            + "LOC+18+SOL'"
            + "LOC+35+SE'"
            + "LOC+36+TZ'"
            + "LOC+116+SE003033'"
            + "DTM+9:20090527:102'"
            + "DTM+268:20090626:102'"
            + "DTM+182:20090527:102'";

    public static void main(String[] args) {
        List<String> edifactLOCs = parseLOCs(); //Create List and assign it to return value of parseLOCs()
        for(String LOC: edifactLOCs){
            System.out.println(LOC);    //Loop through List and print value to console
        }
        System.out.println("TEST " + test(edifactLOCs)); //Print out return value of test method.
    }

    public static List<String> parseLOCs() {
        List<String> results = new ArrayList<>();
        String[] lines = edifact.split("'"); //Split text into lines using '. 
        for (String line : lines) {          //Loop through lines and if the line has "LOC" in in, split that line using +
            if (line.contains("LOC")) {
                String[] locs = line.split("\\+"); // + symbol must be escaped due to split method taking regex expressions.
                results.add(locs[1]);
                results.add(locs[2]); //Add 2nd and 3rd element to list and return it.
            }
        }
        return results;
    }
    
    public static String test(List<String> LOCS){
        //Create List of results that we want and compare it to the given List. If equal return PASSED, if not return FAILED
        List<String> expectedResults = new ArrayList<>();
        expectedResults.add("17");
        expectedResults.add("IT044100");
        expectedResults.add("18");
        expectedResults.add("SOL");
        expectedResults.add("35");
        expectedResults.add("SE");
        expectedResults.add("36");
        expectedResults.add("TZ");
        expectedResults.add("116");
        expectedResults.add("SE003033");
        if(expectedResults.equals(LOCS)){
            return "PASSED";
        }
        return "FAILED";
    }
}
