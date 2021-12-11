import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  private SERVER_ADDRESS = "http://localhost:8080";
  isSynced = true;

  constructor(private http: HttpClient) { }

  returnQueue(): Observable<Object>{
    return this.http.get<Object>(this.SERVER_ADDRESS + '/queue', {withCredentials: true});
  }
  returnQueueSongInformation(): Observable<Object>{
    return this.http.get<Object>(this.SERVER_ADDRESS + '/queue/song', {withCredentials: true});
  }
  addSongToQueue(songId: Number){
    this.http.post(this.SERVER_ADDRESS + '/queue/song/' + songId,"", {withCredentials: true}).subscribe(
      response => {
      },
      error => {
        alert(error.error);
      }
    )
    this.isSynced = false;
  }
  addCollectionToQueue(){}
  removeFromQueue(songId: number): Observable<Object>{
    return this.http.delete(this.SERVER_ADDRESS + '/queue/song/' + songId, {withCredentials: true})
  }
}
