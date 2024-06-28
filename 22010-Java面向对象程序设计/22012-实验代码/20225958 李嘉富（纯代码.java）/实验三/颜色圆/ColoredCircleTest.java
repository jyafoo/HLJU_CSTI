package Third;

import second.Circle;
import second.Point;
public class ColoredCircleTest {
    public static void main(String[] args) {
        System.out.println("test6------------------");
        ColoredCircle cc1 = new ColoredCircle();
        System.out.println(cc1);
        ColoredCircle cc2 = new ColoredCircle(null,-1);
        System.out.println(cc2);
        ColoredCircle cc3 = new ColoredCircle(null,null);
        System.out.println(cc3);
        System.out.println("test7------------------");
        ColoredCircle cc4 = new ColoredCircle(new Point(0,0),10);
        System.out.println(cc4);
        System.out.println("test8----------------------------");
        ColoredCircle cc8 = new ColoredCircle(new Color(0, 0, 0), new Color(1, 1, 1));
        System.out.println(cc8);
        System.out.println("test9----------------------------");
        ColoredCircle cc9 = new ColoredCircle(new Point(1,1),10,new Color(0,0,0),new Color(1,1,1));
        System.out.println(cc9);
        System.out.println("test10----------------------------");
        ColoredCircle cc10 = new ColoredCircle(null,-10,null,null);
        System.out.println(cc10);
        System.out.println("test11----------------------------");
        cc10.setCenterColor(null);
        System.out.println(cc10.getCenterColor());
        System.out.println("test12----------------------------");
        cc10.setBorderColor(null);
        System.out.println(cc10.getBorderColor());
        System.out.println("test13----------------------------");
        Circle c14 = new Circle();
        ColoredCircle c2 = new ColoredCircle();
        show(c2.relation(c14));
        System.out.println("test14----------------------------");
        ColoredCircle coloredCircle1 = new ColoredCircle();
        ColoredCircle coloredCircle2 = new ColoredCircle();
        show(coloredCircle2.relation(coloredCircle1));
        System.out.println("test15----------------------------");
        ColoredCircle cc15 = new ColoredCircle(new Color(1,1,1),new Color(1,1,1));
        Circle cc152 = new ColoredCircle();
        show(cc15.relation(cc152));

    }

    public static void show(int t) {

        switch (t) {
            case 0:
                System.out.println("同一圆");
                break;
            case 1:
                System.out.println("同心圆也是包含圆");
                break;
            case 2:
                System.out.println("内含圆");
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
            default:
                System.out.println("error!");
                break;
        }
    }
}