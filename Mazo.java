package DeckOfCards;

import org.example.Pila;
import java.util.ArrayList;
import java.util.Collections;

public class Mazo {
    private Pila<CartaInglesa> cartas;
    private int cantidadCartas;

    public Mazo() {
        llenarYMezclar();
    }

    private void llenarYMezclar() {
        ArrayList<CartaInglesa> listaTemporal = new ArrayList<>();
        for (int i = 2; i <= 14; i++) {
            for (Palo palo : Palo.values()) {
                listaTemporal.add(new CartaInglesa(i, palo, palo.getColor()));
            }
        }
        Collections.shuffle(listaTemporal);

        cartas = new Pila<>(listaTemporal.size());
        for (CartaInglesa c : listaTemporal) {
            cartas.push(c);
        }
        cantidadCartas = listaTemporal.size();
    }

    public CartaInglesa obtenerUnaCarta() {
        if (!cartas.pila_vacia()) {
            cantidadCartas--;
            return cartas.pop();
        }
        return null;
    }

    public void regresarCartaAlFondo(CartaInglesa carta) {
        Pila<CartaInglesa> aux = new Pila<>(52);

        while (!cartas.pila_vacia()) {
            aux.push(cartas.pop());
        }

        cartas.push(carta);

        while (!aux.pila_vacia()) {
            cartas.push(aux.pop());
        }

        cantidadCartas++;
    }

    public int getCantidadCartas() {
        return cantidadCartas;
    }


    public ArrayList<CartaInglesa> getCartas() {
        ArrayList<CartaInglesa> lista = new ArrayList<>();
        Pila<CartaInglesa> aux = new Pila<>(52);


        while (!cartas.pila_vacia()) {
            CartaInglesa c = cartas.pop();
            lista.add(c);
            aux.push(c);
        }


        while (!aux.pila_vacia()) {

            cartas.push(aux.pop());
        }

        return lista;
    }
}