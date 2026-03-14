package solitaire;

import DeckOfCards.CartaInglesa;

/**
 * Modela el montón de descarte (waste pile) del solitario.
 * Las cartas llegan boca arriba desde el DrawPile.
 *
 * @author Cecilia Curlango
 * @version 2025
 */
public class WastePile {
    private Pila<CartaInglesa> cartas;

    public WastePile() {
        cartas = new Pila<>();
    }

    public void addCartas(Pila<CartaInglesa> nuevas) {
        if (nuevas == null) return;

        Pila<CartaInglesa> auxiliar = new Pila<>();
        while (!nuevas.pilaVacia()) {
            CartaInglesa c = nuevas.pop();
            c.makeFaceUp();
            auxiliar.push(c);
        }
        while (!auxiliar.pilaVacia()) {
            cartas.push(auxiliar.pop());
        }
    }

    public Pila<CartaInglesa> emptyPile() {
        Pila<CartaInglesa> temp = cartas;
        cartas = new Pila<>();
        return temp;
    }


    public CartaInglesa verCarta() {
        return cartas.peek();
    }


    public CartaInglesa getCarta() {
        return cartas.pop();
    }


    public boolean hayCartas() {
        return !cartas.pilaVacia();
    }

    @Override
    public String toString() {
        if (cartas.pilaVacia()) return "-W-";
        return cartas.peek().toString();
    }

    public Pila<CartaInglesa> getPila() {
    return cartas;
}
public void setPila(Pila<CartaInglesa> nueva) {
    this.cartas = nueva;
}
}
