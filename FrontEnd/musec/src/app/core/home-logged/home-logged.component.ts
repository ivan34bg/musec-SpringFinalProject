import { Component, OnInit } from '@angular/core';
import { genreShortInfo } from 'src/app/models/genre/genreShortInfo.model';
import { songTopTen } from 'src/app/models/topTen/songTopTen.Model';
import { GenreService } from 'src/app/services/genre.service';
import { PlayerService } from 'src/app/services/player.service';
import { SongService } from 'src/app/services/song.service';

@Component({
  selector: 'app-home-logged',
  templateUrl: './home-logged.component.html',
  styleUrls: ['./home-logged.component.scss']
})
export class HomeLoggedComponent implements OnInit {
  genres: genreShortInfo[] = new Array();
  songs: songTopTen[] = new Array();
  allGenresLoaded = false;


  constructor(
    private genreService: GenreService,
    private songService: SongService,
    private playerService: PlayerService
    ) { }

  ngOnInit(): void {
    this.genreService.genreShortInfo().subscribe(
      response => {
        this.genres = JSON.parse(JSON.stringify(response));
      },
      error => {}
    )

    this.songService.loadTenNewestSongs().subscribe(
      response => {
        this.songs = JSON.parse(JSON.stringify(response));
      },
      error => {}
    )
  }

  loadAllGenres(){
    this.genreService.genreShortAll().subscribe(
      response => {
        this.genres = JSON.parse(JSON.stringify(response));
        this.allGenresLoaded = true;
      },
      error => {}
    )
  }
  
  addToQueue(songId: Number){
    this.playerService.addSongToQueue(songId);
  }
}
