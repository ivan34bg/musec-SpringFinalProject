export class singleShortInfo{
    private _id = -1;
    private _singleName = '';

    constructor(){}

    public get id() {
        return this._id;
    }
    public set id(value) {
        this._id = value;
    }
    public get singleName() {
        return this._singleName;
    }
    public set singleName(value) {
        this._singleName = value;
    }
}