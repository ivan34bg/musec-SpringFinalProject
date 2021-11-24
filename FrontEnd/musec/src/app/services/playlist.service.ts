import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlaylistService {
  private SERVER_ADDRESS = "http://localhost:8080";

  constructor(private http: HttpClient, private router: Router) { }

  requestPlaylist(id: Number): Observable<Object>{
    return this.http.get<Object>(this.SERVER_ADDRESS + '/playlist/' + id, {withCredentials: true});
  }

  createPlaylist(form: FormData){
    this.http.post(this.SERVER_ADDRESS + '/playlist/create', form, {withCredentials: true}).subscribe(
      response => {
        this.router.navigate(["/my-profile"]);
      },
      error => {
        console.log(error.error)
      }
    )
  }
}
