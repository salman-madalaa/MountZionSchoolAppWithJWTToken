package com.zion.school.model.security.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zion.school.helper.ImageHelper;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 20)
    private String firstName;

    @Size(max = 20)
    private String lastName;

    @Lob
    private String image;

    private Long phoneNumber;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;



}
