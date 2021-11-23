import { playlistInfoSongAlbum } from "./playlistInfoSongAlbum.model";
import { playlistInfoSongArtist } from "./playlistInfoSongArtist.model";
import { playlistInfoSongSingle } from "./playlistInfoSongSingle.model";

export class playlistInfoSong{
    private _songName: String = '';
    private _uploader = new playlistInfoSongArtist();
    private _album = new playlistInfoSongAlbum();
    private _single = new playlistInfoSongSingle();

    constructor(){}

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