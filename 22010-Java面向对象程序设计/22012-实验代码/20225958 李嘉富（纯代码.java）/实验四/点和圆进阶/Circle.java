package Four;

import second.Point;

public class Circle extends second.Circle {
    //构造方法
    //空参构造
    public Circle(){
        super();
    }
    //有参构造
    public Circle(double x, double y, double r){
        super(x,y,r);
    }
    //此处p为试验2的point
    public Circle(Point p, double r){
        super(p,r);
    }

    //set方法


    @Override
    public void setCenter(Point center) {
        super.setCenter(center);
    }

    @Override
    public void setR(double r) {
        super.setR(r);
    }

    //get方法
    @Override
    public double getR() {
        return super.getR();
    }

    @Override
    public double getCenterX() {
        return super.getCenterX();
    }

    @Override
    public double getCenterY() {
        return super.getCenterY();
    }
    //面积
    @Override
    public double area() {
        return super.area();
    }
    //周长
    @Override
    public double perimeter() {
        return super.perimeter();
    }

    @Override
    public int relation(second.Circle circle) {
        return super.relation(circle);
    }

    @Override
    public boolean equals(Object obj) {
        //1、比较两个引用是否指向同一个实际对象,其实就是比较地址
        if (this == obj)
            return true;
        //2、判断obj是否为空和两个引用指向的对象是否一致
        if (obj == null || !(obj instanceof second.Circle))
            return false;
        //3、比较各个属性值
        second.Circle c = (second.Circle) obj;
        return c.getCenterX() == this.getCenterX()
                && c.getCenterY() == this.getCenterY()
                && c.getR() == this.getR();
    }

    @Override
    public String toString() {

        return "{X = " + getCenterX() + ",Y = " + getCenterY() + ",R = " + getR()+",area="+area()+",perimeter="+perimeter()+"}";
    }


}
