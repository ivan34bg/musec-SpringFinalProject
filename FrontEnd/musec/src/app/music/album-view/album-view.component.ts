import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { albumInfo } from 'src/app/models/albumInfo.model';
import { playlistShortInfo } from 'src/app/models/playlist/playlistShortInfo.model';
import { AlbumService } from 'src/app/services/album.service';
import { PlayerService } from 'src/app/services/player.service';
import { PlaylistService } from 'src/app/services/playlist.service';

@Component({
  selector: 'app-album-view',
  templateUrl: './album-view.component.html',
  styleUrls: ['./album-view.component.scss']
})
export class AlbumViewComponent implements OnInit {
  public albumInfo: albumInfo = new albumInfo();
  public songId: Number = -1;
  public playlistId: Number = -1;
  public doesUserHavePlaylists = false;
  public playlists: playlistShortInfo[] = new Array();

  constructor(
    private router: Router, 
    private albumService: AlbumService, 
    private playerService: PlayerService,
    private playlistService: PlaylistService,
    private activatedRoute: ActivatedRoute
    ) { }

  ngOnInit(): void {
    this.albumService.requestAlbum(this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        let album = JSON.parse(JSON.stringify(response));
        this.albumInfo.albumName = album.albumName;
        this.albumInfo.albumCreator = album.uploader;
        this.albumInfo.albumPicLink = album.albumPicLocation;
        this.albumInfo.albumSongs = album.songs;
      },
      error => {
        this.router.navigate(['/browse'])
        alert('Album cannot be found!');
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
    }
    this.songId = -1;
    this.playlistId = -1;
  }

  listenTheAlbum(){
    this.albumService.listenToAlbum(this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        this.playerService.isSynced = false;
      },
      error => {}
    )
  }
}
