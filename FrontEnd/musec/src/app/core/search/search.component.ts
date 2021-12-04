import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { albumSearch } from 'src/app/models/search/albumSearch.model';
import { playlistSearch } from 'src/app/models/search/playlistSearch.model';
import { singleSearch } from 'src/app/models/search/singleSearch.model';
import { songSearch } from 'src/app/models/search/songSearch.model';
import { userSearch } from 'src/app/models/search/userSearch.model';
import { AlbumService } from 'src/app/services/album.service';
import { PlaylistService } from 'src/app/services/playlist.service';
import { SingleService } from 'src/app/services/single.service';
import { SongService } from 'src/app/services/song.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
  param: string = '';
  foundAlbums: albumSearch[] = new Array();
  foundPlaylists: playlistSearch[] = new Array();
  foundSingles: singleSearch[] = new Array();
  foundUsers: userSearch[] = new Array();
  foundSongs: songSearch[] = new Array();

  constructor(
    private activatedRoute: ActivatedRoute,
    private albumService: AlbumService,
    private playlistService: PlaylistService,
    private singleService: SingleService,
    private userService: UserService,
    private songService: SongService
  ) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((p: ParamMap) => {
      this.param = p.get('param')!;
    })

    this.albumService.searchAlbums(this.param).subscribe(
      response => {
        this.foundAlbums = JSON.parse(JSON.stringify(response));
      },
      error => {}
    )

    this.playlistService.searchPlaylist(this.param).subscribe(
      response => {
        this.foundPlaylists = JSON.parse(JSON.stringify(response));
      },
      error => {}
    )

    this.singleService.searchSingle(this.param).subscribe(
      response => {
        this.foundSingles = JSON.parse(JSON.stringify(response));
      },
      error => {}
    )

    this.userService.searchUsers(this.param).subscribe(
      response => {
        this.foundUsers = JSON.parse(JSON.stringify(response));
      },
      error => {}
    )

    this.songService.searchSongs(this.param).subscribe(
      response => {
        this.foundSongs = JSON.parse(JSON.stringify(response));
      },
       error => {}
    )
  }

}
