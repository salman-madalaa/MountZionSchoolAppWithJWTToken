import { AfterViewInit, Component, OnInit, Pipe, PipeTransform, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router, Routes } from '@angular/router';
import { CommonDialogComponent } from 'src/app/common-dialog/common-dialog.component';
import { Student } from 'src/app/model/Student';
import { ConfirmationDialogService } from 'src/app/services/conformatioDialog/confirmation-dialog.service';
import { ImportExportService } from 'src/app/services/importExport/import-export.service';
import { LoaderService } from 'src/app/services/loader/loader.service';
import { StudentService } from 'src/app/services/studentService/student.service';
import { NewStudentComponent } from '../new-student/new-student.component';
import { UpdateStudentComponent } from '../update-student/update-student.component';

@Component({
  selector: 'app-all-students',
  templateUrl: './all-students.component.html',
  styleUrls: ['./all-students.component.css']
})
export class AllStudentsComponent implements OnInit, AfterViewInit {



  displayedColumns = ['registrationId', 'Image', 'dateOfAdmission', 'samagraId', 'firstName', 'lastName', 'fatherName', 'motherName', 'mobileNumber',
    'presentAddress', 'permanentAddress', 'classToJoin', 'gender', 'dateOfBirth', 'marksOfIdentification', 'religion', 'caste', 'castId',
    'aadharNumber', 'bankAccountNumber', 'ifscCode', 'childHandicapped', 'fatherMotherExpired', 'siblings', 'rteStudent',
    // 'admissionFee', 'examFee', 'schoolFee', 'busFee', 'totalFee','admissionFeePaid', 'admissionFeeDue', 'examFeePaid', 'examFeeDue', 'busFeePaid', 'busFeeDue',
    'Actions'];


  public dataSource = new MatTableDataSource<Student>();

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  sortProperty: 'desc';
  id: any;
  id1: any;
  rte: any;
  type: string;
  filterText = '';
  isActivateFilter: boolean = false;
  isRteStudent: boolean;
  classname: string;

  constructor(private service: StudentService, public dialog: MatDialog, private route: ActivatedRoute, private loaderSer: LoaderService,
    private _importExportService: ImportExportService, private dialogSer: ConfirmationDialogService) {
    this.route.params.subscribe(params => {
      this.ngOnInit();
    });
  }

  ngOnInit(): void {

    this.type = this.route.snapshot.params['type'];
    this.id = this.route.snapshot.params['className'];

    this.loadData();

  }

  loadData() {

    this.dataSource.filterPredicate = function (data, filter: string): boolean {
      return data.firstName.toLowerCase().includes(filter) ||
        data.registrationId.toString().includes(filter) ||
        data.samagraId.toString().includes(filter)
    };

    if (this.type == "rte" && this.id != undefined && this.id != null) {
      console.log("Rte individula students load");
      this.getRteStudents(this.id);
      this.isRteStudent = true;
      this.classname = this.id;

    } else if (this.id == undefined && this.type === "rte") {
      console.log("all Rte Students Load");
      this.getAllRteStudents();
      this.isRteStudent = true;
      this.classname = 'all';
    }

    if (this.type == "normal" && this.id != undefined && this.id != null) {
      console.log("normal individula class students load");
      this.getNormalStudents(this.id);
      this.isRteStudent = false;
      this.classname = this.id;
    } else if (this.id == undefined && this.type === "normal") {
      console.log("all normal students");
      this.getAllNormalStudents();
      this.isRteStudent = false;
      this.classname = 'all';
    }

  }


  ngAfterViewInit() {

    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;

    // const sortState: Sort = {active: 'registrationId', direction: 'desc'};
    // this.sort.active = sortState.active;
    // this.sort.direction = sortState.direction;
    // this.sort.sortChange.emit(sortState);
  }


  pageable = {
    page: 0,
    size: 20,
    sort: {
      field: "modifiedDate",
      order: "DESC"
    }
  };


  // ------------ Filter method ----------------//
  applyFilter(filterValue: string) {
    this.filterText = filterValue.trim();
    filterValue = this.filterText.toLowerCase();
    this.dataSource.filter = filterValue;

  }


  getAllNormalStudents() {
    this.loaderSer.showNgxSpinner();
    this.service.getAllNormalStudents(this.pageable).subscribe((data) => {
      this.dataSource.data = data;
      console.log(data);
      this.loaderSer.hideNgxSpinner();
    }, (error) => {
      console.log(error);
      this.loaderSer.hideNgxSpinner();
    });

  }

