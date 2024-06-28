package second;
public class CircleTest {
    public static void main(String[] args) {
        //test2
        System.out.println("test2------------------");
        Circle c1 = new Circle();
        System.out.println(c1.getCenterX() + "\t" + c1.getCenterY() + "\t" + c1.getR());
        System.out.println();
        //test3
        System.out.println("test3-----------------");
        Circle c2 = new Circle(1, 1, 10);
        System.out.println(c2.getCenterX() + "\t" + c2.getCenterY() + "\t" + c2.getR());
        System.out.println("周长：" + c2.perimeter());
        System.out.println("面积：" + c2.area());
        System.out.println();
        //test4
        System.out.println("test4-----------------");
        Circle c3 = new Circle(1, 1, -1);
        System.out.println(c3.getCenterX() + "\t" + c3.getCenterY() + "\t" + c3.getR());
        System.out.println("周长：" + c3.perimeter());
        System.out.println("面积：" + c3.area());
        System.out.println();
        System.out.println("test5-----------------");
        //test5
        Point s1 = new Point(1, 1);
        Circle c4 = new Circle(s1, 10);
        System.out.println(c4.getCenterX() + "\t" + c4.getCenterY() + "\t" + c4.getR());
        System.out.println("周长：" + c4.perimeter());
        System.out.println("面积：" + c4.area());
        System.out.println();
        //test6
        System.out.println("test6-----------------");
        Point s2 = new Point(1, 1);
        Circle c5 = new Circle(s2, -1);
        System.out.println(c5.getCenterX() + "\t" + c5.getCenterY() + "\t" + c5.getR());
        System.out.println("周长：" + c5.perimeter());
        System.out.println("面积：" + c5.area());
        System.out.println();
        //test7
        System.out.println("test7-----------------");
        Circle c6 = new Circle(null, 10);
        System.out.println(c6.getCenterX() + "\t" + c6.getCenterY() + "\t" + c6.getR());
        System.out.println("周长：" + c6.perimeter());
        System.out.println("面积：" + c6.area());
        System.out.println();
        //test8
        System.out.println("test8-----------------");
        Point p = new Point();
        p.setX(10);
        p.setY(10);
        System.out.println(p.getX() + "\t" + p.getY());
        System.out.println();
        //test9
        System.out.println("test9-----------------");
        Circle c7 = new Circle();
        c7.setR(20);
        System.out.println(c7.getR());
        System.out.println();
        //test10
        System.out.println("test10-----------------");
        c7.setR(-20);
        System.out.println(c7.getR());
        System.out.println();
        //test11
        System.out.println("test11-----------------");
        Circle c9 = new Circle();
        c9.setCenter(new Point(20, 20));
        System.out.println(c9.getCenterX() + "\t" + c9.getCenterY());
        System.out.println();
        //test12
        System.out.println("test12-----------------");
        System.out.println(c9);
        System.out.println("test13-----------------");
        Circle c15 = new Circle();
        Circle c16 = new Circle(0, 0, 0);
        show(c15, c16);
        System.out.println("test14-----------------");
        Circle c17 = new Circle(0, 0, 1);
        show(c15, c17);
        System.out.println("test15-----------------");
        Circle c18 = new Circle(0, 0, 10);
        Circle c19 = new Circle(1, 1, 5);
        show(c18, c19);
        System.out.println("test16-----------------");
        Circle c21 = new Circle(0, 5, 10);
        show(c18, c21);
        System.out.println("test17-----------------");
        Circle c22 = new Circle(0, 20, 10);
        show(c18, c22);
    }


    //根据返回值，输出圆的关系
    public static void show(Circle c1, Circle c2){
        int result = c1.relation(c2);
        switch (result){
            case 0:
                System.out.println("同一圆");
                break;
            case 1:
                System.out.println("同心圆也是包含圆");
                break;
            case 2:
                System.out.println("内含圆");
                break;
            case 3:
                System.out.println("内切圆");
                break;
            case 4:
                System.out.println("相交圆");
                break;
            case 5:
                System.out.println("分离圆");
                break;
            case 6:
                System.out.println("外切圆也是分离圆");
                break;
        }
    }

}
