export class albumInfoUser{
    private _id: Number = -1;
    private _fullName: String = '';
    private _profilePicLink: String = '';

    constructor(){}

    public get id(): Number {
        return this._id;
    }
    public set id(value: Number) {
        this._id = value;
    }
    public get fullName(): String {
        return this._fullName;
    }
    public set fullName(value: String) {
        this._fullName = value;
    }
    public get profilePicLink(): String {
        return this._profilePicLink;
    }
    public set profilePicLink(value: String) {
        this._profilePicLink = value;
    }
}