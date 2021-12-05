import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnInit {
  isUserArtist = false;
  isOpened:boolean = false;

  constructor(private userService: UserService, private router: Router) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  ngOnInit(): void {
    this.userService.isUserArtist().subscribe(
      response => {
        this.isUserArtist = true;
      },
      error => {
        this.isUserArtist = false;
      }
    )
  }

  get isUserLoggedIn(){
    return this.userService.isUserLogged();
  }

  logout(){
    this.userService.logoutUser();
  }
}
