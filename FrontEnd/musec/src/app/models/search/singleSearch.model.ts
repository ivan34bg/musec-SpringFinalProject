export class singleSearch{
    private _id: Number = -1;
    private _singleName = '';

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
}