package blackjack;

import DeckOfCards.CartaInglesa;

public class Movimiento {
    public enum TipoAccion {
        PEDIR,
        PLANTARSE }

    private final int jugadorIndex;
    private final TipoAccion tipo;
    private final CartaInglesa cartaRobada;
    private final boolean estabaPaso;
    private final boolean estabaPlantado;

    public Movimiento(int jugadorIndex, TipoAccion tipo, CartaInglesa cartaRobada, boolean estabaPaso, boolean estabaPlantado) {
        this.jugadorIndex = jugadorIndex;
        this.tipo = tipo;
        this.cartaRobada = cartaRobada;
        this.estabaPaso = estabaPaso;
        this.estabaPlantado = estabaPlantado;
    }

    public int getJugadorIndex() {
        return jugadorIndex;
    }
    public TipoAccion getTipo() {
        return tipo;
    }
    public CartaInglesa getCartaRobada() {
        return cartaRobada;
    }
    public boolean isEstabaPaso() {
        return estabaPaso;
    }
    public boolean isEstabaPlantado() {
        return estabaPlantado;
    }
}