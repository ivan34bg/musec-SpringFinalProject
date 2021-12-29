import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
  }

  login(){
    if(!this.username.trim()){
      window.alert("Username cannot be empty")
    }
    else{
      if(!this.password.trim()){
        window.alert("Password cannot be empty")
      }
      else {
        this.isWorking = true;
        this.userService.loginUser(this.username, this.password).subscribe(
          respone => {
            this.userService.isLoggedIn = true;
            this.router.navigate(["/"])
          },
          error => {
            this.isWorking = false;
            window.alert(error.error);
          }
        )
      }
    }
  }
}
