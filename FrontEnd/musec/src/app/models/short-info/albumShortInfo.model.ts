export class albumShortInfo{
    private _id = -1;
    private _albumName = '';

    constructor(){}

    public get id() {
        return this._id;
    }
    public set id(value) {
        this._id = value;
    }
    public get albumName() {
        return this._albumName;
    }
    public set albumName(value) {
        this._albumName = value;
    }
}