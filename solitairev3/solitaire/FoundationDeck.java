package solitaire;

import DeckOfCards.CartaInglesa;
import DeckOfCards.Palo;

public class FoundationDeck {
    private Palo palo;
    private Pila<CartaInglesa> cartas = new Pila<>();

/*          public FoundationDeck(CartaInglesa carta) {
        palo = carta.getPalo();
        // solo agrega la carta si es un A
        if (carta.getValorBajo() == 1) {
            cartas.add(carta);
        }
             */
    public FoundationDeck(Palo palo) {
        this.palo = palo;
    }
    /* */
    public FoundationDeck(CartaInglesa carta) {
        this.palo = carta.getPalo();
        // solo agrega la carta si es un As
        if (carta.getValorBajo() == 1) {
            cartas.push(carta);
        }
    }

/*  public boolean agregarCarta(CartaInglesa carta) {
        boolean agregado = false;
        if (carta.tieneElMismoPalo(palo)) {
            if (cartas.isEmpty()) {
                if (carta.getValorBajo() == 1) {
                    // si no hay cartas entonces la carta debe ser un A
                    cartas.add(carta);
                    agregado = true;
                }
            } else {
                // si hay cartas entonces debe haber secuencia
                CartaInglesa ultimaCarta = cartas.getLast();
                if (ultimaCarta.getValorBajo() + 1 == carta.getValorBajo()) {
                    // agregar la carta si el la siguiente a la última
                    cartas.add(carta);
                    agregado = true;
                }
            }
        }
        return agregado;
    } */
    public boolean agregarCarta(CartaInglesa carta) {
        if (!carta.tieneElMismoPalo(palo)) return false;

        if (cartas.pilaVacia()) {
            if (carta.getValorBajo() == 1) {
                cartas.push(carta);
                return true;
            }
        } else {
            CartaInglesa ultimaCarta = cartas.peek();
            if (ultimaCarta.getValorBajo() + 1 == carta.getValorBajo()) {
                cartas.push(carta);
                return true;
            }
        }
        return false;
    }

/*    CartaInglesa removerUltimaCarta() {
        CartaInglesa ultimaCarta = null;
        if (!cartas.isEmpty()) {
            ultimaCarta = cartas.getLast();
            cartas.remove(ultimaCarta);
        }
        return ultimaCarta;
    }
 */
    public CartaInglesa removerUltimaCarta() {
        if (cartas.pilaVacia()) return null;
        return cartas.pop();
    }
/*    public CartaInglesa getUltimaCarta() {
        CartaInglesa ultimaCarta = null;
        if (!cartas.isEmpty()) {
            ultimaCarta = cartas.getLast();
        }
        return ultimaCarta;
    } */

    public CartaInglesa getUltimaCarta() {
        return cartas.peek();
    }

    /*    
    public boolean estaVacio() {
        return cartas.isEmpty();
    }
     */
    public boolean estaVacio() {
        return cartas.pilaVacia();
    }

    @Override
    public String toString() {
        if (cartas.pilaVacia()) return "---";
        return cartas.peek().toString();
    }

    public Pila<CartaInglesa> getPila() {
    return cartas;
}
public void setPila(Pila<CartaInglesa> nueva) {
    this.cartas = nueva;
}
}