package GUI;

import Entidades.Apostador;

import java.util.Date;
import java.util.List;

public class ApostadorObserver extends Apostador {

    public ApostadorObserver(String email, String nome, String password, String username, String idApostador, Date dataNascimento) {
        super(email, nome, password, username, idApostador, dataNascimento);
    }

    public ApostadorObserver() {
    }

    public ApostadorObserver(Apostador a) {
        super(a);
    }

    public void update(){
        ///
        System.out.println("SE ESTAS A  VER ISTO E PQ O OBSERVER TA A DAR");
    }

    public void update(Double nApostas){
        ///
        System.out.println("Apostador: " + this.getEmail() + "\nNumero de apostas a verificar: " + nApostas);
    }
}
