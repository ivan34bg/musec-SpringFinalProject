import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SingleService {
  private SERVER_ADDRESS = "http://localhost:8080";

  constructor(private http: HttpClient, private router: Router) { }

  requestSingleById(id: Number): Observable<Object>{
    return this.http.get<Object>(this.SERVER_ADDRESS + '/single/' + id, {withCredentials: true});
  }
  uploadSingleInfo(form: FormData, songForm: FormData) {
    this.http.post<Object>(this.SERVER_ADDRESS + '/single/create', form, {withCredentials: true, observe: 'response'}).subscribe(
      response => {
        var singleLocation = response.headers.get("Location");
        this.uploadSingleSong(songForm, singleLocation!);
        this.router.navigate(["/my-profile"]);
      },
      error => {}
    )
  }
  uploadSingleSong(form: FormData, singleLocation: String){
    this.http.post(this.SERVER_ADDRESS + singleLocation + "/song", form, {withCredentials: true}).subscribe(
      response => {},
      error => {
        console.log(error.error);
      }
    );
  }
  returnShortInfoOfSingles(): Observable<Object>{
    return this.http.get(this.SERVER_ADDRESS + '/single/user', {withCredentials: true});
  }

  returnShortInfoOfSinglesOfUserById(userId: Number): Observable<Object>{
    return this.http.get(this.SERVER_ADDRESS + '/single/user/' + userId, {withCredentials: true});
  }

  deleteSingle(singleId: Number): Observable<Object>{
    return this.http.delete(this.SERVER_ADDRESS + '/single/' + singleId, {withCredentials: true});
  }

  adminDeleteSingle(singleId: Number): Observable<Object>{
    return this.http.delete(this.SERVER_ADDRESS + '/admin/single/' + singleId, {withCredentials: true});
  }

  searchSingle(param: string): Observable<Object>{
    return this.http.get(this.SERVER_ADDRESS + '/single/search', {withCredentials: true, params: new HttpParams().set("param", param)})
  }
}
