package org.example;

public class Pila<T> {

    private T[] pila;
    private T dato;
    private int maximoValor;
    private int minimoValor = 0;
    private int tope = -1;

    public Pila(int maximoValor) {
        this.maximoValor = maximoValor;
        pila = (T[]) new Object[maximoValor];
    }

    public boolean pila_llena() {
        return tope == maximoValor - 1;
    }

    public boolean pila_vacia() {
        return tope == -1;
    }

    public void push(T dato) {
        if (pila_llena()) {
            System.out.println("Pila llena");
        } else {
            tope++;
            pila[tope] = dato;
        }
    }

    public T pop() {
        if (pila_vacia()) {
            System.out.println("SubDesbordamiento");
            return null;
        } else {
            dato = pila[tope];
            tope--;
            return dato;
        }
    }
}