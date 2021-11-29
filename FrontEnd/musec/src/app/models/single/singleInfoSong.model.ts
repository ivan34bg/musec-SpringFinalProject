export class singleInfoSong{
    private _id: Number = -1;
    private _songName: String = '';

    constructor(){}

    public get id(): Number {
        return this._id;
    }
    public set id(value: Number) {
        this._id = value;
    }
    public get songName(): String {
        return this._songName;
    }
    public set songName(value: String) {
        this._songName = value;
    }
}