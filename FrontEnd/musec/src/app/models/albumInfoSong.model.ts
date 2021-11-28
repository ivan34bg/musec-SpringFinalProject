import { Component, HostBinding } from "@angular/core";

export class albumInfoSong{
    private _songName: String = ''
    private _songGenre: String = ''
    private _songLocation: String = ''
    private _rColor: Number = 0;
    private _gColor: Number = 0;
    private _bColor: Number = 0;

    constructor(){}
    
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
    public get songLocation(): String {
        return this._songLocation
    }
    public set songLocation(value: String) {
        this._songLocation = value
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