package org.example;

import blackjack.JuegoBlackjack;
import blackjack.Jugador;
import java.util.Scanner;

public class PrincipalConsola {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JuegoBlackjack juego = new JuegoBlackjack();

        System.out.println("=== JUEGO DEL 21 (BLACKJACK) ===");
        System.out.print("Ingrese el numero de jugadores: ");
        int numJugadores = scanner.nextInt();
        scanner.nextLine();

        juego.iniciarJuego(numJugadores);

        while (!juego.isJuegoTerminado()) {
            Jugador jugadorActual = juego.getJugadorActual();

            System.out.println("\n----------------------------------------");
            System.out.println("Turno de: " + jugadorActual.getNombre());
            System.out.println("Cartas: " + jugadorActual.getMano());
            System.out.println("Puntaje actual: " + juego.calcularPuntaje(jugadorActual.getMano()));
            System.out.println("----------------------------------------");
            System.out.println("1: Pedir Carta");
            System.out.println("2: Plantarse");
            System.out.println("3: Deshacer Movimiento (Undo)");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    juego.pedirCarta();
                    break;
                case 2:
                    juego.plantarse();
                    break;
                case 3:
                    if (juego.deshacerMovimiento()) {
                        System.out.println("¡Movimiento deshecho con exito!");
                    } else {
                        System.out.println("No es posible deshacer el movimiento (historial vacio o turno finalizado).");
                    }
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
                    break;
            }
        }

        System.out.println("\n=== FIN DE LA PARTIDA ===");
        System.out.println(juego.getMensajeEstado());
        scanner.close();
    }
}