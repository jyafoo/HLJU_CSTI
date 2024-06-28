package Five;



public class ArrayReverserTest {
    /*
    编写 ArrayReverser 类的测试类实验 5.ArrayTest。该类首先创建一个一维数组，并
    将其内容用随机数填充；其后利用上述一维数组创建 ArrayReverser 对象，并通过
    toString()方法查看初始数据；最后调用 reverse()方法后再次调用 toString()查看反
    转后的数据。
     */
    public static void main(String[] args) {
        System.out.println("test1----------------------------");
        ArrayReverser test1 = new ArrayReverser(null);
        System.out.println(test1.getData().length);
        System.out.println("test2----------------------------");
        int[] data2 = new int[]{};
        ArrayReverser test2 = new ArrayReverser(data2);
        System.out.println(test2);
        System.out.println("test3----------------------------");
        ArrayReverser test3 = new ArrayReverser();
        test3.setData(null);
        System.out.println(test3);
        System.out.println("test4----------------------------");
        int[] data4 = new int[]{1,2,3,4,5};
        ArrayReverser test4 = new ArrayReverser();
        test4.setData(data4);
        System.out.println(test4);
        System.out.println("test5----------------------------");
        int[] data5 = new int[]{1,2,3,4,5};
        ArrayReverser test5 = new ArrayReverser(data5);
        test5.reverse();
        System.out.println(test5);
        System.out.println("test6----------------------------");
        int[] data6 = new int[6];
        for (int i = 0; i < data6.length; i++) {
            data6[i] = (int) (Math.random() * 20 + 1);
            System.out.print(data6[i] + " ");
        }
        System.out.println();
        ArrayReverser arr = new ArrayReverser();
        arr.setData(data6);
        System.out.println(arr);



    }
}
