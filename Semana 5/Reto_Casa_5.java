
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
import java.text.DecimalFormat;

public class Reto_Casa_5 extends JPanel
        implements KeyListener {
    
    public Edge[] ed;
    public Point4[] puntos;
    double d;
    
    public Reto_Casa_5() {
        // El panel, por defecto no es "focusable". 
        // Hay que incluir estas líneas para que el panel pueda
        // agregarse como KeyListsener.
        d=500;
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
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

    public void trasladar(Point4 mover) { 
        Matrix4x4 matrizt = new Matrix4x4();
        double dx = mover.x;
        double dy = mover.y;
        double dz = mover.z;
        Point4 []tras = new Point4[puntos.length];
        matrizt.x[0][0] = 1;
        matrizt.x[0][1] = 0;
        matrizt.x[0][2] = 0;
        matrizt.x[0][3] = dx;
        matrizt.x[1][0] = 0;
        matrizt.x[1][1] = 1;
        matrizt.x[1][2] = 0;
        matrizt.x[1][3] = dy;
        matrizt.x[2][0] = 0;
        matrizt.x[2][1] = 0;
        matrizt.x[2][2] = 1;
        matrizt.x[2][3] = dz;
        matrizt.x[3][0] = 0;
        matrizt.x[3][1] = 0;
        matrizt.x[3][2] = 0;
        matrizt.x[3][3] = 1;
        
        for (int i = 0; i < puntos.length; i++){
            tras[i] = Matrix4x4.times(matrizt, puntos[i]);
            puntos[i].x=tras[i].x;
            puntos[i].y=tras[i].y;
            puntos[i].z=tras[i].z;
            puntos[i].w=tras[i].w;
            
        }      
    }
    
    public void rotacionz(Point4 mover){
        Matrix4x4 matrizt = new Matrix4x4();
        double cx = puntos[7].x, cy = puntos[7].y, cz = puntos[7].z;
        Point4 p = new Point4(-cx,-cy,-cz,1);
        trasladar(p);
        double cos = Math.cos(mover.x);
        double sen = Math.sin(mover.y);
        Point4 [] tras = new Point4[puntos.length];
        matrizt.x[0][0] = cos;
        matrizt.x[0][1] = -sen;
        matrizt.x[0][2] = 0;
        matrizt.x[0][3] = 0;
        matrizt.x[1][0] = sen;
        matrizt.x[1][1] = cos;
        matrizt.x[1][2] = 0;
        matrizt.x[1][3] = 0;
        matrizt.x[2][0] = 0;
        matrizt.x[2][1] = 0;
        matrizt.x[2][2] = 1; 
        matrizt.x[2][3] = 0;
        matrizt.x[3][0] = 0;
        matrizt.x[3][1] = 0;
        matrizt.x[3][2] = 0; 
        matrizt.x[3][3] = 1;
        
        for (int i = 0; i < puntos.length; i++){
            tras[i] = matrizt.times(matrizt, puntos[i]);
            puntos[i].x=tras[i].x;
            puntos[i].y=tras[i].y;
            puntos[i].z=tras[i].z;
            puntos[i].w=tras[i].w;
        }
        Point4 ip = new Point4(cx,cy,cz,1);
        trasladar(ip);         
    }
    
    
      public void rotacionx(Point4 mover){
        Matrix4x4 matrizt = new Matrix4x4();
        double cx = puntos[7].x, cy = puntos[7].y, cz = puntos[7].z;
        Point4 p = new Point4(-cx,-cy,-cz,1);
        trasladar(p);
        double cos = Math.cos(mover.x);
        double sen = Math.sin(mover.y);
        Point4 [] tras = new Point4[puntos.length];
        matrizt.x[0][0] = 1;
        matrizt.x[0][1] = 0;
        matrizt.x[0][2] = 0;
        matrizt.x[0][3] = 0;
        matrizt.x[1][0] = 0;
        matrizt.x[1][1] = cos;
        matrizt.x[1][2] = -sen;
        matrizt.x[1][3] = 0;
        matrizt.x[2][0] = 0;
        matrizt.x[2][1] = sen;
        matrizt.x[2][2] = cos; 
        matrizt.x[2][3] = 0;
        matrizt.x[3][0] = 0;
        matrizt.x[3][1] = 0;
        matrizt.x[3][2] = 0; 
        matrizt.x[3][3] = 1;

        for (int i = 0; i < puntos.length; i++){
            tras[i] = matrizt.times(matrizt, puntos[i]);
            puntos[i].x=tras[i].x;
            puntos[i].y=tras[i].y;
            puntos[i].z=tras[i].z;
            puntos[i].w=tras[i].w;
        }
        Point4 ip = new Point4(cx,cy,cz,1);
        trasladar(ip); 
    }      
     
    public void rotaciony(Point4 mover){
        Matrix4x4 matrizt = new Matrix4x4();
        double cx = puntos[7].x, cy = puntos[7].y, cz = puntos[7].z;
        Point4 p = new Point4(-cx,-cy,-cz,1);
        trasladar(p);
        double cos = Math.cos(mover.x);
        double sen = Math.sin(mover.y);
        Point4 [] tras = new Point4[puntos.length];
        matrizt.x[0][0] = cos;
        matrizt.x[0][1] = 0;
        matrizt.x[0][2] = sen;
        matrizt.x[0][3] = 0;
        matrizt.x[1][0] = 0;
        matrizt.x[1][1] = 1;
        matrizt.x[1][2] = 0;
        matrizt.x[1][3] = 0;
        matrizt.x[2][0] = -sen;
        matrizt.x[2][1] = 0;
        matrizt.x[2][2] = cos; 
        matrizt.x[2][3] = 0;
        matrizt.x[3][0] = 0;
        matrizt.x[3][1] = 0;
        matrizt.x[3][2] = 0; 
        matrizt.x[3][3] = 1;

        for (int i = 0; i < puntos.length; i++){
            tras[i] = matrizt.times(matrizt, puntos[i]);
            puntos[i].x=tras[i].x;
            puntos[i].y=tras[i].y;
            puntos[i].z=tras[i].z;
            puntos[i].w=tras[i].w;
        }
        Point4 ip = new Point4(cx,cy,cz,1);
        trasladar(ip); 
        
    }
              
    public void escalonar(Point4 mover){
     
        Matrix4x4 matrizt = new Matrix4x4();        
        double cx = puntos[7].x, cy = puntos[7].y, cz = puntos[7].z;
        Point4 p = new Point4(-cx,-cy,-cz,1);
       trasladar(p);
        
        Point4 [] tras = new Point4[puntos.length];
        double sx= mover.x;
        double sy= mover.y;
        double sz= mover.z;
        matrizt.x[0][0] = sx;
        matrizt.x[0][1] = 0;
        matrizt.x[0][2] = 0; 
        matrizt.x[0][3] = 0;
        matrizt.x[1][0] = 0;
        matrizt.x[1][1] = sy;
        matrizt.x[1][2] = 0;
        matrizt.x[1][3] = 0;
        matrizt.x[2][0] = 0;
        matrizt.x[2][1] = 0;
        matrizt.x[2][2] = sz;
        matrizt.x[2][3] = 0;
        matrizt.x[3][0] = 0;
        matrizt.x[3][1] = 0;
        matrizt.x[3][2] = 0;
        matrizt.x[3][3] = 1;
        
        for (int i = 0; i < puntos.length; i++){
            tras[i] = matrizt.times(matrizt, puntos[i]);
            puntos[i].x=tras[i].x;
            puntos[i].y=tras[i].y;
            puntos[i].z=tras[i].z;
            puntos[i].w=tras[i].w;
        }
        Point4 ip = new Point4(cx,cy,cz,1);
        trasladar(ip); 
        
    }
   
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        //System.out.println("Key pressed");
        Point4 m = new Point4(0, 0, 0, 1);
        if (tecla == KeyEvent.VK_W) {
            m.y += 10; 
            m.x = 0;
            m.z =0;
            trasladar(m);
        } else if (tecla == KeyEvent.VK_S) {
            m.y -= 10;
            m.x = 0;
            m.z = 0;
            trasladar(m);
        } else if (tecla == KeyEvent.VK_D) {
            m.x += 10;
            m.y = 0;
            m.z = 0;
            trasladar(m);
        } else if (tecla == KeyEvent.VK_A) {
            m.x -= 10;
            m.y = 0;
            m.z=0;
            trasladar(m);
        }else if (tecla == KeyEvent.VK_Z) {
            m.x = 0;
            m.y = 0;
            m.z += 10;
            trasladar(m);
        } else if (tecla == KeyEvent.VK_X) {
            m.x = 0;
            m.y = 0;
            m.z -= 10;
            trasladar(m);
        } else if (tecla == KeyEvent.VK_O){
            m.x = 0.9;
            m.y = 0.9;
            rotacionz(m);
        } else if (tecla == KeyEvent.VK_L){
            m.x -=- 0.9;
            m.y -= 0.9;  
            rotacionz(m);
        }else if (tecla == KeyEvent.VK_U){
            m.x =0.9;
            m.y = 0.9;
            rotacionx(m);
        } else if (tecla == KeyEvent.VK_J){
            m.x -=0.9;
            m.y -= 0.9;  
            rotacionx(m);
        }else if (tecla == KeyEvent.VK_I){
            m.x = 0.9;
            m.y= 0.9;
            rotaciony(m);
        } else if (tecla == KeyEvent.VK_K){
            m.x -= 0.9;
            m.y -=0.9;
            rotaciony(m);
        } else if(tecla == KeyEvent.VK_UP){
            m.x = 1.1;
            m.y = 1.1;
            m.z = 1.1;
            escalonar(m);
        }else if(tecla == KeyEvent.VK_DOWN){
            m.x = 0.9;
            m.y = 0.9;
            m.z = 0.9;
            escalonar(m);
        }
        repaint();
      
    
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    public static void main(String[] args) {
        // Crear un nuevo Frame
        JFrame frame = new JFrame("Reto5Casa");
        // Al cerrar el frame, termina la ejecución de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new Reto_Casa_5());
        // Asignarle tamaño
        frame.setSize(400, 400);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
    }
}
