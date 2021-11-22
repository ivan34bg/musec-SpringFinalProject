import { profileInfoAlbum } from "./profileInfoAlbum.model";
import { profileInfoPlaylist } from "./profileInfoPlaylist.model";
import { profileInfoSingle } from "./profileInfoSingle.model";

export class profileInfo{  
    private _profilePicLink: String = '';      
    private _username: String = '';
    private _roleNames: String[] = new Array;
    private _playlists: profileInfoPlaylist[]  = new Array;
    private _albums: profileInfoAlbum[]  = new Array;
    private _singles: profileInfoSingle[] = new Array;
    constructor(

    ){}

    public get singles(): profileInfoSingle[] {
        return this._singles;
    }
    public set singles(value: profileInfoSingle[]) {
        this._singles = value;
    }
    public get albums(): profileInfoAlbum[] {
        return this._albums;
    }
    public set albums(value: profileInfoAlbum[]) {
        this._albums = value;
    }
    public get playlists(): profileInfoPlaylist[] {
        return this._playlists;
    }
    public set playlists(value: profileInfoPlaylist[]) {
        this._playlists = value;
    }
    public get roleNames(): String[] {
        return this._roleNames;
    }
    public set roleNames(value: String[]) {
        this._roleNames = value;
    }
    public get username(): String {
        return this._username;
    }
    public set username(value: String) {
        this._username = value;
    }
    public get profilePicLink(): String {
        return this._profilePicLink;
    }
    public set profilePicLink(value: String) {
        this._profilePicLink = value;
    }
}