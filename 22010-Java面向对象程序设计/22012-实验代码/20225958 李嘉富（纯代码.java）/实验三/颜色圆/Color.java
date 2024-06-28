package Third;

public class Color {
    private int red;
    private int green;
    private int blue;

    //常量
    public static final Color RED = new Color(255,0,0);
    public static final Color GREEN = new Color(0,255,0);
    public static final Color BLUE = new Color(0,0,255);
    public static final Color WHITE = new Color(255,255,255);
    public static final Color BLACK = new Color(0,0,0);
    public static final Color GRAY = new Color(128,128,128);
    //构造器
    //无参构造
    public Color(){

        this.red = 0;
        this.blue = 0;
        this.green = 0;
    }

    //有参构造
    public Color(int red, int green, int blue){
        if (red > 255 || red < 0) {
            System.out.println("Red error!");
        } else {
            this.red = red;
        }
        if (blue > 255 || blue < 0) {
            System.out.println("Blue error!");
        } else {
            this.blue = blue;
        }
        if (green > 255 || green < 0) {
            System.out.println("Green error!");
        } else {
            this.green = green;
        }
    }

    //set方法
    public void setRed(int red){
        if(red >= 0 && red <= 255)
            this.red = red;
    }

    public void setBlue(int blue) {
        if(blue >= 0 && blue <= 255)
            this.blue = blue;
    }

    public void setGreen(int green) {
        if(green >= 0 && green <= 255)
            this.green = green;
    }

    //get方法
    public int getRed() {
        return red;
    }

    public int getBlue() {
        return blue;
    }

    public int getGreen() {
        return green;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Color)) return false;

        Color color = (Color) o;

        if (red != color.red || green != color.green || blue != color.blue)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Color{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }


}
