import { albumInfoSong } from "./albumInfoSong.model";
import { albumInfoUser } from "./albumInfoUser.model";

export class albumInfo{
    private _albumPicLink: String = '';
    private _albumName: String = '';
    private _albumCreator: albumInfoUser = new albumInfoUser();
    private _albumSongs: albumInfoSong[] = new Array();

    constructor(){}    

    public get albumPicLink(): String {
        return this._albumPicLink;
    }
    public set albumPicLink(value: String) {
        this._albumPicLink = value;
    }
    public get albumName(): String {
        return this._albumName;
    }
    public set albumName(value: String) {
        this._albumName = value;
    }    
    public get albumCreator(): albumInfoUser {
        return this._albumCreator;
    }
    public set albumCreator(value: albumInfoUser) {
        this._albumCreator = value;
    }
    public get albumSongs(): albumInfoSong[] {
        return this._albumSongs;
    }
    public set albumSongs(value: albumInfoSong[]) {
        this._albumSongs = value;
    }
}