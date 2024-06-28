package Four;
public class Test {
    public static void main(String[] args) {
        System.out.println("test2-----------------------");
        Point p1 = new Point();
        Point p2 = new Point(0,10);
        System.out.println(p1);
        System.out.println(p1.distance(p2));
        System.out.println("test3-----------------------");
        Circle c1 = new Circle();
        Circle c2 = new Circle(0,0,10);
        System.out.println(c1);
        System.out.println(c2);
        show(c1.relation(c2));
        System.out.println("test4-----------------------");
        System.out.println(p1);
        System.out.println("test5-----------------------");
        System.out.println(c1);
        System.out.println("test6-----------------------");
        System.out.println(p1.equals(p2));
        System.out.println("test7-----------------------");
        Point p3 = new Point(0,0);
        System.out.println(p1.equals(p3));
        System.out.println("test8-----------------------");
        second.Point point2 = new second.Point(0,0);
        System.out.println(p1.equals(point2));
        System.out.println("test9:实验二没有重写equals-----------------------");
        System.out.println(point2.equals(p1));
        System.out.println("test10-----------------------");
        Circle circle1 = new Circle();
        Circle circle2 = new Circle(0,0,10);
        System.out.println(circle1.equals(circle2));
        System.out.println("test11-----------------------");
        Circle circle3 = new Circle(0,0,0);
        System.out.println(circle3.equals(circle1));
        System.out.println("test12:p3,circle1-----------------------");
        Circle circle4 = new Circle(p3,0);
        System.out.println(circle1.equals(circle4));
        System.out.println(circle1.equals(p3));
        System.out.println(p3.equals(circle1));
        System.out.println("test13:point2,circle1-----------------------");
        Circle circle5 = new Circle(point2,0) ;
        System.out.println(circle5.equals(circle1));
        System.out.println("test14:circle1-----------------------");
        second.Circle circle6 = new second.Circle(0,0,0);
        System.out.println(circle1.equals(circle6));
        System.out.println(circle6.equals(circle1));

    }

    public static void show(int t) {

        switch (t) {
            case 0:
                System.out.println("同一圆");
                break;
            case 1:
                System.out.println("同心圆");
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
