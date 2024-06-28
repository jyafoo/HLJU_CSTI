package hlju.rjgc;

/**
 * 产生式
 */
public class Production {

    char left;      //产生式左部，采用char型进行保存  LL(1)文法
    String right;       // 产生式右部
    String first;       // first集
    String follow;      // follow集
    String select;      // select集

    public Production(char left, String right) {
        this.left = left;
        this.right = right;
    }

    /**
     * 产生式右边
     */
    @Override
    public String toString() {
        return "->"+right;
    }

    /**
     * 输出完整产生式
     */
    public String print(){
        return left+"->"+right;
    }
}
