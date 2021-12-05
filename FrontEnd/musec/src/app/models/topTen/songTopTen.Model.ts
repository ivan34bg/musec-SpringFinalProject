import { songTopTenAlbum } from "./songTopTenAlbum.model";
import { songTopTenSingle } from "./songTopTenSingle.model";
import { songTopTenUploader } from "./songTopTenUploader.model";

export class songTopTen{
    private _id: Number = -1;
    private _songName = '';
    private _uploader: songTopTenUploader = new songTopTenUploader();
    private _album: songTopTenAlbum = new songTopTenAlbum();
    private _single: songTopTenSingle = new songTopTenSingle(); 

    constructor(){};

    public get id(): Number {
        return this._id;
    }
    public set id(value: Number) {
        this._id = value;
    }
    public get songName() {
        return this._songName;
    }
    public set songName(value) {
        this._songName = value;
    }   
    public get uploader(): songTopTenUploader {
        return this._uploader;
    }
    public set uploader(value: songTopTenUploader) {
        this._uploader = value;
    }
    public get album(): songTopTenAlbum {
        return this._album;
    }
    public set album(value: songTopTenAlbum) {
        this._album = value;
    }
    public get single(): songTopTenSingle {
        return this._single;
    }
    public set single(value: songTopTenSingle) {
        this._single = value;
    }
}