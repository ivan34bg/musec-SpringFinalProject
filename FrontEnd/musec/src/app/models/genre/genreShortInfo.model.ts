export class genreShortInfo{
    private _id: Number = -1;
    private _genreName = '';

    constructor(){}

    public get id(): Number {
        return this._id;
    }
    public set id(value: Number) {
        this._id = value;
    }
    public get genreName() {
        return this._genreName;
    }
    public set genreName(value) {
        this._genreName = value;
    }
}