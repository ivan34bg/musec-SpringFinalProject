import { Component, OnInit } from '@angular/core';
import { playlistShortInfo } from 'src/app/models/playlist/playlistShortInfo.model';
import { albumShortInfo } from 'src/app/models/short-info/albumShortInfo.model';
import { singleShortInfo } from 'src/app/models/short-info/singleShortInfo.model';
import { AlbumService } from 'src/app/services/album.service';
import { PlayerService } from 'src/app/services/player.service';
import { PlaylistService } from 'src/app/services/playlist.service';
import { SingleService } from 'src/app/services/single.service';

@Component({
  selector: 'app-main-change-view',
  templateUrl: './main-change-view.component.html',
  styleUrls: ['./main-change-view.component.scss']
})
export class MainChangeViewComponent implements OnInit {
  playlists: playlistShortInfo[] = new Array();
  albums: albumShortInfo[] = new Array();
  singles: singleShortInfo[] = new Array();
  isDeleting = false;

  constructor(
    private playlistService: PlaylistService, 
    private playerService: PlayerService,
    private albumService: AlbumService, 
    private singleService: SingleService) { }

  ngOnInit(): void {
    this.syncPage();
  }

  deletePlaylist(playlistId: Number){
    this.isDeleting = true;
    this.playlistService.deletePlaylist(playlistId).subscribe(
      response => {
        this.syncPage();
      },
      error => {}
    )
  }

  deleteAlbum(albumId: Number){
    this.isDeleting = true;
    this.albumService.deleteAlbum(albumId).subscribe(
      response => {
        this.playerService.isSynced = false;
        this.syncPage();
      },
      error => {}
    )
  }

  deleteSingle(singleId: Number){
    this.isDeleting = true;
    this.singleService.deleteSingle(singleId).subscribe(
      response => {
        this.playerService.isSynced = false;
        this.syncPage();
      },
      error => {}
    )
  }

  syncPage(){
    this.playlistService.returnUserPlaylistsShortInfo().subscribe(
      response => {
        this.playlists = JSON.parse(JSON.stringify(response));
        this.isDeleting = false;
      },
      error => {}
    );

    this.albumService.returnShortInfoOfAlbums().subscribe(
      response => {
        this.albums = JSON.parse(JSON.stringify(response));
      },
      error => {}
    )

    this.singleService.returnShortInfoOfSingles().subscribe(
      response => {
        this.singles = JSON.parse(JSON.stringify(response));
      },
      error => {}
    )
  }
}
