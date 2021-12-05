import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { genreExtendedInfo } from 'src/app/models/genre/genreExtendedInfo.model';
import { GenreService } from 'src/app/services/genre.service';
import { PlayerService } from 'src/app/services/player.service';

@Component({
  selector: 'app-genre',
  templateUrl: './genre.component.html',
  styleUrls: ['./genre.component.scss']
})
export class GenreComponent implements OnInit {
  genre: genreExtendedInfo = new genreExtendedInfo();

  constructor(
    private genreService: GenreService, 
    private activatedRoute: ActivatedRoute,
    private playerService: PlayerService
    ) { }

  ngOnInit(): void {
    this.genreService.fetchGenreSongsById(this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        this.genre = JSON.parse(JSON.stringify(response));
      },
      error => {}
    );
  }

  addToQueue(songId: Number){
    this.playerService.addSongToQueue(songId);
  }
}
