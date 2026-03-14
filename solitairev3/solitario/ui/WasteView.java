package solitario.ui;

import DeckOfCards.CartaInglesa;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import solitaire.WastePile;

/**
 * Vista del montón de descarte (WastePile).
 * Muestra la carta del tope. Click para seleccionarla.
 */
public class WasteView extends StackPane {

    public static final double ANCHO_CARTA = CardView.WIDTH;
    public static final double ALTO_CARTA  = CardView.HEIGHT;

    private WastePile wastePile;
    private CardView cartaView;

    public WasteView(WastePile wastePile) {
        this.wastePile = wastePile;
        setPrefSize(ANCHO_CARTA, ALTO_CARTA);
        setMinSize(ANCHO_CARTA, ALTO_CARTA);
        actualizarVista();
    }

    public void actualizarVista() {
        getChildren().clear();
        cartaView = null;

        if (wastePile.hayCartas()) {
            CartaInglesa carta = wastePile.verCarta();
            cartaView = new CardView(carta);
            cartaView.setMouseTransparent(true); // los clicks los maneja el WasteView
            getChildren().add(cartaView);
        } else {
            Rectangle placeholder = new Rectangle(ANCHO_CARTA, ALTO_CARTA);
            placeholder.setArcWidth(10);
            placeholder.setArcHeight(10);
            placeholder.setFill(Color.LIGHTGRAY.deriveColor(0, 1, 1, 0.4));
            placeholder.setStroke(Color.GRAY);
            placeholder.setStrokeWidth(1.5);

            Label lbl = new Label("WASTE");
            lbl.setTextFill(Color.GRAY);
            lbl.setStyle("-fx-font-size: 10px;");

            setAlignment(Pos.CENTER);
            getChildren().addAll(placeholder, lbl);
        }
    }

    /** Resalta si esta carta está seleccionada. */
    public void seleccionar(boolean seleccionada) {
        if (cartaView != null) {
            cartaView.seleccionar(seleccionada);
        }
    }

    public WastePile getWastePile() {
        return wastePile;
    }
}
