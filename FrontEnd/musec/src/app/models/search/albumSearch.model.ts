export class albumSearch{
    private _id: Number = -1;
    private _albumName = '';

    constructor(){}

    public get id(): Number {
        return this._id;
    }
    public set id(value: Number) {
        this._id = value;
    }
    public get albumName() {
        return this._albumName;
    }
    public set albumName(value) {
        this._albumName = value;
    }
}