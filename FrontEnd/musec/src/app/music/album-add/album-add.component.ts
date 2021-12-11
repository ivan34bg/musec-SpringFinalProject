import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { interval } from 'rxjs';
import { songAlbumUpload } from 'src/app/models/album-upload/song.model';
import { genreShortInfo } from 'src/app/models/genre/genreShortInfo.model';
import { AlbumService } from 'src/app/services/album.service';
import { GenreService } from 'src/app/services/genre.service';

@Component({
  selector: 'app-album-add',
  templateUrl: './album-add.component.html',
  styleUrls: ['./album-add.component.scss']
})
export class AlbumAddComponent implements OnInit {
  genres: genreShortInfo[] = new Array();
  albumId: Number = -1;
  albumName: string = '';
  albumPic: File | undefined;
  albumPicPreview: any;
  areSongInputsValid = false;
  music: songAlbumUpload[] = new Array();
  isWorking = false;

  constructor(
    private albumService: AlbumService, 
    private genreService: GenreService,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.genreService.genreShortAll().subscribe(
      response => {
        this.genres = JSON.parse(JSON.stringify(response));
      },
      error => {}
    )
    interval(500).subscribe(() => this.checkSongInputs())
  }

  checkSongInputs(){
    if(this.music.length > 0){
      for(let i = 0; i< this.music.length; i++){
        if(!this.music[i].songName.length){
          this.areSongInputsValid = false;
          break;
        }
        if(!this.music[i].songGenre.length){
          this.areSongInputsValid = false;
          break;
        }
        if(i === this.music.length - 1){
          this.areSongInputsValid = true;
        }
      }
    }
  }

  albumPicUploaded(event: any){
    if(event.target.files[0] != null){
      var reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]);
      reader.onload = () => {
        this.albumPicPreview = reader.result;
        this.albumPic = event.target.files[0];
      }
    }
    else{
      this.albumPicPreview = null;
      this.albumPic = undefined;
    }
  }

  musicUploaded(event: any){
    if(this.music.length == 0){
      if(event.target.files.length != 0){
        let musicNameArr: songAlbumUpload[] = new Array();
        let uploadedMusic = event.target.files;
        for(let i = 0; i < event.target.files.length; i++){
          let song = new songAlbumUpload;
          song.songFile = uploadedMusic[i];
          musicNameArr.push(song);
        }
        this.music = musicNameArr;
      }
      else{
        this.music = [];
      }
    }
    else{
      if(event.target.files.length != 0){
        let musicNameArr: songAlbumUpload[] = this.music;
        let uploadedMusic = event.target.files;
        for(let i = 0; i < event.target.files.length; i++){
          let song = new songAlbumUpload;
          song.songFile = uploadedMusic[i];
          musicNameArr.push(song);
        }
        this.music = musicNameArr;
      }
    }

  }

  removeSong(index: number){
    if(this.music.length < 2){
      this.music = [];
    }
    else
      this.music.splice(index, 1);
  }

  onSubmit(){
    this.isWorking = true;
    let albumInfoForm = new FormData();
    albumInfoForm.append("albumName", this.albumName);
    albumInfoForm.append("albumPic", this.albumPic!);
    this.albumService.uploadAlbumInfo(albumInfoForm).subscribe(
      response => {
        this.albumId = parseInt(JSON.stringify(response));
        for(let song of this.music){
          let songForm = new FormData();
          songForm.append("songName", song.songName);
          songForm.append("songFile", song.songFile);
          songForm.append("genre", song.songGenre);
          this.albumService.uploadSongToAlbum(songForm, parseInt(JSON.stringify(response))).subscribe(
            response => {},
            error => {}
          )
        }
        this.router.navigate(["/my-profile"]);
      },
      error => {
        console.log(error.error);
      }
    );
  }
}
