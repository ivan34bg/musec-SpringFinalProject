import { Component, ElementRef, IterableDiffers, OnInit, ViewChild } from '@angular/core';
import { pipe } from 'rxjs';
import { queueSong } from 'src/app/models/queue/queueSong.model';
import { PlayerService } from 'src/app/services/player.service';

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.scss']
})
export class PlayerComponent implements OnInit {
  @ViewChild("audioPlayer")player!: ElementRef;
  albumPic = '';
  volume = 0;
  currentDuration = 0;
  totalDuration = 0;
  audioList: queueSong[] = [];
  Math = Math;

  constructor(private playerService: PlayerService, private iterableDiffers: IterableDiffers) { }

  ngOnInit(): void {
  }

  ended(){
    this.player.nativeElement.src = 'https://www.dropbox.com/s/vdbydrniaqydh8a/02.%20GR%21NGOD%20-%20One%20Man%20Show%20%28Official%20Video%29%20prod.NIki.Kotich.mp3?raw=1';
    this.player.nativeElement.play();
  }

  pause(){
    if(this.player.nativeElement.paused){
      this.player.nativeElement.play();
    }
    else{
      this.player.nativeElement.pause();
    }  
  }

  loaded(){
    this.totalDuration = this.player.nativeElement.duration;
  }

  onTimeChange(){
    this.currentDuration = this.player.nativeElement.currentTime;
  }
}
