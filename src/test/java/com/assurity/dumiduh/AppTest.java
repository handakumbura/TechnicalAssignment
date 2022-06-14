package com.assurity.dumiduh;

import com.assurity.dumiduh.common.Constants;
import com.assurity.dumiduh.models.TestData;
import com.assurity.dumiduh.util.JSONUtil;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.assurity.dumiduh.common.Constants.*;


/**
 * Unit test for simple App.
 */
public class AppTest {
    private JsonPath jsonPathEvaluator;
    private TestData expectedData;

    @BeforeClass
    public void setupTest() {
        //calling the API
        RestAssured.baseURI = Constants.BASE_URL;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(Constants.RESOURCE_PATH);
        Logger.getAnonymousLogger().log(Level.INFO, "The API responded with the status code: \t" + String.valueOf(response.getStatusCode()));
        ;
        jsonPathEvaluator = response.jsonPath();

        //reading the test data
        expectedData = JSONUtil.readTestData();
    }

    @Test
    public void galleryPromotionTest() {
        Assert.assertEquals(jsonPathEvaluator.get(Constants.NAME), expectedData.getName(), NAME_IS_NOT_AS_EXPECTED);
        Assert.assertEquals(jsonPathEvaluator.get(Constants.CANRELIST).toString(), expectedData.getCanRelist(), CAN_RELIST_IS_NOT_AS_EXPECTED);
        ArrayList<HashMap<String, Object>> promotions = jsonPathEvaluator.get(Constants.PRMOTIONS);
        for (int x = 0; x < promotions.size(); x++) {
            if (promotions.get(x).get(Constants.NAME).equals(expectedData.getPromotionName())) {
                Assert.assertEquals(promotions.get(x).get(DESCRIPTION), expectedData.getPromotionDescription(), DESCRIPTION_IS_NOT_AS_EXPECTED);
            }
        }

    }
}
