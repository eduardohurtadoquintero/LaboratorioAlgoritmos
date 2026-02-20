package solitario.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import solitaire.SolitaireGame;
import solitaire.TableauDeck;
import solitaire.FoundationDeck;
import java.util.Optional;

public class SolitaireFX extends Application {

    private SolitaireGame juego;
    private GridPane tableroGrid;
    private Label estadoLabel;
    private TextField txtTableauOrigen;
    private TextField txtTableauDestino;
    private boolean juegoTerminado = false;

    // Vistas
    private DrawView drawView;
    private WasteView wasteView;
    private FoundationView[] foundationViews;
    private TableauView[] tableauViews;

    @Override
    public void start(Stage primaryStage) {
        juego = new SolitaireGame();

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #2e7d32;");

        tableroGrid = new GridPane();
        tableroGrid.setHgap(10);
        tableroGrid.setVgap(10);
        tableroGrid.setPadding(new Insets(20));
        tableroGrid.setAlignment(Pos.CENTER);

        crearVistas();
        organizarTablero();

        root.setCenter(tableroGrid);
        root.setTop(crearPanelControl());
        root.setBottom(crearPanelEstado());

        Scene scene = new Scene(root, 1024, 768);

        primaryStage.setTitle("Solitario Clásico");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();

        actualizarTodo();
    }

    private void crearVistas() {
        drawView = new DrawView(juego.getDrawPile());
        wasteView = new WasteView(juego.getWastePile());

        String[] simbolos = {"♠", "♥", "♣", "♦"};
        foundationViews = new FoundationView[4];
        for (int i = 0; i < 4; i++) {
            foundationViews[i] = new FoundationView(
                    juego.getFoundation().get(i), i, simbolos[i]);
        }

        // Tableaux
        tableauViews = new TableauView[7];
        for (int i = 0; i < 7; i++) {
            tableauViews[i] = new TableauView(juego.getTableau().get(i), i + 1);
        }
    }

    private void organizarTablero() {
        tableroGrid.add(drawView, 0, 0);
        tableroGrid.add(wasteView, 1, 0);

        VBox espacio = new VBox();
        espacio.setPrefSize(80, 120);
        tableroGrid.add(espacio, 2, 0);

        for (int i = 0; i < 4; i++) {
            tableroGrid.add(foundationViews[i], 3 + i, 0);
        }

        for (int i = 0; i < 7; i++) {
            tableroGrid.add(tableauViews[i], i, 1);
        }
    }

    private HBox crearPanelControl() {
        HBox panel = new HBox(10);
        panel.setPadding(new Insets(10));
        panel.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-background-color: #1b5e20;");

        // Controles básicos
        Button btnDraw = new Button("Robar Cartas (B)");
        Button btnReload = new Button("Recargar Draw (C)");
        Button btnWasteToFoundation = new Button("Waste → Foundation (A)");
        Button btnNuevoJuego = new Button("Nuevo Juego");

        // Controles para Tableau
        Label lblOrigen = new Label("Origen:");
        lblOrigen.setTextFill(Color.WHITE);
        txtTableauOrigen = new TextField();
        txtTableauOrigen.setPromptText("1-7");
        txtTableauOrigen.setPrefWidth(50);



        Label lblDestino = new Label("Destino:");
        lblDestino.setTextFill(Color.WHITE);
        txtTableauDestino = new TextField();
        txtTableauDestino.setPromptText("1-7");
        txtTableauDestino.setPrefWidth(50);

        Button btnTableauToFoundation = new Button("Tableau → Foundation (D)");
        Button btnTableauToTableau = new Button("Tableau → Tableau (E)");
        Button btnWasteToTableau = new Button("Waste → Tableau (F)");

        // Acciones
        btnNuevoJuego.setOnAction(e -> nuevoJuego());

        btnDraw.setOnAction(e -> {
            if (!juegoTerminado) {
                juego.drawCards();
                actualizarTodo();
                verificarEstadoJuego();
            }
        });

        btnReload.setOnAction(e -> {
            if (!juegoTerminado) {
                juego.reloadDrawPile();
                actualizarTodo();
                verificarEstadoJuego();
            }
        });

        btnWasteToFoundation.setOnAction(e -> {
            if (!juegoTerminado) {
                if (juego.moveWasteToFoundation()) {
                    actualizarTodo();
                    estadoLabel.setText("Carta movida a Foundation");
                    verificarEstadoJuego();
                } else {
                    mostrarError("No se puede mover esa carta a Foundation");
                }
            }
        });

        btnTableauToFoundation.setOnAction(e -> {
            if (!juegoTerminado) {
                try {
                    int origen = Integer.parseInt(txtTableauOrigen.getText());
                    if (origen >= 1 && origen <= 7) {
                        if (juego.moveTableauToFoundation(origen)) {
                            actualizarTodo();
                            estadoLabel.setText("Carta movida de Tableau " + origen + " a Foundation");
                            verificarEstadoJuego();
                        } else {
                            mostrarError("No se puede mover esa carta a Foundation");
                        }
                    } else {
                        mostrarError("El número de Tableau debe ser entre 1 y 7");
                    }
                } catch (NumberFormatException ex) {
                    mostrarError("Ingresa un número válido");
                }
            }
        });

        btnTableauToTableau.setOnAction(e -> {
            if (!juegoTerminado) {
                try {
                    int origen = Integer.parseInt(txtTableauOrigen.getText());
                    int destino = Integer.parseInt(txtTableauDestino.getText());
                    if (origen >= 1 && origen <= 7 && destino >= 1 && destino <= 7) {
                        if (juego.moveTableauToTableau(origen, destino)) {
                            actualizarTodo();
                            estadoLabel.setText("Cartas movidas de Tableau " + origen + " a Tableau " + destino);
                            verificarEstadoJuego();
                        } else {
                            mostrarError("No se puede hacer ese movimiento");
                        }
                    } else {
                        mostrarError("Los números de Tableau deben ser entre 1 y 7");
                    }
                } catch (NumberFormatException ex) {
                    mostrarError("Ingresa números válidos");
                }
            }
        });

        btnWasteToTableau.setOnAction(e -> {
            if (!juegoTerminado) {
                try {
                    int destino = Integer.parseInt(txtTableauDestino.getText());
                    if (destino >= 1 && destino <= 7) {
                        if (juego.moveWasteToTableau(destino)) {
                            actualizarTodo();
                            estadoLabel.setText("Carta movida de Waste a Tableau " + destino);
                            verificarEstadoJuego();
                        } else {
                            mostrarError("No se puede mover esa carta al Tableau");
                        }
                    } else {
                        mostrarError("El número de Tableau debe ser entre 1 y 7");
                    }
                } catch (NumberFormatException ex) {
                    mostrarError("Ingresa un número válido");
                }
            }
        });

        panel.getChildren().addAll(
                btnNuevoJuego, btnDraw, btnReload, btnWasteToFoundation,
                lblOrigen, txtTableauOrigen, lblDestino, txtTableauDestino,
                btnTableauToFoundation, btnTableauToTableau, btnWasteToTableau
        );

        return panel;
    }

