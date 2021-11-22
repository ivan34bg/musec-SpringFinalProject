export class profileInfoSingle{
    constructor(
        private _id: String,
        private _name: String
    ){}

    public get name(): String {
        return this._name;
    }
    public set name(value: String) {
        this._name = value;
    }
    public get id(): String {
        return this._id;
    }
    public set id(value: String) {
        this._id = value;
    }
}