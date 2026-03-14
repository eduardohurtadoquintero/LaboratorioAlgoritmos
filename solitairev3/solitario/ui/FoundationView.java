package solitario.ui;

import DeckOfCards.CartaInglesa;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import solitaire.FoundationDeck;

/**
 * Vista de un montículo de fundación (FoundationDeck).
 * Click para depositar la carta seleccionada.
 */
public class FoundationView extends StackPane {

    public static final double ANCHO_CARTA = CardView.WIDTH;
    public static final double ALTO_CARTA  = CardView.HEIGHT;

    private FoundationDeck foundationDeck;
    private int indice;
    private String simbolo;

    public FoundationView(FoundationDeck foundationDeck, int indice, String simbolo) {
        this.foundationDeck = foundationDeck;
        this.indice = indice;
        this.simbolo = simbolo;
        setPrefSize(ANCHO_CARTA, ALTO_CARTA);
        setMinSize(ANCHO_CARTA, ALTO_CARTA);
        actualizarVista();
    }

    public void actualizarVista() {
        getChildren().clear();

        if (foundationDeck.estaVacio()) {
            Rectangle placeholder = new Rectangle(ANCHO_CARTA, ALTO_CARTA);
            placeholder.setArcWidth(10);
            placeholder.setArcHeight(10);
            placeholder.setFill(Color.LIGHTGRAY.deriveColor(0, 1, 1, 0.4));
            placeholder.setStroke(Color.GRAY);
            placeholder.setStrokeWidth(1.5);

            Label lbl = new Label(simbolo);
            lbl.setTextFill(Color.GRAY);
            lbl.setStyle("-fx-font-size: 24px;");

            setAlignment(Pos.CENTER);
            getChildren().addAll(placeholder, lbl);
        } else {
            CartaInglesa ultimaCarta = foundationDeck.getUltimaCarta();
            CardView cardView = new CardView(ultimaCarta);
            cardView.setMouseTransparent(true);
            getChildren().add(cardView);
        }
    }

    public FoundationDeck getFoundationDeck() {
        return foundationDeck;
    }

    public int getIndice() {
        return indice;
    }
}
