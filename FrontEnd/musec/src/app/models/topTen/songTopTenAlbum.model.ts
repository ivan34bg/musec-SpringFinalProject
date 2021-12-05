export class songTopTenAlbum{
    private _id: Number = -1;
    private _albumName = '';    
    private _albumPicLocation = '';

    constructor(){}

    public get id(): Number {
        return this._id;
    }
    public set id(value: Number) {
        this._id = value;
    }
    public get albumName() {
        return this._albumName;
    }
    public set albumName(value) {
        this._albumName = value;
    }
    public get albumPicLocation() {
        return this._albumPicLocation;
    }
    public set albumPicLocation(value) {
        this._albumPicLocation = value;
    }
}