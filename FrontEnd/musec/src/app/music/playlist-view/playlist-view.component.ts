import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { playlistInfo } from 'src/app/models/playlist/playlistInfo.model';
import { PlaylistService } from 'src/app/services/playlist.service';

@Component({
  selector: 'app-playlist-view',
  templateUrl: './playlist-view.component.html',
  styleUrls: ['./playlist-view.component.scss']
})
export class PlaylistViewComponent implements OnInit {
  public playlistInfo = new playlistInfo();

  constructor(
    private playlistService: PlaylistService,
    private activatedRoute: ActivatedRoute,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.playlistService.requestPlaylist(this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        let playlist = JSON.parse(JSON.stringify(response));
        this.playlistInfo.playlistName = playlist.playlistName;
        this.playlistInfo.playlistCreator = playlist.playlistCreator;
        this.playlistInfo.songs = playlist.songs;
        console.log(this.playlistInfo)
      },
      error => {
        alert("This playlist could not be found");
        this.router.navigate(['/browse'])
      }
    )
  }

  blackState:boolean = true;
  redState: boolean = false;

  toggle() {
    this.blackState = !this.blackState;
    this.redState = !this.redState;
  }
}
