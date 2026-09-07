import blackjack.JuegoBlackjack;
import blackjack.Jugador;
import DeckOfCards.CartaInglesa;
import gui.ComponenteCarta;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PrincipalBlackjack extends Application {

    private JuegoBlackjack juego;
    private Stage stagePrincipal;

    private HBox contenedorCrupier;
    private HBox contenedorJugadores;
    private Label lblPuntosCrupier;
    private Label lblTurno;
    private Label lblEstado;

    private Button btnPedir;
    private Button btnPlantarse;
    private Button btnNuevaRonda;
    private Button btnCambiarModo;

    @Override
    public void start(Stage primaryStage) {
        this.stagePrincipal = primaryStage;
        juego = new JuegoBlackjack();
        mostrarMenuSeleccion();
    }

    private void mostrarMenuSeleccion() {
        VBox rootMenu = new VBox(20);
        rootMenu.setStyle("-fx-background-color: #2d5016;");
        rootMenu.setAlignment(Pos.CENTER);
        rootMenu.setPadding(new Insets(30));

        Label lblTitulo = crearEtiquetaTexto("SELECCIONA EL NÚMERO DE JUGADORES");
        lblTitulo.setFont(Font.font("System", FontWeight.BOLD, 20));

        HBox opciones = new HBox(15);
        opciones.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 4; i++) {
            int num = i;
            Button btnNum = new Button(num + (num == 1 ? " Jugador" : " Jugadores"));
            estilarBoton(btnNum);
            btnNum.setOnAction(e -> {
                juego.iniciarJuego(num);
                mostrarPantallaJuego();
            });
            opciones.getChildren().add(btnNum);
        }

        rootMenu.getChildren().addAll(lblTitulo, opciones);
        Scene scene = new Scene(rootMenu, 850, 600);
        stagePrincipal.setTitle("Blackjack - Modo de Juego");
        stagePrincipal.setScene(scene);
        stagePrincipal.setResizable(false);
        stagePrincipal.show();
    }

    private void mostrarPantallaJuego() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #2d5016;");
        root.setPadding(new Insets(20));

        VBox boxCrupier = new VBox(10);
        boxCrupier.setAlignment(Pos.CENTER);
        lblPuntosCrupier = crearEtiquetaTexto("CRUPIER");
        contenedorCrupier = new HBox(10);
        contenedorCrupier.setAlignment(Pos.CENTER);
        contenedorCrupier.setMinHeight(ComponenteCarta.ALTO + 10);
        boxCrupier.getChildren().addAll(lblPuntosCrupier, contenedorCrupier);

        VBox centro = new VBox(15);
        centro.setAlignment(Pos.CENTER);

        lblTurno = crearEtiquetaTexto("");
        lblTurno.setFont(Font.font("System", FontWeight.BOLD, 18));

        lblEstado = crearEtiquetaTexto("");
        lblEstado.setFont(Font.font("System", FontWeight.BOLD, 16));
        lblEstado.setStyle("-fx-text-fill: #f1c40f;");

        contenedorJugadores = new HBox(20);
        contenedorJugadores.setAlignment(Pos.CENTER);

        centro.getChildren().addAll(boxCrupier, lblTurno, lblEstado, contenedorJugadores);
        root.setCenter(centro);

        HBox controles = new HBox(15);
        controles.setAlignment(Pos.CENTER);
        controles.setPadding(new Insets(15, 0, 0, 0));

        btnPedir = new Button("Pedir Carta");
        btnPlantarse = new Button("Plantarse");
        btnNuevaRonda = new Button("Nueva Ronda");
        btnCambiarModo = new Button("Cambiar Jugadores");

        estilarBoton(btnPedir);
        estilarBoton(btnPlantarse);
        estilarBoton(btnNuevaRonda);
        estilarBoton(btnCambiarModo);

        btnPedir.setOnAction(e -> {
            juego.pedirCarta();
            actualizarVista();
        });

        btnPlantarse.setOnAction(e -> {
            juego.plantarse();
            actualizarVista();
        });

        btnNuevaRonda.setOnAction(e -> {
            juego.nuevaRonda();
            actualizarVista();
        });

        btnCambiarModo.setOnAction(e -> mostrarMenuSeleccion());

        controles.getChildren().addAll(btnPedir, btnPlantarse, btnNuevaRonda, btnCambiarModo);
        root.setBottom(controles);

        actualizarVista();

        Scene scene = new Scene(root, 1300, 750);
        stagePrincipal.setTitle("Juego 21 / Blackjack (" + juego.getJugadores().size() + " Jugadores)");
        stagePrincipal.setScene(scene);
    }

    private void actualizarVista() {
        contenedorCrupier.getChildren().clear();
        for (CartaInglesa carta : juego.getManoCrupier()) {
            ComponenteCarta comp = new ComponenteCarta(carta);
            comp.actualizarApariencia();
            contenedorCrupier.getChildren().add(comp);
        }

        contenedorJugadores.getChildren().clear();
        for (int i = 0; i < juego.getJugadores().size(); i++) {
            Jugador j = juego.getJugadores().get(i);
            VBox boxJ = new VBox(5);
            boxJ.setAlignment(Pos.CENTER);

            boolean esSuTurno = (i == juego.getTurnoActual()) && !juego.isJuegoTerminado();
            String estiloBorder = esSuTurno ? "-fx-border-color: #f1c40f; -fx-border-width: 2px; -fx-padding: 5;" : "-fx-padding: 5;";
            boxJ.setStyle(estiloBorder);

            int p = juego.calcularPuntaje(j.getMano());
            Label lblNom = crearEtiquetaTexto(j.getNombre() + " (" + p + " pts)");

            HBox cartasJ = new HBox(5);
            cartasJ.setAlignment(Pos.CENTER);
            for (CartaInglesa carta : j.getMano()) {
                ComponenteCarta comp = new ComponenteCarta(carta);
                comp.actualizarApariencia();
                cartasJ.getChildren().add(comp);
            }

            boxJ.getChildren().addAll(lblNom, cartasJ);
            contenedorJugadores.getChildren().add(boxJ);
        }

        if (juego.isJuegoTerminado()) {
            int pCrupier = juego.calcularPuntaje(juego.getManoCrupier());
            lblPuntosCrupier.setText("CRUPIER (" + pCrupier + " pts)");
            lblTurno.setText("--- JUEGO TERMINADO ---");
            lblEstado.setText(juego.getMensajeEstado());

            btnPedir.setDisable(true);
            btnPlantarse.setDisable(true);
        } else {
            lblPuntosCrupier.setText("CRUPIER");
            Jugador actual = juego.getJugadorActual();
            if (actual != null) {
                lblTurno.setText("TURNO DE: " + actual.getNombre().toUpperCase());
            }
            lblEstado.setText("");

            btnPedir.setDisable(false);
            btnPlantarse.setDisable(false);
        }
    }

    private Label crearEtiquetaTexto(String texto) {
        Label lbl = new Label(texto);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 15));
        lbl.setStyle("-fx-text-fill: white;");
        return lbl;
    }

    private void estilarBoton(Button btn) {
        btn.setFont(Font.font("System", FontWeight.BOLD, 13));
        btn.setMinWidth(110);
        btn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #2d5016; -fx-cursor: hand;");
    }

    public static void main(String[] args) {
        launch(args);
    }
}