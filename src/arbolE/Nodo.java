/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolE;

/**
 *
 * @author sophi
 * 
 * Parte 1. Analisis sintactico  
 * Parte 2. Analisis semantico 
 * Parte 3. Codigo intermedio
 * Parte 4. Codigo Objeto
 * 
 * 
 */



public class Nodo {
    
    // Atributos
        private String dato;
        private Nodo padre;
        private Nodo izquierdo;
        private Nodo derecho;
        
        
        private String codigoIntermedio; //
        private String lugar; // Para los temporales

        // 9 de Julio - valor evaluado del nodo (resultado de la operacion
        // o valor asignado por el usuario cuando el nodo es un identificador)
        private String valor;

    public Nodo(String dato){ //Información
        this.dato = dato;
    }// Constructor

    public Nodo(String dato, Nodo izquierdo, Nodo derecho) {
        this.dato = dato;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.padre = null;
        this.codigoIntermedio = "";
        this.lugar = "";
    }// Constructor

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public Nodo getPadre() {
        return padre;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }

    public Nodo getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(Nodo izquierdo) {
        this.izquierdo = izquierdo;
    }

    public Nodo getDerecho() {
        return derecho;
    }

    public void setDerecho(Nodo derecho) {
        this.derecho = derecho;
    }

    public String getCodigoIntermedio() {
        return codigoIntermedio;
    }

    public void setCodigoIntermedio(String codigoIntermedio) {
        this.codigoIntermedio = codigoIntermedio;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }


}
