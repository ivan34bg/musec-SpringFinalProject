import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlaylistService {
  private SERVER_ADDRESS = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  requestPlaylist(id: Number): Observable<Object>{
    return this.http.get<Object>(this.SERVER_ADDRESS + '/playlist/' + id, {withCredentials: true});
  }
}
