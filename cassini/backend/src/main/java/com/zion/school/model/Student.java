package com.zion.school.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Lenovo on 01-03-2021.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "STUDENT")
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID",nullable = false)
    private Long id;

    @Column(name = "REGISTRATIONID",nullable = false)
    private String registrationId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "FATHER_NAME")
    private String fatherName;

    @Column(name = "MOTHER_NAME")
    private String motherName;

    @Column(name = "MOBILE_NUMBER")
    private Long mobileNumber;

    @Column(name = "PRESENT_ADDRESS")
    private String presentAddress;

    @Column(name = "PERMENT_ADDRESS")
    private String permanentAddress;

    @Column(name = "SAMAGRA_ID")
    private String samagraId;

    @Column(name = "DATE_OF_ADMISSION")
    @Temporal(TemporalType.DATE)
    private Date dateOfAdmission;

    @Column(name = "CLASS_TO_JOIN")
    private String classToJoin;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "DATE_OF_BIRTH")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "MARKS_OF_IDENTIFICATION")
    private String marksOfIdentification;

    @Column(name = "RELIGION")
    private String religion;

    @Column(name = "CASTE")
    private String caste;

    @Column(name = "CAST_ID")
    private String castId;

    @Column(name = "AADHAR_NUMBER")
    private Long aadharNumber;

    @Column(name = "BANK_ACCOUNT_NUMBER")
    private Long bankAccountNumber;

    @Column(name = "IFSC_CODE")
    private String ifscCode;

    @Column(name = "CHILD_HANDICAPPED")
    private Boolean childHandicapped;

    @Column(name = "FATHER_MOTHER_EXPIRED")
    private Boolean fatherMotherExpired;

    @Column(name = "SIBLINGS")
    private Boolean siblings = false;

    @Column(name = "RTE_STUDENTS")
    private Boolean rteStudent = false;


    @OneToMany(targetEntity = SiblingInformation.class, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "Student_Sibling_fk", referencedColumnName = "ID", nullable = false, updatable = false)
    private Set<SiblingInformation> siblingInformation;


    @Transient
    private StudentImage studentImage;


}
