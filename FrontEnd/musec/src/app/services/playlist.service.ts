import { HttpClient, HttpParams } from '@angular/common/http';
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

  returnIfUserHasPlaylists(): Observable<Object>{
    return this.http.get<Object>(this.SERVER_ADDRESS + "/playlist/check", {withCredentials: true});
  }

  returnUserPlaylistsShortInfo(): Observable<Object>{
    return this.http.get<Object>(this.SERVER_ADDRESS + "/playlist", {withCredentials: true});
  }

  addSongToPlaylist(playlistId: Number, songId: Number){
    this.http.post(this.SERVER_ADDRESS + "/playlist/song/" + playlistId, songId.toString(), {withCredentials: true, }).subscribe(
      response =>{}, 
      error => {
        alert(error.error);
      }
    )
  }

  addSongToPlaylistObservable(playlistId: Number, songId: Number): Observable<Object>{
    return this.http.post(this.SERVER_ADDRESS + "/playlist/song/" + playlistId, songId.toString(), {withCredentials: true, });
  }

  deleteSongFromPlaylist(playlistId: Number, songId: Number) : Observable<Object>{
    return this.http.delete(this.SERVER_ADDRESS + '/playlist/song/' + playlistId, {body: songId, withCredentials: true});
  }

  deletePlaylist(playlistId: Number): Observable<Object>{
    return this.http.delete(this.SERVER_ADDRESS + '/playlist/' + playlistId, {withCredentials: true});
  }

  searchPlaylist(param: string): Observable<Object>{
    return this.http.get(this.SERVER_ADDRESS + '/playlist/search', {withCredentials: true, params: new HttpParams().set("param", param)})
  }

  listenToPlaylist(playlistId: Number): Observable<Object>{
    return this.http.post(this.SERVER_ADDRESS + '/playlist/' + playlistId + '/queue', '', {withCredentials: true});
  }
}
