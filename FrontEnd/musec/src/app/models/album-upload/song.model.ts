export class songAlbumUpload{
    private _songFile!: File;
    private _songName: string = '';
    private _songGenre: string = '';

    constructor(){}

    public get songFile(): File {
        return this._songFile;
    }
    public set songFile(value: File) {
        this._songFile = value;
    }
    public get songName(): string {
        return this._songName;
    }
    public set songName(value: string) {
        this._songName = value;
    }   
    public get songGenre(): string {
        return this._songGenre;
    }
    public set songGenre(value: string) {
        this._songGenre = value;
    }
}