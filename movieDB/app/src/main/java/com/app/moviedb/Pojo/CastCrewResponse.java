package com.app.moviedb.Pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastCrewResponse {

    @SerializedName("cast")
    private List<Cast> cast = null;
    @SerializedName("crew")
    private List<Crew> crew = null;



    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }
}
