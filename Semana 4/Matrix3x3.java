
public class Matrix3x3 {

    public int n = 3;
    public double[][] x;

    public Matrix3x3() {
        x = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                x[i][j] = 0.0;
            }
        }

    }

    public static Point3 times(Matrix3x3 matriz, Point3 point) {
        double[] a = new double[3];
        double[] pointarr = {point.x, point.y, point.w};

        for (int i = 0; i < matriz.n; i++) {
            for (int j = 0; j < matriz.n; j++) {
                a[i] += (matriz.x[i][j] * pointarr[j]);
            }
        }

        Point3 point2 = new Point3(a[0], a[1], a[2]);
        return point2;
    }

    public static Matrix3x3 times(Matrix3x3 matriz1, Matrix3x3 matriz2) {
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
