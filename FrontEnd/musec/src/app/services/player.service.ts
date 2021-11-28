import { Injectable, Input } from '@angular/core';
import { queueSong } from '../models/queue/queueSong.model';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  private queueArr: queueSong[] = new Array();

  constructor() { }

  returnQueue(){
    let sample = new queueSong();
    sample._url = "https://www.dropbox.com/s/8b5vco7z78tu7nv/music.mp3?raw=1";
    sample._title = "second";
    sample._cover = "https://www.dropbox.com/s/m5o07yqjmm705bd/1649-peepotired.png?raw=1";
    this.queueArr.push(sample);
    return this.queueArr;
  }
  addSongToQueue(songUrl: String, songTitle: String, songPicUrl: String){
    let newSong = new queueSong();
    newSong._url = songUrl;
    newSong._title = songTitle;
    newSong._cover = songPicUrl;

    this.queueArr.push(newSong);
  }
  addCollectionToQueue(){}
  removeFromQueue(queueIndex: number){
    this.queueArr.splice(queueIndex, 1)
  }
}
