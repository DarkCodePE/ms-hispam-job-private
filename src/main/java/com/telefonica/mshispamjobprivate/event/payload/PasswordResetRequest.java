package com.telefonica.mshispamjobprivate.event.payload;

import com.telefonica.mshispamjobprivate.validation.annotation.MatchPassword;

import javax.validation.constraints.NotBlank;

@MatchPassword
public class PasswordResetRequest {

    @NotBlank(message = "The email for which the password needs to be reset can not be empty")
    private String email;

    @NotBlank(message = "New password cannot be blank")
    private String password;

    @NotBlank(message = "Confirm Password cannot be blank")
    private String confirmPassword;

    @NotBlank(message = "Password reset token for the specified email has to be supplied")
    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
