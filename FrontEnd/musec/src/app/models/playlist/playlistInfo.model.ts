import { playlistInfoCreator } from "./playlistInfoCreator.model";
import { playlistInfoSong } from "./playlistInfoSong.model";

export class playlistInfo{
    private _playlistName: String = '';
    private _playlistCreator = new playlistInfoCreator();
    private _songs: playlistInfoSong[] = new Array();
    private _canEdit = false;

    constructor(){}

    public get playlistName(): String {
        return this._playlistName;
    }
    public set playlistName(value: String) {
        this._playlistName = value;
    }    
    public get playlistCreator(): playlistInfoCreator {
        return this._playlistCreator;
    }
    public set playlistCreator(value: playlistInfoCreator) {
        this._playlistCreator = value;
    }    
    public get songs(): playlistInfoSong[] {
        return this._songs;
    }
    public set songs(value: playlistInfoSong[]) {
        this._songs = value;
    }    
    public get canEdit() {
        return this._canEdit;
    }
    public set canEdit(value) {
        this._canEdit = value;
    }
}