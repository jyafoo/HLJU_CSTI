package Five;

public class MatrixTest {
    public static void main(String[] args) throws IllegalArgumentException, IllegalIndexException {
        /*Matrixs m1 = new Matrixs(1,2);
        Matrixs m2 = new Matrixs(3,3);
        System.out.println(m1.getRow());
        m1.setData(0,1,2.0);
        System.out.println(m1.getData(0, 1));

        m1.setData(new double[][]{{1,2}});
        m2.setData(new double[][]{{1,2,3},{2,3,4},{1,2,3}});
        Matrixs m3 = m1.multiply(m2);
        m3.toString();*/
        System.out.println("test7----------------------------");
        Matrixs m7 = null;
        try {
            m7 = new Matrixs(2,3);
            System.out.println(m7);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }

        System.out.println("test8----------------------------");
        double[][] data8= new double[1][2];
        Matrixs m8 = new Matrixs(data8);
        System.out.println(m8);
        System.out.println("test9----------------------------");

        try {
            Matrixs m9 = new Matrixs(0,3);
            System.out.println(m9);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println("test10----------------------------");
        try {
            Matrixs m10 = new Matrixs(null);
            System.out.println(m10);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        try {
            System.out.println("test11|test12----------------------------");
            Matrixs m11 = new Matrixs();
            System.out.println(m11.getCols() +"\t"+ m11.getRows());
            System.out.println("test13|test14----------------------------");
            Matrixs m13 = new Matrixs();
            System.out.println(m13.getData(0,0));
            m13.setData(0,0,10);
            System.out.println(m13.getData(0,0));

            System.out.println("test15----------------------------");
            // System.out.println(m13.getData(-1,0));
            System.out.println("test16----------------------------");
            System.out.println(m13.getData(m13.getRows(),0));
        }catch (IllegalIndexException illegalIndexException){
            System.out.println(illegalIndexException);
        }

        System.out.println("test17----------------------------");
        Matrixs m1 = new Matrixs();
        double[][] data1= new double[][]{{1,2},{2,3}};
        m1.setData(data1);
        Matrixs m2 = new Matrixs();
        double[][] data2= new double[][]{{1,3},{3,1}};
        m2.setData(data2);
        try {
            System.out.println(m1.multiply(m2));
        } catch (MatrixMultiplicationException e) {
            System.out.println(e);
        }
        System.out.println("test18----------------------------");
        try {
            m1.multiply(null);
        } catch (MatrixMultiplicationException e) {
            System.out.println(e);
        }
        System.out.println("test19----------------------------");
        double[][] data3= new double[][]{{1,2},{2,3},{4,3}};
        m1.setData(data3);
        double[][] data4= new double[][]{{1,3},{3,1}};
        m2.setData(data4);
        try {
            System.out.println(m2.multiply(m1));
        } catch (MatrixMultiplicationException e) {
            System.out.println(e);
        }
        System.out.println("test20----------------------------");
        System.out.println(m1);
    }
}
