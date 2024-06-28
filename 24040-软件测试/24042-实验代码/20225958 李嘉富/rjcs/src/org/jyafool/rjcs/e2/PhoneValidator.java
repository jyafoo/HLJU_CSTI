package org.jyafool.rjcs.e2;

import java.util.Scanner;

public class PhoneValidator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入电话号码：");
        String phoneNumber = scanner.nextLine();
        String errorMessage = validatePhoneNumber(phoneNumber);
        if (errorMessage != null) {
            System.out.println("电话号码不合法：" + errorMessage);
        } else {
            System.out.println("电话号码合法");
        }
    }

    public static String validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return "电话号码不能为空";
        }

        String[] parts = phoneNumber.split("\\s+");

        if (parts.length != 3) {
            return "电话号码必须由三部分构成";
        }

        String areaCode = parts[0];
        String prefix = parts[1];
        String suffix = parts[2];

        if (areaCode.isEmpty() || areaCode.length() != 3) {
            return "地区码必须为空白或三位数字";
        }

        if (prefix.isEmpty() || prefix.length() != 3 || prefix.startsWith("0") || prefix.startsWith("1")) {
            return "前缀码必须为非\"0\"或\"1\"开头的三位数字";
        }

        if (suffix.isEmpty() || suffix.length() != 4 || !suffix.matches("\\d+")) {
            return "后缀码必须为四位数字";
        }

        return null;
    }

}
