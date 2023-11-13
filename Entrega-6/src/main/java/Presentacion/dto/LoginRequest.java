package Presentacion.dto;
public class LoginRequest {

    private String email;
    private String password;

    public String getUEmail() {
        return email;
    }

    public void setUsername(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + email + '\'' +
                '}';
    }
}