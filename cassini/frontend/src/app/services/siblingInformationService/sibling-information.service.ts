import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SiblingInformationService {

  baseUrl = environment.baseUrl;
  
  constructor(private http: HttpClient) { }

  updateSibInfo(id: number, ob): Observable<any> {
    let body = JSON.stringify(ob);
    let options = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.put(this.baseUrl + "api/siblingInformation/" + id, body, options)
  }

  public delete(id: number) {
    return this.http.delete(this.baseUrl + "api/siblingInformation/" + id);
  }
}
