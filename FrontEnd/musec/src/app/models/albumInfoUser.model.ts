export class albumInfoUser{
    private _fullName: String = '';
    private _profilePicLink: String = '';

    constructor(){}

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