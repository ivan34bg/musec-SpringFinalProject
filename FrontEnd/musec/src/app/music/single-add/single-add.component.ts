import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { songAlbumUpload } from 'src/app/models/album-upload/song.model';
import { genreShortInfo } from 'src/app/models/genre/genreShortInfo.model';
import { GenreService } from 'src/app/services/genre.service';
import { SingleService } from 'src/app/services/single.service';

@Component({
  selector: 'app-single-add',
  templateUrl: './single-add.component.html',
  styleUrls: ['./single-add.component.scss']
})
export class SingleAddComponent implements OnInit {
  genres: genreShortInfo[] = new Array();
  singleName: string = '';
  singlePic: File | undefined;
  singlePicPreview: any;
  music: songAlbumUpload | undefined;
  isWorking = false;

  constructor(
    private genreService: GenreService,
    private singleService: SingleService, 
    private router: Router
    ) { }

  ngOnInit(): void {
    this.genreService.genreShortAll().subscribe(
      response => {
        this.genres = JSON.parse(JSON.stringify(response));
      },
      error => {}
    )
  }

  albumPicUploaded(event: any){
    if(event.target.files[0] != null){
      var reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]);
      reader.onload = () => {
        this.singlePicPreview = reader.result;
        this.singlePic = event.target.files[0];
      }
    }
    else{
      this.singlePicPreview = null;
      this.singlePic = undefined;
    }
  }

  musicUploaded(event: any){
      if(event.target.files.length != 0){
        let uploadedMusic = event.target.files;
        let song = new songAlbumUpload;
        song.songFile = uploadedMusic[0];
        this.music = song;
      }
      else{
        this.music = new songAlbumUpload;
      }
  }

  onSubmit(){
    this.isWorking = true;
    let singleInfoForm = new FormData();
    singleInfoForm.append('singleName', this.singleName);
    singleInfoForm.append('singlePic', this.singlePic!);
    this.singleService.uploadSingleInfo(singleInfoForm).subscribe(
      response => {
        let songForm = new FormData();
        songForm.append("songName", this.music!.songName)
        songForm.append("songFile", this.music!.songFile)
        songForm.append("genre", this.music!.songGenre);
        this.singleService.uploadSingleSong(songForm, parseInt(JSON.stringify(response)));
        this.router.navigate(["/my-profile"]);
      },
      error => {
        console.log(error.error)
      }
    );
  }
}
