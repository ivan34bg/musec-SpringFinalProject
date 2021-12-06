import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile-pic-change-view',
  templateUrl: './profile-pic-change-view.component.html',
  styleUrls: ['./profile-pic-change-view.component.scss']
})
export class ProfilePicChangeViewComponent implements OnInit {
  profilePic: File|undefined
  profilePicPreview: any;
  isChanging = false;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  albumPicUploaded(event: any){
    if(event.target.files[0] != null){
      
      var reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]);
      reader.onload = () => {
        this.profilePicPreview = reader.result;
        this.profilePic = event.target.files[0];
      }
    }
    else{
      console.log(event.target.files[0]);
      this.profilePicPreview = null;
      this.profilePic = undefined;
    }
  }

  onSubmit(){
    this.isChanging = true;
    this.userService.changeProfilePic(this.profilePic!);
  }
}
