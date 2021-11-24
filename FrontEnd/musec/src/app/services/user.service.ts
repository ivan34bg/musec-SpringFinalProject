import { Inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

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

  registerUser(registerUser: FormData){
    this.http.post(this.SERVER_ADDRESS + "/user/register", registerUser, {withCredentials: true}).subscribe(
      (response) => {
        this.router.navigate(["/login"]);
      },
      (error) => {
        alert(error.error);
      }
    )
  }

  isUserLogged(): boolean{
    return this.isLoggedIn;
  }


  serverIsUserLogged(): Observable<boolean>{
    return this.http.get<boolean>(this.SERVER_ADDRESS + '/user/logged-in-test', {withCredentials: true});
  }

  loggedInStateKeeper(){
    this.http.get(this.SERVER_ADDRESS + '/user/logged-in-test', {withCredentials: true}).subscribe(
      (response) => {
        this.isLoggedIn = true;
      },
      (error) => {
        this.isLoggedIn = false;
      }
    )
  }

  profileDetailsFetcher(id: Number = -1): Observable<Object>{
    console.log(id);
    if(id === -1){
      return this.http.get<Object>(this.SERVER_ADDRESS + '/user/self-profile', {withCredentials: true});
    }
    else return this.http.get<Object>(this.SERVER_ADDRESS + '/user/' + id, {withCredentials: true});
  }
}
