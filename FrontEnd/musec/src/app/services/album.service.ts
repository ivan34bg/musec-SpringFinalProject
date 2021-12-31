import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { songAlbumUpload } from '../models/album-upload/song.model';

@Injectable({
  providedIn: 'root'
})
export class AlbumService {
  private SERVER_ADDRESS = "http://localhost:8080";

  constructor(private http: HttpClient, private router: Router) { }

  requestAlbum(id: Number): Observable<Object>{
    return this.http.get<Object>(this.SERVER_ADDRESS+'/album/'+id, {withCredentials: true});
  }

  uploadAlbumInfo(form: FormData, music: songAlbumUpload[]) {
    this.http.post(this.SERVER_ADDRESS + '/album/create', form, {withCredentials: true, observe: 'response'}).subscribe(
      response => {
        var albumLocation = response.headers.get('Location');
        for(let song of music){
          let songForm = new FormData();
          songForm.append("songName", song.songName);
          songForm.append("songFile", song.songFile);
          songForm.append("genre", song.songGenre);
          this.uploadSongToAlbum(songForm, albumLocation!);
        }
        this.router.navigate(["my-profile"]);
      },
      error => {}
    )
  }

  uploadSongToAlbum(form: FormData, albumLocation: String){
    this.http.post(this.SERVER_ADDRESS + albumLocation + "/song", form, {withCredentials: true}).subscribe(
      response => {},
      error => {}
    )
  }

  returnShortInfoOfAlbums(): Observable<Object>{
    return this.http.get<Object>(this.SERVER_ADDRESS + '/album/user', {withCredentials: true});
  }

  returnShortInfoOfAlbumsOfUserById(userId: Number): Observable<HttpResponse<Object>>{
    return this.http.get(this.SERVER_ADDRESS + '/album/user/' + userId, {observe: 'response' ,withCredentials: true});
  }

  deleteAlbum(albumId: Number): Observable<Object>{
    return this.http.delete(this.SERVER_ADDRESS + '/album/' + albumId, {withCredentials: true});
  }

  adminDeleteAlbum(albumId: Number): Observable<Object>{
    return this.http.delete(this.SERVER_ADDRESS + '/admin/album/' + albumId, {withCredentials: true});
  }

  searchAlbums(param: string): Observable<Object>{
    return this.http.get(this.SERVER_ADDRESS + '/album/search', {withCredentials: true, params: new HttpParams().set("param", param)})
  }

  listenToAlbum(albumId: Number): Observable<Object>{
    return this.http.post(this.SERVER_ADDRESS + '/album/' + albumId + '/queue', '', {withCredentials: true});
  }
}
