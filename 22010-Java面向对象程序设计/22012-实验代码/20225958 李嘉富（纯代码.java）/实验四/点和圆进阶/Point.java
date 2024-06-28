package Four;

public class Point extends second.Point {
    //构造方法
    //空参构造
    public Point(){
        //调用父类无参构造
        super();
    }
    //有参构造
    public Point(double x, double y){
        //调用父类有参构造
        super(x,y);
    }

    //set方法
    @Override
    public void setX(double x) {
        super.setX(x);
    }
    @Override
    public void setY(double y){
        super.setY(y);
    }

    //get方法
    @Override
    public double getX() {
        return super.getX();
    }

    @Override
    public double getY() {
        return super.getY();
    }


    @Override
    public double distance(second.Point p) {
        return super.distance(p);
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return  true;
        if (obj == null || !(obj instanceof second.Point))   // || this.getClass() != obj.getClass()
            return false;
        second.Point point = (second.Point) obj;
        return getX() == point.getX() && getY() == point.getY();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
