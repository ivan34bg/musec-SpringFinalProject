import { genreExtendedArtistInfo } from "./genreExtendedArtistInfo.model";

export class genreExtendedSongInfo{
    private _id: Number = -1;
    private _songName = '';
    private _uploader: genreExtendedArtistInfo = new genreExtendedArtistInfo();

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
    public get uploader(): genreExtendedArtistInfo {
        return this._uploader;
    }
    public set uploader(value: genreExtendedArtistInfo) {
        this._uploader = value;
    }
}