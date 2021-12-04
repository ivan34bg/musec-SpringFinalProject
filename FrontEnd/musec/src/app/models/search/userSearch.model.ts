export class userSearch{
    private _id = -1;
    private _fullName = '';

    constructor(){}

    public get id() {
        return this._id;
    }
    public set id(value) {
        this._id = value;
    }
    public get fullName() {
        return this._fullName;
    }
    public set fullName(value) {
        this._fullName = value;
    }
}