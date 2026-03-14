package solitario.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import solitaire.SolitaireGame;


public class SolitaireFX extends Application {

    private SolitaireGame juego;

    private DrawView drawView;
    private WasteView wasteView;
    private FoundationView[] foundationViews;
    private TableauView[] tableauViews;

    private String seleccion = null;

    private Label estadoLabel;
    private BorderPane root;

    private static final String[] SIMBOLOS = { "♣", "♦", "❤", "♠" };

    @Override
    public void start(Stage primaryStage) {
        juego = new SolitaireGame();

        root = new BorderPane();
        root.setStyle("-fx-background-color: #2e7d32;");
        root.setPadding(new Insets(16));

        crearVistas();
        root.setTop(crearBarraSuperior());
        root.setCenter(crearTablero());
        root.setBottom(crearBarraEstado());

        Scene scene = new Scene(root, 820, 680);
        primaryStage.setTitle("Solitario Clásico");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void crearVistas() {
        drawView = new DrawView(juego.getDrawPile());
        drawView.setCursor(javafx.scene.Cursor.HAND);
        drawView.setOnMouseClicked(e -> manejarClickDraw());

        wasteView = new WasteView(juego.getWastePile());
        wasteView.setCursor(javafx.scene.Cursor.HAND);
        wasteView.setOnMouseClicked(e -> manejarClickWaste());

        foundationViews = new FoundationView[4];
        for (int i = 0; i < 4; i++) {
            foundationViews[i] = new FoundationView(
                    juego.getFoundation()[i], i, SIMBOLOS[i]);
            foundationViews[i].setCursor(javafx.scene.Cursor.HAND);
            final int idx = i;
            foundationViews[i].setOnMouseClicked(e -> manejarClickFoundation(idx));
        }

        tableauViews = new TableauView[7];
        for (int i = 0; i < 7; i++) {
            tableauViews[i] = new TableauView(juego.getTableau()[i], i);
            tableauViews[i].setCursor(javafx.scene.Cursor.HAND);
            final int idx = i;
            tableauViews[i].setOnMouseClicked(e ->
                    manejarClickTableau(idx, e.getY()));
        }
    }

    private HBox crearBarraSuperior() {
        HBox barra = new HBox(12);
        barra.setStyle("-fx-background-color: #1b5e20; -fx-background-radius: 8;");
        barra.setPadding(new Insets(8, 12, 8, 12));
        barra.setAlignment(Pos.CENTER_LEFT);

        Button btnNuevo = new Button("Nuevo Juego");
        btnNuevo.setOnAction(e -> nuevoJuego());

        Button btnUndo = new Button("↩ Deshacer");
        btnUndo.setOnAction(e -> {
            limpiarSeleccion();
            if (juego.undoo()) {
                actualizarTodo();
                setEstado("Movimiento deshecho.");
            } else {
                setEstado("No hay movimientos para deshacer.");
            }
        });

        barra.getChildren().addAll(btnNuevo, btnUndo);
        return barra;

    }

    private Pane crearTablero() {
        HBox filaSuperior = new HBox(10);
        filaSuperior.setAlignment(Pos.CENTER_LEFT);

        filaSuperior.getChildren().add(drawView);
        filaSuperior.getChildren().add(wasteView);

        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);
        filaSuperior.getChildren().add(espaciador);

        for (FoundationView fv : foundationViews) {
            filaSuperior.getChildren().add(fv);
        }

        HBox filaTableau = new HBox(10);
        filaTableau.setAlignment(Pos.TOP_LEFT);
        filaTableau.setPadding(new Insets(12, 0, 0, 0));
        for (TableauView tv : tableauViews) {
            filaTableau.getChildren().add(tv);
        }

        VBox tablero = new VBox(12);
        tablero.getChildren().addAll(filaSuperior, filaTableau);
        return tablero;
    }

    private HBox crearBarraEstado() {
        HBox barra = new HBox();
        barra.setPadding(new Insets(6, 12, 4, 12));

        estadoLabel = new Label("Haz click en el mazo para empezar");
        estadoLabel.setTextFill(Color.WHITE);
        estadoLabel.setStyle("-fx-font-size: 13px;");
        barra.getChildren().add(estadoLabel);
        return barra;
    }

