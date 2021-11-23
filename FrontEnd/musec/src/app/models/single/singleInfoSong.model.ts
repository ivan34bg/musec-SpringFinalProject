export class singleInfoSong{
    private _songName: String = '';
    private _songLocation: String = '';

    constructor(){}

    public get songName(): String {
        return this._songName;
    }
    public set songName(value: String) {
        this._songName = value;
    }
    public get songLocation(): String {
        return this._songLocation;
    }
    public set songLocation(value: String) {
        this._songLocation = value;
    }
}