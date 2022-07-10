package Entidades;

import java.time.LocalDateTime;

public class Gestor {
    private String id;
    private String password;

    public Gestor(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    boolean validaEvento(LocalDateTime dataEvento){
        return true;
    }

    @Override
    public String toString() {
        return "Gestor{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
