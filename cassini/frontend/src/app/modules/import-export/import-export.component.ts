import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationDialogService } from 'src/app/services/conformatioDialog/confirmation-dialog.service';
import { ImportExportService } from 'src/app/services/importExport/import-export.service';
import { LoaderService } from 'src/app/services/loader/loader.service';


@Component({
  selector: 'app-import-export',
  templateUrl: './import-export.component.html',
  styleUrls: ['./import-export.component.css']
})

export class ImportExportComponent implements OnInit {

  selectedFile: File | null = null;
  public import: FormGroup;
  isImport: boolean;
  isExport: boolean;
  isRteStudent: boolean;
  type: any;
  className: any;

  constructor(private _importExportService: ImportExportService, private route: ActivatedRoute, private formBuilder: FormBuilder,
    private router: Router, private loaderSer: LoaderService, private dialogSer: ConfirmationDialogService) {

    if (this.router.url == '/import') {
      this.isImport = true;
      this.isExport = false;
    } else {
      this.isImport = false;
      this.isExport = true;
    }

  }

  ngOnInit(): void {

    this.import = this.formBuilder.group({
      isRteStudent: new FormControl('', [Validators.required]),
      className: new FormControl('', [Validators.required]),
    });

  }

  importStudentData() {
    this.loaderSer.showNgxSpinner();
    const file = new FormData();
    file.append('file', this.selectedFile, this.selectedFile.name);
    this._importExportService.import(file).subscribe((data) => {
      this.loaderSer.hideNgxSpinner();
      this.loaderSer.showSucessSnakbar("Data imported successfully");
      this.selectedFile = null;
    }, (error) => {
      this.selectedFile = null;
      console.log(error);
      this.loaderSer.hideNgxSpinner();
      this.loaderSer.showFailureSnakbar(error.error.message);
    })
  }

  onFileChange(event) {
    this.selectedFile = event.target.files[0];

    const msg = `Are you sure you want to Import this file ?` + '  ' + this.selectedFile.name;
    const title = "Import Confirm Action";

    this.dialogSer.openConfirmationDialog(msg, title).afterClosed().subscribe(res => {
      if (res) {
        this.importStudentData();
      } else {
        this.selectedFile = null;
      }
    })

  }


  rteStudent(val: boolean): void {
    this.isRteStudent = val;
  }


  exportIndividual(value) {
    this.isRteStudent = value.isRteStudent;
    this.className = value.className;
    this.loaderSer.showNgxSpinner();
    this._importExportService.exportInividual(this.isRteStudent, this.className).subscribe(x => {
      var blob = new Blob([x], { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" });
      if (window.navigator && window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveOrOpenBlob(blob);
        return;
      }
      const data = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = data;
      link.download = this.className + 'Class.xlsx';
      link.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));

      setTimeout(function () {
        window.URL.revokeObjectURL(data);
        link.remove();
      }, 100);
      this.loaderSer.hideNgxSpinner();
      this.loaderSer.showSucessSnakbar(this.className + " Class data Exported successfully ");
    }, (error) => {
      console.log(error);
      console.log(error);
      this.loaderSer.hideNgxSpinner();
      this.loaderSer.showFailureSnakbar(error.error.message);
    });
  }

  exportTotalData() {
    this._importExportService.export().subscribe((x) => {
      var blob = new Blob([x], { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" });
      if (window.navigator && window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveOrOpenBlob(blob);
        return;
      }
      const data = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = data;
      link.download = 'TotalData.xlsx';
      link.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));

      setTimeout(function () {
        window.URL.revokeObjectURL(data);
        link.remove();
      }, 100);
      this.loaderSer.hideNgxSpinner();
      this.loaderSer.showSucessSnakbar(" Total data Exported successfully ");
    }, (error) => {
      console.log(error);
      this.loaderSer.hideNgxSpinner();
      this.loaderSer.showFailureSnakbar(error.error.message);
    });
  }


}
