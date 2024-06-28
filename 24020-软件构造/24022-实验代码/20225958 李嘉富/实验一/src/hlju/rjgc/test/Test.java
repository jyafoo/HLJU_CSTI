package hlju.rjgc.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Test {

    // 定义状态转换矩阵，第一列为状态，第一行为字符（可以使用稀疏数组优化空间）
    public static final String[][] stateMatrix = new String[][] {
            {""  ,"i" ,"n" ,"t" , "f","m","a","c","h","r" , "e","u" , "+" , "-", "*", "/", "=",">" ,"," ,";" ,"(" ,")" ,"{" ,"}" },
            {"0" ,"1" , "" , "" , "" ,"8", "","4", "","13", "" , "" , "19","19","19","19","19","19","20","20","20","20","20","20"},
            {"1" ,""  ,"2" , "" ,"12","" , "","" , "", "" , "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"8" ,""  ,""  , "" , "" ,"" ,"9","" , "", "" , "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"4" ,""  ,""  , "" , "" ,"" ,"" ,"" ,"5", "" , "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"13",""  ,""  , "" , "" ,"" ,"" ,"" ,"" , "" ,"14", "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"19",""  ,""  , "" , "" ,"" ,"" ,"" ,"" , "" , "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"20",""  ,""  , "" , "" ,"" ,"" ,"" ,"" , "" , "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"2" ,""  ,""  ,"3" , "" ,"" ,"" ,"" ,"" , "" , "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"12",""  , "" , "" , "" ,"" ,"" ,"" ,"" , "" , "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"9" ,"10", "" , "" , "" ,"" ,"" ,"" ,"" , "" , "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"5" , "" , "" , "" , "" ,"" ,"6","" ,"" , "" , "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"14", "" , "" ,"15", "" ,"" ,"" ,"" ,"" , "" , "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"3" , "" , "" , "" , "" ,"" ,"" ,"" ,"" , "" , "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"10", "" ,"11", "" , "" ,"" ,"" ,"" ,"" , "" , "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"6" , "" , "" , "" , "" ,"" ,"" ,"" ,"" ,"7" , "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"15", "" , "" , "" , "" ,"" ,"" ,"" ,"" , "" , "" ,"16",  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"11", "" , "" , "" , "" ,"" ,"" ,"" ,"" , "" , "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"7" , "" , "" , "" , "" ,"" ,"" ,"" ,"" , "" , "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"16", "" , "" , "" , "" ,"" ,"" ,"" ,"" ,"17", "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"17", "" ,"18", "" , "" ,"" ,"" ,"" ,"" , "" , "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
            {"18", "" , "" , "" , "" ,"" ,"" ,"" ,"" , "" , "" , "" ,  "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" , "" },
    };
    // 初态
    public static final String initialState = "0";
    public static String currentState = "0";
    public static HashMap<String, String> map = new HashMap<>();
    // 标识符位置
    public static int index = 1;

    // 定义终态集合
    public static final String[] finalState = {"3","7","11","12","18","19","20"};

    /**
     * 获取接收字符后的状态
     */
    public static String getNextCurrentState(String accept, String currentState) {
        // 如果存在状态集中，则获取状态值
        if(isExist(accept)) {
            int currentStateRow = getCurrentStateRow(currentState);
            int currentStateCol = getCurrentStateCol(accept);
            if(currentStateRow != -1 && currentStateCol != -1) {
                return stateMatrix[currentStateRow][currentStateCol];
            }
        }
        return null;
    }

    /**
     * 判断是否存在状态集中
     */
    public static boolean isExist(String s) {
        for (int i = 1; i < stateMatrix[0].length; i++) {
            if(s.equals(stateMatrix[0][i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前状态的列
     */
    public static int getCurrentStateCol(String s) {
        // 如果存在状态集中，则获取状态值
        if(isExist(s)) {
            for (int i = 0; i < stateMatrix[0].length; i++) {
                if(s.equals(stateMatrix[0][i]) && !stateMatrix[0][i].equals("")) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 获取当前状态的行
     */
    public static int getCurrentStateRow(String s) {
        // 如果存在状态集中，则获取状态值
        for (int i = 1; i < stateMatrix.length; i++) {
            if(s.equals(stateMatrix[i][0])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 判断是否为终态
     */
    public static boolean isFinalState(String s) {
        for (int i = 0; i < finalState.length; i++) {
            if(s.equals(finalState[i])) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {

        //test();

        // 是否是标识符
        boolean flag = false;
        // 是否是数字
        boolean isShuZi = false;
        boolean end = false;

        // 创建一个文件字节输入流（从文件 -> 内存）
        FileInputStream fileInputStream = new FileInputStream("source.txt");
        ArrayList<String> list = new ArrayList<>();
        int read = 0;
        while((read = fileInputStream.read()) != -1) {
            list.add(((char) read + ""));
        }
        // 创建一个文件字节输出流（从内存 -> 文件）
        FileOutputStream outputStream = new FileOutputStream("target.txt");

        // 创建一个文件字节输出流（从内存 -> 文件）
        FileOutputStream outIdentiflier = new FileOutputStream("identifier.txt");

        // 用于存放文件内容
        String[] array = list.toArray(new String[list.size()]);

        // 遍历数组的当前指针
        int current = 0;
        // 存放字符串
        String token = "";
        // 遍历数组内容
        while(!(array[current].equals("@"))) {
            // 如果当前为空格、换行、回车，则继续循环
            if(array[current].equals(" ") || array[current].equals("\n") || array[current].equals("\r") || array[current].equals("\t")) {
                current++;
                continue;
            }
            // 1. 判断是否存在状态矩阵中
            if(isExist(array[current]) && !flag) {
                token += array[current];
                // 1.1 获取接收字符后的状态值
                String nextCurrentState = getNextCurrentState(array[current], currentState);
                currentState = nextCurrentState;
                // 单独检查是否为终态
                String nextCurrentState1 = getNextCurrentState(array[current], "0");
                if(isFinalState(nextCurrentState1) && token.length() > 1) {
                    if(!map.containsKey(token.substring(0,token.length()-1))) {
                        map.put(token.substring(0,token.length()-1), 1+ "：" + index);
                        outIdentiflier.write((index + "：").getBytes(StandardCharsets.UTF_8));
                        index++;
                        outIdentiflier.write(((token.substring(0,token.length()-1) +  "\n").getBytes(StandardCharsets.UTF_8)));
                        System.out.println("(" + "1：" + index + "： "+ token + ")");
                    }
                }
                // 如果查询状态并不是下一个状态
                if(nextCurrentState == null ||  nextCurrentState.equals("")) {
                    flag = true;
                    currentState ="0";
                    token = "";
                }
                if(nextCurrentState != null&& !nextCurrentState.equals("")) {
                    // 1.2 判断是否为终态
                    boolean finalState = isFinalState(nextCurrentState);
                    // a. 如果为终态
                    if(finalState) {
                        insert(token);
                        currentState = "0";
                        token = "";
                    }
                    current++;
                }
            }else {
                // 判断是数字 但是没用标识符状态
                while(Character.isDigit(array[current].charAt(0)) && !flag) {
                    token += array[current];
                    current++;
                    isShuZi = true;
                }
                if(isShuZi){
                    map.put(token,"2");
                    System.out.println("(" + "2：" +token + ")");
                    token = "";
                    isShuZi = false;
                }
                // 标识符：遇到非字符 or 数字停止
                if(Character.isAlphabetic(array[current].charAt(0)) || Character.isDigit(array[current].charAt(0))) {
                    token += array[current++];
                    // 设置状态标志
                    flag = true;
                    end = true;
                }else {
                    if(flag && end) {
                        // 解除标识符状态标志
                        if(!map.containsKey(token)) {
                            map.put(token,1+ "：" + index);
                            outIdentiflier.write((index+ "：").getBytes(StandardCharsets.UTF_8));
                            index++;
                            outIdentiflier.write((token + "\n").getBytes(StandardCharsets.UTF_8));
                            System.out.println("(" + "1：" + index + "："+token + ")");
                        }
                        flag = false;
                        end = false;
                        token = "";
                    }else {
                        flag = false;
                    }
                }
            }
        }
        // 遍历map集合
        map.forEach((key , value) ->{
            try {
                outputStream.write((value + "：").getBytes(StandardCharsets.UTF_8));
                outputStream.write((key + "\n").getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 1-关键字；2-标识符；3-运算符；4-常数；5-界符
     */
    public static void insert(String s) throws Exception {
        if(isKeywords(s)) {
            map.put(s,"3");
            System.out.println("(" + "3：" + s + ")");
        }else if(isOperator(s)) {
            map.put(s, "4");
            System.out.println("(" + "4：" + s + ")");
        }else if(isSeparator(s)) {
            map.put(s, "5");
            System.out.println("(" + "5：" + s + ")");
        }
    }

    /**
     * 判断是否为关键字
     */
    public static boolean isKeywords(String s) throws Exception {
        // 创建一个文件字节输入流（从文件 -> 内存）
        FileInputStream fileInputStream = new FileInputStream("keywords.txt");
        ArrayList<String> list = new ArrayList<>();
        int read = 0;
        String str = "";
        while((read = fileInputStream.read()) != -1) {
            str += (char)read;
        }
        // 用于存放文件内容
        String[] keyArray = str.split(",");
        for(int i=0 ; i< keyArray.length; i++){
            if(s.equals(keyArray[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前字符是否是运算符
     */
    public static boolean isOperator(String s) throws Exception {
        // 创建一个文件字节输入流（从文件 -> 内存）
        FileInputStream fileInputStream = new FileInputStream("operator.txt");
        ArrayList<String> list = new ArrayList<>();
        int read = 0;
        while((read = fileInputStream.read()) != -1) {
            list.add(((char) read + ""));
        }
        // 用于存放文件内容
        String[] operator = list.toArray(new String[list.size()]);
        for(int i=0 ; i< operator.length; i++){
            if(s.equals(operator[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前字符是否是分隔符
     */
    public static boolean isSeparator(String s) throws Exception {
        // 创建一个文件字节输入流（从文件 -> 内存）
        FileInputStream fileInputStream = new FileInputStream("separator.txt");
        ArrayList<String> list = new ArrayList<>();
        int read = 0;
        while((read = fileInputStream.read()) != -1) {
            list.add(((char) read + ""));
        }
        // 用于存放文件内容
        String[] separator = list.toArray(new String[list.size()]);
        for(int i=0 ; i< separator.length; i++){
            if(s.equals(separator[i])) {
                return true;
            }
        }
        return false;
    }


}
