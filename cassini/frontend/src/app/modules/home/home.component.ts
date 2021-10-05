import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router, Routes } from '@angular/router';
import { HomeService } from 'src/app/services/home/home.service';
import { LoaderService } from 'src/app/services/loader/loader.service';
import { NewStudentComponent } from '../student/new-student/new-student.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit,OnDestroy {
  result: string = '';
  type:any;
  allStundents;
  allRteStundents;

 // arr2 = ["firstClass","secondClass","thirdClass","fourthClass","fifthClass","sixClass","seventhClass","eightClass","ninthClass","tenthClass"]


 constructor(public dialog: MatDialog, private homeSer: HomeService,private router: Router,private route: ActivatedRoute, public loaderSer:LoaderService) { }


  ngOnInit(): void {
    this.loaderSer.isHomeActive.next(true);

    this.type = this.route.snapshot.params['rtes'];
    if(this.type != null ){
      this.getAllRteStudentsCount();
    }
    else{
      this.getAllStudentsCounts();
    }

  }


//-----------------------RTE Students---------------------

  arr = ["nursary","kg1","kg2","first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth"];
  arr2 = ["nursary","kg1","kg2","first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth"]

  getAllRteStudentsCount() {
    this.loaderSer.showNgxSpinner();
    this.getIndividualRteClassStudentCount();
    this.homeSer.getAllRteStudentsCount().subscribe((data) => {
      this.allRteStundents= data;
     // this.getIndividualRteClassStudentCount();
      this.loaderSer.hideNgxSpinner();
    },(error)=>{
      console.log(error);
      this.loaderSer.hideNgxSpinner();
    });

  }

getIndividualRteClassStudentCount(){

  for (let i=0,j=0;i<this.arr.length,j<this.arr2.length;i++,j++) {

    this.homeSer.getRteStudentsClassCount(this.arr[i]).subscribe((data:any) => {

      this.arr2[j] = data;
   // console.log(this.arr[i] +"=" + this.arr2[j]);

    },(error)=>{
      console.log(error);
      this.loaderSer.hideNgxSpinner();
    });

  }

}


//-----------------------Normal Students---------------------

  arr4 = ["nursary","kg1","kg2","first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth","ninth","tenth"];
  arr5 = ["nursary","kg1","kg2","first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth","ninth","tenth"]

  getAllStudentsCounts() {
    this.loaderSer.showNgxSpinner();
    this.getIndividualNormalClassStudentCount();
    this.homeSer.getAllStudentsCount().subscribe((data) => {
      this.allStundents= data;
      this.loaderSer.hideNgxSpinner();
    },(error)=>{
      console.log(error);
      this.loaderSer.hideNgxSpinner();
    });

  }

  getIndividualNormalClassStudentCount(){

    for (let i=0,j=0;i<this.arr4.length,j<this.arr5.length;i++,j++) {

      this.homeSer.getStudentsClassCount(this.arr4[i]).subscribe((data:any) => {

        this.arr5[j] = data;
     // console.log(this.arr4[i] +"=" + this.arr5[j]);

      },(error)=>{
        console.log(error);
        this.loaderSer.hideNgxSpinner();
      });
    }
  }


//----------------------New Student Pop up window ------------------
  newStudent() {
    const dialogRef = this.dialog.open(NewStudentComponent, { hasBackdrop: true, disableClose: true, width: '1000px' });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      this.result = dialogResult;
      if (this.result == 'success') {
        this.getAllStudentsCounts();
        this.loaderSer.showSucessSnakbar("Student added successfully");
      }
      else if (this.result == 'failure') {
        this.loaderSer.showFailureSnakbar("Student added Failure");
      }
    });
  }



  allNormalStuents(normal:String){
    this.router.navigate(['students/',normal,'all']);
  }

  allRteStuents(rte:any){
    this.router.navigate(['students/',rte,'all']);
  }


  details(type:string,className:any){
    this.router.navigate(['students/',type,'class',className]);
  }

  ngOnDestroy(){

    this.loaderSer.isHomeActive.next(false);
  }
}
