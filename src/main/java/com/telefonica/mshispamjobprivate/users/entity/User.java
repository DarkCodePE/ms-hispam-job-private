package com.telefonica.mshispamjobprivate.users.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.telefonica.mshispamjobprivate.shared.entity.BaseEntity;
import com.telefonica.mshispamjobprivate.users.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String names;
    private String lastnames;
    private String username;
    private String email;
    private String displayname;
    private String country;
    private Date birthDate;
    private String civilStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_document_type")
    private DocumentType documentType;

    private String documentNumber;
    private String homeAddress;
    private String city;
    private String phone;
    private String about;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_salary_currency")
    private SalaryCurrency salaryCurrency;

    private Integer salaryAmount;
    private String password;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean active;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "USER_AUTHORITY", joinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")}, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")})
    private Set<Role> roles = new HashSet<>();

    @Column(name = "IS_EMAIL_VERIFIED", nullable = false)
    private Boolean isEmailVerified;

    public void addRole(Role role) {
        roles.add(role);
        role.getUserList().add(this);
    }

    public void addRoles(Set<Role> roles) {
        roles.forEach(this::addRole);
    }
    public void setEmailVerified(Boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public void markVerificationConfirmed() {
        setEmailVerified(true);
    }

    public User(User dto) {
//		names			= dto.getNames();
//		lastnames		= dto.getLastnames();
//		mail			= dto.getMail();
        displayname     = dto.getDisplayname();
        country			= dto.getCountry();
        birthDate		= dto.getBirthDate();
        civilStatus		= dto.getCivilStatus();
        documentNumber	= dto.getDocumentNumber();
        homeAddress		= dto.getHomeAddress();
        city			= dto.getCity();
        phone			= dto.getPhone();
        about			= dto.getAbout();
        salaryAmount	= dto.getSalaryAmount();
        isEmailVerified = dto.getIsEmailVerified();
    }

}
