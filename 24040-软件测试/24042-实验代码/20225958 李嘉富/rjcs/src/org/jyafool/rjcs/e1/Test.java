package org.jyafool.rjcs.e1;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        while(true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入日期(格式为：yyyyMMdd)：");
            String inputDate = scanner.nextLine();
            String preDate = getPreDate(inputDate);
            System.out.println("前一天日期为：" + preDate);
        }
    }


    // 判断输入日期是否合法
    private static boolean isValidDate(int year, int month, int day) {
        if (year >= 1000 && year <= 2020) {
            if(month >= 1 && month <= 12) {
                if(day >= 1 && day <= 31) {
                    return true;
                }
            }
        }
        return false;
    }

    // 判断是否为闰年
    public static boolean leapYear(int year) {
        if((year % 4 == 0 && year%100 != 0) || (year%400 == 0)) {
            return true;
        }
        return false;
    }

    // 获取前一天
    public static String getPreDate(String inputDate) {
        int year = Integer.parseInt(inputDate.substring(0, 4));
        int month = Integer.parseInt(inputDate.substring(4, 6));
        int day = Integer.parseInt(inputDate.substring(6, 8));
        if(isValidDate(year, month, day)) {
            boolean isLeapYear = leapYear(year);
            if(day == 1) {
                if(month == 1) {
                    // 2020/01/01
                    year = year - 1;
                    month = 12;
                    day = 31;
                }else if(month == 2 || month == 4 || month == 6 || month == 8 || month == 9 || month == 11) {
                    // 2020/08/01
                    month = month - 1;
                    day = 31;
                }else if(month == 5 || month == 7 || month == 10 || month == 12) {
                    // 2020/07/01
                    month = month - 1;
                    day = 30;
                }else {
                    // 如果是闰年
                    if(isLeapYear) {
                        month = month - 1;
                        day = 29;
                    }else {
                        // 不是闰年
                        month = month - 1;
                        day = 28;
                    }
                }
            }else {
                day = day - 1;
            }
            return String.format("%04d%02d%02d", year, month, day);
        }else {
            System.out.println("日期不合法");
            return "";
        }
    }
}
