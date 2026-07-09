/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolE;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JColorChooser;
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
 * NOMBRE:  
 * FECHA:
 * ==============================================
 */
public class PanelArbol extends JPanel {
    private final Nodo raiz;
    private final int RADIO = 20;
    private final int ESPACIO_VERTICAL = 60;
    int anchoLineas = 0 ;
    int anchoNodos = 0;
    JColorChooser colorLineasChooser = new JColorChooser();
    JColorChooser colorNodosChooser = new JColorChooser();

    public PanelArbol(Nodo raiz) {
        this.raiz = raiz;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (raiz != null) {
            // LINEAS...MODIFICAR
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON);
            
            // INICIA DESDE EL CENTRO SUPERIOR
            dibujarNodo(g2, raiz, getWidth() / 2, 40, getWidth() / 4);
        }
    }

    public void setAnchoLineas(int anchoLineas) {
        this.anchoLineas = anchoLineas;
    }

    public void setAnchoNodos(int anchoNodos) {
        this.anchoNodos = anchoNodos;
    }

    public void setColorLineas(JColorChooser colorLineasChooser) {
        this.colorLineasChooser = colorLineasChooser;
    }

    public void setColorNodos(JColorChooser colorNodosChooser) {
        this.colorNodosChooser = colorNodosChooser;
    }

    

    public int getAnchoLineas() {
        return anchoLineas;
    }

    public int getAnchoNodos() {
        return anchoNodos;
    }

    public JColorChooser getColorLineasChooser() {
        return colorLineasChooser;
    }

    public JColorChooser getColorNodosChooser() {
        return colorNodosChooser;
    }

    

    private void dibujarNodo(Graphics2D g, Nodo nodo, int x, int y, int espacioHorizontal) {
        if (nodo == null) return;

        // Dibujar NODOS IZQUIERDO Y DERECHO 
        g.setColor(Color.BLACK);
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

        // FORMATO DEL NODO
        g.setColor(new Color(173, 216, 230)); 
        g.fillOval(x - RADIO, y - RADIO, 2 * RADIO, 2 * RADIO);
        g.setColor(Color.BLUE);
        g.drawOval(x - RADIO, y - RADIO, 2 * RADIO, 2 * RADIO);

        //TEXTO CENTRADO DEL NODO
        g.setColor(Color.BLACK);
        FontMetrics fm = g.getFontMetrics();
        int anchoTexto = fm.stringWidth(nodo.getDato());
        int altoTexto = fm.getAscent();
        g.drawString(nodo.getDato(), x - (anchoTexto / 2), y + (altoTexto / 4));
    }//dibujarNodo
    
}//FIN CLASE
