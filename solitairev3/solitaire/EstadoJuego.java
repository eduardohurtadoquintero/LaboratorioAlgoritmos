package solitaire;

import DeckOfCards.CartaInglesa;

public class EstadoJuego {
    public final Pila<CartaInglesa>[] tableau;      
    public final Pila<CartaInglesa>[] foundation;   
    public final Pila<CartaInglesa> drawPile;
    public final Pila<CartaInglesa> wastePile;


    public EstadoJuego(TableauDeck[] tab, FoundationDeck[] found,
                       DrawPile draw, WastePile waste) {
        // Copiar tableau
        tableau = new Pila[7];
        for (int i = 0; i < 7; i++) {
            tableau[i] = copiarPila(tab[i].getPila());
        }
        // Copiar foundation
        foundation = new Pila[4];
        for (int i = 0; i < 4; i++) {
            foundation[i] = copiarPila(found[i].getPila());
        }
        drawPile  = copiarPila(draw.getPila());
        wastePile = copiarPila(waste.getPila());
    }

    private Pila<CartaInglesa> copiarPila(Pila<CartaInglesa> original) {
        Pila<CartaInglesa> temp   = new Pila<>();
        Pila<CartaInglesa> copia  = new Pila<>();

        while (!original.pilaVacia()) temp.push(original.pop());
   
        while (!temp.pilaVacia()) {
            CartaInglesa c = temp.pop();
            original.push(c);
            copia.push(c);  
        }
        return copia;
    }
}