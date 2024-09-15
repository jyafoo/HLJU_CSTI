package Five;

public class ArrayReverser {
/*
      该类包含一个 int[]数组 data、
      构造方法 ArrayReverser(int[] data)、获取 data 数组的方法 int[] getData()、设置 data
      数组的方法 void setData(int[] data)、将 data 数组内容转换为字符串的 toString()方
      法以及用于反转 data 数组内容（即第一个元素和最后一个元素互换、第二个元素
      和倒数第二个元素互换…）的方法 void reverse()。
  */
    private int[] data;

    public ArrayReverser(){
        this.data = new int[]{};
    }

    public ArrayReverser(int[] data){
        if(data == null){
            this.data = new int[]{};
        } else {
            this.data = new int[data.length];
            for (int i = 0; i < data.length; i++) {
                this.data[i] = data[i];
            }
        }

    }
    public void setData(int[] data) {
        if (data == null)
            return;
        this.data = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = data[i];
        }
    }
    public int[] getData() {
        return data;   //地址赋值
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        System.out.print("data [" + data.length + "] = " );
        for (int i = 0; i < data.length; i++) {
            sb.append(data[i]).append(" ");
        }
        return sb.toString();
    }

    public void reverse(){
        int temp;
        for (int i = 0; i <= (data.length - 1)/ 2;i++){
            temp = data[i];
            data[i] = data[data.length - 1 - i];
            data[data.length - 1 - i] = temp;
        }
    }
}
