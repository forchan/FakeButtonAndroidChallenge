package com.challenge.chris.fakebutton.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Chris on 3/2/18.
 *
 * Data model of a FakeButton user response.
 *
 * FakeButtonUserResponse has 4 attributes: id, name, email, and candidate.
 */

public class FakeButtonUserResponse {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("candidate")
    @Expose
    private String candidate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    @Override
    public String toString() {
        return  "id='" + id + "'\n" +
                "name='" + name + "'\n" +
                "email='" + email + "'\n" +
                "candidate=" + candidate;
    }
}

