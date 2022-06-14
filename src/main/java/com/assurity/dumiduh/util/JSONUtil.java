package com.assurity.dumiduh.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.assurity.dumiduh.models.TestData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONUtil {

    @SuppressWarnings("unchecked")
    public static TestData readTestData() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("src\\main\\java\\com\\assurity\\dumiduh\\resources\\test_data.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray testDataList = (JSONArray) obj;
            return parseTheTestDataObject((JSONObject) testDataList.get(0));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static TestData parseTheTestDataObject(JSONObject testDataObject) {
        TestData testData = new TestData();
        testData.setName((String) testDataObject.get("Name"));
        testData.setCanRelist((String) testDataObject.get("CanRelist"));
        JSONObject promotions = (JSONObject) testDataObject.get("Promotions");
        testData.setPromotionName((String) promotions.get("Name"));
        testData.setPromotionDescription((String) promotions.get("Description"));
        return testData;
    }
}

