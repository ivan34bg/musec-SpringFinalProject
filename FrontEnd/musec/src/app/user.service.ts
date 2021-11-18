import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private isLoggedIn:boolean = false;
  private SERVER_ADDRESS = "http://localhost:8080";

  constructor(private http: HttpClient, private router: Router) { }

  loginUser(username: string, password: string){
    var loginForm = new FormData();
    loginForm.append("username", username);
    loginForm.append("password", password);

    this.http.post(this.SERVER_ADDRESS + "/login", loginForm, {withCredentials: true}).subscribe(
      (response) => {
        this.isLoggedIn = true;
        this.router.navigate(['/']);
      },
      (error) => {
        alert(error.error)
      }
    );
  }

  logoutUser(){
    this.http.get(this.SERVER_ADDRESS + "/user/logout", {withCredentials: true}).subscribe(
      (response) => {
        this.isLoggedIn = false;
        this.router.navigate(['/login']);
      },
      (error) => {
      }
    )
  }

  isUserLogged(): boolean{
    return this.isLoggedIn;
  }
}
