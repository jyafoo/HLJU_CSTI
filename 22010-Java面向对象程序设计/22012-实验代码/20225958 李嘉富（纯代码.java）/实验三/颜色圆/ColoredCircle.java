package Third;

import second.Circle;
import second.Point;

public class ColoredCircle extends Circle {
    private Color centerColor;
    private Color borderColor;


    //无参构造
    public ColoredCircle() {
        //调用父类空参构造
        super();

        this.borderColor = new Color();
        this.centerColor = new Color();
    }

    //带参构造
    public ColoredCircle(double x, double y, double r) {
        //调用父类带参构造
        super(x, y, r);

        this.borderColor = new Color();
        this.centerColor = new Color();
    }

    public ColoredCircle(Point center, double r) {
        //调用父类带参构造
        super(center, r);

        this.borderColor = new Color();
        this.centerColor = new Color();
    }

    public ColoredCircle(Color centerColor, Color borderColor) {
        super();

        if (borderColor == null) {
            this.borderColor = new Color();
        } else {
            this.borderColor = borderColor;
        }
        if (centerColor == null) {
            this.centerColor = new Color();
        } else {
            this.centerColor = centerColor;
        }
    }

    public ColoredCircle(Point center, double r, Color centerColor, Color borderColor) {
        super(center, r);
        if (borderColor == null) {
            this.borderColor = new Color();
        } else {
            this.borderColor = borderColor;
        }
        if (centerColor == null) {
            this.centerColor = new Color();
        } else {
            this.centerColor = centerColor;
        }
    }

    //set方法
    public void setBorderColor(Color borderColor) {
        if (borderColor != null)
            this.borderColor = borderColor;
    }

    public void setCenterColor(Color centerColor) {
        if (centerColor != null)
            this.centerColor = centerColor;
    }


    //get方法

    public Color getBorderColor() {
        return borderColor;
    }

    public Color getCenterColor() {
        return centerColor;
    }



    //1、先进行对象判断：Circle 对象还是 ColoredCircle 对象
    //2、进行颜色判断
    //3、判断圆的位置关系
    @Override
    public int relation(Circle circle) {
        int result;
        //调用父类relation
        result = super.relation(circle);
        if (result == 0) {
            if (circle instanceof ColoredCircle) {
                //对象类型相同时
                ColoredCircle temp = (ColoredCircle) circle;
                if (!(this.centerColor.equals(temp.centerColor)
                        && this.borderColor.equals(temp.borderColor))) {
                    result = 1;
                }

            } else {
                //对象类型不同时，只能是同心圆
                result = 1;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "ColorCircle{" +
                "area=" + super.area()+
                ",perimeter="+super.perimeter()+
                ",center=" + getCenter() +
                ",r=" + getR() +
                ",borderColor=" + borderColor +
                ", centerColor=" + centerColor +
                "}";
    }
}