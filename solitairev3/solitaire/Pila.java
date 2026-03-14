package solitaire;

import java.util.ArrayList;

public class Pila<T> {
    private T[] pila;
    private int tope;

    public Pila() {
        pila = (T[]) new Object[10];
        tope = -1;
    }

    public Pila(int cantidad) {
        pila = (T[]) new Object[cantidad > 0 ? cantidad : 10];
        tope = -1;
    }

    public boolean pilaLlena() {
        return tope == pila.length - 1;
    }

    public boolean pilaVacia() {
        return tope == -1;
    }

    public void push(T dato) {
        if (pilaLlena()) {
            // Auto-redimensionar: duplicar capacidad
            T[] nueva = (T[]) new Object[pila.length * 2];
            System.arraycopy(pila, 0, nueva, 0, pila.length);
            pila = nueva;
        }
        tope++;
        pila[tope] = dato;
    }

    public T pop() {
        if (pilaVacia()) {
            return null;
        }

        T dato = pila[tope];
        pila[tope] = null;
        tope--;
        return dato;
    }
    public String invierteCadena(String texto) {

        Pila<Character> pila = new Pila<>(texto.length());
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) {
            pila.push(texto.charAt(i));
        }

        Character c;
        while ((c = pila.pop()) != null) {
            resultado.append(c);
        }

        return resultado.toString();
    }

    public int size() {
        return tope + 1;
    }

    public T peek() {
        if (pilaVacia()) {
            return null;
        }
        return pila[tope];
    }
    public ArrayList<T> toList() {
        ArrayList<T> lista = new ArrayList<>();
        Pila<T> temp = new Pila<>();
        while (!this.pilaVacia()) {
            T e = this.pop();
            lista.add(e);
            temp.push(e);
        }
        while (!temp.pilaVacia()) {
            this.push(temp.pop());
        }
        return lista;
    }

    public T get(int indice) {
        if (indice < 0 || indice > tope) return null;
        return pila[indice];
    }
}