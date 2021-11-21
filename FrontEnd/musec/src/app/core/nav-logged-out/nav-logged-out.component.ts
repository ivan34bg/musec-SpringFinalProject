import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/user.service';

@Component({
  selector: 'app-nav-logged-out',
  templateUrl: './nav-logged-out.component.html',
  styleUrls: ['./nav-logged-out.component.scss']
})
export class NavLoggedOutComponent implements OnInit {
  
  isOpened:boolean = false;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  get isUserLoggedIn(){
    return this.userService.isUserLogged();
  }
}
