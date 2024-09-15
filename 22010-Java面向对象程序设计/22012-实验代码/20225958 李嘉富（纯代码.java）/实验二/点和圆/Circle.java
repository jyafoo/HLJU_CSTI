package second;

public class Circle {
     private double r;
     private Point center;
    //构造器
    //空参构造
    public Circle(){
        this.center = new Point(0,0);
        this.r = 0;
    }

    //带参构造
    public Circle(double x, double y, double r){
        this.center = new Point(x,y);
        if(r > 0){
            this.r = r;
        }else{
            this.r = 0;
        }
    }

    //Circle(Point c,int r)
    public Circle(Point center, double r){
        if (center == null) {
            this.center = new Point(0,0);
        }else {
            this.center = center;
        }
        if(r > 0){
            this.r = r;
        }else{
            this.r = 0;
        }
    }

    //set方法
    public void setR(double r){
        if(r > 0){
            this.r = r;
        }
    }
    public void setCenter(Point center) {
        if(center != null){
            this.center.setX(center.getX());
            this.center.setY(center.getY());
        }
    }

    //get方法
    public double getR() {
        return r;
    }

    public double getCenterX() {
        return center.getX();
    }
    public double getCenterY() {
        return center.getY();
    }

    //此处问题：返回的是center地址，该怎明确它的值???
    public Point getCenter() {
        return center;
    }

    //周长
    public double perimeter(){
        return (2 * Math.PI * r);
    }

    //面积
    public double area(){
        return (Math.PI * r * r);
    }

    //圆的判断        }
    public int relation(Circle circle){
        //两圆半径之和
        double sumR = this.r + circle.r;
        //两元半径值之差
        double subR = Math.abs(this.r - circle.r);
        //两圆心距离
        double distance = this.center.distance(circle.center);
        //判断两元圆心位置关系
        //this.center.equals(circle.center)
        if (this.getCenter().getX() == circle.getCenterX() &&
            this.getCenterY() == circle.getCenterY()){        //圆心相等时
            if (this.r == circle.r){
                return 0;//同一圆
            } else {
                return 1;//同心圆也是包含圆
            }
        }else {
            //圆心不等时
            if (sumR > distance){        //d < r1+r2
                if (subR > distance){
                    return 2;//内含圆
                }  else if (subR == distance){
                    return 3;//内切圆
                } else {
                    return 4;//相交圆
                }
            } else if (sumR < distance){     //d > r1+r2
                return 5;//分离:两圆的半径之和小于两圆圆心距离
            } else {
                return 6;//外切:两圆的半径之和等于两圆圆心距离
            }
        }
    }

    //equals()重写
   /* @Override
    public boolean equals(Object obj) {
        //1、比较两个引用是否指向同一个实际对象,其实就是比较地址
        if (this == obj)
            return true;
        //2、判断obj是否为空和两个引用指向的对象是否一致
        if (obj == null || obj instanceof Circle)
            return false;
        //3、进行强转，比较各个属性值
        Circle c = (Circle) obj;
        return c.getCenterX() == this.getCenterX()
                && c.getCenterY() == this.getCenterY()
                && c.getR() == this.getR();
    }*/

    @Override
    public String toString() {
        return "circle{ x = " + getCenterX() + ",y = " + getCenterY() +
                ",r = " + getR() +
                ",perimeter = " + perimeter() +
                ",area = " + area() +
                "}";
    }
}
