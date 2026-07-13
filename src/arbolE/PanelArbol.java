/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolE;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import javax.swing.JPanel;

/**
 *
 * @author marie
 * =============================================
 * MÉTODO PARA DIBUJAR ÁRBOL GRÁFICO
 * INSTRUCCIONES:
 * a. Solicitar el ancho y color de las líneas
 * b. Solicitar el ancho y color de los nodos
 * c. Decorarlo con contenido del nodo
 * d. Agregar el método ON CLOSE con la opción de this.dispose() para EVITAR
 *    que cierre el proyecto.
 *
 * NOMBRE: Gonzalez Zapete Sophia 
 * FECHA: 9/julio/2026
 * ==============================================
 *
 * 9 de Julio - Rediseño del nodo: cada nodo se dibuja como un rectangulo
 * dividido en dos celdas, igual que el "Visualizador de Arboles - LyA2":
 *   - Celda izquierda: el token/operador (dato del nodo), con el color
 *     seleccionado por el usuario.
 *   - Celda derecha: el valor evaluado del nodo (Nodo.getValor()), que se
 *     calcula en ArbolIa.evaluar() a partir de la tabla de simbolos.
 */
public class PanelArbol extends JPanel {
    private final Nodo raiz;

    // Dimensiones del rectangulo que representa cada nodo (token | valor)
    private final int ANCHO_NODO = 140;
    private final int ALTO_NODO = 70;
    private final int ESPACIO_VERTICAL = 100;

    // Color del texto que muestra el valor evaluado (celda derecha)
    private final Color COLOR_VALOR_OPERADOR = new Color(178, 34, 34);
    private final Color COLOR_VALOR_HOJA = Color.BLACK;

    // Grosor (en pixeles) de las lineas y del contorno/division de los nodos
    int anchoLineas = 1;
    int anchoNodos = 1;

    // Color de las lineas del arbol y color de los nodos (celda del token)
    Color colorLineas = Color.BLACK;
    Color colorNodos = new Color(173, 216, 230);

    public PanelArbol(Nodo raiz) {
        this.raiz = raiz;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (raiz != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // INICIA DESDE EL CENTRO SUPERIOR
            dibujarNodo(g2, raiz, getWidth() / 2, 60, getWidth() / 4);
        }
    }

    public void setAnchoLineas(int anchoLineas) {
        this.anchoLineas = Math.max(1, anchoLineas);
    }

    public void setAnchoNodos(int anchoNodos) {
        this.anchoNodos = Math.max(1, anchoNodos);
    }

    public void setColorLineas(Color colorLineas) {
        if (colorLineas != null) {
            this.colorLineas = colorLineas;
        }
    }

    public void setColorNodos(Color colorNodos) {
        if (colorNodos != null) {
            this.colorNodos = colorNodos;
        }
    }

    public int getAnchoLineas() {
        return anchoLineas;
    }

    public int getAnchoNodos() {
        return anchoNodos;
    }

    public Color getColorLineas() {
        return colorLineas;
    }

    public Color getColorNodos() {
        return colorNodos;
    }

    private void dibujarNodo(Graphics2D g, Nodo nodo, int x, int y, int espacioHorizontal) {
        if (nodo == null) return;

        Stroke trazoLineas = new BasicStroke(anchoLineas);

        // Lineas hacia los hijos IZQUIERDO Y DERECHO
        g.setStroke(trazoLineas);
        g.setColor(colorLineas);
        if (nodo.getIzquierdo() != null) {
            g.drawLine(x, y, x - espacioHorizontal, y + ESPACIO_VERTICAL);
            dibujarNodo(g, nodo.getIzquierdo(), x - espacioHorizontal,
                    y + ESPACIO_VERTICAL, espacioHorizontal / 2);
        }
        if (nodo.getDerecho() != null) {
            g.drawLine(x, y, x + espacioHorizontal, y + ESPACIO_VERTICAL);
            dibujarNodo(g, nodo.getDerecho(), x
                    + espacioHorizontal, y + ESPACIO_VERTICAL, espacioHorizontal / 2);
        }

        // ===== RECTANGULO DEL NODO (token | valor) =====
        int nodoX = x - ANCHO_NODO / 2;
        int nodoY = y - ALTO_NODO / 2;
        int mitad = ANCHO_NODO / 2;
        boolean esOperador = nodo.getIzquierdo() != null || nodo.getDerecho() != null;

        // Celda izquierda: token / operador
        g.setColor(colorNodos);
        g.fillRect(nodoX, nodoY, mitad, ALTO_NODO);

        // Celda derecha: valor evaluado (fondo blanco, igual que el panel)
        g.setColor(Color.WHITE);
        g.fillRect(nodoX + mitad, nodoY, mitad, ALTO_NODO);

        // Borde exterior del rectangulo completo
        g.setStroke(new BasicStroke(1));
        g.setColor(Color.BLACK);
        g.drawRect(nodoX, nodoY, ANCHO_NODO, ALTO_NODO);

        // Division central resaltada (ancho configurable por el usuario)
        g.setStroke(new BasicStroke(Math.max(2, anchoNodos)));
        g.setColor(colorNodos.darker());
        g.drawLine(nodoX + mitad, nodoY, nodoX + mitad, nodoY + ALTO_NODO);
        g.setStroke(new BasicStroke(1));

        // Texto del token (celda izquierda)
        FontMetrics fm = g.getFontMetrics();
        String token = nodo.getDato();
        int anchoToken = fm.stringWidth(token);
        g.setColor(Color.BLACK);
        g.drawString(token, nodoX + (mitad - anchoToken) / 2, y + fm.getAscent() / 4);

        // Texto del valor evaluado (celda derecha)
        String valor = nodo.getValor() != null ? nodo.getValor() : "";
        int anchoValor = fm.stringWidth(valor);
        g.setColor(esOperador ? COLOR_VALOR_OPERADOR : COLOR_VALOR_HOJA);
        g.drawString(valor, nodoX + mitad + (mitad - anchoValor) / 2, y + fm.getAscent() / 4);
    }//dibujarNodo

}//FIN CLASE
