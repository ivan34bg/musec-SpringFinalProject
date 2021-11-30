export class playlistShortInfo{
    private _id: Number = -1;
    private _playlistName = '';

    constructor(){}

    public get id(): Number {
        return this._id;
    }
    public set id(value: Number) {
        this._id = value;
    }
    public get playlistName() {
        return this._playlistName;
    }
    public set playlistName(value) {
        this._playlistName = value;
    }
}