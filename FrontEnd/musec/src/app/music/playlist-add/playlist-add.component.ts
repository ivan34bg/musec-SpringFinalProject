import { Component, OnInit } from '@angular/core';
import { PlaylistService } from 'src/app/services/playlist.service';

@Component({
  selector: 'app-playlist-add',
  templateUrl: './playlist-add.component.html',
  styleUrls: ['./playlist-add.component.scss']
})
export class PlaylistAddComponent implements OnInit {

  playlistName = '';
  availability = false;
  editability = false;

  constructor(private playlistService: PlaylistService) { }

  ngOnInit(): void {

  }

  onSubmit(){
    if(!this.playlistName.trim()){
      window.alert("Playlist name cannot be empty")
    }
    else{
      let form = new FormData();
      form.append("playlistName", this.playlistName);
      form.append("isPublic", this.availability.toString());
      form.append("openToPublicEditsOrNot", this.editability.toString());
      this.playlistService.createPlaylist(form);
    }

  }
}
