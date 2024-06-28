package first;

public class NineMultiplication {
    public static void main(String[] args) {
        //1
        for(int i = 1;i <= 9;i++){
            for(int k = 1;k <= i;k++){
                System.out.print(k + "*" + i + "=" + i*k + "\t");
            }
            System.out.println();
        }
        System.out.println();

        //2
        for(int i = 1;i <= 9;i++){
            for(int space = 0;space < i - 1;space++){
                System.out.print("     " + "\t");
            }
            for(int k = i;k <= 9;k++){
                System.out.print(k + "*" + i + "=" + i*k + "\t");
            }
            System.out.println();
        }
        System.out.println();

        //3
        for(int i = 1;i <= 9;i++){
            for(int space = 0;space < 9 - i;space++){
                System.out.print("    ");
            }
            for(int k = 1;k <= i;k++){
                System.out.print(k + "*" + i + "=" + i*k + "\t");
            }
            System.out.println();
        }


    }
}
