package hlju.rjgc;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test2 {
    List<Production> productionlist = new ArrayList<>(); // 产生式集合
    String nonTerminator = ""; // 终结符
    String terminator = ""; // 非终结符
    int[][] analyticalStatement; // 预测分析表，用二维数组存储
    String analysisStack = "";// 分析栈

    List<String> firstList = new ArrayList<>();// first集合
    List<String> followList = new ArrayList<>();// follow集合
    List<String> selectList = new ArrayList<>();// select集合

    /**
     * 跳过文件形式，直接在代码中初始化产生式
     */
    void init() {
        // 初始化产生式
        productionlist.add(new Production('E', "TA"));
        productionlist.add(new Production('A', "+TA"));
        productionlist.add(new Production('A', ""));
        productionlist.add(new Production('T', "FB"));
        productionlist.add(new Production('B', "*FB"));
        productionlist.add(new Production('B', ""));
        productionlist.add(new Production('F', "i"));
        productionlist.add(new Production('F', "(E)"));
        // 初始化分析栈
        analysisStack = mergeAndRepet(analysisStack, "#");
        analysisStack = mergeAndRepet(analysisStack, String.valueOf(productionlist.get(0).left));

        // 初始化产生式的SELECT集合，查询产生式中的终结符和非终结符 整理follow first
        for (Production production : productionlist) {
            production.first = first(production);
            production.follow = follow(production);
            production.select = select(production);

            selectList.add("Select(" + production.print() + ") = {" + production.select + "}");
            if (!production.first.contains("#")) {
                firstList.add("First(" + production.left + ") = {" + production.first + "}");
            }
            followList.add("Follow(" + production.left + ") = {" + production.follow + "}");
            nonTerminator = mergeAndRepet(nonTerminator, String.valueOf(production.left));// 所有的终结符
            terminator = mergeAndRepet(terminator, production.select);// 所有的非终结符
        }


        // 创建预测分析表
        analyticalStatement = new int[nonTerminator.length()][terminator.length()];
        for (int i = 0; i < nonTerminator.length(); i++) {
            for (int j = 0; j < terminator.length(); j++) {
                boolean flag = false;
                int o;
                for (o = 0; o < productionlist.size(); o++) {     // E->TA
                    if (productionlist.get(o).left == nonTerminator.charAt(i) && productionlist.get(o).select.contains(String.valueOf(terminator.charAt(j)))) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    analyticalStatement[i][j] = o;
                } else {
                    analyticalStatement[i][j] = -1;
                }
            }
        }
    }

    /**
     * 输出预测分析表
     */
    void outputAnalyticalStatement() {
        // 打印终结符
        for (int i = 0; i < terminator.length(); i++) {
            if (i == 0) {
                System.out.print("      " + terminator.charAt(i) + "        ");
            } else {
                System.out.print(terminator.charAt(i) + "        ");
            }
        }
        System.out.println();

        // 打印非终结符
        for (int i = 0; i < nonTerminator.length(); i++) {
            System.out.print(nonTerminator.charAt(i) + "      ");
            for (int j = 0; j < terminator.length(); j++) {
                if (analyticalStatement[i][j] > -1) {
                    System.out.print(productionlist.get(analyticalStatement[i][j]).toString() + "      ");
                } else {
                    System.out.print("0        ");
                }
            }
            System.out.println();
        }
    }

    /**
     * 查找产生式中左部与c相同的产生式
     */
    List<Production> FindProductionInLeft(char c) { // 为了找first集合
        List<Production> ps = new ArrayList<>();
        Production p;
        for (Production production : productionlist) {
            p = production;
            if (p.left == c) {
                ps.add(p);
            }
        }
        return ps;
    }

    /**
     * 查找产生式中右部与c相同的产生式
     */
    List<Production> FindProductionInRight(char c) {// follow集
        List<Production> ps = new ArrayList<>();
        Production p;
        for (Production production : productionlist) {
            p = production;
            if (p.right.contains(String.valueOf(c))) {
                ps.add(p);
            }
        }
        return ps;
    }

    /**
     * 将S2合并到S1中，并去重
     */
    String mergeAndRepet(String s1, String s2) {// 123  234
        StringBuilder s = new StringBuilder(s1);
        for (int i = 0; i < s2.length(); i++) {
            if (!s1.contains(String.valueOf(s2.charAt(i)))) {
                s.insert(s.length(), s2.charAt(i));// 在s末尾加上s2中扫描到的字母
            }
        }
        return s.toString();
    }

    /**
     * 将S2合并到S1中
     */
    String merge(String s1, String s2) {// 合并 123 234  123234
        StringBuilder s = new StringBuilder(s1);
        for (int i = 0; i < s2.length(); i++) {
            s.insert(s.length(), s2.charAt(i));
        }
        return s.toString();
    }

    /**
     * 查询产生式的FIRST集合
     */
    String first(Production p) {
        if (!p.right.equals("")) {
            if (p.right.charAt(0) < 'A' || p.right.charAt(0) > 'Z') {
                return String.valueOf(p.right.charAt(0));
            } else {
                String s = "";  // E -> TA
                // T -> ...
                List<Production> p1 = FindProductionInLeft(p.right.charAt(0));
                for (int i = 0; i < p1.size(); i++) {
                    s = mergeAndRepet(s, first(p1.get(i)));
                }
                return s;
            }
        } else {
            return follow(p);
        }
    }

    /**
     * 查询产生式的FOLLOW集合
     */
    String follow(Production p) {
        String s = ""; // E->TA
        // F->Ea  || F->aE follow(F) || E->aE
        List<Production> ps = FindProductionInRight(p.left);    // 找到左对应的右部分

        if (p.left == productionlist.get(0).left) {
            s = mergeAndRepet(s, "#");   // 如果产生式的左部和开始符E相同，把#也加到s中
        }

        if (ps != null && !ps.isEmpty()) {
            for (int i = 0; i < ps.size(); i++) {
                if (p.left == ps.get(i).right.charAt(ps.get(i).right.length() - 1)) {// if左部的可以在产生式的长度-1中找到 说明是最后一个字符 F->aE follow(F) || E->aE
                    if (p.left != ps.get(i).left) {       // F->aE 左右不相等 求Follow(F)
                        s = mergeAndRepet(s, follow(ps.get(i)));     // 合并
                    }
                } else {
                    int j;
                    for (j = 0; j < ps.get(i).right.length(); j++) {        // 找到规约的
                        if (p.left == ps.get(i).right.charAt(j)) {
                            break;
                        }
                    }

                    if (ps.get(i).right.charAt(j + 1) < 'A' || ps.get(i).right.charAt(j + 1) > 'Z') {
                        s = mergeAndRepet(s, String.valueOf(ps.get(i).right.charAt(j + 1)));
                    } else {         // 找其下一个的first集
                        List<Production> pss = FindProductionInLeft(ps.get(i).right.charAt(j + 1)); // F->ET T->...
                        for (Production production : pss) {
                            s = mergeAndRepet(s, first(production));
                        }
                    }
                }
            }
        }

        return s;
    }

    /**
     * 查询产生式的SELECT集合
     */
    String select(Production p) {
        if (p.right.equals("")) {
            return follow(p);
        } else {
            return first(p);
        }
    }

    /**
     * 控制输出格式
     */
    String ss1(int steps) {
        if (steps < 10) {
            return "    ";
        } else {
            return "   ";
        }
    }

    /**
     * 控制输出格式
     */
    String ss(String s) {
        int a = 15;
        String ss = "";
        String space = " ";
        for (int i = 0; i < a - s.length(); i++) {
            ss = merge(ss, space);
        }
        return ss;
    }
    /**
     * 录入并分析表达式
     */
    void AnalysisProcedure() {
        Scanner scanner = new Scanner(System.in);

        String string = scanner.nextLine();             // 录入要分析的表达式
        string = merge(string, "#");
        int steps = 1;
        while (true) {
            if (string == null || string.isEmpty()) {
                System.out.println(steps + ss1(steps) + " " + analysisStack + ss(analysisStack) + " " + ss(string) + string + "   " + "不接受");
                break;
            }
            if (analysisStack.charAt(analysisStack.length() - 1) < 'A' || analysisStack.charAt(analysisStack.length() - 1) > 'Z') { // 匹配终结符的情况
                if (analysisStack.charAt(analysisStack.length() - 1) == string.charAt(0)) {  // 找字符串第一个和栈中的最后一个字符相等，
                    if (string.charAt(0) == '#') {      // 结束条件
                        System.out.println(steps + ss1(steps) + " " + analysisStack + ss(analysisStack) + " " + ss(string) + string + "    " + "接受");
                        break;
                    }
                    System.out.println(steps + ss1(steps) + " " + analysisStack + ss(analysisStack) + " " + ss(string) + string + "   " + string.charAt(0) + "匹配");
                    analysisStack = analysisStack.substring(0, analysisStack.length() - 1); // 删减操作
                    string = string.substring(1);   // 返回他的子串
                } else {
                    System.out.println(steps + ss1(steps) + " " + analysisStack + ss(analysisStack) + " " + ss(string) + string + "   " + "不接受");
                    break;
                }
            } else { // 匹配表达式的情况

                // 找表格
                int i, j;
                for (i = 0; i < nonTerminator.length(); i++) { // 找到非终结符下标位置
                    if (nonTerminator.charAt(i) == analysisStack.charAt(analysisStack.length() - 1)) {
                        break;
                    }
                }
                for (j = 0; j < terminator.length(); j++) {  // 找到终结符下标位置
                    if (terminator.charAt(j) == string.charAt(0)) {
                        break;
                    }
                }

                if (analyticalStatement[i][j] != -1) {
                    Production p = productionlist.get(analyticalStatement[i][j]);
                    if (!p.right.equals("")) {
                        System.out.println(steps + ss1(steps) + " " + analysisStack + ss(analysisStack) + " " + ss(string) + string + "   " + p.left + "->" + p.right);
                        analysisStack = analysisStack.substring(0, analysisStack.length() - 1);
                        // 将产生式的右部倒着加入到分析栈中
                        for (int o = p.right.length() - 1; o >= 0; o--) {
                            analysisStack = merge(analysisStack, String.valueOf(p.right.charAt(o)));
                        }
                    } else { // 推出""情况
                        System.out.println(steps + ss1(steps) + " " + analysisStack + ss(analysisStack) + " " + ss(string) + string + "   " + p.left + "->" + p.right);
                        analysisStack = analysisStack.substring(0, analysisStack.length() - 1);
                    }
                } else {
                    System.out.println(steps + ss1(steps) + " " + analysisStack + ss(analysisStack) + " " + ss(string) + string + "   " + "不接受");
                    break;
                }
            }
            steps++;
        }
    }

    public static void main(String[] args) {
        Test2 test3 = new Test2();
        test3.init();
        System.out.println("其中表达式文法为");
        for (Production p : test3.productionlist) {
            System.out.println(p.print());
        }
        System.out.println("first集合");
        for (String s1 : test3.firstList) {
            System.out.println(s1);
        }
        System.out.println("followList集合");
        for (String s1 : test3.followList) {
            System.out.println(s1);
        }
        System.out.println("SelectList集合");
        for (String s1 : test3.selectList) {
            System.out.println(s1);
        }
        System.out.println("预测分析表为");
        test3.outputAnalyticalStatement();
        System.out.println("请输入你要分析的表达式");
        test3.AnalysisProcedure();
    }

}
