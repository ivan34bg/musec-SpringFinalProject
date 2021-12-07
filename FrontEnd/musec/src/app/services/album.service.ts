import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AlbumService {
  private SERVER_ADDRESS = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  requestAlbum(id: Number): Observable<Object>{
    return this.http.get<Object>(this.SERVER_ADDRESS+'/album/'+id, {withCredentials: true});
  }

  uploadAlbumInfo(form: FormData): Observable<Object>{
    return this.http.post<Object>(this.SERVER_ADDRESS + '/album/create', form, {withCredentials: true});
  }

  uploadSongToAlbum(form: FormData, albumId: Number){
    this.http.post(this.SERVER_ADDRESS + '/album/song/' + albumId, form, {withCredentials: true}).subscribe(
      response => {},
      error => {
        console.log(error.error)
      }
    );
  }

  returnShortInfoOfAlbums(): Observable<Object>{
    return this.http.get(this.SERVER_ADDRESS + '/album/user', {withCredentials: true});
  }

  returnShortInfoOfAlbumsOfUserById(userId: Number): Observable<Object>{
    return this.http.get(this.SERVER_ADDRESS + '/album/user/' + userId, {withCredentials: true});
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
