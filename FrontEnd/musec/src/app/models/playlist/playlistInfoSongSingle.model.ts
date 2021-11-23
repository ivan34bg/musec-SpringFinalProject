export class playlistInfoSongSingle{
    private _id: Number = -1;
    private _singleName: String = '';

    constructor(){}

    public get id(): Number {
        return this._id;
    }
    public set id(value: Number) {
        this._id = value;
    }
    public get singleName(): String {
        return this._singleName;
    }
    public set singleName(value: String) {
        this._singleName = value;
    }
}