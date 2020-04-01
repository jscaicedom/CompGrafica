
public class Point4 {
     double x, y, z,w;
   
   public Point4(double x, double y, double z, double w){
       this.x=x;
       this.y=y;
       this.z=z;
       this.w=w;
   
   }
    public  double magnitude(){
        return Math.sqrt(  Math.pow(x,2) +  Math.pow(y,2) +  Math.pow(z,2) + Math.pow(w,2));
    }
    public void normalize(){
       
       x = x/w;
       y = y/w;
       z = z/w;
       w = 1;       
    }
}
