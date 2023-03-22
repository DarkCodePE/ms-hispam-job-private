package com.telefonica.mshispamjobprivate.users.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    private String names;
    private String lastnames;
    private String mail;
    private String displayname;
    private String country;
    private Date birthDate;
    private String civilStatus;
    private Integer idDocumentType;
    private String documentNumber;
    private String homeAddress;
    private String city;
    private String phone;
    private String about;
    private Integer idSalaryCurrency;
    private Integer salaryAmount;
}
