package solitaire;

import DeckOfCards.Carta;
import DeckOfCards.CartaInglesa;

import java.util.ArrayList;

/**
 * Modela un mazo de cartas de solitario.
 * @author Cecilia Curlango
 * @version 2025
 */
public class DrawPile {
    private Pila<CartaInglesa> cartas;
    private int cuantasCartasSeEntregan = 1;

    public DrawPile() {
        DeckOfCards.Mazo mazo = new DeckOfCards.Mazo();
        cartas = mazo.getCartas();
        setCuantasCartasSeEntregan(3);
    }

    /**
     * Establece cuantas cartas se sacan cada vez.
     * Puede ser 1 o 3 normalmente.
     * @param cuantasCartasSeEntregan
     */
    public void setCuantasCartasSeEntregan(int cuantasCartasSeEntregan) {
        this.cuantasCartasSeEntregan = cuantasCartasSeEntregan;
    }

    /**
     * Regresa la cantidad de cartas que se sacan cada vez.
     * @return cantidad de cartas que se entregan
     */
    public int getCuantasCartasSeEntregan() {
        return cuantasCartasSeEntregan;
    }


    public Pila<CartaInglesa> getCartas(int cantidad) {
        Pila<CartaInglesa> retiradas = new Pila<>();

        for (int i = 0; i < cantidad && !cartas.pilaVacia(); i++) {
            retiradas.push(cartas.pop());
        }

        return retiradas;
    }

 
//    public ArrayList<CartaInglesa> retirarCartas() {
//        ArrayList<CartaInglesa> retiradas = new ArrayList<>();
//        int maximoARetirar = cartas.size() < cuantasCartasSeEntregan ? cartas.size() : cuantasCartasSeEntregan;
//
//        for (int i = 0; i < maximoARetirar; i++) {
//            CartaInglesa retirada = cartas.remove(0);
//            retirada.makeFaceUp();
//            retiradas.add(retirada);
//        }
//        return retiradas;
//    }

    public Pila<CartaInglesa> retirarCartas(){
        Pila<CartaInglesa> retiradas = new Pila<>();
        int maximoARetirar = cartas.size() < cuantasCartasSeEntregan ? cartas.size() : cuantasCartasSeEntregan;

        for (int i = 0; i < maximoARetirar; i++){
            CartaInglesa retirada = cartas.pop();
            retirada.makeFaceUp();
            retiradas.push(retirada);
        }
        return retiradas;
    }


//    public boolean hayCartas() {
//        return cartas.size() > 0;
//    }
    public boolean hayCartas() {
        return !cartas.pilaVacia();
    }

//    public CartaInglesa verCarta() {
//        CartaInglesa regresar = null;
//        if (!cartas.pilaVacia()) {
//            regresar = cartas.peek();
//        }
//        return regresar;
//    }
public CartaInglesa verCarta() {
    return cartas.peek();
}

//    public void recargar(ArrayList<CartaInglesa> cartasAgregar) {
//        cartas = cartasAgregar;
//        for (CartaInglesa aCarta : cartas) {
//            aCarta.makeFaceDown();
//        }
//    }


    public void recargar(Pila<CartaInglesa> cartasAgregar){

        Pila<CartaInglesa> auxiliar = new Pila<>();

        while (!cartasAgregar.pilaVacia()){
            CartaInglesa carta = cartasAgregar.pop();
            carta.makeFaceDown();
            auxiliar.push(carta);
        }

        cartas = auxiliar;
    }

    @Override
    public String toString() {
        if (cartas.pilaVacia()) {
            return "-E-";
        }
        return "@";
    }

    public Pila<CartaInglesa> getPila() {
            return cartas;
}
public void setPila(Pila<CartaInglesa> nueva) {
    this.cartas = nueva;
}
}
