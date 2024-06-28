package second;

public class Point {
    private double x;
    private double y;
    //构造器
    //空参构造
    public Point(){
        x = 0.0;     //定义原点
        y = 0.0;
    }
    //带参构造
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    //set方法
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }

    //get方法
    public double getX() {
        return x;
    }
    public double getY(){
        return y;
    }

    //计算两点距离
    public double distance(Point p){
        double dis;
        dis = Math.sqrt(Math.pow((this.x - p.x),2) + Math.pow((this.y - p.y),2));
        return dis;
    }

    /*
    //判断两点是否一致
    public boolean equals(Object p){
        //比较地址是否相同，若地址相同，则指向也相同，最终结果肯定相同
        if (this == p)
            return true;
        //判断是否为空，空则false；再比较两个类的类型，getclass获取，不一致也false
        if (p == null)// || p instanceof Point
            return false;
        // 需要比较子类对象成员时需要进行转型操作
        Point point = (Point) p;
        return this.x == point.x && this.y == point.y;
    }
    */

    @Override
    public String toString() {
        return "[ center：x = " + this.x + ",y = " + this.y + "]";
    }
}
