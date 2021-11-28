export class queueSong{
    private url: String = '';
    private title: String = '';
    private cover: String = '';

    constructor(){}

    public get _url(): String {
        return this.url;
    }
    public set _url(value: String) {
        this.url = value;
    }
    public get _title(): String {
        return this.title;
    }
    public set _title(value: String) {
        this.title = value;
    }
    public get _cover(): String {
        return this.cover;
    }
    public set _cover(value: String) {
        this.cover = value;
    }
}