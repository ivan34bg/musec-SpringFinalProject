import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SingleService {
  private SERVER_ADDRESS = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  requestSingleById(id: Number): Observable<Object>{
    return this.http.get<Object>(this.SERVER_ADDRESS + '/single/' + id, {withCredentials: true});
  }
  uploadSingleInfo(form: FormData): Observable<Object>{
    return this.http.post<Object>(this.SERVER_ADDRESS + '/single/create', form, {withCredentials: true});
  }
  uploadSingleSong(form: FormData, singleId: Number){
    this.http.post(this.SERVER_ADDRESS + '/single/song/' + singleId, form, {withCredentials: true}).subscribe(
      response => {},
      error => {
        console.log(error.error);
      }
    );
  }
}
