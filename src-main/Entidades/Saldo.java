package Entidades;

import java.util.HashMap;

public class Saldo {
    private String idApostador;
    private HashMap<Moeda, Float> carteira;

    public Saldo(String id){
        idApostador = id;
        carteira = new HashMap<>();
    }

    public void adicionarSaldo(Moeda m, float valor){
        if(carteira.containsKey(m)){
            carteira.put(m,carteira.get(m)+valor);
        } else carteira.put(m,valor);
    }

    public void retirarSaldo(Moeda m, float valor){
        if(carteira.containsKey(m)){
            carteira.put(m,carteira.get(m)-valor);
        }
    }

    public HashMap<Moeda, Float> getCarteira() {
        return carteira;
    }

    public void setCarteira(HashMap<Moeda, Float> carteira) {
        this.carteira = carteira;
    }

    public float saldo(){
        float v = 0;

        for(Moeda m : carteira.keySet()){
            v += carteira.get(m) * m.getRatio();
        }

        return v;
    }

    public boolean isItOkay(Moeda m, float valor){
        if(carteira.containsKey(m)){
            if(carteira.get(m) >= valor) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Saldo{" +
                "carteira=" + carteira +
                '}';
    }
}
