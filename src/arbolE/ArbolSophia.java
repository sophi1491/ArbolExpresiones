/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 *
 * @author sophiazapete
 */
public class ArbolSophia {
    //Atributos
    Stack<Nodo> ArbolNodo; //pila semantica
    Stack<String> caracter;
    //Identificar entre operador y operandos
    final String espacios = "\t";
    final String aritmeticos = "+-*/()^=";
    final String variables = "abcdefghijklmnopqrstuvwxyz";
    final String opMultiplica = "*";
    private Nodo raiz;
 
    // 30 junio 2026
    String[] temporales = {"T1", "T2", "T3", "T4", "T5"};
    HashMap<String, String> tablaSimbolos;
    HashMap<String, String> erroresSemanticos;
    HashMap<String, String> producciones;
    int paso;
 
    // 1 de Julio
    ArrayList<String> reglasEjecutadas;
 
    //Construtor
    public ArbolSophia() {
        reglasEjecutadas = new ArrayList<>();
        tablaSimbolos = new HashMap();
        erroresSemanticos = new HashMap();
        producciones = new HashMap();
 
        ArbolNodo = new Stack<Nodo>();
        caracter = new Stack<String>();
 
        paso = 0;
    } // fin - constructor
 
    //**** Reglas Ejecutadas == 1 de julio
    public String getReglasEjecutadas() {
        String ReglasE = "";
        for (int i = 0; i < reglasEjecutadas.size(); i++) {
            System.out.println("Reglas ejecutadas " + reglasEjecutadas.get(i));
            ReglasE += reglasEjecutadas.get(i) + "\n";
        }
        return ReglasE;
    } // fin getReglasEjecutadas
 
    public void agregarValex(String lexema, String valor) {
 
    } // agregarValex - análisis semantico
 
    public String regresaValex(String lexema) {
        return this.tablaSimbolos.get(lexema);
    } // fin - regresar
 
    public void guardar() {
        //permite construir el árbol
        paso++;
 
        Nodo izquierdo = ArbolNodo.pop();
        Nodo derecho = ArbolNodo.pop();
 
        String operador = caracter.peek();
 
        ArbolNodo.push(new Nodo(caracter.pop(), izquierdo, derecho));
 
        if (operador.equals("+"))
            reglasEjecutadas.add("p" + paso + " E.nodo = new Nodo (+, E1.nodo, T.nodo)");
        if (operador.equals("*"))
            reglasEjecutadas.add("p" + paso + " E.nodo = new Nodo (*, E1.nodo, T.nodo)");
        if (operador.equals("-"))
            reglasEjecutadas.add("p" + paso + " E.nodo = new Nodo (-, E1.nodo, T.nodo)");
        if (operador.equals("/"))
            reglasEjecutadas.add("p" + paso + " E.nodo = new Nodo (/, E1.nodo, T.nodo)");
    } // fin - guardar
 
    // metodo de nodo
    public Nodo crear(String expresion) {
        // 1. Considerar la expresión como un conjunto de tokens
        // 0. Inicializar valores para varias ejecuciones
        paso = 0;
 
        // 2. Separacion de tokens de la expresión
        StringTokenizer tokenizer = new StringTokenizer(expresion, espacios + aritmeticos, true);
 
        // 3. Mientras existan tokens
        while (tokenizer.hasMoreTokens()) {
            // 4. Omitir espacios en blanco
            String token = tokenizer.nextToken();
            System.out.println(" Token " + token);
 
            if (espacios.contains(token))
                // 5. Se trata de un espacio
                System.out.println("Omitiendo espacios");
            else if (!aritmeticos.contains(token)) {
                // 6. Es un operando (identificador): se apila como hoja
                ArbolNodo.push(new Nodo(token));
                paso++;
                reglasEjecutadas.add("p" + paso + " T.nodo = new Hoja(id<" + token + ">,id.entrada_" + token + ") ");
            } else if (token.equals(")")) {
                while (!caracter.empty() && !caracter.peek().equals("("))
                    guardar();
                caracter.pop();
            } else {
                if (!token.equals("(") && !caracter.empty()) {
                    String exa = caracter.peek();
                    while (!exa.equals("(") && !caracter.empty()
                            && aritmeticos.indexOf(exa) >= aritmeticos.indexOf(token)) {
                        guardar();
                        if (!caracter.empty())
                            exa = caracter.peek();
                    } // fin while !exa
                } // fin if token
                caracter.push(token);
            } // fin else
        } // while - hasMoreTokens
 
        while (!caracter.empty()) {
            if (caracter.peek().equals("(")) // el caracter tiene simbolo de apertura
                caracter.pop();
            else {
                guardar();
                raiz = ArbolNodo.peek();
            }
        } // fin while !caracter.empty
        return raiz;
    } // fin metodo crear
}
