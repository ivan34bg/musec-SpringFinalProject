import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  username = "";
  password = "";
  isWorking = false;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  login(){
    this.isWorking = true;
    this.userService.loginUser(this.username, this.password);
  }
}
