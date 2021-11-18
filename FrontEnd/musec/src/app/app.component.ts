import { Component } from '@angular/core';
import { UserService } from './user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'musec';

  constructor(private userService: UserService){}

  get isUserLoggedIn(){
    return this.userService.isUserLogged();
  }
}
