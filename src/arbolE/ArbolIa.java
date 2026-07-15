/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolE;

import arbolE.Nodo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
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

// 9 de Julio - lectura de valores de identificadores desde consola
Scanner entrada;

// 10 de Julio - tripletas {op, arg1, arg2} para FrameTripletas, se llenan
// dentro de guardar() cada vez que se resuelve un operador con sus operandos.
ArrayList<String[]> tripletas;

//Construtor
public ArbolIa(){
reglasEjecutadas = new ArrayList<>();
tablaSimbolos = new HashMap();
erroresSemanticos = new HashMap();
producciones = new HashMap();
tripletas = new ArrayList<>();

ArbolNodo = new Stack<Nodo>();
caracter = new Stack<String>();

entrada = new Scanner(System.in);

paso = 0;
} // fin - constructor


// 10 de Julio - tripletas para FrameTripletas
public ArrayList<String[]> getTripletas(){
	return tripletas;
} // fin getTripletas


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


// 8 de Julio - Tabla de Simbolos
// Imprime en el output (consola) la tabla de simbolos ya construida
// (con los valores que el usuario proporciono por System.in al evaluar).
public void mostrarTablaSimbolos(){
	StringBuilder sb = new StringBuilder("Tabla de Simbolos {");
	boolean primero = true;
	for (String clave : tablaSimbolos.keySet()){
		if(!primero) sb.append(", ");
		sb.append(clave).append("=").append(tablaSimbolos.get(clave));
		primero = false;
	}
	sb.append("}");
	System.out.println(sb.toString());
} // fin mostrarTablaSimbolos


// 9 de Julio - Evaluacion del AST
// Recorre el arbol en postorden: en cada hoja identificador (id) se pregunta
// por System.in el valor que el usuario le quiere asignar (una sola vez por
// variable, usando tablaSimbolos como cache); con las hojas ya resueltas se
// calcula el resultado de cada operador de la pila de caracteres (+,-,*,/,^)
// y se guarda en Nodo.valor para poder dibujarlo en el AST.
public int evaluar(Nodo nodo){
	if(nodo == null) return 0;

	boolean esHoja = nodo.getIzquierdo() == null && nodo.getDerecho() == null;

	if(esHoja){
		String token = nodo.getDato();
		int valorNodo;
		if(token.matches("-?\\d+")){
			// Es un numero literal
			valorNodo = Integer.parseInt(token);
		} else {
			// Es un identificador (id) -> se pregunta su valor una sola vez
			// por variable. Si lo que escribe no es un numero entero, se le
			// vuelve a preguntar hasta que ingrese uno valido (ya no se
			// asigna 0 por defecto).
			if(!tablaSimbolos.containsKey(token)){
				boolean valido = false;
				String linea = "";
				while(!valido){
					System.out.print("Ingrese el valor de la variable '"+token+"': ");
					linea = entrada.nextLine().trim();
					try{
						Integer.parseInt(linea);
						valido = true;
					}catch(NumberFormatException ex){
						System.out.println("'"+linea+"' no es un numero entero valido. Intenta de nuevo.");
					}
				}
				tablaSimbolos.put(token, linea);
			}
			valorNodo = Integer.parseInt(tablaSimbolos.get(token));
		}
		nodo.setValor(String.valueOf(valorNodo));
		return valorNodo;
	}

	// Nodo operador: primero se resuelven sus hijos (postorden)
	int izq = evaluar(nodo.getIzquierdo());
	int der = evaluar(nodo.getDerecho());
	int resultado;

	switch(nodo.getDato()){
		case "+": resultado = izq + der; break;
		case "-": resultado = izq - der; break;
		case "*": resultado = izq * der; break;
		case "/": resultado = der != 0 ? izq / der : 0; break;
		case "^": resultado = (int) Math.pow(izq, der); break;
		default:  resultado = 0;
	}

	nodo.setValor(String.valueOf(resultado));
	return resultado;
} // fin evaluar


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

        Nodo derecho = ArbolNodo.pop();
        Nodo  izquierdo= ArbolNodo.pop();
        String operador = caracter.pop();
        ArbolNodo.push(new Nodo(operador, izquierdo, derecho));

                String reglaE = "E.nodo = new Nodo ("+ operador + ",E1.nodo,T.nodo)";
                reglasEjecutadas.add("p"+paso+" "+reglaE);

                // 10 de Julio - tripleta {op, arg1, arg2} para FrameTripletas
                tripletas.add(new String[]{ operador, izquierdo.getDato(), derecho.getDato() });
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


// 10 de Julio - Conversion de AST a GAD (grafo aciclico dirigido), para
// PanelGrafo. Unifica hojas repetidas y subexpresiones identicas ya
// resueltas (mismo operador con los mismos hijos), reasignando los
// punteros izquierdo/derecho para que compartan el mismo Nodo.
public Nodo convertirAGAD(Nodo raizAST) {
    HashMap<String, Nodo> tabla = new HashMap<>();
    return convertir(raizAST, tabla);
}

private Nodo convertir(Nodo n, HashMap<String, Nodo> tabla) {
    if (n == null) return null;

    if (n.getIzquierdo() == null && n.getDerecho() == null) {
        String clave = "HOJA#" + n.getDato();
        Nodo existente = tabla.get(clave);
        if (existente != null) return existente; // reutiliza
        tabla.put(clave, n);
        return n;
    }

    // Procesar hijos primero (post-orden): así al llegar al padre
    // ya sabemos si los hijos son nodos compartidos o no.
    Nodo izqNuevo = convertir(n.getIzquierdo(), tabla);
    Nodo derNuevo = convertir(n.getDerecho(), tabla);

    // Reasignar hijos (puede que ahora apunten a nodos ya existentes)
    n.setIzquierdo(izqNuevo);
    n.setDerecho(derNuevo);

    String clave = n.getDato() + "#"
                 + System.identityHashCode(izqNuevo) + "#"
                 + System.identityHashCode(derNuevo);

    Nodo existente = tabla.get(clave);
    if (existente != null) return existente;

    tabla.put(clave, n);
    return n;
}

}