  getNormalStudents(id: any) {
    this.loaderSer.showNgxSpinner();
    this.service.getNormalClassStudents(id).subscribe((data) => {
      this.dataSource.data = data;
      this.loaderSer.hideNgxSpinner();
    }, (error) => {
      console.log(error);
      this.loaderSer.hideNgxSpinner();
    });

  }


  getAllRteStudents() {
    this.loaderSer.showNgxSpinner();
    this.service.getAllRteStudents(this.pageable).subscribe((data) => {
      this.dataSource.data = data;
      this.loaderSer.hideNgxSpinner();
    }, (error) => {
      console.log(error);
      this.loaderSer.hideNgxSpinner();
    });
  }

  getRteStudents(id: any) {
    this.loaderSer.showNgxSpinner();
    this.service.getRteClassStudents(id).subscribe((data) => {
      this.dataSource.data = data;
      this.loaderSer.hideNgxSpinner();
    }, (error) => {
      console.log(error);
      this.loaderSer.hideNgxSpinner();
    });
  }


  edit(student) {
    const dialogRef = this.dialog.open(UpdateStudentComponent, { data: student, disableClose: true, hasBackdrop: true, width: '1000px' });
    dialogRef.afterClosed().subscribe(dialogResult => {
      this.result = dialogResult;
      if (this.result == 'success') {
        // this.dialog.open(SuccessMessageComponent, { width: "600px", panelClass: 'dialog-container-custom-success', data: new SuccessMessage("Student updated successfully") });
        this.ngOnInit();
        this.loaderSer.showSucessSnakbar("Student Updated Successfully");
      }
    })
  }


  result: string = '';
  delete(element): void {

    const msg = `Are you sure you want to delete this student ?` + '  ' + element.firstName;
    const title = "Student Deletion Confirm Action";

    this.dialogSer.openConfirmationDialog(msg, title).afterClosed().subscribe(res => {

      if (res) {
        this.loaderSer.showNgxSpinner();
        element.status = 'DELETED';
        this.service.delete(element.id).subscribe((data) => {
          this.ngOnInit();
          this.loaderSer.hideNgxSpinner();
          this.loaderSer.showSucessSnakbar(element.firstName + " Deleted Successfully");
        }, (error) => {
          console.log(error);
          this.loaderSer.hideNgxSpinner();
          this.loaderSer.showFailureSnakbar(error.error.message);
        })
      }


    });


  }

  newStudent() {
    const dialogRef = this.dialog.open(NewStudentComponent, { disableClose: true, hasBackdrop: true, width: '1000px' });
    dialogRef.beforeClosed().subscribe((dialogResult) => {
      this.result = dialogResult;
      if (this.result == 'success') {
        this.ngOnInit();
        this.loaderSer.showSucessSnakbar("Student created Successfully");
      }
    });
  }

  export() {
    this.loaderSer.showNgxSpinner();
    this._importExportService.exportInividual(this.isRteStudent, this.classname).subscribe(x => {
      var blob = new Blob([x], { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" });
      if (window.navigator && window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveOrOpenBlob(blob);
        return;
      }
      const data = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = data;
      link.download = this.classname + 'Class' + this.type + 'Studnts.xlsx';
      link.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));

      setTimeout(function () {
        window.URL.revokeObjectURL(data);
        link.remove();
      }, 100);
      this.loaderSer.hideNgxSpinner();
      this.loaderSer.showSucessSnakbar(this.classname + " class " + this.type + " students data exported successfully");
    }, (error) => {
      console.log(error);
      this.loaderSer.hideNgxSpinner();
      this.loaderSer.showFailureSnakbar(error.error.message);
    });
  }


  openBigImage(ob) {
    const dialogRef = this.dialog.open(CommonDialogComponent, {

      data: { ob, title: "image" },
    });

  }

}



@Pipe({
  name: 'highlightSearch'
})
export class HighlightSearchPipe implements PipeTransform {
  constructor() { }

  transform(value: string, search: string): string {
    const valueStr = value + ''; // Ensure numeric values are converted to strings
    return valueStr.replace(new RegExp('(?![^&;]+;)(?!<[^<>]*)(' + search + ')(?![^<>]*>)(?![^&;]+;)', 'gi'), '<strong class="text-highlight">$1</strong>');
  }
}

