import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GenreService {
  private SERVER_ADDRESS = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  genreShortInfo(): Observable<Object>{
    return this.http.get(this.SERVER_ADDRESS + '/genre/short', {withCredentials: true});
  }

  fetchGenreSongsById(genreId: Number): Observable<Object>{
    return this.http.get(this.SERVER_ADDRESS + '/genre/' + genreId, {withCredentials: true});
  }
}
