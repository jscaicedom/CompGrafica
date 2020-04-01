
/**
 *
 * @autor Sarai
 *  
 */
import java.io.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Reto_Clase_5 extends JPanel{
    
    public Edge[] ed;
    public Point4[] puntos;
    double d;
    
    public Reto_Clase_5() {
        d=500;
        leer("/home/diana/Descargas/CompGráfica/Semana 5/casita3d.txt" );
    }
    
  
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        // size es el tamaño de la ventana.
        Dimension size = getSize();
        // Insets son los bordes y los títulos de la ventana.
        Insets insets = getInsets();
        int w =  size.width - insets.left - insets.right;
        int h =  size.height - insets.top - insets.bottom;
        
        g2d.drawLine(w/2, 0, w/2, h);
        g2d.drawLine(0, h/2, w, h/2);
        //Proyectar
        Matrix4x4 matp = new Matrix4x4();
        matp.x[0][0]=1;
        matp.x[1][1]=1;
        matp.x[2][2]=1;
        matp.x[3][2]= -1/d;
        for (Edge ed1 : ed) { 
            Point4 a = ed1.point1;
            Point4 b = ed1.point2;
            Point4 a1 = Matrix4x4.times(matp, a);
            Point4 b1 = Matrix4x4.times(matp, b);
            a1.normalize();
            b1.normalize();
            g2d.drawLine((int) (a1.x)+(w/2),(h/2)- (int) (a1.y), (int) (b1.x)+(w/2),(h/2)- (int) (b1.y));
        }       
    }
    
    public void leer(String archivo) {
        Scanner s;
        File f = new File(archivo);
        try {
            s = new Scanner(f);
            int tamañoPunto = Integer.parseInt(s.nextLine());
            int cont1 = 0;
            puntos = new Point4[tamañoPunto];
            while (s.hasNextLine() && cont1 < tamañoPunto) {
                String line = s.nextLine();
                try (Scanner s1 = new Scanner(line)) {
                    s1.useDelimiter("\\s+");
                    puntos[cont1] = new Point4(Integer.parseInt(s1.next()), Integer.parseInt(s1.next()),Integer.parseInt(s1.next()), 1);
                    s1.close();
                }

                cont1++;
            }
            int tamañoEjes = Integer.parseInt(s.nextLine());
            int cont2 = 0;
             ed = new Edge[tamañoEjes];
            while (cont2 < tamañoEjes) {
                String line = s.nextLine();
                try (Scanner s1 = new Scanner(line)) {
                    s1.useDelimiter("\\s+");
                    ed[cont2] = new Edge(puntos[Integer.parseInt(s1.next())], puntos[Integer.parseInt(s1.next())]);
                    s1.close();
                }

                cont2++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe...");
        }
      
    }

    public static void main(String[] args) {
        // Crear un nuevo Frame
        JFrame frame = new JFrame("Reto5Clase");
        // Al cerrar el frame, termina la ejecución de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new Reto_Clase_5());
        // Asignarle tamaño
        frame.setSize(400, 400);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
    }
}
