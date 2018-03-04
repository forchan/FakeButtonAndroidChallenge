package com.challenge.chris.fakebutton.rest;

import com.challenge.chris.fakebutton.model.FakeButtonUser;
import com.challenge.chris.fakebutton.model.FakeButtonUserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Chris on 3/2/18.
 *
 * This interface contains methods that are going to be used to execute HTTP requests.
 *
 * Fake Button's API docs can be found at:
 * http://fake-button.herokuapp.com/docs/index.html
 */

public interface FakeButtonApiService {

    @POST("/user")
    Call<FakeButtonUserResponse> createUser(@Body FakeButtonUser fakeButtonUser);

    @GET("/user")
    Call<List<FakeButtonUserResponse>> getUsersByCandidate(@Query("candidate") String candidate);

}
