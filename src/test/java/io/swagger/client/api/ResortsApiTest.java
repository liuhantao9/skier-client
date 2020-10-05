/*
 * Ski Data API for NEU Seattle distributed systems course
 * An API for an emulation of skier managment system for RFID tagged lift tickets. Basis for CS6650 Assignments for 2019
 *
 * OpenAPI spec version: 1.13
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.swagger.client.api;

import io.swagger.client.ApiException;
import io.swagger.client.model.ResponseMsg;
import io.swagger.client.model.TopTen;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for ResortsApi
 */
@Ignore
public class ResortsApiTest {

    private final ResortsApi api = new ResortsApi();

    /**
     * get the top 10 skier vertical totals for this day
     *
     * get the top 10 skier vertical totals for this day
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getTopTenVertTest() throws ApiException {
        List<String> resort = null;
        List<String> dayID = null;
        TopTen response = api.getTopTenVert(resort, dayID);

        // TODO: test validations
    }
}
