/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolE;

import java.util.Stack;

/**
 *
 * @author sophi
 */
public class Arbol {
    //Atributos
    Stack<Nodo> ArbolNodo; //pila semantica
    Stack<String> caracter;
     //Identificar entre operador y operandos 
    final String espacios = "\t";
    final String aritmeticos = "+-*()^=";
    final String variables = "abcdefghijklmnopqrstuvwxyz";
    final String opMultiplica  = "*";
    private Nodo raiz;
          
    
  
}
