import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SongService {

  private SERVER_ADDRESS = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  searchSongs(param: string): Observable<Object>{
    return this.http.get(this.SERVER_ADDRESS + "/song/search", {withCredentials: true, params: new HttpParams().set('param', param)})
  }

  loadTenNewestSongs(): Observable<Object>{
    return this.http.get(this.SERVER_ADDRESS + '/song/newest-ten', {withCredentials: true});
  }
}
