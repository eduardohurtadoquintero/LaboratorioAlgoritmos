package solitario.ui;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.List;

public class TableroFX extends GridPane {

    private static final int PADDING = 20;
    private static final int ESPACIO_ENTRE_CARTAS = 10;
    private static final int ANCHO_CARTA = 80;
    private static final int ALTO_CARTA = 120;


    private List<StackPane> bases;
    private List<VBox> pilas;
    private HBox reserva;
    private HBox descarte;
    private List<CardView> cartas;

    public TableroFX() {
        inicializarTablero();
    }

    private void inicializarTablero() {
        setHgap(ESPACIO_ENTRE_CARTAS);
        setVgap(ESPACIO_ENTRE_CARTAS);
        setPadding(new Insets(PADDING));
        setAlignment(Pos.CENTER);

        cartas = new ArrayList<>();
        bases = new ArrayList<>();
        pilas = new ArrayList<>();

        crearZonaReserva();
        crearZonaDescarte();
        crearZonasBase();
        crearZonasPilas();

        organizarZonas();
    }

    private void crearZonaReserva() {
        reserva = new HBox(ESPACIO_ENTRE_CARTAS);
        reserva.setAlignment(Pos.CENTER);
        reserva.setPrefSize(ANCHO_CARTA, ALTO_CARTA);
        reserva.setMinSize(ANCHO_CARTA, ALTO_CARTA);

        agregarPlaceholder(reserva, "Reserva");
    }

    private void crearZonaDescarte() {
        descarte = new HBox(ESPACIO_ENTRE_CARTAS);
        descarte.setAlignment(Pos.CENTER);
        descarte.setPrefSize(ANCHO_CARTA, ALTO_CARTA);
        descarte.setMinSize(ANCHO_CARTA, ALTO_CARTA);

        agregarPlaceholder(descarte, "Descarte");
    }

    private void crearZonasBase() {
        for (int i = 0; i < 4; i++) {
            StackPane base = new StackPane();
            base.setPrefSize(ANCHO_CARTA, ALTO_CARTA);
            base.setMinSize(ANCHO_CARTA, ALTO_CARTA);

            Rectangle placeholder = new Rectangle(ANCHO_CARTA, ALTO_CARTA);
            placeholder.setFill(Color.LIGHTGRAY);
            placeholder.setStroke(Color.GRAY);
            placeholder.setStrokeWidth(1.5);
            placeholder.setArcWidth(15);
            placeholder.setArcHeight(15);

            Label texto = new Label(getSimboloPalo(i));
            texto.setTextFill(Color.GRAY);

            base.getChildren().addAll(placeholder, texto);
            bases.add(base);
        }
    }

    private void crearZonasPilas() {
        for (int i = 0; i < 7; i++) {
            VBox pila = new VBox(-ALTO_CARTA + 20);
            pila.setAlignment(Pos.TOP_CENTER);
            pila.setPrefSize(ANCHO_CARTA, ALTO_CARTA * 4);
            pila.setMinSize(ANCHO_CARTA, ALTO_CARTA);

            Rectangle placeholder = new Rectangle(ANCHO_CARTA, ALTO_CARTA);
            placeholder.setFill(Color.LIGHTGRAY.deriveColor(0, 1, 1, 0.3));
            placeholder.setStroke(Color.GRAY);
            placeholder.setStrokeWidth(1);
            placeholder.setArcWidth(15);
            placeholder.setArcHeight(15);

            pila.getChildren().add(placeholder);
            pilas.add(pila);
        }
    }

    private void organizarZonas() {
        add(reserva, 0, 0);
        add(descarte, 1, 0);

        StackPane espacio = new StackPane();
        espacio.setPrefSize(ANCHO_CARTA, ALTO_CARTA);
        add(espacio, 2, 0);

        for (int i = 0; i < bases.size(); i++) {
            add(bases.get(i), 3 + i, 0);
        }

        for (int i = 0; i < pilas.size(); i++) {
            add(pilas.get(i), i, 1);
        }
    }

    private void agregarPlaceholder(HBox contenedor, String texto) {
        Rectangle placeholder = new Rectangle(ANCHO_CARTA, ALTO_CARTA);
        placeholder.setFill(Color.LIGHTGRAY.deriveColor(0, 1, 1, 0.3));
        placeholder.setStroke(Color.GRAY);
        placeholder.setStrokeWidth(1.5);
        placeholder.setArcWidth(15);
        placeholder.setArcHeight(15);

        Label label = new Label(texto);
        label.setTextFill(Color.GRAY);

        StackPane stack = new StackPane();
        stack.getChildren().addAll(placeholder, label);
        contenedor.getChildren().add(stack);
    }

    private String getSimboloPalo(int indice) {
        switch (indice) {
            case 0: return "♠";
            case 1: return "♥";
            case 2: return "♣";
            case 3: return "♦";
            default: return "";
        }
    }


    public void agregarCartaAReserva(CardView carta) {
        reserva.getChildren().clear();
        reserva.getChildren().add(carta);
        cartas.add(carta);
    }

    public void agregarCartaADescarte(CardView carta) {
        descarte.getChildren().clear();
        descarte.getChildren().add(carta);
        cartas.add(carta);
    }

    public void agregarCartaABase(CardView carta, int indiceBase) {
        if (indiceBase >= 0 && indiceBase < bases.size()) {
            StackPane base = bases.get(indiceBase);
            base.getChildren().clear();
            base.getChildren().add(carta);
            cartas.add(carta);
        }
    }

    public void agregarCartaAPila(CardView carta, int indicePila) {
        if (indicePila >= 0 && indicePila < pilas.size()) {
            VBox pila = pilas.get(indicePila);
            pila.getChildren().add(carta);
            cartas.add(carta);

            carta.toFront();
        }
    }

    public void limpiarPila(int indicePila) {
        if (indicePila >= 0 && indicePila < pilas.size()) {
            VBox pila = pilas.get(indicePila);
            pila.getChildren().clear();

            // Restaurar placeholder
            Rectangle placeholder = new Rectangle(ANCHO_CARTA, ALTO_CARTA);
            placeholder.setFill(Color.LIGHTGRAY.deriveColor(0, 1, 1, 0.3));
            placeholder.setStroke(Color.GRAY);
            placeholder.setStrokeWidth(1);
            placeholder.setArcWidth(15);
            placeholder.setArcHeight(15);

            pila.getChildren().add(placeholder);
        }
    }

    // Getters
    public List<CardView> getCartas() {
        return cartas;
    }

    public HBox getReserva() {
        return reserva;
    }

    public HBox getDescarte() {
        return descarte;
    }

    public List<StackPane> getBases() {
        return bases;
    }

    public List<VBox> getPilas() {
        return pilas;
    }
}