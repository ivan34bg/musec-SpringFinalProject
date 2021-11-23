import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnInit {

  isOpened:boolean = false;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  get isUserLoggedIn(){
    return this.userService.isUserLogged();
  }

  openDropdown(event:Event){
    console.log(event.target);
  }

  logout(){
    this.userService.logoutUser();
  }
}
