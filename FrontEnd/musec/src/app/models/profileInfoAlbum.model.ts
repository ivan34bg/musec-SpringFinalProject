export class profileInfoAlbum{

    constructor(
        private _id: String,
        private _albumName: String,
        private _albumPicLocation: String
    ){}

    public get albumName(): String {
        return this._albumName;
    }
    public set albumName(value: String) {
        this._albumName = value;
    }
    public get id(): String {
        return this._id;
    }
    public set id(value: String) {
        this._id = value;
    }    
    public get albumPicLocation(): String {
        return this._albumPicLocation;
    }
    public set albumPicLocation(value: String) {
        this._albumPicLocation = value;
    }
}