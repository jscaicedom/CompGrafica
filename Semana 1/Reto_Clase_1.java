
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JFrame;

/**
 *
 * @author Sarai
 */
public class Reto_Clase_1 extends JPanel {

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

        //ejes
        Bresenham(g2d, 200, 0, 200, 400);
        Bresenham(g2d, 0, 200, 400, 200);
        //puntos
        int x1 = 50;
        int y1 = 50;
        int x2 = 150;
        int y2 = 100;

        Bresenham(g2d, x1 + 200, 200 - y1, x2 + 200, 200 - y2);
    }

    public void Bresenham(Graphics g, int x0, int y0, int x1, int y1) {
        int x, y, dx, dy, p, incE, incNE, stepx, stepy;
        dx = (x1 - x0);
        dy = (y1 - y0);

        if (dy < 0) {
            dy = -dy;
            stepy = -1;
        } else {
            stepy = 1;
        }

        if (dx < 0) {
            dx = -dx;
            stepx = -1;
        } else {
            stepx = 1;
        }

        x = x0;
        y = y0;
        g.drawLine(x0, y0, x0, y0);

        if (dx > dy) {
            p = 2 * dy - dx;
            incE = 2 * dy;
            incNE = 2 * (dy - dx);
            while (x != x1) {
                x = x + stepx;
                if (p < 0) {
                    p = p + incE;
                } else {
                    y = y + stepy;
                    p = p + incNE;
                }
                g.drawLine(x, y, x, y);
            }
        } else {
            p = 2 * dx - dy;
            incE = 2 * dx;
            incNE = 2 * (dx - dy);
            while (y != y1) {
                y = y + stepy;
                if (p < 0) {
                    p = p + incE;
                } else {
                    x = x + stepx;
                    p = p + incNE;
                }
                g.drawLine(x, y, x, y);
            }
        }
    }

    public static void main(String[] args) {
        // Crear un nuevo Frame
        JFrame frame = new JFrame("Reto1Clase");
        // Al cerrar el frame, termina la ejecuciÃ³n de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new Reto_Clase_1());
        // Asignarle tamaÃ±o
        frame.setSize(400, 400);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
    }
}
