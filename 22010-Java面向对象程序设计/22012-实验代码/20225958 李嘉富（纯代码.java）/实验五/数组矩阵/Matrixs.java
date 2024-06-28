package Five;

public class Matrixs {
    /*
    编写矩阵类实验 5.Matrix。该类包括矩阵数据数组 double data[][]，构造方法
    Matrix(int rows,int cols)、Matrix(double data[][])，获取某元素值的方法 double
    getData(int row,int col)，设置某元素值的方法 void setData(int row,int col,double
            value)，获取矩阵行数方法 int getRows()，获取矩阵列数方法 int getCols()，计算两
    个矩阵的乘积的方法 Matrix multiply(Matrix m)以及 equals()、toString()等内容。

 */

    private double data[][];

    /*在 Matrix 类的所有构造方法中，如果行数或列数值小于 1，或形参 data 为空，则
    抛出 IllegalArgumentException 异常；*/

    public Matrixs() {
        this.data = new double[1][1];
    }

    public Matrixs(int row, int col) throws IllegalArgumentException {
        if (col <= 0 || row <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.data = new double[row][col];
        }
    }

    public Matrixs(double data[][]) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        this.data = data;
    }
//    public Matrixs(double data[][], int row, int col){
//        if (col <= 0 || row <= 0){
//            System.out.println("error");
//        } else {
//            this.col = col;
//            this.row = row;
////            this.data = new double[row][col];
//        }
//        this.data = data;
//    }


    public void setData(double[][] data) {
        this.data = data;
    }

    public void setData(int row, int col, int value) throws IllegalIndexException{
        if (row < 0 || col < 0 || row > getRows() || col > getCols()) throw new IllegalIndexException();
        data[row][col] = value;
    }

    public int getRows() {
        return data.length;
    }

    public int getCols() {
        return data[0].length;
    }

    public double getData(int col, int row) throws IllegalIndexException {
        if (col < 0 || row < 0 || col >= getCols() || row >= getRows()){
            throw new IllegalIndexException();
        }
        return data[row][col];
    }

    /*在 multiply(…)方法中，如果形参为空对象，或两个矩阵的行列数不满足矩阵相乘
    规则，则抛出 MatrixMultiplicationException 异常。*/
    public Matrixs multiply(Matrixs m) throws MatrixMultiplicationException, IllegalArgumentException {
        if (m == null || this.getCols() != m.getRows() ) {
            throw new MatrixMultiplicationException();
        }
        //创建临时对象
        Matrixs temp = new Matrixs(this.getRows(),m.getCols());
        //先判断能否相乘
            //行*列
            for (int i = 0; i < this.getRows(); i++) {
                for (int j = 0; j < this.data[i].length; j++) {
//                    temp.data[i][j] = 0;
                    for (int k = 0; k < m.data[j].length; k++) {
                        temp.data[i][j] += this.data[i][k] * m.data[k][j];
                    }
                }
            }
        return temp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || !(obj instanceof Matrixs))
            return false;
        //强转对比具体值
        Matrixs m = (Matrixs) obj;
        if (this.getCols() == m.getCols() && this.getRows() == m.getRows()) {
            for (int i = 0; i < getRows(); i++) {
                for (int j = 0; j < getCols() ; j++) {
                    if (this.data[i][j] != m.data[i][j])
                        return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("data[" + data.length + "][" + data[0].length + "] = " + "\n");
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                sb.append("   " + data[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}