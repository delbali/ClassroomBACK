package it.itresources.springtut.springtutorial.model;

public class AuthToken {
    private String token;
    private String type = "Bearer";

    public AuthToken() {
    }

    public AuthToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AuthToken token(String token) {
        setToken(token);
        return this;
    }

    public AuthToken type(String type) {
        setType(type);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " token='" + getToken() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }

}
