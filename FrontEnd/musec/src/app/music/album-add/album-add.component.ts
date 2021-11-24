import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { songAlbumUpload } from 'src/app/models/album-upload/song.model';
import { AlbumService } from 'src/app/services/album.service';

@Component({
  selector: 'app-album-add',
  templateUrl: './album-add.component.html',
  styleUrls: ['./album-add.component.scss']
})
export class AlbumAddComponent implements OnInit {
  albumId: Number = -1;
  albumName: string = '';
  albumPic: File | undefined;
  albumPicPreview: any;
  music: songAlbumUpload[] = new Array();

  constructor(private albumService: AlbumService, private router: Router) { }

  ngOnInit(): void {
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
          this.albumService.uploadSongToAlbum(songForm, parseInt(JSON.stringify(response)))
          this.router.navigate(["/my-profile"]);
        }
      },
      error => {
        console.log(error.error);
      }
    );
  }
}
