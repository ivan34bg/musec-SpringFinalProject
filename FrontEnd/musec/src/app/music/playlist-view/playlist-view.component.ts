import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { playlistInfo } from 'src/app/models/playlist/playlistInfo.model';
import { PlaylistService } from 'src/app/services/playlist.service';

@Component({
  selector: 'app-playlist-view',
  templateUrl: './playlist-view.component.html',
  styleUrls: ['./playlist-view.component.scss'],
  animations: [
    trigger('blackState', [
      state('true', style({opacity: '1', background: 'black'})),
      state('false', style({ opacity: '0', background: 'black'})),
      transition('0 <=> 1', animate('1000ms ease'))
    ]),
    trigger('redState', [
      state('true', style({opacity: '1', background:'linear-gradient(90deg, rgba(0,0,0,1) 42%, rgba(121,9,9,1) 83%)'})),
      state('false', style({ opacity: '0', background:'linear-gradient(90deg, rgba(0,0,0,1) 42%, rgba(121,9,9,1) 83%)'})),
      transition('0 <=> 1', animate('1000ms ease'))
    ])
  ]
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
