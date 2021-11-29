import { queueFullSongAlbum } from "./queueFullSongAlbum.model";
import { queueFullSongArtist } from "./queueFullSongArtist.model";
import { queueFullSongSingle } from "./queueFullSongSingle.model";

export class queueFullSongInfo{
    private _id: number = -1;
    private _songName: String = '';
    private _uploader = new queueFullSongArtist();
    private _album = new queueFullSongAlbum();
    private _single = new queueFullSongSingle();

    constructor(){}

    public get id(): number {
        return this._id;
    }
    public set id(value: number) {
        this._id = value;
    }
    public get songName(): String {
        return this._songName;
    }
    public set songName(value: String) {
        this._songName = value;
    }
    public get uploader() {
        return this._uploader;
    }
    public set uploader(value) {
        this._uploader = value;
    }
    public get album() {
        return this._album;
    }
    public set album(value) {
        this._album = value;
    }
    public get single() {
        return this._single;
    }
    public set single(value) {
        this._single = value;
    }
}