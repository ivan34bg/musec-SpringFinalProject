export class queueFullSongAlbum{
    private _id: Number = -1;
    private _albumName: String = '';

    constructor(){}

    public get id(): Number {
        return this._id;
    }
    public set id(value: Number) {
        this._id = value;
    }
    public get albumName(): String {
        return this._albumName;
    }
    public set albumName(value: String) {
        this._albumName = value;
    }
}