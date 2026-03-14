package solitaire;

import DeckOfCards.CartaInglesa;
import java.util.ArrayList;

/**
 * Modela una columna del tablero de solitario (tableau).
 * Internamente usa Pila<CartaInglesa>.
 *
 * @author Cecilia Curlango
 * @version 2025
 */
public class TableauDeck {
    private Pila<CartaInglesa> cartas;

    public TableauDeck() {
        cartas = new Pila<>();
    }
/*    public void inicializar(ArrayList<CartaInglesa> cartas) {
        this.cartas = cartas;
        // voltear la última carta recibida
        CartaInglesa ultima = cartas.getLast();
        ultima.makeFaceUp();
    } */
    
    public void inicializar(Pila<CartaInglesa> iniciales) {
        cartas = new Pila<>();
        if (iniciales == null || iniciales.pilaVacia()) return;

        Pila<CartaInglesa> temp = new Pila<>();
        while (!iniciales.pilaVacia()) {
            temp.push(iniciales.pop());
        }
        while (!temp.pilaVacia()) {
            cartas.push(temp.pop());
        }
        if (!cartas.pilaVacia()) {
            cartas.peek().makeFaceUp();
        }
    }

    /**
       public boolean agregarCarta(CartaInglesa carta) {
        boolean agregado = false;

        if (sePuedeAgregarCarta(carta)) {
            carta.makeFaceUp();
            cartas.add(carta);
            agregado = true;
        }
        return agregado;
    }
     */
    public boolean agregarCarta(CartaInglesa carta) {
        if (cartas.pilaVacia()) {
            if (carta.getValor() == 13) {
                carta.makeFaceUp();
                cartas.push(carta);
                return true;
            }
            return false;
        }
        CartaInglesa tope = cartas.peek();
        if (!tope.getColor().equals(carta.getColor())
                && tope.getValor() - 1 == carta.getValor()) {
            carta.makeFaceUp();
            cartas.push(carta);
            return true;
        }
        return false;
    }

    /**
    public boolean agregarBloqueDeCartas(ArrayList<CartaInglesa> cartasRecibidas) {
        boolean resultado = false;

        if (!cartasRecibidas.isEmpty()) {
            CartaInglesa primera = cartasRecibidas.getFirst();
            // si la primera carta del bloque recibido se puede agregar al tableau actual
            if (sePuedeAgregarCarta(primera)) {
                // se agrega todo el bloque
                cartas.addAll(cartasRecibidas);
                resultado = true;
            }
        }
        return resultado;
    }
     */
    public boolean agregarBloqueDeCartas(Pila<CartaInglesa> bloque) {
        if (bloque == null || bloque.pilaVacia()) return false;

        CartaInglesa cartaBase = bloque.get(0);
        if (cartaBase == null) return false;

        boolean puedeColocar;
        if (cartas.pilaVacia()) {
            puedeColocar = (cartaBase.getValor() == 13);
        } else {
            CartaInglesa topeActual = cartas.peek();
            puedeColocar = !topeActual.getColor().equals(cartaBase.getColor())
                    && topeActual.getValor() - 1 == cartaBase.getValor();
        }
        if (!puedeColocar) return false;

        Pila<CartaInglesa> temp = new Pila<>();
        while (!bloque.pilaVacia()) {
            temp.push(bloque.pop());
        }
        while (!temp.pilaVacia()) {
            CartaInglesa c = temp.pop();
            c.makeFaceUp();
            cartas.push(c);
        }
        return true;
    }

    /**
     * Remueve y devuelve un bloque desde la carta con 'valor' hasta el tope.
     * La carta base queda en posicion 0 del bloque devuelto.
     */
    public Pila<CartaInglesa> removeStartingAt(int valor) {
        Pila<CartaInglesa> temp = new Pila<>();
        boolean encontrado = false;

        while (!cartas.pilaVacia()) {
            CartaInglesa c = cartas.pop();
            temp.push(c);
            if (c.getValor() == valor && c.isFaceup()) {
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            while (!temp.pilaVacia()) {
                cartas.push(temp.pop());
            }
            return null;
        }

        Pila<CartaInglesa> bloque = new Pila<>();
        while (!temp.pilaVacia()) {
            bloque.push(temp.pop());
        }
        return bloque;
    }

    /**
     * Devuelve un bloque de cartas a la columna SIN modificar su estado face-up/down.
     * Usar SOLO para restaurar un movimiento fallido.
     * El bloque debe tener la carta base en posicion 0 (fondo).
     */
    public void restaurarBloque(Pila<CartaInglesa> bloque) {
        if (bloque == null || bloque.pilaVacia()) return;
        // El bloque tiene: base en pos 0, tope en el tope de la pila.
        // Necesitamos empujar desde la base hacia el tope.
        Pila<CartaInglesa> temp = new Pila<>();
        while (!bloque.pilaVacia()) {
            temp.push(bloque.pop()); // invierte: tope queda en fondo de temp
        }
        while (!temp.pilaVacia()) {
            cartas.push(temp.pop()); // base se empuja primero → queda en fondo
        }
    }

    public CartaInglesa removerUltimaCarta() {
        if (cartas.pilaVacia()) return null;
        return cartas.pop();
    }

    public CartaInglesa verUltimaCarta() {
        return cartas.peek();
    }

    public boolean isEmpty() {
        return cartas.pilaVacia();
    }

    /**
     * Devuelve las cartas de abajo hacia arriba para la vista.
     */
    public ArrayList<CartaInglesa> getCards() {
        ArrayList<CartaInglesa> lista = new ArrayList<>();
        Pila<CartaInglesa> temp = new Pila<>();
        while (!cartas.pilaVacia()) {
            temp.push(cartas.pop());
        }
        while (!temp.pilaVacia()) {
            CartaInglesa c = temp.pop();
            lista.add(c);
            cartas.push(c);
        }
        return lista;
    }

    @Override
    public String toString() {
        if (cartas.pilaVacia()) return "[ vacio ]";
        ArrayList<CartaInglesa> lista = getCards();
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < lista.size(); i++) {
            sb.append(lista.get(i));
            if (i < lista.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public Pila<CartaInglesa> getPila() {
    return cartas;
}
public void setPila(Pila<CartaInglesa> nueva) {
    this.cartas = nueva;
}
}
