package DeckOfCards;

import solitaire.Pila;

/**
 * Modelo de mazo de cartas sin jokers usando Pila.
 * @author Cecilia Curlango Rosas
 * @version 2025-2
 */
public class Mazo {
    private Pila<CartaInglesa> cartas;

    public Mazo() {
        cartas = new Pila<>();
        llenar();  // crea todas las cartas
        mezclar(); // mezcla las cartas
    }

    /**
     * Obtiene todas las cartas del mazo como pila.
     * @return Pila de cartas
     */
    public Pila<CartaInglesa> getCartas() {
        return cartas;
    }

    /**
     * Obtiene una carta del mazo (tope).
     * @return CartaInglesa o null si no hay
     */
    public CartaInglesa obtenerUnaCarta() {
        return cartas.pop();
    }

    /**
     * Llena el mazo con cartas del 2 al As (14), de cada palo.
     */
    private void llenar() {
        // Crea las cartas en orden y las mete al tope de la pila
        for (int i = 2; i <= 14; i++) {
            for (Palo palo : Palo.values()) {
                CartaInglesa c = new CartaInglesa(i, palo, palo.getColor());
                cartas.push(c);
            }
        }
    }

    /**
     * Mezcla las cartas.
     * Como usamos Pila, necesitamos un ArrayList temporal.
     */
    private void mezclar() {
        // Sacamos todas las cartas a una lista temporal
        java.util.ArrayList<CartaInglesa> temp = new java.util.ArrayList<>();
        while (!cartas.pilaVacia()) {
            temp.add(cartas.pop());
        }

        java.util.Collections.shuffle(temp);

        // Volvemos a meterlas a la pila
        for (int i = temp.size() - 1; i >= 0; i--) {
            cartas.push(temp.get(i));
        }
    }

    /**
     * Ordena las cartas de menor a mayor (por valor y palo).
     * Necesitamos lista temporal porque Collections.sort no funciona sobre Pila.
     */
    public void ordenar() {
        java.util.ArrayList<CartaInglesa> temp = new java.util.ArrayList<>();
        while (!cartas.pilaVacia()) {
            temp.add(cartas.pop());
        }

        java.util.Collections.sort(temp);

        for (int i = temp.size() - 1; i >= 0; i--) {
            cartas.push(temp.get(i));
        }
    }

    @Override
    public String toString() {
        return cartas.toString();
    }
}