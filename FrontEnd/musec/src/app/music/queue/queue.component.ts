import { Component, OnInit } from '@angular/core';
import { playlistInfo } from 'src/app/models/playlist/playlistInfo.model';
import { queueFullSongInfo } from 'src/app/models/queue/queueFullSongInfo.model';
import { queueSong } from 'src/app/models/queue/queueSong.model';
import { PlayerService } from 'src/app/services/player.service';

@Component({
  selector: 'app-queue',
  templateUrl: './queue.component.html',
  styleUrls: ['./queue.component.scss']
})
export class QueueComponent implements OnInit {
  public queue: queueFullSongInfo[] = new Array();

  constructor(private playerService: PlayerService) { }

  ngOnInit(): void {
    this.syncQueue();
  }

  deleteSong(songId: number){
    this.playerService.removeFromQueue(songId).subscribe(
      response => {
        this.syncQueue();
        this.playerService.isSynced = false;
      },
      error => {}
    );
  }
  syncQueue(){
    this.playerService.returnQueueSongInformation().subscribe(
      response => {
        this.queue = JSON.parse(JSON.stringify(response));
      },
      error => {}
    )
  }
}
