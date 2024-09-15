package first;
import java.util.Scanner;
public class FactorialTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N;
        do{
            N = scanner.nextInt();
        } while(N < 0 || N>20);
        int sum = 1;
        for(int i = N;i > 0;i--){
            sum *= i;
        }
        System.out.println(sum);
    }
}
