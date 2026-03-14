package solitario.ui;

import DeckOfCards.CartaInglesa;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import solitaire.TableauDeck;

import java.util.ArrayList;
import java.util.List;

/**
 * Vista de una columna del tableau.
 * Muestra las cartas apiladas con un desplazamiento vertical.
 * Click en cualquier carta boca arriba → selecciona la secuencia desde ahí.
 * Click en columna vacía → destino.
 */
public class TableauView extends Pane {

    public static final double ANCHO_CARTA       = CardView.WIDTH;
    public static final double ALTO_CARTA        = CardView.HEIGHT;
    public static final double ESPACIO_BOCA_ABAJO = 18;
    public static final double ESPACIO_BOCA_ARRIBA = 25;

    private TableauDeck tableauDeck;
    private int indice;
    private List<CardView> cardViews = new ArrayList<>();

    // Carta de selección: índice dentro de la lista de cartas boca arriba
    private int indiceSeleccionado = -1;

    public TableauView(TableauDeck tableauDeck, int indice) {
        this.tableauDeck = tableauDeck;
        this.indice = indice;
        setPrefSize(ANCHO_CARTA, ALTO_CARTA * 3);
        setMinSize(ANCHO_CARTA, ALTO_CARTA);
        actualizarVista();
    }

    public void actualizarVista() {
        getChildren().clear();
        cardViews.clear();
        indiceSeleccionado = -1;

        ArrayList<CartaInglesa> cartas = tableauDeck.getCards();

        if (cartas.isEmpty()) {
            // Mostrar placeholder de columna vacía
            Rectangle placeholder = new Rectangle(ANCHO_CARTA, ALTO_CARTA);
            placeholder.setArcWidth(10);
            placeholder.setArcHeight(10);
            placeholder.setFill(Color.LIGHTGRAY.deriveColor(0, 1, 1, 0.3));
            placeholder.setStroke(Color.GRAY);
            placeholder.setStrokeWidth(1.5);
            placeholder.getStrokeDashArray().addAll(6.0, 4.0);
            placeholder.setLayoutX(0);
            placeholder.setLayoutY(0);

            Label texto = new Label(String.valueOf(indice + 1));
            texto.setTextFill(Color.GRAY);
            texto.setStyle("-fx-font-size: 18px;");
            texto.setLayoutX(ANCHO_CARTA / 2 - 8);
            texto.setLayoutY(ALTO_CARTA / 2 - 12);

            getChildren().addAll(placeholder, texto);
            ajustarAltura(ALTO_CARTA);
            return;
        }

        double y = 0;
        for (int i = 0; i < cartas.size(); i++) {
            CartaInglesa carta = cartas.get(i);
            CardView cv = new CardView(carta);
            cv.setLayoutX(0);
            cv.setLayoutY(y);
            cv.setMouseTransparent(true); // clicks los maneja el TableauView
            cardViews.add(cv);
            getChildren().add(cv);

            if (i < cartas.size() - 1) {
                y += carta.isFaceup() ? ESPACIO_BOCA_ARRIBA : ESPACIO_BOCA_ABAJO;
            }
        }

        double alturaTotal = y + ALTO_CARTA;
        ajustarAltura(alturaTotal);
    }

    private void ajustarAltura(double altura) {
        setPrefHeight(altura);
        setMinHeight(altura);
    }

    /**
     * Resalta las cartas seleccionadas desde el índice dado hasta el tope.
     * @param desdeIndice índice de la primera carta a resaltar (-1 para deseleccionar todo)
     */
    public void resaltarDesde(int desdeIndice) {
        for (int i = 0; i < cardViews.size(); i++) {
            cardViews.get(i).seleccionar(desdeIndice >= 0 && i >= desdeIndice);
        }
        indiceSeleccionado = desdeIndice;
    }

    /** Deselecciona todas las cartas de esta columna. */
    public void deseleccionar() {
        resaltarDesde(-1);
    }

    /**
     * Dado un punto Y relativo a este pane, devuelve el índice de la carta
     * que está en esa posición (o -1 si no hay). Solo para cartas boca arriba.
     */
    public int getIndiceCarta(double y) {
        for (int i = cardViews.size() - 1; i >= 0; i--) {
            CardView cv = cardViews.get(i);
            if (y >= cv.getLayoutY() && cv.getCarta().isFaceup()) {
                return i;
            }
        }
        return -1;
    }

    public TableauDeck getTableauDeck() {
        return tableauDeck;
    }

    public int getIndice() {
        return indice;
    }

    public List<CardView> getCardViews() {
        return cardViews;
    }
}
