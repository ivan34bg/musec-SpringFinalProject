export class queueSong{
    private _songLocation: String = '';
    private _songName: String = '';
    private _songPic: String = '';

    constructor(){}

    public get songLocation(): String {
        return this._songLocation;
    }
    public set songLocation(value: String) {
        this._songLocation = value;
    }
    public get songName(): String {
        return this._songName;
    }
    public set songName(value: String) {
        this._songName = value;
    }  
    public get songPic(): String {
        return this._songPic;
    }
    public set songPic(value: String) {
        this._songPic = value;
    }
}