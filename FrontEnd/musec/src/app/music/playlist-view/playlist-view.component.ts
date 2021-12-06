import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { playlistInfo } from 'src/app/models/playlist/playlistInfo.model';
import { songSearch } from 'src/app/models/search/songSearch.model';
import { PlayerService } from 'src/app/services/player.service';
import { PlaylistService } from 'src/app/services/playlist.service';
import { SongService } from 'src/app/services/song.service';

@Component({
  selector: 'app-playlist-view',
  templateUrl: './playlist-view.component.html',
  styleUrls: ['./playlist-view.component.scss']
})
export class PlaylistViewComponent implements OnInit {
  addSongPopup = false;
  public param = '';
  public songs: songSearch[] = new Array();
  public playlistInfo = new playlistInfo();

  constructor(
    private playlistService: PlaylistService,
    private playerService: PlayerService,
    private songService: SongService,
    private activatedRoute: ActivatedRoute,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.syncPlaylist();
  }

  blackState:boolean = true;
  redState: boolean = false;

  toggle() {
    this.blackState = !this.blackState;
    this.redState = !this.redState;
  }

  delete(songId:  Number){
    this.playlistService.deleteSongFromPlaylist(this.activatedRoute.snapshot.params.id, songId).subscribe(
      response => {
        this.syncPlaylist();
      }
    );
    
  }

  syncPlaylist(){
    this.playlistService.requestPlaylist(this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        let playlist = JSON.parse(JSON.stringify(response));
        this.playlistInfo.canEdit = playlist.canEdit;
        this.playlistInfo.playlistName = playlist.playlistName;
        this.playlistInfo.playlistCreator = playlist.playlistCreator;
        this.playlistInfo.songs = playlist.songs;
      },
      error => {
        alert("This playlist could not be found");
        this.router.navigate(['/browse'])
      }
    )
  }

  search(){
    this.songService.searchSongs(this.param).subscribe(
      response => {
        this.songs = JSON.parse(JSON.stringify(response));
        console.log(this.songs)
      },
      error => {}
    );
  }

  addSongToPlaylist(songId: Number){
    this.playlistService.addSongToPlaylistObservable(this.activatedRoute.snapshot.params.id, songId).subscribe(
      response => {
        this.addSongPopup = false;
        this.syncPlaylist();
      },
      error => {}
    )
  }

  listenThePlaylist(){
    this.playlistService.listenToPlaylist(this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        this.playerService.isSynced = false;
      },
      error => {}
    )
  }
}
