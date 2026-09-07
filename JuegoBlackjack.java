package blackjack;

import DeckOfCards.CartaInglesa;
import DeckOfCards.Mazo;
import java.util.ArrayList;

public class JuegoBlackjack {
    private Mazo mazo;
    private final ArrayList<Jugador> jugadores;
    private final ArrayList<CartaInglesa> manoCrupier;
    private int turnoActual;
    private boolean juegoTerminado;
    private String mensajeEstado;

    public JuegoBlackjack() {
        jugadores = new ArrayList<>();
        manoCrupier = new ArrayList<>();
        reiniciarMazo();
    }

    public void reiniciarMazo() {

        mazo = new Mazo();
    }

    public void iniciarJuego(int numJugadores) {
        jugadores.clear();
        for (int i = 1; i <= numJugadores; i++) {
            jugadores.add(new Jugador("Jugador " + i));
        }
        nuevaRonda();
    }

    public void nuevaRonda() {
        if (mazo.getCartas().size() < (jugadores.size() + 1) * 4) {
            reiniciarMazo();
        }

        manoCrupier.clear();
        for (Jugador j : jugadores) {
            j.getMano().clear();
            j.setPlantado(false);
            j.setPaso(false);
        }

        juegoTerminado = false;
        mensajeEstado = "";
        turnoActual = 0;

        for (Jugador j : jugadores) {
            CartaInglesa c1 = mazo.obtenerUnaCarta();
            CartaInglesa c2 = mazo.obtenerUnaCarta();
            if (c1 != null) { c1.makeFaceUp(); j.getMano().add(c1); }
            if (c2 != null) { c2.makeFaceUp(); j.getMano().add(c2); }
        }

        CartaInglesa c3 = mazo.obtenerUnaCarta();
        CartaInglesa c4 = mazo.obtenerUnaCarta();
        if (c3 != null) { c3.makeFaceUp(); manoCrupier.add(c3); }
        if (c4 != null) { c4.makeFaceDown(); manoCrupier.add(c4); }

        verificarTurno();
    }

    public void pedirCarta() {
        if (juegoTerminado || turnoActual >= jugadores.size()) return;

        Jugador jActual = jugadores.get(turnoActual);
        CartaInglesa c = mazo.obtenerUnaCarta();
        if (c != null) {
            c.makeFaceUp();
            jActual.getMano().add(c);
        }

        int puntaje = calcularPuntaje(jActual.getMano());
        if (puntaje > 21) {
            jActual.setPaso(true);
            siguienteTurno();
        } else if (puntaje == 21) {
            jActual.setPlantado(true);
            siguienteTurno();
        }
    }

    public void plantarse() {
        if (juegoTerminado || turnoActual >= jugadores.size()) return;
        jugadores.get(turnoActual).setPlantado(true);
        siguienteTurno();
    }

    private void siguienteTurno() {
        turnoActual++;
        if (turnoActual >= jugadores.size()) {
            juegoTerminado = true;
            jugarCrupier();
            evaluarGanador();
        } else {
            verificarTurno();
        }
    }

    private void verificarTurno() {
        if (turnoActual < jugadores.size()) {
            Jugador j = jugadores.get(turnoActual);
            if (calcularPuntaje(j.getMano()) == 21) {
                j.setPlantado(true);
                siguienteTurno();
            }
        }
    }

    private void jugarCrupier() {
        revelarCartaCrupier();
        while (calcularPuntaje(manoCrupier) < 17) {
            CartaInglesa c = mazo.obtenerUnaCarta();
            if (c == null) break;
            c.makeFaceUp();
            manoCrupier.add(c);
        }
    }

    private void revelarCartaCrupier() {
        for (CartaInglesa c : manoCrupier) {
            c.makeFaceUp();
        }
    }

    private void evaluarGanador() {
        int pCrupier = calcularPuntaje(manoCrupier);
        mensajeEstado = "";

        for (Jugador j : jugadores) {
            int pJ = calcularPuntaje(j.getMano());


            mensajeEstado += j.getNombre() + ": ";

            if (pJ > 21) {
                mensajeEstado += "Perdió BUU | ";
            } else if (pCrupier > 21 || pJ > pCrupier) {
                mensajeEstado += "Ganó WUUW | ";
            } else if (pCrupier > pJ) {
                mensajeEstado += "Perdió BUU| ";
            } else {
                mensajeEstado += "Empate | ";
            }
        }
    }

    public int calcularPuntaje(ArrayList<CartaInglesa> mano) {
        int total=0;
        int ases=0;

        for (CartaInglesa c : mano) {
            int v = c.getValor();
            if (v == 14) {
                ases++;
                total += 11;
            } else if (v >= 11 && v <= 13) {
                total += 10;
            } else {
                total += v;
            }
        }

        while (total > 21 && ases > 0) {
            total -= 10;
            ases--;
        }

        return total;
    }

    public ArrayList<Jugador> getJugadores() { return jugadores; }
    public ArrayList<CartaInglesa> getManoCrupier() { return manoCrupier; }
    public int getTurnoActual() { return turnoActual; }
    public Jugador getJugadorActual() {
        if (turnoActual >= 0 && turnoActual < jugadores.size()) return jugadores.get(turnoActual);
        return null;
    }
    public boolean isJuegoTerminado() { return juegoTerminado; }
    public String getMensajeEstado() { return mensajeEstado; }
}