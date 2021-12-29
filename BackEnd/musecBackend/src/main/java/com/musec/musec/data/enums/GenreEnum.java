package com.musec.musec.data.enums;

public enum GenreEnum {
    Rock, Pop, HipHop("Hip Hop"), Jazz, Country, Folk, Metal, HeavyMetal("Heavy Metal"), Blues, RNB,
    Electronic, Classical, Reggae, Instrumental, Opera, Dubstep;

    private final String properName;

    GenreEnum(){
        properName = this.name();
    }

    GenreEnum(String properName){
        this.properName = properName;
    }

    public String getProperName() {
        return properName;
    }
}
