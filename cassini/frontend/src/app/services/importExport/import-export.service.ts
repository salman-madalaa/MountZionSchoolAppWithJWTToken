import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ImportExportService {
  exportIndividual(isRteStudent: boolean, className: any) {
    throw new Error('Method not implemented.');
  }
  baseUrl = environment.baseUrl;

  constructor(private http:HttpClient) { }

  import(im):Observable<any>{
    return this.http.post(this.baseUrl + 'api/exportimport/upload',im);
  }

  export():Observable<Blob>{
    return this.http.get(this.baseUrl + 'api/exportimport/download',{responseType:'blob'});
  }

  exportInividual(isRteStudent:boolean,className:String):Observable<Blob>{
    return this.http.get(this.baseUrl + 'api/exportimport/download/'+isRteStudent+ '/' + className,{responseType:'blob'});
  }

}
