package it.itresources.springtut.springtutorial.model.request;

import javax.validation.constraints.NotNull;

public class PasswordUpdateRequest {

    @NotNull
    String oldPassword;

    @NotNull
    String newPassword;

    public PasswordUpdateRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
