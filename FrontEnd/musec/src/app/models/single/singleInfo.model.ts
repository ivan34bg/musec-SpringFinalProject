import { singleInfoSong } from "./singleInfoSong.model";
import { singleInfoUser } from "./singleInfoUser.model";

export class singleInfo{
    private _singleName: String = '';
    private _singlePicLocation: String = '';
    private _song = new singleInfoSong();
    private _uploader = new singleInfoUser();

    constructor(){}

    public get song() {
        return this._song;
    }
    public set song(value) {
        this._song = value;
    }
    public get uploader() {
        return this._uploader;
    }
    public set uploader(value) {
        this._uploader = value;
    }
    public get singleName(): String {
        return this._singleName;
    }
    public set singleName(value: String) {
        this._singleName = value;
    }
    public get singlePicLocation(): String {
        return this._singlePicLocation;
    }
    public set singlePicLocation(value: String) {
        this._singlePicLocation = value;
    }
}