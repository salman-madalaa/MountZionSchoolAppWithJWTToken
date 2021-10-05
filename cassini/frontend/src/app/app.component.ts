import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormBuilder } from '@angular/forms'
import { LoaderService } from './services/loader/loader.service';
import { AuthenticationService } from './services/authService/authentication.service';
import { Observable } from 'rxjs';
import { shareReplay, map } from 'rxjs/operators';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Cassini E.M.School (Hyderabad)';
  // title = 'Mount Zion E.M.School (Tendukheda)';
  productForm: FormGroup;
  isLogged: boolean = true;
  panelOpenState = false;

  isDarkTheme: boolean = false;

  normalClass: string[] = ["nursary","kg1","kg2","first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth","ninth","tenth"];
  rteClass: string[] = ["nursary","kg1","kg2","first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth"];


  constructor(public authSer: AuthenticationService, private _router: Router, private dialog: MatDialog, private fb: FormBuilder,
    public loaderSer: LoaderService,public activateRoute: ActivatedRoute) {

  }

  ngOnInit() {
    this.isDarkTheme = localStorage.getItem('theme') == "Dark" ? true : false;
    // this.authSer.logout();
    // this._router.navigateByUrl("/login");
  }

  logout() {
    this.authSer.logout();
    this._router.navigateByUrl("/logout");
  }

  home() {
    this._router.navigate(['home']);
  }

  normalStudents() {
    this._router.navigate(['home']);
  }

  RteStudents(rte) {
    this._router.navigate(['home/', rte]);
  }

  normalClassStudents(className){
    this._router.navigate(['/students/class/', className]);
  }

  storeThemeSelection(val:boolean) {
    this.isDarkTheme = val;
    localStorage.setItem('theme', this.isDarkTheme ? "Dark" : "Light");
  }
}
