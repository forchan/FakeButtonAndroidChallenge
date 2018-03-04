package com.challenge.chris.fakebutton.rest;

/**
 * Created by Chris on 3/2/18.
 *
 * This utility class contains the base URL and provides the FakeButtonApiService interface with
 * a get FakeButtonApiService static method to the rest of this application.
 */

public class FakeButtonApiUtil {

    private static final String BASE_URL = "http://fake-button.herokuapp.com";

    private FakeButtonApiUtil() {}

    public static FakeButtonApiService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(FakeButtonApiService.class);
    }
}
