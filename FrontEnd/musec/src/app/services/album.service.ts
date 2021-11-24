import { HttpClient } from '@angular/common/http';
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
}
