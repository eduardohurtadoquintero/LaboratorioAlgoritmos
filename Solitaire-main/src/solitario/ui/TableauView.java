
package solitario.ui;

import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import solitaire.TableauDeck;
import DeckOfCards.CartaInglesa;

public class TableauView extends VBox {

    private static final double ANCHO_CARTA = 80;
    private static final double ALTO_CARTA = 120;
    private static final int ESPACIO_ENTRE_CARTAS = -100; // Superposición negativa

    private TableauDeck tableauDeck;
    private int indice;

    public TableauView(TableauDeck tableauDeck, int indice) {
        super(ESPACIO_ENTRE_CARTAS);
        this.tableauDeck = tableauDeck;
        this.indice = indice;

        setPrefSize(ANCHO_CARTA, ALTO_CARTA * 4);
        setMinSize(ANCHO_CARTA, ALTO_CARTA);

        actualizarVista();
    }

    public void actualizarVista() {
        getChildren().clear();

        if (tableauDeck.isEmpty()) {
            // Mostrar placeholder para tableau vacío - ¡AHORA PUEDES MOVER CARTAS AQUÍ!
            Rectangle placeholder = new Rectangle(ANCHO_CARTA, ALTO_CARTA);
            placeholder.setFill(Color.LIGHTGRAY.deriveColor(0, 1, 1, 0.3));
            placeholder.setStroke(Color.GRAY);
            placeholder.setStrokeWidth(2);
            placeholder.setStrokeDashOffset(5);
            placeholder.getStrokeDashArray().addAll(5.0, 5.0); // Línea punteada
            placeholder.setArcWidth(15);
            placeholder.setArcHeight(15);

            Label texto = new Label("VACÍO");
            texto.setTextFill(Color.GRAY);
            texto.setStyle("-fx-font-size: 10px;");

            // Usar un StackPane para combinar placeholder y texto
            javafx.scene.layout.StackPane stack = new javafx.scene.layout.StackPane();
            stack.getChildren().addAll(placeholder, texto);
            getChildren().add(stack);
        } else {
            // Mostrar todas las cartas
            for (CartaInglesa carta : tableauDeck.getCards()) {
                CardView cardView = new CardView(carta);
                getChildren().add(cardView);
            }
        }
    }

    public TableauDeck getTableauDeck() {
        return tableauDeck;
    }

    public int getIndice() {
        return indice;
    }
}