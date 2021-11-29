import { Component, HostBinding } from "@angular/core";

export class albumInfoSong{
    private _id: Number = -1;

    private _songName: String = ''
    private _songGenre: String = ''
    private _rColor: Number = 0;
    private _gColor: Number = 0;
    private _bColor: Number = 0;

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
    public get rColor(): Number {
        return this._rColor;
    }
    public set rColor(value: Number) {
        this._rColor = value;
    }
    public get gColor(): Number {
        return this._gColor;
    }
    public set gColor(value: Number) {
        this._gColor = value;
    }
    public get bColor(): Number {
        return this._bColor;
    }
    public set bColor(value: Number) {
        this._bColor = value;
    }
}