package solitaire;

import DeckOfCards.CartaInglesa;
import DeckOfCards.Palo;

/**
 * Juego de solitario.
 *
 * @author Cecilia Curlango
 * @version 2025-2
 */
public class SolitaireGame {
    private TableauDeck[] tableau = new TableauDeck[7];
    private FoundationDeck[] foundation = new FoundationDeck[4];
    private FoundationDeck lastFoundationUpdated;
    private DrawPile drawPile;
    private WastePile wastePile;
    private Pila<EstadoJuego> historial = new Pila<>();

    public SolitaireGame() {
        drawPile = new DrawPile();
        wastePile = new WastePile();
        createTableaux();
        createFoundations();
        wastePile.addCartas(drawPile.retirarCartas());
    }

    public void reloadDrawPile() {
        guardarEstado();
        Pila<CartaInglesa> cards = wastePile.emptyPile();
        drawPile.recargar(cards);
    }

    public void drawCards() {
        guardarEstado();
        Pila<CartaInglesa> cards = drawPile.retirarCartas();
        wastePile.addCartas(cards);
    }

    public boolean moveWasteToTableau(int tableauDestino) {
        guardarEstado();
        return moveWasteToTableau(tableau[tableauDestino - 1]);
    }

    public boolean moveTableauToTableau(int tableauFuente, int tableauDestino) {
        guardarEstado();
        TableauDeck fuente = tableau[tableauFuente - 1];
        TableauDeck destino = tableau[tableauDestino - 1];

        int valorQueDebeTenerLaCartaInicial;
        if (!destino.isEmpty()) {
            valorQueDebeTenerLaCartaInicial = destino.verUltimaCarta().getValor() - 1;
        } else {
            valorQueDebeTenerLaCartaInicial = 13; 
        }

        Pila<CartaInglesa> bloque = fuente.removeStartingAt(valorQueDebeTenerLaCartaInicial);
        if (bloque != null && !bloque.pilaVacia()) {
            if (destino.agregarBloqueDeCartas(bloque)) {
                if (!fuente.isEmpty()) {
                    fuente.verUltimaCarta().makeFaceUp();
                }
                return true;
            } else {
              
                fuente.restaurarBloque(bloque);
            }
        }
        return false;
    }

    public boolean moveTableauToFoundation(int numero) {
        guardarEstado();
        TableauDeck fuente = tableau[numero - 1];
        CartaInglesa carta = fuente.removerUltimaCarta();
        if (carta == null) return false;

        if (moveCartaToFoundation(carta)) {
            if (!fuente.isEmpty()) {
                fuente.verUltimaCarta().makeFaceUp();
            }
            return true;
        }


        fuente.agregarCarta(carta);
        return false;
    }

    public boolean moveWasteToTableau(TableauDeck destino) {
        guardarEstado();
        CartaInglesa carta = wastePile.verCarta();
        if (carta == null) return false;

        if (moveCartaToTableau(carta, destino)) {
            wastePile.getCarta(); 
            return true;
        }
        return false;
    }

    public boolean moveWasteToFoundation() {
        guardarEstado();
        CartaInglesa carta = wastePile.verCarta();
        if (carta == null) return false;

        if (moveCartaToFoundation(carta)) {
            wastePile.getCarta(); // eliminar carta del WastePile
            return true;
        }
        return false;
    }

    private boolean moveCartaToTableau(CartaInglesa carta, TableauDeck destino) {
        guardarEstado();
        return destino.agregarCarta(carta);
    }

    private boolean moveCartaToFoundation(CartaInglesa carta) {
        guardarEstado();
        int paloIndex = carta.getPalo().ordinal();
        FoundationDeck destino = foundation[paloIndex];
        lastFoundationUpdated = destino;
        return destino.agregarCarta(carta);
    }

    public boolean isGameOver() {
        for (FoundationDeck f : foundation) {
            if (f.estaVacio()) return false;
            if (f.getUltimaCarta().getValor() != 13) return false;
        }
        return true;
    }

    private void createFoundations() {
        Palo[] palos = Palo.values();
        for (int i = 0; i < palos.length; i++) {
            foundation[i] = new FoundationDeck(palos[i]);
        }
    }

    private void createTableaux() {
        for (int i = 0; i < tableau.length; i++) {
            tableau[i] = new TableauDeck();
            tableau[i].inicializar(drawPile.getCartas(i + 1));
        }
    }

    public DrawPile getDrawPile() {
        return drawPile;
    }

    public WastePile getWastePile() {
        return wastePile;
    }

    public TableauDeck[] getTableau() {
        return tableau;
    }

    public FoundationDeck[] getFoundation() {
        return foundation;
    }

    public FoundationDeck getLastFoundationUpdated() {
        return lastFoundationUpdated;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Foundations:\n");
        for (FoundationDeck f : foundation) {
            sb.append(f).append("\n");
        }

        sb.append("\nTableaux:\n");
        for (int i = 0; i < tableau.length; i++) {
            sb.append(i + 1).append(": ").append(tableau[i]).append("\n");
        }

        sb.append("\nWaste:\n").append(wastePile).append("\n");
        sb.append("Draw:\n").append(drawPile).append("\n");

        return sb.toString();
    }

    public void undo() {
        if (lastFoundationUpdated != null) {
            CartaInglesa carta = lastFoundationUpdated.removerUltimaCarta();
            if (carta != null) {
                }
            }
        }

        private void guardarEstado() {
    historial.push(new EstadoJuego(tableau, foundation, drawPile, wastePile));
}

    public boolean undoo() {
    if (historial.pilaVacia()) return false;

    EstadoJuego estado = historial.pop();

    // tableau
    for (int i = 0; i < 7; i++) {
        tableau[i].setPila(estado.tableau[i]);
    }
    // foundation
    for (int i = 0; i < 4; i++) {
        foundation[i].setPila(estado.foundation[i]);
    }
    drawPile.setPila(estado.drawPile);
    wastePile.setPila(estado.wastePile);
    return true;
}
    }
