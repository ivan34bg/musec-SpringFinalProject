import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { playlistShortInfo } from 'src/app/models/playlist/playlistShortInfo.model';
import { singleInfo } from 'src/app/models/single/singleInfo.model';
import { PlayerService } from 'src/app/services/player.service';
import { PlaylistService } from 'src/app/services/playlist.service';
import { SingleService } from 'src/app/services/single.service';

@Component({
  selector: 'app-single-view',
  templateUrl: './single-view.component.html',
  styleUrls: ['./single-view.component.scss']
})
export class SingleViewComponent implements OnInit {
  public singleInfo: singleInfo = new singleInfo();
  public playlistId: Number = -1;
  public playlists: playlistShortInfo[] = new Array();
  public doesUserHavePlaylists = false;
  public songId: Number = -1;

  constructor(
    private singleService: SingleService,
    private playerService: PlayerService, 
    private playlistService: PlaylistService,
    private router: Router, 
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.singleService.requestSingleById(this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        let single = JSON.parse(JSON.stringify(response));
        this.singleInfo.singleName = single.singleName;
        this.singleInfo.singlePicLocation = single.singlePicLocation;
        this.singleInfo.song = single.song;
        this.singleInfo.uploader = single.uploader;
      },
      error => {
        alert("This single cannot be found");
        this.router.navigate(['/browse']);
      }
    );
    this.playlistService.returnIfUserHasPlaylists().subscribe(
      response => {
        this.doesUserHavePlaylists = true;
        this.playlistService.returnUserPlaylistsShortInfo().subscribe(
          response => {
            this.playlists = JSON.parse(JSON.stringify(response));
          },
          error => {}
        )
      },
      error => {}
    )
  }
  addToQueue(songId: Number){
    this.playerService.addSongToQueue(songId);
  }
  addSongToPlaylistPopup(songId: Number){
    this.songId = songId;
  }
  addSongToPlaylist(){
    if(this.playlistId > -1){
      this.playlistService.addSongToPlaylist(this.playlistId, this.songId);
      alert("The song was added to the playlist")
    }
    this.songId = -1;
    this.playlistId = -1;
  }
}
