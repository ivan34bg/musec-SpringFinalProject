import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { albumShortInfo } from 'src/app/models/short-info/albumShortInfo.model';
import { singleShortInfo } from 'src/app/models/short-info/singleShortInfo.model';
import { AlbumService } from 'src/app/services/album.service';
import { PlayerService } from 'src/app/services/player.service';
import { SingleService } from 'src/app/services/single.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.scss']
})
export class AdminPanelComponent implements OnInit {
  isWorking = false;
  isUserArtist = false;
  singles: singleShortInfo[] = new Array();
  albums: albumShortInfo[] = new Array();

  constructor(
    private userService: UserService,
    private albumService: AlbumService,
    private singleService: SingleService,
    private playerService: PlayerService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.userService.isOtherUserAdmin(this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        if(JSON.stringify(response) == "true"){
          this.router.navigate(['/browse']);
          alert("You cannot administrate other admins");
        }
        else{
          this.albumService.returnShortInfoOfAlbumsOfUserById(this.activatedRoute.snapshot.params.id).subscribe(
            response => {
              this.albums = JSON.parse(JSON.stringify(response));
            },
            error => {}
          )
          this.albumSync();
          this.singleSync();
        }
      },
      error => {
        this.router.navigate(['/browse']);
      }
    );
    this.artistCheck();
  }

  artistCheck(){
    this.userService.isOtherUserArtist(this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        this.isUserArtist = true;
      },
      error => {
        this.isUserArtist = false;
      }
    )
  }

  albumSync(){
    this.albumService.returnShortInfoOfAlbumsOfUserById(this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        this.albums = JSON.parse(JSON.stringify(response));
      },
      error => {}
    )
  }

  singleSync(){
    this.singleService.returnShortInfoOfSinglesOfUserById(this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        this.singles = JSON.parse(JSON.stringify(response));
      },
      error => {}
    )
  }

  addArtistRoleToUser(){
    this.userService.addArtistRoleToUser(this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        this.artistCheck();
      },
      error => {}
    )
  }

  removeArtistRoleOfUser(){
    this.userService.removeArtistRoleOfUser(this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        this.artistCheck();
      },
      error => {}
    )
  }

  deleteAlbum(albumId: Number){
    this.isWorking = true;
    this.albumService.adminDeleteAlbum(albumId).subscribe(
      response => {
        this.albumSync();
        this.playerService.isSynced = false;
        this.isWorking = false;
      },
      error => {}
    )
  };
  deleteSingle(singleId: Number){
    this.isWorking = true;
    this.singleService.adminDeleteSingle(singleId).subscribe(
      response => {
        this.singleSync();
        this.playerService.isSynced = false;
        this.isWorking = false;
      },
      error => {}
    )
  }
}
