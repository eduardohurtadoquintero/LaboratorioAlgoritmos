package solitario.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.effect.DropShadow;
import DeckOfCards.CartaInglesa;

public class CardView extends StackPane {

    private static final double WIDTH = 80;
    private static final double HEIGHT = 120;
    private static final Color COLOR_REVES = Color.rgb(26, 77, 140);
    private static final Color BORDE_REVES = Color.rgb(10, 42, 74);

    private CartaInglesa carta;
    private Rectangle fondo;
    private Label texto;

    public CardView(CartaInglesa carta) {
        this.carta = carta;

        inicializarVista();
        actualizarVista();
    }

    private void inicializarVista() {
        fondo = new Rectangle(WIDTH, HEIGHT);
        fondo.setArcWidth(15);
        fondo.setArcHeight(15);
        fondo.setStroke(Color.BLACK);
        fondo.setStrokeWidth(1.5);

        texto = new Label();
        texto.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        setAlignment(Pos.CENTER);
        getChildren().addAll(fondo, texto);

        setEffect(new DropShadow(5, Color.color(0, 0, 0, 0.3)));
        setPrefSize(WIDTH, HEIGHT);
    }

    public void actualizarVista() {
        if (carta == null) return;

        if (!carta.isFaceup()) {
            fondo.setFill(COLOR_REVES);
            fondo.setStroke(BORDE_REVES);
            texto.setText("");
        } else {
            fondo.setFill(Color.WHITE);
            fondo.setStroke(Color.BLACK);
            texto.setText(carta.toString());

            if (carta.getColor().equals("rojo")) {
                texto.setTextFill(Color.RED);
            } else {
                texto.setTextFill(Color.BLACK);
            }
        }
    }

    public CartaInglesa getCarta() {
        return carta;
    }
}