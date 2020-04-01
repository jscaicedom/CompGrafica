

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javafx.util.Pair;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Sarai
 */
public class Reto_Casa_3 extends JPanel {

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);

        // size es el tamaÃƒÂ±o de la ventana.
        Dimension size = getSize();
        // Insets son los bordes y los tÃƒÂ­tulos de la ventana.
        Insets insets = getInsets();

        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;
        
        g2d.drawLine(200, 0, 200, 400);
        g2d.drawLine(0, 200, 400, 200);

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File("C:\\Users\\Cristian Marulanda\\Desktop\\Mora y Pablo\\CompGráfica\\Semana 3\\Casita.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            
            String linea;
            linea = br.readLine();
            Point[] points = new Point[Integer.parseInt(linea)];
            for (int i = 0; i < Integer.parseInt(linea); i++) {
                String linea2 = br.readLine();
                String[] lin = linea2.split(" ");
                Point point = new Point(Double.parseDouble(lin[0]), Double.parseDouble(lin[1]));
                points[i] = point;
                
            }
            linea = br.readLine();
            Edge[] edges = new Edge[Integer.parseInt(linea)];
            for (int i = 0; i < Integer.parseInt(linea); i++) {
                String linea2 = br.readLine();
                String[] lin = linea2.split(" ");
                Edge edge = new Edge(points[Integer.parseInt(lin[0])], points[Integer.parseInt(lin[1])]);
                edges[i] = edge;
            }
            
            for (int a = 0; a < edges.length; a++) {
                //g2d.drawLine((w / 2) + (int) edges[a].point1.x, (h / 2) - (int) edges[a].point1.y, (w / 2) + (int) edges[a].point2.x, (h / 2) - (int) edges[a].point2.y);
                g2d.drawLine((w / 2) + (int) (edges[a].point1.x)/2, (h / 2) - (int) (edges[a].point1.y)/2, (w / 2) + (int) (edges[a].point2.x)/2, (h / 2) - (int) (edges[a].point2.y)/2);        
                
                
                //g2d.drawLine((int) edges[a].point1.x, (int) edges[a].point1.y,  (int) edges[a].point2.x,  (int) edges[a].point2.y);
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    public class Point {

        double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;

        }

    }

    public class Edge {

        public Point point1;
        public Point point2;

        public Edge(Point point1, Point point2) {
            this.point1 = point1;
            this.point2 = point2;
        }

        public String toString() {
            return "Punto 1: " + point1.x + ", " + point1.y + "\nPunto 2: " + point2.x + ", " + point2.y;

        }
    }

    public static void main(String[] args) {
        // Crear un nuevo Frame
        JFrame frame = new JFrame("Reto2Casa");
        frame.setBackground(Color.orange);
        // Al cerrar el frame, termina la ejecuciÃƒÂ³n de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new Reto_Casa_3());
        // Asignarle tamaÃƒÂ±o
        frame.setSize(400, 400);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
    }

}
