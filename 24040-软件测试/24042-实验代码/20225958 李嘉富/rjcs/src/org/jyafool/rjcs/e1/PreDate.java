package org.jyafool.rjcs.e1;

import java.util.Scanner;

public class PreDate {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入日期(格式为：yyyyMMdd)：");
        String inputDate = scanner.nextLine();
        String preDate = getPreDate(inputDate);
        System.out.println("前一天日期为：" + preDate);
    }

    public static String getPreDate(String inputDate) {
        int year = Integer.parseInt(inputDate.substring(0, 4));
        int month = Integer.parseInt(inputDate.substring(4, 6));
        int day = Integer.parseInt(inputDate.substring(6, 8));

        if (!isValidDate(year, month, day)) {
            return "无效日期";
        }

        if (day > 1) {
            day--;
        } else {
            if (month == 1) {
                year--;
                month = 12;
            } else {
                month--;
            }
            day = getMonthDays(year, month);
        }

        return String.format("%04d%02d%02d", year, month, day);
    }

    private static boolean isValidDate(int year, int month, int day) {
        if (year < 1000 || year > 2020 || month < 1 || month > 12) {
            return false;
        }

        int maxDay = getMonthDays(year, month);
        if (day < 1 || day > maxDay) {
            return false;
        }

        return true;
    }

    private static int getMonthDays(int year, int month) {
        int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            daysOfMonth[1] = 29; // 闰年2月有29天
        }
        return daysOfMonth[month - 1];
    }

}
