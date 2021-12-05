import { Component, OnInit } from '@angular/core';
import { genreShortInfo } from 'src/app/models/genre/genreShortInfo.model';
import { GenreService } from 'src/app/services/genre.service';

@Component({
  selector: 'app-home-logged',
  templateUrl: './home-logged.component.html',
  styleUrls: ['./home-logged.component.scss']
})
export class HomeLoggedComponent implements OnInit {
  genres: genreShortInfo[] = new Array();

  constructor(private genreService: GenreService) { }

  ngOnInit(): void {
    this.genreService.genreShortInfo().subscribe(
      response => {
        this.genres = JSON.parse(JSON.stringify(response));
      },
      error => {}
    )
  }

}
