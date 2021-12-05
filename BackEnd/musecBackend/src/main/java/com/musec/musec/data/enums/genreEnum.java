package com.musec.musec.data.enums;

public enum genreEnum {
    Rock, Pop, HipHop("Hip Hop"), Jazz, Country, Folk, Metal, HeavyMetal("Heavy Metal"), Blues, RNB,
    Electronic, Classical, Reggae, Instrumental, Opera, Dubstep;

    private final String properName;

    genreEnum(){
        properName = this.name();
    }

    genreEnum(String properName){
        this.properName = properName;
    }

    public String getProperName() {
        return properName;
    }
}
