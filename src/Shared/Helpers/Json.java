/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared.Helpers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Json {

    static JSONParser parser = new JSONParser();

    public static JSONObject parse(String str) {
        return (JSONObject) Json.parseObj(str);
    }

    public static JSONArray parseArray(String str) {
        return (JSONArray) Json.parseObj(str);
    }

    public static Object parseObj(String str) {
        try {
            Object obj = parser.parse(str);
            return obj;
        } catch (ParseException ex) {
            System.err.println("Error while parse json. " + ex.getMessage());
        }

        return null;
    }
}
