package org.jyafool.rjcs.e3;

import java.util.Scanner;

public class RMBConverter {
    private static final String[] NUMBERS = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static final String[] UNITS = {"元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入金额（12位以内，小数点后2位）：");
        double money = scanner.nextDouble();

        String chinese = convertToChinese(money);
        System.out.println("人民币" + chinese);
    }

    private static String convertToChinese(double money) {
        if (money < 0 || money > 999999999999.99) {
            throw new IllegalArgumentException("金额超出计算范围");
        }

        String result = "";

        // 处理整数部分
        long integerPart = (long) money;
        int index = 0; // 当前处理的数字位数
        boolean lastIsZero = false; // 上一位是否是零
        while (integerPart > 0) {
            int num = (int) (integerPart % 10);
            if (num == 0) {
                if (!lastIsZero && index != 0 && index != 4) {
                    result = NUMBERS[num] + result;
                    lastIsZero = true;
                }
            } else {
                result = NUMBERS[num] + UNITS[index % 9] + result;
                lastIsZero = false;
            }
            index++;
            integerPart /= 10;
        }
        if (result.length() > 0 && result.charAt(0) == '壹' && result.length() > 1) {
            result = result.substring(1);
        }
        result += "元";

        // 处理小数部分
        int decimalPart = (int) Math.round((money - (long) money) * 100);
        if (decimalPart == 0) {
            result += "整";
        } else {
            int jiao = decimalPart / 10;
            int fen = decimalPart % 10;
            if (jiao != 0) {
                result += NUMBERS[jiao] + "角";
            }
            if (fen != 0) {
                result += NUMBERS[fen] + "分";
            }
        }

        return result;
    }
}
