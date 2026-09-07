import blackjack.JuegoBlackjack;
import blackjack.Jugador;
import DeckOfCards.CartaInglesa;
import java.util.Scanner;

public class PrincipalConsola {

    private static String obtenerSimbolo(CartaInglesa c) {
        switch (c.getValor()) {
            case 1:
                return "A";
            case 11:
                return "J";
            case 12:
                return "Q";
            case 13:
                return "K";
            default:
                return String.valueOf(c.getValor());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        JuegoBlackjack juego = new JuegoBlackjack();

        System.out.println("==================================");
        System.out.println("           BLACKJACK 21           ");
        System.out.println("==================================");
        System.out.print("Ingresa el número de jugadores (1-4): ");

        int numJugadores = sc.nextInt();
        while (numJugadores < 1 || numJugadores > 4) {
            System.out.print("NO SE PUEDE. Elige entre 1 y 4 jugadores: ");
            numJugadores = sc.nextInt();
        }

        juego.iniciarJuego(numJugadores);
        boolean seguirJugando = true;

        while (seguirJugando) {
            System.out.println("\n----------------------------------");
            System.out.println("          NUEVA RONDA             ");
            System.out.println("----------------------------------");

            CartaInglesa cartaCrupier = juego.getManoCrupier().get(0);
            System.out.println("Carta visible del Crupier: [" + obtenerSimbolo(cartaCrupier) + "]");

            while (!juego.isJuegoTerminado()) {
                Jugador jActual = juego.getJugadorActual();
                if (jActual == null) break;

                System.out.println("\n>>> TURNO DE: " + jActual.getNombre().toUpperCase());

                boolean turnoActivo = true;
                while (turnoActivo && !juego.isJuegoTerminado() && juego.getJugadorActual() == jActual) {
                    int puntos = juego.calcularPuntaje(jActual.getMano());
                    System.out.print("Cartas: ");
                    for (CartaInglesa c : jActual.getMano()) {
                        System.out.print("[" + obtenerSimbolo(c) + "] ");
                    }
                    System.out.println("| Puntos: " + puntos);

                    if (puntos >= 21) {
                        break;
                    }

                    System.out.print("¿Que quieres hacer?(1: Pedir Carta | 2: Plantarse): ");
                    int opcion = sc.nextInt();

                    if (opcion == 1) {
                        juego.pedirCarta();
                    } else {
                        juego.plantarse();
                        turnoActivo = false;
                    }
                }
            }

            System.out.println("\n==================================");
            System.out.println("       RESULTADOS DE LA RONDA      ");
            System.out.println("==================================");
            System.out.print("Mano final del Crupier: ");
            for (CartaInglesa c : juego.getManoCrupier()) {
                System.out.print("[" + obtenerSimbolo(c) + "] ");
            }
            System.out.println("| Puntos: " + juego.calcularPuntaje(juego.getManoCrupier()));
            System.out.println("Resultados: " + juego.getMensajeEstado());

            System.out.print("\n¿Quieres jugar otra ronda? (1: Sí | 2: Salir): ");
            int opcionRonda = sc.nextInt();
            if (opcionRonda == 1) {
                juego.nuevaRonda();
            } else {
                seguirJugando = false;
            }
        }

        System.out.println("\nGracias por jugar");
        sc.close();
    }
}