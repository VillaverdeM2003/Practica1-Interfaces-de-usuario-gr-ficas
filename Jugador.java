package blackjack;

import DeckOfCards.CartaInglesa;
import java.util.ArrayList;

public class Jugador {
    private final String nombre;
    private final ArrayList<CartaInglesa> mano;
    private boolean plantado;
    private boolean paso;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
        this.plantado = false;
        this.paso = false;
    }

    public String getNombre() { return nombre; }
    public ArrayList<CartaInglesa> getMano() { return mano; }
    public boolean isPlantado() { return plantado; }
    public void setPlantado(boolean plantado) { this.plantado = plantado; }
    public boolean isPaso() { return paso; }
    public void setPaso(boolean paso) { this.paso = paso; }
}