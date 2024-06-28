package hlju.rjgc.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {


    public static void main(String[] args) throws Exception {
        // 创建一个文件字节输入流（从文件 -> 内存）
        FileInputStream fileInputStream = new FileInputStream("source.txt");
        ArrayList<String> list = new ArrayList<>();
        int read = 0;
        while ((read = fileInputStream.read()) != -1) {
            list.add(((char) read + ""));
        }
        // 创建一个文件字节输出流（从内存 -> 文件）
        FileOutputStream outputStream = new FileOutputStream("target.txt");


        // 用于存放文件内容
        String[] array = list.toArray(new String[list.size()]);
        HashMap<String, Integer> map = new HashMap<>();


        int current = 0;
        String token = "";
        // 遍历数组内容
        while (!(array[current].equals("@"))) {
            // 如果当前为空格、换行、回车，则继续循环
            if (array[current].equals(" ") || array[current].equals("\n") || array[current].equals("\r") || array[current].equals("\t")) {
                current++;
                continue;
            }

            // 1. 判断是否为字母
            if (Character.isAlphabetic(array[current].charAt(0))) {
                token += array[current++];
                // 1.1 判断是否为字母或数字（有可能是关键字 | 标识符）
                while (Character.isAlphabetic(array[current].charAt(0)) || Character.isDigit(array[current].charAt(0))) {
                    token += array[current++];
                    // a. 判断是否为关键字
                    if (isKeywords(token)) {
                        map.put(token, 1);
                        token = "";
                        break;
                    }
                }
                // 1.2 不是关键字 肯定就是 标识符
                if (!token.equals("")) {
                    map.put(token, 2);
                    token = "";
                }
            }
            // 2. 判断是否为数字
            else if (Character.isDigit(array[current].charAt(0))) {
                token += array[current++];
                // 2.1 数字一直累加字符串，直到不为数字终止
                while (Character.isDigit(array[current].charAt(0))) {
                    token += array[current++];
                }
                map.put(token, 4);
                token = "";
            }
            // 3. 判断是否为运算符
            else if (isOperator(array[current]) || array[current].equals("!")) {
                token += array[current++];
                while (isOperator(array[current])) {
                    token += array[current++];
                }
                map.put(token, 3);
                token = "";
            }
            // 4. 判断是否为届符
            else if (isSeparator(array[current])) {
                token += array[current++];
                // 4.1 届符都是单独出现
                /*while (isSeparator(array[current])) {
                    map.put(token, 5);
                    token = "";
                    token += array[current++];
                }*/
                map.put(token, 5);
                token = "";
            }
        }

        // 遍历map集合
        map.forEach((key, value) -> {
            System.out.println(value + "：" + key);
            try {
                outputStream.write((value + "：").getBytes(StandardCharsets.UTF_8));
                outputStream.write((key + "\n").getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    static Set<String> keySet = new HashSet<>(
            Arrays.asList(
                    "auto", "break", "case", "char",
                    "const", "continue", "default", "do", "double",
                    "else", "enum", "extern", "float", "for", "goto",
                    "if", "int", "long", "include", "return", "short",
                    "signed", "sizeof", "static", "then", "switch",
                    "typedef", "printf", "unsigned", "void", "scanf", "while", "main"
            )
    );


    /**
     * 判断是否为关键字
     */
    public static boolean isKeywords(String s) throws IOException {
        if(!keySet.contains(s)){
            return false;
        }

        // 与当前字符串一一对比
        return true;
    }

    /**
     * 判断当前字符是否是运算符
     */
    public static boolean isOperator(String s) {
        if ("+".equals(s) || "-".equals(s) || "*".equals(s) || "/".equals(s) || "=".equals(s) || ">".equals(s) || "<".equals(s)/* || "<=".equals(s) || ">=".equals(s) || "!=".equals(s)*/) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前字符是否是分隔符
     */
    public static boolean isSeparator(String s) {
        if (",".equals(s) || ";".equals(s) || "{".equals(s) || "}".equals(s) || "(".equals(s) || ")".equals(s) || ".".equals(s) || "[".equals(s) || "]".equals(s)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否为字母
     */
    public static boolean isAlphabet(String s) {
        char c = s.charAt(0);
        if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
            return true;
        }
        return false;
    }


}





















