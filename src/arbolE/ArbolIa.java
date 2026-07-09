/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolE;

import arbolE.Nodo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 *
 * @author sophiazapete
 */
public class ArbolIa {
   //Atributos
    Stack<Nodo> ArbolNodo; //pila semantica
    Stack<String> caracter;
     //Identificar entre operador y operandos 
    final String espacios = "\t";
    final String aritmeticos = "+-*/()^=";
    final String variables = "abcdefghijklmnopqrstuvwxyz";
    final String opMultiplica  = "*";
    private Nodo raiz;
    private String r;
          
    // 30 junio 2026

String[] temporales = {"T1","T2","T3","T4","T5"};

HashMap<String,String> tablaSimbolos;

HashMap<String,String> erroresSemanticos;

HashMap<String,String> producciones;

int paso;

// 1 de Julio 
ArrayList <String> reglasEjecutadas;

//Construtor
public ArbolIa(){
reglasEjecutadas = new ArrayList<>(); 
tablaSimbolos = new HashMap();
erroresSemanticos = new HashMap();
producciones = new HashMap();

ArbolNodo = new Stack<Nodo>();
caracter = new Stack<String>();

paso = 0;
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

private int obtenerPrioridad(String operador){
    switch(operador){
        case "^":
            return 3;
        case "*": case "/":
            return 2;
        case "+": case "-":
            return 1;
        case "=":
            return 0;
        default: 
            return -1;
    
    }
  
}






public void guardar() {
    if(ArbolNodo.size() < 2 || caracter.empty()) return;
    
    paso++;
    
    r = "r" + paso;
    
        Nodo izquierdo = ArbolNodo.pop();
        Nodo derecho = ArbolNodo.pop();
        String operador = caracter.pop();
        ArbolNodo.push(new Nodo(operador, izquierdo, derecho));
    
                String reglaE = "E.nodo = new Nodo ("+ operador + ",E1.nodo,T.nodo)";
                reglasEjecutadas.add("p"+paso+" "+reglaE);
    } 


// metodo de nodo

public Nodo crear (String expresion){
	// 1. Considerar al expresión como un conjunto de tokens
	StringTokenizer tokenizer;
	String token;
        //0. Inicializar valores para varias ejecuciones
	paso = 0;
	//StringTokenizer es una clase del paquete java.util que permite dividir 
	//una cadena de texto (String) en partes más pequeñas llamadas tokens.
        //reglaSemantica = "";
        r = "";
	//2. Separation de tokens de la expresión
	tokenizer = new StringTokenizer(expresion,espacios+aritmeticos+"/",true);
	// 3. Mientras existan tokens
	while(tokenizer.hasMoreTokens()) {
		// 4. Omitir espacios en blanco 
		token = tokenizer.nextToken();
                
		if(espacios.contains(token)) continue;
                
		if (aritmeticos.indexOf(token) < 0){
		 // 6. Extrae de la pila los términos que estaba 
			ArbolNodo.push(new Nodo(token));
			paso++;
                        String regla = "T.nodo = new Hoja(id<"+token+">,id.entrada_"+token+") ";
                        reglasEjecutadas.add("p" + paso + " "+regla);
                        
                    }else if(token.equals("(")) caracter.push(token);
                    else if(token.equals(")")){
                                 while(!caracter.empty() && !caracter.peek().equals("(") )  guardar();
                                    
                                 if (!caracter.empty()) caracter.pop();
                    }//fin if token
                    else{
                        while(!caracter.empty() && !caracter.peek().equals("(")){
                            if(obtenerPrioridad(caracter.peek()) >= obtenerPrioridad(token)) guardar();
                            else break;// fin del if y el else 
                        }// fin while !caracter.empty
                        caracter.push(token);
                    }    
				
		}//while-tokenizar - hasmore tokens	
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
