export class songSearch{
    private _id: Number = -1;
    private _songName = '';
    private _albumId: Number | undefined;
    private _singleId: Number | undefined;

    constructor(){}

    public get id(): Number {
        return this._id;
    }
    public set id(value: Number) {
        this._id = value;
    }
    public get songName() {
        return this._songName;
    }
    public set songName(value) {
        this._songName = value;
    }
    public get albumId(): Number | undefined {
        return this._albumId;
    }
    public set albumId(value: Number | undefined) {
        this._albumId = value;
    }
    public get singleId(): Number | undefined {
        return this._singleId;
    }
    public set singleId(value: Number | undefined) {
        this._singleId = value;
    }
}