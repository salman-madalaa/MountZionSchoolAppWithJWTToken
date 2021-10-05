import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { StudentService } from './student.service';

describe('StudentService', () => {
  let service: StudentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule],
      providers: [ StudentService ]
    });
    service = TestBed.inject(StudentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('get all Students data',()=>{
    const data= [
      {aadharNumber: '',
        admissionFee: 100,
        admissionFeeDue: null,
        admissionFeePaid: null,
        bankAccountNumber: 1523647,
        busFee: 100,
        busFeeDue: null,
        busFeePaid: null,
        castId: "123456",
        caste: "bc d",
        childHandicapped: false,
        classToJoin: "2",
        dateOfAdmission: "2021-03-03",
        dateOfBirth: null,
        examFee: 100,
        examFeeDue: null,
        examFeePaid: null,
        fatherMotherExpired: false,
        fatherName: "shanmukhi",
        firstName: "salman1111",
        gender: "male",
        ifscCode: "SBI0425",
        lastName: "madala",
        marksOfIdentification: "a",
        mobileNumber: 9703395735,
        motherName: "samrajyam",
        permanentAddress: "aaaaa",
        presentAddress: "present",
        registrationId: 38,
        religion: "hindu",
        samagraId: 1111,
        schoolFee: 100,
        totalFee: null}
    ]
  })
});