    private void manejarClickDraw() {
        cancelarSeleccion();
        if (juego.getDrawPile().hayCartas()) {
            juego.drawCards();
            setEstado("Cartas robadas del mazo.");
        } else {
            juego.reloadDrawPile();
            setEstado("Mazo recargado desde el descarte.");
        }
        actualizarTodo();
    }

    private void manejarClickWaste() {
        if (!juego.getWastePile().hayCartas()) {
            cancelarSeleccion();
            setEstado("El descarte está vacío.");
            return;
        }

        if ("WASTE".equals(seleccion)) {
            cancelarSeleccion();
            setEstado("Selección cancelada.");
        } else {
            cancelarSeleccion();
            seleccion = "WASTE";
            wasteView.seleccionar(true);
            setEstado("Carta del descarte seleccionada: "
                    + juego.getWastePile().verCarta() + "  →  elige destino.");
        }
    }

    private void manejarClickFoundation(int idx) {
        if (seleccion == null) {
            setEstado("Primero selecciona una carta.");
            return;
        }

        boolean exito = false;

        if ("WASTE".equals(seleccion)) {
            exito = juego.moveWasteToFoundation();
        } else if (seleccion.startsWith("T")) {
            int numTableau = Integer.parseInt(seleccion.substring(1)) + 1;
            exito = juego.moveTableauToFoundation(numTableau);
        }

        limpiarSeleccion();
        actualizarTodo();

        if (exito) {
            setEstado("¡Carta movida a la fundación!");
            verificarGanador();
        } else {
            setEstado("Movimiento no válido hacia la fundación.");
        }
    }


    private void manejarClickTableau(int idx, double clickY) {
        String clave = "T" + idx;

        if (seleccion == null) {
            if (juego.getTableau()[idx].isEmpty()) {
                setEstado("La columna " + (idx + 1) + " está vacía.");
                return;
            }
            TableauView tv = tableauViews[idx];
            int indiceCarta = tv.getIndiceCarta(clickY);
            if (indiceCarta < 0) {
                setEstado("Selecciona una carta boca arriba.");
                return;
            }
            seleccion = clave;
            tv.resaltarDesde(indiceCarta);
            setEstado("Seleccionado tableau " + (idx + 1)
                    + " desde " + tv.getTableauDeck().getCards().get(indiceCarta)
                    + "  →  elige destino.");

        } else if (clave.equals(seleccion)) {
            cancelarSeleccion();
            setEstado("Selección cancelada.");

        } else {
            boolean exito = false;

            if ("WASTE".equals(seleccion)) {
                exito = juego.moveWasteToTableau(idx + 1);
            } else if (seleccion.startsWith("T")) {
                int origen = Integer.parseInt(seleccion.substring(1)) + 1;
                exito = juego.moveTableauToTableau(origen, idx + 1);
            }

            limpiarSeleccion();
            actualizarTodo();

            if (exito) {
                setEstado("¡Movimiento exitoso a la columna " + (idx + 1) + "!");
                verificarGanador();
            } else {
                setEstado("Movimiento no válido.");
            }
        }
    }


    private void limpiarSeleccion() {
        seleccion = null;
    }

    private void cancelarSeleccion() {
        seleccion = null;
        actualizarTodo();
    }

    private void actualizarTodo() {
        drawView.actualizarVista();
        wasteView.actualizarVista();
        for (FoundationView fv : foundationViews) {
            fv.actualizarVista();
        }
        for (TableauView tv : tableauViews) {
            tv.actualizarVista();
        }
    }

    private void setEstado(String mensaje) {
        if (estadoLabel != null) estadoLabel.setText(mensaje);
    }

    private void verificarGanador() {
        if (juego.isGameOver()) {
            setEstado("🎉 ¡GANASTE! Felicidades.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Solitario");
            alert.setHeaderText("¡Ganaste!");
            alert.setContentText("Completaste el solitario. ¡Bien hecho!");
            alert.showAndWait();
        }
    }

    private void nuevoJuego() {
        cancelarSeleccion();
        juego = new SolitaireGame();
        crearVistas();
        root.setTop(crearBarraSuperior());
        root.setCenter(crearTablero());
        root.setBottom(crearBarraEstado());
        setEstado("Nuevo juego iniciado. Haz click en el mazo para empezar.");
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
