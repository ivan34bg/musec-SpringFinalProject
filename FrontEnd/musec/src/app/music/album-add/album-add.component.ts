import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-album-add',
  templateUrl: './album-add.component.html',
  styleUrls: ['./album-add.component.scss']
})
export class AlbumAddComponent implements OnInit {
  image: any;
  music: File[] = [];

  constructor() { }

  ngOnInit(): void {
  }

  imageUploaded(event: any){
    if(event.target.files[0] != null){
      var reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]);
      reader.onload = () => {
        this.image = reader.result;
      }
    }
    else{
      this.image = null;
    }
  }

  musicUploaded(event: any){
    if(this.music.length == 0){
      if(event.target.files.length != 0){
        let musicNameArr: File[] = [];
        let uploadedMusic = event.target.files;
        for(let i = 0; i < event.target.files.length; i++){
          musicNameArr.push(uploadedMusic[i]);
        }
        this.music = musicNameArr;
      }
      else{
        this.music = [];
      }
    }
    else{
      if(event.target.files.length != 0){
        let musicNameArr: File[] = this.music;
        let uploadedMusic = event.target.files;
        for(let i = 0; i < event.target.files.length; i++){
          musicNameArr.push(uploadedMusic[i]);
        }
        this.music = musicNameArr;
      }
    }

  }

  removeSong(index: number){
    if(this.music.length < 2){
      this.music = [];
      console.log(this.music);
    }
    else
      this.music.splice(index, 1);
  }
}
