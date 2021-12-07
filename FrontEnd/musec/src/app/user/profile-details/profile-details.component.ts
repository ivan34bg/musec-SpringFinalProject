import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { profileInfo } from 'src/app/models/profileInfo.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile-details',
  templateUrl: './profile-details.component.html',
  styleUrls: ['./profile-details.component.scss']
})
export class ProfileDetailsComponent implements OnInit {
  public userInfo = new profileInfo();
  public isSelfProfile = false;
  public isAdmin = true;
  public isArtist = false;
  public userId = -1;

  constructor(
    private userService: UserService, 
    private activatedRoute: ActivatedRoute
    ) { }

  ngOnInit(): void {
    this.userService.profileDetailsFetcher(this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        let user = JSON.parse(JSON.stringify(response));
        this.userInfo.profilePicLink = user.profilePicLink;
        this.userInfo.fullName = user.fullName;
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
    if(this.activatedRoute.snapshot.params.id == undefined){
      this.isSelfProfile = true;
    }
    else {
      this.isSelfProfile = false;
      this.userService.isOtherUserAdmin(this.activatedRoute.snapshot.params.id).subscribe(
        response => {
          if(JSON.stringify(response) == "true"){
            this.isAdmin = true;
          }
          else{
            this.isAdmin = false;
            this.userId = this.activatedRoute.snapshot.params.id;
          }
        },
        error => {}
      )
    }
  }
}
