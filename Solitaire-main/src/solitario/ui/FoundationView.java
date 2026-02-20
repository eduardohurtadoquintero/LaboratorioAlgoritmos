package solitario.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import solitaire.FoundationDeck;
import DeckOfCards.CartaInglesa;

public class FoundationView extends StackPane {

    private static final double ANCHO_CARTA = 80;
    private static final double ALTO_CARTA = 120;

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
            // Mostrar placeholder con símbolo
            Rectangle placeholder = new Rectangle(ANCHO_CARTA, ALTO_CARTA);
            placeholder.setFill(Color.LIGHTGRAY);
            placeholder.setStroke(Color.GRAY);
            placeholder.setStrokeWidth(1.5);
            placeholder.setArcWidth(15);
            placeholder.setArcHeight(15);

            Label simboloLabel = new Label(simbolo);
            simboloLabel.setTextFill(Color.GRAY);
            simboloLabel.setStyle("-fx-font-size: 24px;");

            getChildren().addAll(placeholder, simboloLabel);
        } else {
            // Mostrar la última carta del foundation
            CartaInglesa ultimaCarta = foundationDeck.getUltimaCarta();
            if (ultimaCarta != null) {
                CardView cardView = new CardView(ultimaCarta);
                getChildren().add(cardView);
            }
        }
    }

    public FoundationDeck getFoundationDeck() {
        return foundationDeck;
    }
}