import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { interval } from 'rxjs';
import { queueSong } from 'src/app/models/queue/queueSong.model';
import { PlayerService } from 'src/app/services/player.service';

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.scss']
})
export class PlayerComponent implements OnInit {
  @ViewChild("audioPlayer")player!: ElementRef;
  @ViewChild("albumPic")albumPic!: ElementRef;
  @ViewChild("songTitle")songTitle!: ElementRef;
  songArr: queueSong[] = new Array();
  currentSongId: number = 0;
  volume = 0;
  currentDuration = 0;
  totalDuration = 0;
  Math = Math;

  constructor(private playerService: PlayerService) { } 
  
  get isSynced(){
    return this.playerService.isSynced;
  }

  ngOnInit(): void {
    this.syncQueue();
    interval(500).subscribe(s =>{
        if(!this.isSynced){
          this.syncQueue();
        }
      }
    )
  }

  ended(){
    this.syncQueue();
    if(this.currentSongId === this.songArr.length - 1){
      this.currentSongId = 0;
    }
    else {
      this.currentSongId+=1;
    }
    this.player.nativeElement.src = this.songArr[this.currentSongId].songLocation;
    this.albumPic.nativeElement.src = this.songArr[this.currentSongId].songPic;
    this.songTitle.nativeElement.textContent = this.songArr[this.currentSongId].songName;
  }

  pause(){
    console.log(this.songArr)
    if(this.player.nativeElement.paused){
      this.player.nativeElement.play();
    }
    else{
      this.player.nativeElement.pause();
    }  
  }

  backwards(){
    this.syncQueue();
    if(this.currentSongId === 0){
      this.currentSongId = this.songArr.length - 1;
    }
    else {
      this.currentSongId--;
    }
    this.player.nativeElement.src = this.songArr[this.currentSongId].songLocation;
    this.albumPic.nativeElement.src = this.songArr[this.currentSongId].songPic;
    this.songTitle.nativeElement.textContent = this.songArr[this.currentSongId].songName;
  }

  loaded(){
    this.totalDuration = this.player.nativeElement.duration;
  }

  syncQueue(){
    this.playerService.returnQueue().subscribe(
      response => {
        this.songArr = JSON.parse(JSON.stringify(response));
        this.playerService.isSynced = true;
      },
      error => { }
    );
  }
}
