import { Component, OnInit } from '@angular/core';
import {} from '@angular/forms'
import { UserService } from 'src/app/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  username = "";
  password = "";

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  login(){
    this.userService.loginUser(this.username, this.password);
  }
}
