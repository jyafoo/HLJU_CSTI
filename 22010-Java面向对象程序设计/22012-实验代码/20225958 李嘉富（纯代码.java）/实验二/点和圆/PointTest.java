package second;

public class PointTest {
    public static void main(String[] args) {
        //test1
        System.out.println("test1----------------");
        Point p1 = new Point();
        System.out.println(p1);
        Point p2 = new Point(0,3);
        System.out.print(p2.getX() + "\t");
        System.out.println(p2.getY());
        Point p3 = new Point(0,2);
        p3.setY(3);
        System.out.println(p1.distance(p2));
        System.out.println(p2.equals(p3));
    }
}
