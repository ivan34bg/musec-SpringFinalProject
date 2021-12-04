import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import * as CryptoJS from 'crypto-js';

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
    loginForm.append("password", this.passwordEncoder(password));

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

  registerUser(username: string, fullName: string, email: string, password: string, birthday: string){
    let form = new FormData();
    form.append('username', username);
    form.append('fullName', fullName);
    form.append('email', email);
    form.append('password', this.passwordEncoder(password));
    form.append('birthday', birthday);
    this.http.post(this.SERVER_ADDRESS + "/user/register", form, {withCredentials: true}).subscribe(
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
    if(id === -1){
      return this.http.get<Object>(this.SERVER_ADDRESS + '/user/self-profile', {withCredentials: true});
    }
    else return this.http.get<Object>(this.SERVER_ADDRESS + '/user/' + id, {withCredentials: true});
  }

  changeProfilePic(profilePic: File){
    let form = new FormData();
    form.append("newProfilePic", profilePic);
    this.http.post(this.SERVER_ADDRESS + '/user/profile-pic', form, {withCredentials: true}).subscribe(
      response => {
        this.router.navigate(['/profile-settings'])
      },
      error => {
        console.log(error.error);
      }
    )
  }

  changeUsername(newUsername: string, oldPassword: string){
    let form = new FormData();
    form.append('newUsername', newUsername);
    form.append('oldPassword', this.passwordEncoder(oldPassword));
    this.http.post(this.SERVER_ADDRESS + '/user/username', form, {withCredentials: true}).subscribe(
      response => {
        this.router.navigate(['/profile-settings'])
      },
      error => {
        alert(error.error)
      }
    )
  }

  changeFullName(newFullName: string, oldPassword: string){
    let form = new FormData();
    form.append('newFullName', newFullName);
    form.append('oldPassword', this.passwordEncoder(oldPassword));
    this.http.post(this.SERVER_ADDRESS + '/user/full-name', form, {withCredentials: true}).subscribe(
      response => {
        this.router.navigate(['/profile-settings'])
      },
      error => {
        alert(error.error)
      }
    )
  }

  changePassword(newPassword: string, oldPassword: string){
    let form = new FormData();
    form.append('newPassword', this.passwordEncoder(newPassword));
    form.append('oldPassword', this.passwordEncoder(oldPassword));
    this.http.post(this.SERVER_ADDRESS + '/user/password', form, {withCredentials: true}).subscribe(
      response => {
        this.router.navigate(['/profile-settings'])
      },
      error => {
        alert(error.error)
      }
    )
  }

  changeEmail(newEmail: string, oldPassword: string){
    let form = new FormData();
    form.append('newEmail', newEmail);
    form.append('oldPassword', this.passwordEncoder(oldPassword));
    this.http.post(this.SERVER_ADDRESS + '/user/email', form, {withCredentials: true}).subscribe(
      response => {
        this.router.navigate(['/profile-settings'])
      },
      error => {
        alert(error.error)
      }
    )
  }

  changeBirthday(newBirthday: string, oldPassword: string){
    let form = new FormData();
    form.append('newBirthday', newBirthday);
    form.append('oldPassword', this.passwordEncoder(oldPassword));
    this.http.post(this.SERVER_ADDRESS + '/user/birthday', form, {withCredentials: true}).subscribe(
      response => {
        this.router.navigate(['/profile-settings'])
      },
      error => {
        alert(error.error)
      }
    )
  }

  searchUsers(param: string): Observable<Object>{
    return this.http.get(this.SERVER_ADDRESS + '/user/search', {withCredentials: true, params: new HttpParams().set("param", param)})
  }

  passwordEncoder(purePassword: string):string {
    return(CryptoJS.SHA512(purePassword).toString());
  }  
}
