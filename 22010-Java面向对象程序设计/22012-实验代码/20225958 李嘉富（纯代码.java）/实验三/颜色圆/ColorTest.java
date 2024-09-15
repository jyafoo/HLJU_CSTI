package Third;

public class ColorTest {
    public static void main(String[] args) {
        System.out.println("test2------------------");
        Color c1 = new Color();
        System.out.println(c1);
        System.out.println("test3------------------");
        Color c2 = new Color(255,255,255);
        System.out.println(c2);
        System.out.println("test4------------------");
        Color c3 = new Color(355,355,355);
        System.out.println(c3);
        System.out.println("test5------------------");
        c2.setRed(355);
        c2.setBlue(355);
        c2.setGreen(355);
        System.out.println(c2);


    }


}
