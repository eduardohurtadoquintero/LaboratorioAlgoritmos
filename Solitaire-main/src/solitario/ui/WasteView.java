package solitario.ui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import solitaire.WastePile;
import DeckOfCards.CartaInglesa;

public class WasteView extends HBox {

    private static final double ANCHO_CARTA = 80;
    private static final double ALTO_CARTA = 120;

    private WastePile wastePile;
    private StackPane contenedor;

    public WasteView(WastePile wastePile) {
        super(10);
        this.wastePile = wastePile;

        setPrefSize(ANCHO_CARTA, ALTO_CARTA);
        setMinSize(ANCHO_CARTA, ALTO_CARTA);

        contenedor = new StackPane();
        contenedor.setPrefSize(ANCHO_CARTA, ALTO_CARTA);

        actualizarVista();
        getChildren().add(contenedor);
    }

    public void actualizarVista() {
        contenedor.getChildren().clear();

        if (!wastePile.hayCartas()) {
            Rectangle placeholder = new Rectangle(ANCHO_CARTA, ALTO_CARTA);
            placeholder.setFill(Color.LIGHTGRAY.deriveColor(0, 1, 1, 0.3));
            placeholder.setStroke(Color.GRAY);
            placeholder.setStrokeWidth(1.5);
            placeholder.setArcWidth(15);
            placeholder.setArcHeight(15);

            Label label = new Label("WASTE");
            label.setTextFill(Color.GRAY);
            label.setStyle("-fx-font-size: 10px;");

            contenedor.getChildren().addAll(placeholder, label);
        } else {
            CartaInglesa carta = wastePile.verCarta();
            CardView cardView = new CardView(carta);
            contenedor.getChildren().add(cardView);
        }
    }

    public WastePile getWastePile() {
        return wastePile;
    }
}