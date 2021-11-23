import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { profileInfo } from 'src/app/models/profileInfo.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile-details',
  templateUrl: './profile-details.component.html',
  styleUrls: ['./profile-details.component.scss']
})
export class ProfileDetailsComponent implements OnInit {
  public userInfo = new profileInfo();
  public isArtist = false;

  constructor(private userService: UserService, private http: HttpClient) { }

   ngOnInit(): void {
    this.userService.selfProfileDetails().subscribe(
      response => {
        let user = JSON.parse(JSON.stringify(response));
        this.userInfo.profilePicLink = user.profilePicLink;
        this.userInfo.username = user.username;
        this.userInfo.roleNames = user.roleNames;
        this.userInfo.playlists = user.playlists;
        if(this.userInfo.roleNames.filter(a => a == "ARTIST").length > 0){
          this.isArtist = true;
          this.userInfo.singles = user.singles;
          this.userInfo.albums = user.albums;
        }
      },
      error => {}
    )
  }
}
