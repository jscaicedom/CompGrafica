

/**
 *
 * @author Sarai
 */
public class Reto_Clase_3 {

    public class Point3 {

        double x, y, w;

        public Point3(double x, double y, double w) {
            this.x = x;
            this.y = y;
            this.w = w;

        }
    }

    public class Point4 {

        double x, y, w, z;

        public Point4(double x, double y, double w, double z) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.z = z;

        }

    }

    public class Matrix3x3 {

        public int n = 3; //dimension
        private double[][] x;

        public Matrix3x3() {
            x = new double[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    x[i][j] = 0.0;
                }
            }

        }

        public Point3 times(Matrix3x3 matriz, Point3 point) {
            double[] a = new double[3];
            double[] pointarr = {point.x, point.y, point.w};

            for (int i = 0; i < matriz.n; i++) {
                for (int j = 0; j < matriz.n; j++) {
                    a[i] += (matriz.x[i][j] * pointarr[j]);
                }
            }

            Point3 point3 = new Point3(a[0], a[1], a[2]);
            return point3;
        }

        public Matrix3x3 times(Matrix3x3 matriz1, Matrix3x3 matriz2) {
            Matrix3x3 matrizf = new Matrix3x3();
            for (int i = 0; i < matriz2.n; i++) {
                for (int j = 0; j < matriz1.n; j++) {
                    for (int k = 0; k < matriz1.n; k++) {
                        matrizf.x[i][j] += (matriz1.x[j][k] * matriz2.x[k][i]);
                    }
                }

            }

            return matrizf;

        }

    }

    public class Matrix4x4 {

        public int n = 4; //dimension
        private double[][] x;

        public Matrix4x4() {
            x = new double[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    x[i][j] = 0.0;
                }
            }

        }

        public Point4 times(Matrix4x4 matriz, Point4 point) {
            double[] a = new double[4];
            double[] pointarr = {point.x, point.y, point.w, point.z};

            for (int i = 0; i < matriz.n; i++) {
                for (int j = 0; j < matriz.n; j++) {
                    a[i] += (matriz.x[i][j] * pointarr[j]);
                }
            }

            Point4 point4 = new Point4(a[0], a[1], a[2], a[3]);
            return point4;

        }

        public Matrix4x4 times(Matrix4x4 matriz1, Matrix4x4 matriz2) {
            Matrix4x4 matrizf = new Matrix4x4();
            for (int i = 0; i < matriz2.n; i++) {
                for (int j = 0; j < matriz1.n; j++) {
                    for (int k = 0; k < matriz1.n; k++) {
                        matrizf.x[i][j] += (matriz1.x[j][k] * matriz2.x[k][i]);
                    }
                }

            }

            return matrizf;
        }

    }

    public class Vector4 {

        double x, y, z;

        public Vector4(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Vector4 crossProduct(Vector4 v1, Vector4 v2) {
            double x, y, z;

            x = (v1.y * v2.z - v1.z * v2.y);
            y = -(v1.x * v2.z - v1.z * v2.x);
            z = v1.x * v2.y - v1.y * v2.x;

            Vector4 vector = new Vector4(x, y, z);

            return vector;

        }

        public double dotProduct(Vector4 v1, Vector4 v2) {
            return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
        }

        public double magnitude() {
            return (double) Math.sqrt((double) Math.pow(x, 2) + (double) Math.pow(y, 2) + (double) Math.pow(z, 2));
        }

        public void normalize() {

            x = x / (magnitude());
            y = y / (magnitude());
            z = z / (magnitude());

        }

    }



}
