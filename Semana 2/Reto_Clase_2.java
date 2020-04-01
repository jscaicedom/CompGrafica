

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Sarai
 */
public class Reto_Clase_2 extends JPanel {

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);

        // size es el tamaÃ±o de la ventana.
        Dimension size = getSize();
        // Insets son los bordes y los tÃ­tulos de la ventana.
        Insets insets = getInsets();

        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;
        
        g2d.drawLine(100, 100, 300, 100);//arriba
        g2d.drawLine(100, 300, 300, 300);//abajo
        g2d.drawLine(100, 100, 100, 300);//ladizq
        g2d.drawLine(300, 100, 300, 300);//ladoder
        
        //linea dentro
        CohenSutherland(g2d, 200, 200, 250, 250);
        //linea fuera
        CohenSutherland(g2d, 50, 350, 350, 350);
        
    }

    public static final int INSIDE = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 4;
    public static final int TOP = 8;

    public static final int COHEN_SUTHERLAND = 0;

    private int xMin = 100;
    private int xMax = 300;
    private int yMin = 100;
    private int yMax = 300;

    private int computeOutCode(double x, double y) {
        int code = INSIDE;

        if (x < xMin) {
            code |= LEFT;
        } else if (x > xMax) {
            code |= RIGHT;
        }
        if (y < yMin) {
            code |= BOTTOM;
        } else if (y > yMax) {
            code |= TOP;
        }

        return code;
    }

    public void CohenSutherland(Graphics g, int x0, int y0, int x1, int y1) {

        int outCode0 = computeOutCode(x0, y0);
        int outCode1 = computeOutCode(x1, y1);
        boolean accept = false;

        while (true) {
            if ((outCode0 | outCode1) == 0) { // Bitwise OR is 0. Trivially accept
                accept = true;
                break;
            } else if ((outCode0 & outCode1) != 0) { // Bitwise AND is not 0. Trivially reject
                break;
            } else {
                int x, y;

                // Pick at least one point outside rectangle
                int outCodeOut = (outCode0 != 0) ? outCode0 : outCode1;

                // Now find the intersection point;
                // use formulas y = y0 + slope * (x - x0), x = x0 + (1 / slope) * (y - y0)
                if ((outCodeOut & TOP) != 0) {
                    x = x0 + (x1 - x0) * (yMax - y0) / (y1 - y0);
                    y = yMax;
                } else if ((outCodeOut & BOTTOM) != 0) {
                    x = x0 + (x1 - x0) * (yMin - y0) / (y1 - y0);
                    y = yMin;
                } else if ((outCodeOut & RIGHT) != 0) {
                    y = y0 + (y1 - y0) * (xMax - x0) / (x1 - x0);
                    x = xMax;
                } else {
                    y = y0 + (y1 - y0) * (xMin - x0) / (x1 - x0);
                    x = xMin;
                }

                // Now we move outside point to intersection point to clip
                if (outCodeOut == outCode0) {
                    x0 = x;
                    y0 = y;
                    outCode0 = computeOutCode(x0, y0);
                } else {
                    x1 = x;
                    y1 = y;
                    outCode1 = computeOutCode(x1, y1);
                }
            }
        }
        if (accept) {
            g.setColor(Color.green);
            g.drawLine(x0, y0, x1, y1);
        }else{
            g.setColor(Color.red);
            g.drawLine(x0, y0, x1, y1);
        }
    }

    public static void main(String[] args) {
        // Crear un nuevo Frame
        JFrame frame = new JFrame("Reto2Clase");
        frame.setBackground(Color.orange);
        // Al cerrar el frame, termina la ejecuciÃ³n de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new Reto_Clase_2());
        // Asignarle tamaÃ±o
        frame.setSize(400, 400);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
    }

}
