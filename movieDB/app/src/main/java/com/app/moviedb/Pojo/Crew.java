package com.app.moviedb.Pojo;

import com.google.gson.annotations.SerializedName;

public class Crew {


    @SerializedName("job")
    private String job;
    @SerializedName("name")
    private String name;
    @SerializedName("profile_path")
    private String profilePath;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePath() {
        return "https://image.tmdb.org/t/p/w500" + profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
