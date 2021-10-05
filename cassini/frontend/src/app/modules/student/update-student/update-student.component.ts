import { Component, EventEmitter, Inject, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ConfirmationDialogService } from 'src/app/services/conformatioDialog/confirmation-dialog.service';
import { LoaderService } from 'src/app/services/loader/loader.service';
import { SiblingInformationService } from 'src/app/services/siblingInformationService/sibling-information.service';
import { StudentService } from 'src/app/services/studentService/student.service';
import { AllStudentsComponent } from '../all-students/all-students.component';

@Component({
  selector: 'app-update-student',
  templateUrl: './update-student.component.html',
  styleUrls: ['./update-student.component.css'],
})

export class UpdateStudentComponent implements OnInit {



  public student: FormGroup;
  FMExpired: boolean;
  isRteStudent: boolean;
  isSiblings: boolean;
  imageSrc: any;
  retrieveResonse: any;
  base64Data: any;
  selectedFile: File;
  id: number;


  class: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<UpdateStudentComponent>, private service: StudentService,
    private router: Router, private sibInfoSer: SiblingInformationService, public _snackBar: MatSnackBar,
    public loaderSer: LoaderService, private dialogSer: ConfirmationDialogService) {

    this.student = this.formBuilder.group({
      id: new FormControl('', []),
      registrationId: new FormControl('', []),
      dateOfAdmission: new FormControl('', []),
      samagraId: new FormControl('', []),
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      fatherMotherExpired: new FormControl('', [Validators.required]).setValue(this.data.fatherMotherExpired),
      fatherName: new FormControl('', []),
      motherName: new FormControl('', []),
      mobileNumber: ['', [Validators.required, Validators.pattern('[6-9]\\d{9}'), Validators.maxLength(10)]],
      presentAddress: new FormControl('', []),
      permanentAddress: new FormControl('', []),
      classToJoin: new FormControl('classToJoin', [Validators.required]),
      gender: new FormControl('', [Validators.required]),
      dateOfBirth: new FormControl('', []),
      marksOfIdentification: new FormControl('', []),
      religion: new FormControl('', []),
      caste: new FormControl('', []),
      castId: new FormControl('', []),
      aadharNumber: new FormControl('', [Validators.minLength(12), Validators.maxLength(12)]),
      bankAccountNumber: new FormControl('', []),
      ifscCode: new FormControl('', []),
      childHandicapped: new FormControl('', []),
      siblings: new FormControl('', []),
      rteStudent: new FormControl('', [Validators.required]),
      siblingInformation: this.formBuilder.array([
        this.formBuilder.group({
          name: new FormControl('', [Validators.required]),
          age: new FormControl('', [Validators.required])
        })
      ]),
    });


  }

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    this.student.patchValue({
      id: this.data.id,
      registrationId: this.data.registrationId,
      dateOfAdmission: this.data.dateOfAdmission,
      samagraId: this.data.samagraId,
      firstName: this.data.firstName,
      lastName: this.data.lastName,
      fatherMotherExpired: this.data.fatherMotherExpired,
      fatherName: this.data.fatherName,
      motherName: this.data.motherName,
      mobileNumber: this.data.mobileNumber,
      presentAddress: this.data.presentAddress,
      permanentAddress: this.data.permanentAddress,
      classToJoin: this.data.classToJoin,
      gender: this.data.gender,
      dateOfBirth: this.data.dateOfBirth,
      marksOfIdentification: this.data.marksOfIdentification,
      religion: this.data.religion,
      caste: this.data.caste,
      castId: this.data.castId,
      aadharNumber: this.data.aadharNumber,
      bankAccountNumber: this.data.bankAccountNumber,
      ifscCode: this.data.ifscCode,
      childHandicapped: this.data.childHandicapped,
      siblings: this.data.siblings,
      rteStudent: this.data.rteStudent,
      siblingInformation: this.data.siblingInformation,

    });

    this.FMExpired = this.data.fatherMotherExpired;
    this.isRteStudent = this.data.rteStudent;
    this.isSiblings = this.data.siblings;
    this.student.setControl('siblingInformation', this.setExistsSiblingInformation(this.data.siblingInformation));
    this.id = this.data.id;

    this.imageSrc = 'data:image/jpeg;base64,' + this.data.studentImage.picByte;


  }


  setExistsSiblingInformation(siblingInformationSets: any): FormArray {
    const formArray = new FormArray([]);
    siblingInformationSets.forEach(s => {
      formArray.push(this.formBuilder.group({
        id: s.id,
        name: new FormControl(s.name, [Validators.required]),
        age: new FormControl(s.age, [Validators.required]),
      }));
    });
    return formArray;
  }



  fatherMotherExpired(val: boolean): void {
    if (val == false) {
      this.student.patchValue({
        fatherName: this.data.fatherName,
        motherName: this.data.motherName
      });
    }
    if (val == true) {
      this.student.patchValue({
        fatherName: '',
        motherName: ''
      });
    }
    this.FMExpired = val;
  }

  rteStudent(val: boolean): void {
    this.student.patchValue({
      classToJoin: '',
    });
    this.isRteStudent = val;
  }

  siblings(val: boolean): void {
    if (val == false) {
      // (this.student.get("siblingInformation") as FormArray).clear();
    }
    this.isSiblings = val;
  }


  update(student) {
    this.loaderSer.showNgxSpinner();
    this.service.updateStudent(student.id, student).subscribe((data) => {
      this.dialogRef.close('success');
    }, (error) => {
      console.log(error);
      this.loaderSer.hideNgxSpinner();
      this.loaderSer.showFailureSnakbar(error.error.message);
    })
  }


  // updateSibInfo(info) {
  //   this.loaderSer.showNgxSpinner();
  //   this.sibInfoSer.updateSibInfo(info.id, info).subscribe((data) => {
  //     this.dialogRef.close('success');
  //     this.loaderSer.hideNgxSpinner();
  //   }, (error) => {
  //     console.log(error);
  //     this.dialogRef.close('failure');
  //     this.loaderSer.showNgxSpinner();
  //   })
  // }

  deleteSibInfo(i: number, info) {
    this.loaderSer.showNgxSpinner();
    this.sibInfoSer.delete(info.id).subscribe((data) => {
      this.removeSibling(i);
      this.loaderSer.hideNgxSpinner();
      this.loaderSer.showSucessSnakbar("Sibling delete successfully");
    }, (error) => {
      console.log(error);
      this.loaderSer.hideNgxSpinner();
      this.loaderSer.showFailureSnakbar(error.error.message);
    });
  }



  //----- add the Sibling Information--------------------//
  siblingInformation(): FormArray {
    return this.student.get("siblingInformation") as FormArray
  }

  newSibling(): FormGroup {
    return this.formBuilder.group({
      id: null,
      name: new FormControl('', [Validators.required]),
      age: new FormControl('', [Validators.required]),
    });
  }

  addSibling() {
    this.siblingInformation().push(this.newSibling());
  }

  removeSibling(i: number) {
    this.siblingInformation().removeAt(i);
  }

  fileValidate(event) {
    let isValid = false;

    if (Math.round(event.target.files[0].size / 1024) < 70 && Math.round(event.target.files[0].size / 1024) > 40) {
      isValid = true;
    } else {
      isValid = false;
      this.loaderSer.showNormalSnakbar("Image size should be beteen 40kb to 70 kb");
    }
    return isValid;
  }

  onFileChange(event) {

    if (this.fileValidate(event)) {
      this.selectedFile = event.target.files[0];
      const image = new FormData();
      image.append('imageFile', this.selectedFile, this.selectedFile.name);
      this.loaderSer.showNgxSpinner();
      this.service.updateImage(this.id, image).subscribe((data) => {
        this.retrieveResonse = data;
        this.base64Data = this.retrieveResonse.picByte;
        this.imageSrc = 'data:image/png;base64,' + this.base64Data;
        this.loaderSer.hideNgxSpinner();
        this.loaderSer.showSucessSnakbar("Image uploaded succesfully");
      }, (error) => {
        event = null;
        console.log(error);
        this.loaderSer.hideNgxSpinner();
        this.loaderSer.showFailureSnakbar(error.error.message);
      });
    }
  }

  deleteImage() {

    const msg = `Are you sure you want to delete this image ?`;
    const title = "Image Deletion Confirm Action";

    this.dialogSer.openConfirmationDialog(msg, title).afterClosed().subscribe(res => {
      if (res) {
        this.loaderSer.showNgxSpinner();
        this.service.deleteImage(this.id).subscribe((data) => {
          this.imageSrc = null;
          this.loaderSer.hideNgxSpinner();
          this.loaderSer.showSucessSnakbar("Image delete succesfully");
        }, (error) => {
          console.log(error);
          this.loaderSer.hideNgxSpinner();
          this.loaderSer.showFailureSnakbar(error.error.message);
        });
      }
    });
  }


  newImageUpload(event) {
    if (this.fileValidate(event)) {
      this.loaderSer.showNgxSpinner();
      this.selectedFile = event.target.files[0];
      const image = new FormData();
      image.append('imageFile', this.selectedFile, this.selectedFile.name);
      this.service.uploadImage(this.id, image).subscribe((data) => {
        this.retrieveResonse = data;
        this.base64Data = this.retrieveResonse.picByte;
        this.imageSrc = 'data:image/png;base64,' + this.base64Data;
        this.loaderSer.hideNgxSpinner();
        this.loaderSer.showSucessSnakbar("Image uploaded succesfully");
      }, (error) => {
        console.log(error);
        this.loaderSer.hideNgxSpinner();
        this.loaderSer.showFailureSnakbar(error.error.message);
      })
    }
  }
}
