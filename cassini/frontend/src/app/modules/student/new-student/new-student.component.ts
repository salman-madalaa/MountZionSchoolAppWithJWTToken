import { Component, ElementRef, OnInit, ViewChildren } from '@angular/core';
import { FormGroup, FormControl, Validators, FormControlName, FormBuilder, FormArray } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Student } from 'src/app/model/Student';
import { LoaderService } from 'src/app/services/loader/loader.service';
import { StudentService } from 'src/app/services/studentService/student.service';

@Component({
  selector: 'app-new-student',
  templateUrl: './new-student.component.html',
  styleUrls: ['./new-student.component.css']
})
export class NewStudentComponent implements OnInit {



  public student: FormGroup;
  dataSource: Student[];
  selected = '';
  FMExpired = true;
  isRteStudent = false;
  isSiblings = false;
  myFormGroup: any;
  imageSrc: string;
  selectedFile: File;

  constructor(private router: Router, private service: StudentService, private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<NewStudentComponent>, public dialog: MatDialog, public loaderSer: LoaderService) {

    this.student = this.formBuilder.group({
      registrationId: new FormControl('', [Validators.required]),
      dateOfAdmission: new FormControl('', []),
      samagraId: new FormControl('', []),
      firstName: new FormControl('', []),
      lastName: new FormControl('', []),
      fatherName: new FormControl('', []),
      motherName: new FormControl('', []),
      mobileNumber: ['', [Validators.required, Validators.pattern('[6-9]\\d{9}'), Validators.maxLength(10)]],
      presentAddress: new FormControl('', []),
      permanentAddress: new FormControl('', []),
      classToJoin: new FormControl('', []),
      gender: new FormControl('', []),
      dateOfBirth: new FormControl('', []),
      marksOfIdentification: new FormControl('', []),
      religion: new FormControl('', []),
      caste: new FormControl('', []),
      castId: new FormControl('', []),
      aadharNumber: new FormControl('', [Validators.minLength(12), Validators.maxLength(12)]),
      bankAccountNumber: new FormControl('', []),
      ifscCode: new FormControl('', []),
      childHandicapped: new FormControl('', []),
      fatherMotherExpired: new FormControl('', [Validators.required]),
      siblings: new FormControl('', [Validators.required]),
      rteStudent: new FormControl('', [Validators.required]),
      siblingInformation: this.formBuilder.array([]),
      // admissionFee: new FormControl('0',[]),
      // examFee: new FormControl('0',[]),
      // schoolFee: new FormControl('0',[]),
      // busFee: new FormControl('0',[]),
    });

  }


  ngOnInit(): void {
  }

  fatherMotherExpired(val: boolean): void {
    if (val) {
      this.student.patchValue({
        fatherName: '',
        motherName: ''
      });
    }
    this.FMExpired = val;
  }

  rteStudent(val: boolean): void {
    if (val) {
      this.student.patchValue({
        classToJoin: '',
      });
    }
    this.isRteStudent = val;
  }

  siblings(val: boolean): void {
    if (val == false) {
      (this.student.get("siblingInformation") as FormArray).clear();
    }
    this.isSiblings = val;
  }

  create(student) {
    this.loaderSer.showNgxSpinner();
    this.service.Create(student).subscribe((data) => {
      if (this.selectedFile != null && data.id != null && data.id != undefined) {
        this.onUpload(data.id);
      } else {
        this.dialogRef.close('success');
        this.loaderSer.hideNgxSpinner();
      }
    }, (error) => {
      console.log(error);
      this.loaderSer.hideNgxSpinner();
      this.loaderSer.showFailureSnakbar(error.error.message);
    })
  }


  //----- add the Sibling Information--------------------//
  siblingInformation(): FormArray {
    return this.student.get("siblingInformation") as FormArray
  }

  newSibling(): FormGroup {
    return this.formBuilder.group({
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

  onFileChange(event) {

    if (Math.round(event.target.files[0].size / 1024) < 70 && Math.round(event.target.files[0].size / 1024) > 40) {
      this.selectedFile = event.target.files[0];

      const reader = new FileReader();
      this.imageSrc = reader.result as string;

      if (event.target.files && event.target.files.length) {
        const [image] = event.target.files;
        reader.readAsDataURL(image);

        reader.onload = () => {

          this.imageSrc = reader.result as string;

          this.student.patchValue({
            image: JSON.stringify(reader.result)
          });

        };

      }

    } else {
      this.loaderSer.showNormalSnakbar("Image size should be beteen 40kb to 70 kb");
    }

  }


  onUpload(id) {
    const image = new FormData();
    image.append('imageFile', this.selectedFile, this.selectedFile.name);

    this.service.uploadImage(id, image).subscribe((data) => {
      this.dialogRef.close('success');
      console.log("image uploaded succesfully");
      this.loaderSer.hideNgxSpinner();
    }, (error) => {
      console.log("image uploaded failure");
      this.loaderSer.hideNgxSpinner();
    });

  }


  noImage() {
    this.imageSrc = 'D:/School App/front end/registrationWithMaterialdesign/src/assets/noImage.jpg';
  }

}
