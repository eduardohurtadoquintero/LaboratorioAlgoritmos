package solitario.ui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import solitaire.DrawPile;

public class   DrawView extends HBox {

    private static final double ANCHO_CARTA = 80;
    private static final double ALTO_CARTA = 120;

    private DrawPile drawPile;
    private StackPane contenedor;

    public DrawView(DrawPile drawPile) {
        super(10);
        this.drawPile = drawPile;

        setPrefSize(ANCHO_CARTA, ALTO_CARTA);
        setMinSize(ANCHO_CARTA, ALTO_CARTA);

        contenedor = new StackPane();
        contenedor.setPrefSize(ANCHO_CARTA, ALTO_CARTA);

        actualizarVista();
        getChildren().add(contenedor);
    }

    public void actualizarVista() {
        contenedor.getChildren().clear();

        if (!drawPile.hayCartas()) {
            Rectangle placeholder = new Rectangle(ANCHO_CARTA, ALTO_CARTA);
            placeholder.setFill(Color.LIGHTGRAY.deriveColor(0, 1, 1, 0.3));
            placeholder.setStroke(Color.GRAY);
            placeholder.setStrokeWidth(1.5);
            placeholder.setArcWidth(15);
            placeholder.setArcHeight(15);

            Label label = new Label("DRAW");
            label.setTextFill(Color.GRAY);
            label.setStyle("-fx-font-size: 10px;");

            contenedor.getChildren().addAll(placeholder, label);
        } else {
            Rectangle drawPlaceholder = new Rectangle(ANCHO_CARTA, ALTO_CARTA);
            drawPlaceholder.setFill(COLOR_REVES);
            drawPlaceholder.setStroke(BORDE_REVES);
            drawPlaceholder.setArcWidth(15);
            drawPlaceholder.setArcHeight(15);

            Label cantidadLabel = new Label(String.valueOf(drawPile.getCuantasCartasSeEntregan()));
            cantidadLabel.setTextFill(Color.WHITE);

            contenedor.getChildren().addAll(drawPlaceholder, cantidadLabel);
        }
    }

    private static final Color COLOR_REVES = Color.rgb(26, 77, 140);
    private static final Color BORDE_REVES = Color.rgb(10, 42, 74);

    public DrawPile getDrawPile() {
        return drawPile;
    }
}