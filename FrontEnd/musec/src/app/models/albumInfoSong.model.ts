import { Component, HostBinding } from "@angular/core";

export class albumInfoSong{
    private _id: Number = -1;

    private _songName: String = ''
    private _songGenre: String = ''

    constructor(){}

    public get id(): Number {
        return this._id;
    }
    public set id(value: Number) {
        this._id = value;
    }
    public get songName(): String {
        return this._songName
    }
    public set songName(value: String) {
        this._songName = value
    }
    public get songGenre(): String {
        return this._songGenre
    }
    public set songGenre(value: String) {
        this._songGenre = value
    }    
}