export class songTopTenUploader{
    private _id: Number = -1;
    private _fullName = '';

    constructor(){}

    public get id(): Number {
        return this._id;
    }
    public set id(value: Number) {
        this._id = value;
    }
    public get fullName() {
        return this._fullName;
    }
    public set fullName(value) {
        this._fullName = value;
    }
}