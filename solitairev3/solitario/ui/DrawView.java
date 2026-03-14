package solitario.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import solitaire.DrawPile;


public class DrawView extends StackPane {

    public static final double ANCHO_CARTA = CardView.WIDTH;
    public static final double ALTO_CARTA  = CardView.HEIGHT;

    private DrawPile drawPile;

    public DrawView(DrawPile drawPile) {
        this.drawPile = drawPile;
        setPrefSize(ANCHO_CARTA, ALTO_CARTA);
        setMinSize(ANCHO_CARTA, ALTO_CARTA);
        actualizarVista();
    }

    public void actualizarVista() {
        getChildren().clear();

        Rectangle fondo = new Rectangle(ANCHO_CARTA, ALTO_CARTA);
        fondo.setArcWidth(10);
        fondo.setArcHeight(10);
        fondo.setStrokeWidth(1.5);

        Label label;

        if (drawPile.hayCartas()) {
            // Hay cartas: mostrar reverso de baraja
            fondo.setFill(Color.web("#1a237e"));
            fondo.setStroke(Color.web("#283593"));

            label = new Label(String.valueOf(drawPile.getCuantasCartasSeEntregan()));
            label.setTextFill(Color.WHITE);
            label.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        } else {
            // Sin cartas: mostrar opción de recargar
            fondo.setFill(Color.LIGHTGRAY.deriveColor(0, 1, 1, 0.5));
            fondo.setStroke(Color.GRAY);

            label = new Label("↺");
            label.setTextFill(Color.GRAY);
            label.setStyle("-fx-font-size: 28px;");
        }

        setAlignment(Pos.CENTER);
        getChildren().addAll(fondo, label);
    }

    public DrawPile getDrawPile() {
        return drawPile;
    }
}
