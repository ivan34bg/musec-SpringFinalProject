import { genreExtendedSongInfo } from "./genreExtendedSongInfo.model";

export class genreExtendedInfo{
    private _genreName = '';
    private _songs: genreExtendedSongInfo[] = new Array();

    constructor(){};

    public get genreName() {
        return this._genreName;
    }
    public set genreName(value) {
        this._genreName = value;
    }
    public get songs(): genreExtendedSongInfo[] {
        return this._songs;
    }
    public set songs(value: genreExtendedSongInfo[]) {
        this._songs = value;
    }
}