    private HBox crearPanelEstado() {
        HBox panel = new HBox(10);
        panel.setPadding(new Insets(5, 10, 5, 10));
        panel.setStyle("-fx-background-color: #1b5e20;");
        panel.setAlignment(Pos.CENTER_LEFT);

        estadoLabel = new Label("Juego en progreso");
        estadoLabel.setTextFill(Color.WHITE);

        panel.getChildren().add(estadoLabel);

        return panel;
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

    private void verificarEstadoJuego() {
        if (juego.isGameOver()) {
            juegoTerminado = true;
            estadoLabel.setText("🎉 ¡FELICIDADES! HAS GANADO 🎉");
            mostrarMensajeVictoria();
        } else if (esJuegoPerdido()) {
            juegoTerminado = true;
            estadoLabel.setText("😢 GAME OVER - No hay más movimientos");
            mostrarMensajeDerrota();
        }
    }

    private boolean esJuegoPerdido() {
        boolean drawVacio = !juego.getDrawPile().hayCartas();
        boolean wasteVacio = !juego.getWastePile().hayCartas();

        if (drawVacio && wasteVacio) {
            boolean hayMovimientosTableau = false;
            for (int i = 0; i < 7; i++) {
                TableauDeck origen = juego.getTableau().get(i);
                if (!origen.isEmpty()) {
                    for (int j = 0; j < 7; j++) {
                        if (i != j) {
                            TableauDeck destino = juego.getTableau().get(j);
                            // Verificar si la última carta del origen puede ir al destino
                            if (!origen.isEmpty()) {
                                // Esta es una simplificación, la lógica real es más compleja
                                hayMovimientosTableau = true;
                                break;
                            }
                        }
                    }
                }
            }

            boolean hayMovimientosFoundation = false;
            for (int i = 0; i < 7; i++) {
                TableauDeck tableau = juego.getTableau().get(i);
                if (!tableau.isEmpty()) {
                    hayMovimientosFoundation = true;
                    break;
                }
            }

            return !hayMovimientosTableau && !hayMovimientosFoundation;
        }

        return false;
    }

    private void mostrarMensajeVictoria() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("¡Victoria!");
        alert.setHeaderText("🎉 ¡FELICIDADES! HAS GANADO 🎉");
        alert.setContentText("¿Qué deseas hacer ahora?");

        ButtonType btnNuevoJuego = new ButtonType("Nuevo Juego");
        ButtonType btnSalir = new ButtonType("Salir");

        alert.getButtonTypes().setAll(btnNuevoJuego, btnSalir);

        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent()) {
            if (resultado.get() == btnNuevoJuego) {
                nuevoJuego();
            } else {
                System.exit(0);
            }
        }
    }

    private void mostrarMensajeDerrota() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("😢 No hay más movimientos posibles");
        alert.setContentText("¿Quieres intentarlo de nuevo?");

        ButtonType btnReintentar = new ButtonType("Reintentar");
        ButtonType btnSalir = new ButtonType("Salir");

        alert.getButtonTypes().setAll(btnReintentar, btnSalir);

        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent()) {
            if (resultado.get() == btnReintentar) {
                nuevoJuego();
            } else {
                System.exit(0);
            }
        }
    }

    private void nuevoJuego() {
        juego = new SolitaireGame();
        juegoTerminado = false;

        crearVistas();

        tableroGrid.getChildren().clear();
        organizarTablero();

        actualizarTodo();
        estadoLabel.setText("Nuevo juego iniciado");
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Movimiento Inválido");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}