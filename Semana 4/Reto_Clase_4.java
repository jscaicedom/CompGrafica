

import java.io.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Reto_Clase_4 extends JPanel{

    public Edge[] ed;
    public Point3[] puntos;
    public Point3[] tras;
    public Point3[] rot;
    

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        // size es el tamaño de la ventana.
        Dimension size = getSize();
        // Insets son los bordes y los títulos de la ventana.
        Insets insets = getInsets();
        
        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;
        
        g2d.drawLine(w/2, 0, w/2, h);
        g2d.drawLine(0, h/2, w, h/2);

        Point3 trasx = new Point3(0, 0, 1);
            trasx.y = 50; 
            trasx.x = 25;
            trasladar(trasx);
            
        for (Edge ed1 : ed) {
            g2d.drawLine((int) (ed1.point1.x)+w/2,h/2- (int) (ed1.point1.y), (int) (ed1.point2.x)+w/2,h/2- (int) (ed1.point2.y));
        }
        
    }

    public void leer(String archivo) {
        Scanner s;
        File f = new File(archivo);
        try {
            s = new Scanner(f);
            int tamañoPunto = Integer.parseInt(s.nextLine());
            int cont1 = 0;
            puntos = new Point3[tamañoPunto];
            while (s.hasNextLine() && cont1 < tamañoPunto) {
                String line = s.nextLine();
                try (Scanner s1 = new Scanner(line)) {
                    s1.useDelimiter("\\s+");
                    puntos[cont1] = new Point3((Integer.parseInt(s1.next()))/2, (Integer.parseInt(s1.next()))/2, 1);
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
        JFrame frame = new JFrame("Reto4Clase");
        // Al cerrar el frame, termina la ejecución de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new Reto_Clase_4());
        // Asignarle tamaño
        frame.setSize(400, 400);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
    }

    public void trasladar(Point3 mover) {
        Matrix3x3 matrizt = new Matrix3x3();
        double dx = mover.x;
        double dy = mover.y;
        tras = new Point3[puntos.length];
        for (int i = 0; i < puntos.length; i++){
            matrizt.x[0][0] = 1;
            matrizt.x[0][1] = 0;
            matrizt.x[0][2] = dx;
            matrizt.x[1][0] = 0;
            matrizt.x[1][1] = 1;
            matrizt.x[1][2] = dy;
            matrizt.x[2][0] = 0;
            matrizt.x[2][1] = 0;
            matrizt.x[2][2] = 1;
            tras[i] = matrizt.times(matrizt, puntos[i]);
            puntos[i].x=tras[i].x;
            puntos[i].y=tras[i].y;
        }
        
        OrganizarEd("/home/diana/Descargas/CompGráfica/Semana 4/casita.txt" );
    }
     
    
    public void OrganizarEd(String archivo){
        Scanner s;
        File f = new File(archivo);
        try {
            s = new Scanner(f);
            //int tamañoPunto = Integer.parseInt(s.nextLine());
            int cont1 = 0;
            //puntos = new Point2[tamañoPunto
             while (s.hasNextLine() && cont1 < puntos.length+1) {
                String line = s.nextLine();
                try (Scanner s1 = new Scanner(line)) {
                    s1.nextLine();
                    s1.close();
                }

                cont1++;
            }
        int tamañoEjes = Integer.parseInt(s.nextLine());
            int cont2 = 0;
             ed = new Edge[ed.length];
            while (cont2 < ed.length) {
                String line = s.nextLine();
                try (Scanner s1 = new Scanner(line)) {
                    s1.useDelimiter("\\s+");
                    ed[cont2] = new Edge(tras[Integer.parseInt(s1.next())], tras[Integer.parseInt(s1.next())]);
                    s1.close();
                }

                cont2++;
            }
        }catch (FileNotFoundException e) {
            System.out.println("El archivo no existe...");
        }
    }
    public Reto_Clase_4() {
        leer("/home/diana/Descargas/CompGráfica/Semana 4/casita.txt" );
        
    }

    

   

}
