import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatOptionSelectionChange } from '@angular/material/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public Create(ob): Observable<any> {
    let body = JSON.stringify(ob);
    let options = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.post(this.baseUrl + 'api/student/new', body,options);
  }

  public getAllStudents(pageable): Observable<any> {
    return this.http.get(this.baseUrl + 'api/student/all');
  }

  public getStudents(className): Observable<any> {
    return this.http.get(this.baseUrl + 'api/student/class/' + className);
  }

  updateStudent(id: number, ob): Observable<any> {
    let body = JSON.stringify(ob);
   // let options = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.put(this.baseUrl + 'api/student/' + id, ob);
  }

  public delete(id: number) {
    return this.http.delete(this.baseUrl + "api/student/" + id);
  }

  logout() {
    return this.http.get(this.baseUrl + "api/student/logout");
  }


  //---------------------normal Students ------------------------

  public getAllNormalStudents(pageable): Observable<any> {
    return this.http.get(this.baseUrl + 'api/student/normal/all');
  }


  public getNormalClassStudents(className): Observable<any> {
    return this.http.get(this.baseUrl + 'api/student/normal/class/' + className);
  }


  //---------------------Rte Students ------------------------
  public getAllRteStudents(pageable): Observable<any> {
    return this.http.get(this.baseUrl + 'api/student/rte/all');
  }


  public getRteClassStudents(className): Observable<any> {
    return this.http.get(this.baseUrl + 'api/student/rte/class/' + className);
  }



uploadImage(id,uploadImageData): Observable<any> {
  return this.http.post(this.baseUrl + 'api/studentImage/' + id + '/upload', uploadImageData);
}

updateImage(id,uploadImageData): Observable<any> {
  return this.http.put(this.baseUrl + 'api/studentImage/' + id + '/update', uploadImageData);
}

deleteImage(id): Observable<any> {
  return this.http.delete(this.baseUrl + 'api/studentImage/' + id + '/delete');
}


studentGetImage(id): Observable<any> {
  let options = { headers: new HttpHeaders().set('Content-Type', 'multipart/form-data') };
  return this.http.get(this.baseUrl + 'api/studentImage/get/' + id ,options);
}


}

/*
public getAllStudents(pageable):Observable<any>
{
    return this.http.get("/api/student",{
        params: new HttpParams()
            .set('page',  pageable.page)
            .set('size',  pageable.size)
            .set('sort',  pageable.sort.field).append()
          });
    http://localhost:8084/api/cm/ecos?page=0&size=20&sort=modifiedDate:DESC
    return this.http.get('/api/student?'+'page='+pageable.page+'&size='+pageable.size+'&sort='+pageable.sort.field+':'+pageable.sort.order);

}
*/
