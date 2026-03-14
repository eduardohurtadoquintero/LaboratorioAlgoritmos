package solitario.ui;

import DeckOfCards.CartaInglesa;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class CardView extends StackPane {

    public static final double WIDTH  = 70;
    public static final double HEIGHT = 100;

    // Colores del reverso
    private static final Color COLOR_REVES = Color.web("#1a237e");
    private static final Color BORDE_REVES = Color.web("#283593");

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
        fondo.setArcWidth(10);
        fondo.setArcHeight(10);
        fondo.setStroke(Color.BLACK);
        fondo.setStrokeWidth(1.5);

        texto = new Label();
        texto.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        texto.setAlignment(Pos.CENTER);

        setPrefSize(WIDTH, HEIGHT);
        setAlignment(Pos.CENTER);
        getChildren().addAll(fondo, texto);

        DropShadow sombra = new DropShadow(4, Color.color(0.2, 0.2, 0.2, 0.6));
        setEffect(sombra);
    }

    public void actualizarVista() {
        if (carta == null) return;

        if (!carta.isFaceup()) {
            // Reverso
            fondo.setFill(COLOR_REVES);
            fondo.setStroke(BORDE_REVES);
            texto.setText("");
        } else {
            // Cara
            fondo.setFill(Color.WHITE);
            fondo.setStroke(Color.DARKGRAY);
            texto.setText(carta.toString());
            if ("rojo".equals(carta.getColor())) {
                texto.setTextFill(Color.rgb(200, 0, 0));
            } else {
                texto.setTextFill(Color.BLACK);
            }
        }
    }

    public void seleccionar(boolean seleccionada) {
        if (seleccionada) {
            fondo.setStroke(Color.GOLD);
            fondo.setStrokeWidth(3);
            Glow glow = new Glow(0.6);
            setEffect(glow);
        } else {
            fondo.setStroke(carta != null && carta.isFaceup() ? Color.DARKGRAY : BORDE_REVES);
            fondo.setStrokeWidth(1.5);
            DropShadow sombra = new DropShadow(4, Color.color(0.2, 0.2, 0.2, 0.6));
            setEffect(sombra);
        }
    }

    public CartaInglesa getCarta() {
        return carta;
    }
}
