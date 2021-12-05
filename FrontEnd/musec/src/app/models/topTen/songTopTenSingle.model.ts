export class songTopTenSingle{
    private _id: Number = -1;
    private _singleName = '';
    private _singlePicLocation = '';

    constructor(){}

    public get id(): Number {
        return this._id;
    }
    public set id(value: Number) {
        this._id = value;
    }
    public get singleName() {
        return this._singleName;
    }
    public set singleName(value) {
        this._singleName = value;
    }
    public get singlePicLocation() {
        return this._singlePicLocation;
    }
    public set singlePicLocation(value) {
        this._singlePicLocation = value;
    }
}