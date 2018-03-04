package com.challenge.chris.fakebutton.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Chris on 3/2/18.
 *
 * Data model of a FakeButton user.
 *
 * FakeButtonUser has 3 attributes: name, email, and candidate.
 */

public class FakeButtonUser {

    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("candidate")
    private String candidate;

    public FakeButtonUser(String name, String email, String candidate) {
        this.name = name;
        this.email = email;
        this.candidate = candidate;
    }

    @Override
    public String toString() {
        return  "name='" + name + '\'' +
                "email='" + email + '\'' +
                "candidate=" + candidate;
    }

}
