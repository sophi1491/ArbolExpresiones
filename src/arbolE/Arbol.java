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
          
    // 30 junio 2026

String[] temporales = {"T1","T2","T3","T4","T5"};

HashMap<String,String> tablaSimbolos;

HashMap<String,String> erroresSemanticos;

HashMap<String,String> producciones;

int paso;

// 1 de Julio 
ArrayList <String> reglasEjecutadas;

//Construtor
public Arbol(){
reglasEjecutadas = new ArrayList<String>(); 
tablaSimbolos = new HashMap();
erroresSemanticos = new HashMap();
producciones = new HashMap();

ArbolNodo = new Stack<Nodo>();
caracter = new Stack<String>();


} // fin - constructor


//**** Reglas Ejecutadas == 1 de julio
public String getReglasEjecutadas(){
	String ReglasE = "";
	for(int i=0; i<reglasEjecutadas.size(); i++){
	System.out.println("Reglas ejecutadas "+reglasEjecutadas.get(i));
	ReglasE+=reglasEjecutadas.get(i)+"\n";
	}
	return ReglasE;
} // fin getReglasEjecutadas



public void agregarValex(String lexema,String valor){
	

} // agregarValex - análisis semantico


public String regresaValex (String lexema){
	return this.tablaSimbolos.get(lexema);
} // fin - regresar

public void guardar(){
	//permite construir el árbol
	paso++;
	
	Nodo izquierdo = (Nodo) ArbolNodo.pop();
	Nodo derecho = (Nodo) ArbolNodo.pop();
	
	String operador = caracter.peek();
	// INVESTIGAR que hace peek 
	//El método java.util.Stack.peek() en Java se utiliza para recuperar el primer elemento de 	
	//la pila, es decir, el elemento que se encuentra en la parte superior de la misma. El 		
	//elemento recuperado no se elimina de la pila. 
	
	ArbolNodo.push(new Nodo (caracter.pop(),izquierdo,derecho));

	if (operador.equals("+")){
	String reglaE = "E.nodo = new Nodo (+, E1.nodo, T.nodo)";
	reglasEjecutadas.add("p"+paso+" "+reglaE);
	}
        if (operador.equals("*")){
	String reglaE = "E.nodo = new Nodo (*, E1.nodo, T.nodo)";
	reglasEjecutadas.add("p"+paso+" "+reglaE);
	}
	if (operador.equals("-")){
	String reglaE = "E.nodo = new Nodo (-, E1.nodo, T.nodo)";
	reglasEjecutadas.add("p"+paso+" "+reglaE);
	}
        if (operador.equals("/")){
	String reglaE = "E.nodo = new Nodo (/, E1.nodo, T.nodo)";
	reglasEjecutadas.add("p"+paso+" "+reglaE);
	}
}// fin - guardar


// metodo de nodo

public Nodo crear (String expresion){
	//0. Inicializar valores para varias ejecuciones
	
	paso = 0;
	
	// 1. Considerar al expresión como un conjunto de tokens

	StringTokenizer tokenizer;
	String token;

	//StringTokenizer es una clase del paquete java.util que permite dividir 
	//una cadena de texto (String) en partes más pequeñas llamadas tokens.

	//2. Separation de tokens de la expresión

	tokenizer = new StringTokenizer(expresion,espacios+aritmeticos,true);
	
	// 3. Mientras existan tokens

	while(tokenizer.hasMoreTokens()) {
		// 4. Omitir espacios en blanco 
		token = tokenizer.nextToken();
		System.out.println(" Token "+token);
		if(espacios.indexOf(token)>=0){
		// 5. Se trata de un identificador 
			System.out.println("Se trata de un identificador ");
		}else if (aritmeticos.indexOf(token)<0){
		 // 6. Extrae de la pila los términos que estaba 
			ArbolNodo.push(new Nodo(token));
			paso++;
                        //reglasEjecutadas.add("p" + paso + " T.nodo = new Nodo(" + token + ")");
			}else if(token.equals(")")){
					while(!caracter.empty() && !caracter.peek().equals("(")){
					guardar();
						if(!caracter.empty())
						System.out.println("");
					}// fin while
					
					caracter.pop();
				}// fin else if
                        else{
                            if(!token.equals("(") && !caracter.empty()){
                                String exa = (String) caracter.peek();
                                 while(!exa.equals("(") && caracter.empty() && 
                                         aritmeticos.indexOf(exa) >= aritmeticos.indexOf(token)){
                                     guardar();
                                     if (!caracter.empty()){
                                         exa = (String) caracter.peek();
                                     }// fin if !caracter
                                 }// fin while !exa
                            }//fin if token
                              caracter.push(token);
                        }// fin else 
				
		}//while-tokenizar - hasmore tokens	
           while(!caracter.empty()){
               if(caracter.peek().equals("(")){ // el caracter tiensimbolo de apertura 
                   caracter.pop();
               }else{
                   guardar();
                   raiz = (Nodo) ArbolNodo.peek();
               }// fin del if y el else 
           }// fin while !caracter.empty
           return raiz;
	} // fin metodo crear

	
  
}
