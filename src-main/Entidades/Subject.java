package Entidades;

import GUI.ApostadorObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Subject {
    private List<ApostadorObserver> apostadores;

    private static Subject singleton = new Subject();

    public Subject() {
        this.apostadores = new ArrayList<>();
    }

    public static Subject getInstance(){
        return Subject.singleton;
    }

    public void addApostador(ApostadorObserver a){
        if (this.apostadores.stream().anyMatch(y -> y.getIdApostador().equals(a.getIdApostador()))){
            System.out.println("ApostadorObserver " + a.getIdApostador() + "ja existe.");
        }
        else
            this.apostadores.add(a);
    }

    public void removeApostador(ApostadorObserver a){
        List<ApostadorObserver> apostadorObservers = apostadores.stream().filter(y -> !y.getIdApostador().equals(a.getIdApostador())).collect(Collectors.toList());
        apostadores = apostadorObservers;
    }

    public void notifyApostadores(){
        apostadores.forEach(y -> y.update());
    }

    //Notifica uma lista de apostadores
    //String -> id do apostador
    //Double -> numero de apostas que foram finalizadas
    public void notifyApostadores(HashMap<String,Double> apostador){
        for (ApostadorObserver ao : apostadores){
            if (apostador.containsKey(ao.getIdApostador()))
                ao.update(apostador.get(ao.getIdApostador()));
        }
    }

}
