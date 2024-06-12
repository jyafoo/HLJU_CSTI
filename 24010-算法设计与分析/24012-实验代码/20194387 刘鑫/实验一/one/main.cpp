
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define N 10010
#define SM_LEN 80
#define RE_LEN 14
#define DEL_LEN 12
#define OP_LEN 5

char s[N*10], t, str[N*10], tpre;
char reverseword[][10] = {"case", "char", "do", "else", "for", "if", "int", "include", "main", "printf", "return", "scanf", "then", "while"};
char opflag[] = {'+', '-', '*', '/', '='};
char jiefu[] = {',', ';', '.', '(', ')', '[', ']', '{', '}', '#', '<', '>'};
char id[N][50];
typedef struct two_tuple {
    char a[N][50];
    int len;
}two_tuple;
two_tuple tup;
typedef struct String {
    char a[50];
    int len;
}String;
String op, word, dig, wad, temp;
FILE *fp;
// 1 - 标识符      2 - 常数      3 - 保留字     4 - 运算符     5 - 界符
char statecol[SM_LEN + 2];
int statemarix[N][SM_LEN + 1], stateEnd[N], id_len, cnt;

void Init()
{
    if((fp = fopen("软件构造\\实验1\\状态矩阵.txt", "rb")) == NULL) {
        puts("状态矩阵打开失败!");
        exit(0);
    }
    for(int i = 0; !feof(fp); i ++) {
        cnt ++;
        for(int j = 0; j < SM_LEN; j ++ ) {
            fscanf(fp, "%d", &statemarix[i][j]);
        }
    }
    fclose(fp);
    if((fp = fopen("软件构造\\实验1\\状态列.txt", "rb")) == NULL) {
        puts("状态列打开失败!");
        exit(0);
    }
    for(int i = 0; i < SM_LEN; i ++) {
        fscanf(fp, "%c\n", &statecol[i]);
    }
    fclose(fp);
    if((fp = fopen("软件构造\\实验1\\终态.txt", "rb")) == NULL) {
        puts("关键字终态打开失败!");
        exit(0);
    }
    for(int i = 0; i < cnt; i ++) {
        fscanf(fp, "%d", &stateEnd[i]);
    }
    fclose(fp);
    if((fp = fopen("软件构造\\实验1\\标识符.txt", "rb")) != NULL) {
        for(int i = 0; !feof(fp); i ++ ) {
            fscanf(fp, "%s\n", id[id_len]);
            id_len ++;
        }
        fclose(fp);
    }
}
void saveFILE()
{
    if((fp = fopen("软件构造\\实验1\\状态矩阵.txt", "wb")) == NULL) {
        puts("状态矩阵存储失败!");
        exit(0);
    }
    for(int i = 0; i < cnt; i ++ ) {
        for(int j = 0; j < SM_LEN; j ++ ) {
            fprintf(fp, "%d\t", statemarix[i][j]);
        }
        fputc('\n', fp);
    }
    fclose(fp);
    if((fp = fopen("软件构造\\实验1\\状态列.txt", "wb")) == NULL) {
        puts("状态列存储失败!");
        exit(0);
    }
    for(int i = 0; i < SM_LEN; i ++ ) {
        fprintf(fp, "%c\n", statecol[i]);
    }
    fclose(fp);
    if((fp = fopen("软件构造\\实验1\\终态.txt", "wb")) == NULL) {
        puts("关键字终态存储失败!");
        exit(0);
    }
    for(int i = 0, j = 0; i < cnt; i ++, j ++ ) {
        if(j == 10) {
            fputs("\n", fp);
            j = 0;
        }
        fprintf(fp, "%d\t", stateEnd[i]);
    }
    fclose(fp);
    if((fp = fopen("软件构造\\实验1\\关键字.txt", "wb")) == NULL) {
        puts("关键字存储失败!");
        exit(0);
    }
    for(int i = 0; i < RE_LEN; i ++ ) {
        fputs(reverseword[i], fp);
        fputs("\n", fp);
    }
    fclose(fp);
}
void initinit()
{
    for(int i = 1; i < SM_LEN; i ++ ) {
        if(i <= 26) statecol[i] = 'a' + i - 1;
        else if(i <= 52)    statecol[i] = 'A' + i - 27;
        else if(i <= 62)    statecol[i] = '0' + i - 53;
        else if(i <= 67)   statecol[i] = opflag[i - 63];
        else    statecol[i] = jiefu[i - 68];
    }
    statecol[SM_LEN] = '@';
    for(int i = 0; i < 13; i ++ ) {
        int now = 0;
        for(int j = 0; reverseword[i][j]; j ++ ) {
            int x = 0;
            for(int k = 1; k < SM_LEN; k ++ ) {
                if(reverseword[i][j] == statecol[k]) {
                    x = k;
                    break;
                }
            }
            if(!statemarix[now][x]) {
                statemarix[now][x] = cnt + 1;
                cnt ++;
            }
            statemarix[cnt][0] = cnt;
            now = statemarix[now][x];
            for(int k = 63; k < SM_LEN; k ++ ) {
                statemarix[now][k] = -1;
            }
        }
        stateEnd[now] = 3;
    }
    cnt ++;
    for(int i = 53; i < 63; i ++ ) {
        statemarix[0][i] = cnt;
        stateEnd[cnt] = 2;
    }
    statemarix[cnt][0] = cnt;
    for(int i = 0; i < SM_LEN; i ++ ) {
        if(i >= 53 && i < 63)  statemarix[cnt][i] = cnt;
        else    statemarix[cnt][i] = -1;
    }
    cnt ++;
    for(int i = 63; i < SM_LEN; i ++ ) {
        statemarix[0][i] = cnt;
        statemarix[cnt][0] = cnt;
        if(i < 68)  stateEnd[cnt] = 4;
        else    stateEnd[cnt] = 5;
        cnt ++;
    }
    statemarix[cnt][0] = cnt;
    cnt ++;
    if((fp = fopen("软件构造\\实验1\\运算符.txt", "wb")) == NULL) {
        puts("运算符存储失败!");
        exit(0);
    }
    for(int i = 0; i < OP_LEN; i ++ ) {
        fprintf(fp, "%c\t", opflag[i]);
    }
    fclose(fp);
    if((fp = fopen("软件构造\\实验1\\界符.txt", "wb")) == NULL) {
        puts("界符存储失败!");
        exit(0);
    }
    for(int i = 0; i < DEL_LEN; i ++ ) {
        fprintf(fp, "%c\t", jiefu[i]);
    }
    fclose(fp);
    saveFILE();
    cnt = 0;
}
void CreateIden(String *identifier)
{
    tup.a[tup.len][0] = '(';
    tup.a[tup.len][1] = '1';
    tup.a[tup.len][2] = ',';
    tup.a[tup.len][3] = '\'';
    for(int i = 0; i < identifier->len; i ++ ) {
        tup.a[tup.len][i + 4] = identifier->a[i];
    }
    tup.a[tup.len][identifier->len + 4] = '\'';
    tup.a[tup.len][identifier->len + 5] = ')';
    tup.a[tup.len][identifier->len + 6] = '\0';
    tup.len ++;
    int flag = 0;
    for(int i = 0; i < id_len && !flag; i ++ ) {
        int k = 0;
        for(int j = 0; id[i][j]; j ++ ) {
            if(id[i][j] != identifier->a[k] || k == identifier->len) {
                break;
            }
            k ++;
        }
        if(k == identifier->len)    flag = 1;
    }
    if(!flag) {
        for(int i = 0; i < identifier->len; i ++ ) {
            id[id_len][i] = identifier->a[i];
        }
        id[id_len][identifier->len] = '\0';
        id_len ++;
    }
    identifier->len = 0;
}
void CreateDig(String *dig)
{
    tup.a[tup.len][0] = '(';
    tup.a[tup.len][1] = '2';
    tup.a[tup.len][2] = ',';
    tup.a[tup.len][3] = '\'';
    for(int i = 0; i < dig->len; i ++ ) {
        tup.a[tup.len][i + 4] = dig->a[i];
    }
    tup.a[tup.len][dig->len + 4] = '\'';
    tup.a[tup.len][dig->len + 5] = ')';
    tup.a[tup.len][dig->len + 6] = '\0';
    tup.len ++;
    dig->len = 0;
}
void CreateReword(String *word)
{
    tup.a[tup.len][0] = '(';
    tup.a[tup.len][1] = '3';
    tup.a[tup.len][2] = ',';
    tup.a[tup.len][3] = '\'';
    for(int i = 0; i < word->len; i ++ ) {
        tup.a[tup.len][i + 4] = word->a[i];
    }
    tup.a[tup.len][word->len + 4] = '\'';
    tup.a[tup.len][word->len + 5] = ')';
    tup.a[tup.len][word->len + 6] = '\0';
    tup.len ++;
    word->len = 0;
}
void CreateOP(String *op)
{
    tup.a[tup.len][0] = '(';
    tup.a[tup.len][1] = '4';
    tup.a[tup.len][2] = ',';
    tup.a[tup.len][3] = '\'';
    for(int i = 0; i < op->len; i ++ ) {
        tup.a[tup.len][i + 4] = op->a[i];
    }
    tup.a[tup.len][op->len + 4] = '\'';
    tup.a[tup.len][op->len + 5] = ')';
    tup.a[tup.len][op->len + 6] = '\0';
    tup.len ++;
    op->len = 0;
}
void CreateDel(String *temp)
{
    tup.a[tup.len][0] = '(';
    tup.a[tup.len][1] = '5';
    tup.a[tup.len][2] = ',';
    tup.a[tup.len][3] = '\'';
    tup.a[tup.len][4] = temp->a[0];
    tup.a[tup.len][5] = '\'';
    tup.a[tup.len][6] = ')';
    tup.a[tup.len][7] = '\0';
    tup.len ++;
    temp->len = 0;
}
void InputFile(FILE *fp)
{
    if((fp = fopen("软件构造\\实验1\\shuru.txt", "wb")) == NULL) {
            printf("文件打开失败!\n");
            exit(0);
    }
    printf("输入:\n");
    int len = 0, flag = 0;
    do {
        tpre = t;
        scanf("%c", &t);
        if(t == '/' && '/' == tpre) {
            flag = 1;
            len --;
        }
        if(t == '\n')   flag = 0;
        if(flag || t == ' ' || t == '\n')        continue;
        str[len ++] = t;
    }while(t != '@');
    str[len ++] = '\0';
    for(int i = 0; i < len; i ++ ) {
        fputc(str[i], fp);
    }
    fclose(fp);
}
void ReadFile(FILE *fp, int *len)
{
    if((fp = fopen("软件构造\\实验1\\shuru.txt", "rb")) == NULL) {
        printf("文件读取失败!\n");
        exit(0);
    }
    char ch;
    while((ch = fgetc(fp)) != EOF) {
        s[*len] = ch;
        *len += 1;
    }
    s[(*len) ++] = '\0';
//    fread(s, sizeof(char)*N, 1, fp);
    fclose(fp);

}

void Output()
{
    for(int i = 0; i < tup.len; i ++ ) {
        printf("%d: %s\n", i + 1, tup.a[i]);
    }
}
void Work(int len)
{
    int now = 0;
    for(int i = 0; i < len; i ++ ) {
        int x = 0;
        for(int j = 1; j <= SM_LEN; j ++ ) {
            if(s[i] == statecol[j]) {
                x = j;
                break;
            }
        }
        if(x == SM_LEN)     break;
        if(!x || statemarix[now][x] < 0) {
            if(stateEnd[now] == 2) {
                CreateDig(&temp);
            }
            else if(stateEnd[now] == 1) {
                CreateIden(&temp);
            }
            if(!x)      printf("第%d个字符%c不合法\n", i, s[i]);
            else    i --;
            now = 0;
        }
        else if(statemarix[now][x]) {
            temp.a[temp.len ++] = s[i];
            now = statemarix[now][x];
            if(stateEnd[now] == 3) {
                CreateReword(&temp);
                now = 0;
            }
            else if(stateEnd[now] == 4) {
                CreateOP(&temp);
                now = 0;
            }
            else if(stateEnd[now] == 5) {
                CreateDel(&temp);
                now = 0;
            }
            else if(!stateEnd[now]) stateEnd[now] = 1;
        }
        else {
            temp.a[temp.len ++] = s[i];
            statemarix[now][x] = cnt + 1;
            cnt ++;
            statemarix[cnt][0] = cnt;
            now = statemarix[now][x];
            for(int j = 63; j < SM_LEN; j ++ ) {
                statemarix[cnt][j] = -1;
            }
            if(!stateEnd[now])  stateEnd[now] = 1;
        }
    }
    if((fp = fopen("软件构造\\实验1\\标识符.txt", "wb")) == NULL) {
        puts("标识符存储失败!");
        exit(0);
    }
    for(int i = 0; i < id_len; i ++ ) {
        fprintf(fp, "%s\n", id[i]);
    }
    fclose(fp);
    saveFILE();
}

int main()
{
    int len = 0;
    initinit();
    Init();
    InputFile(fp);
    ReadFile(fp, &len);
    Work(len);
    Output();
    return 0;
}

/*      输入样例        */
//#include <stdio.h>
//int main()
//{
//        int ab = 0, cd = 1, xy = 2;
//        for(int i = 0; i < n; i ++ ) {
//                ab = cd;
//                cd = xy;
//                xy = ab + cd;
//        }
//        if(!n)  printf("0\n");
//        else if(n == 1) printf("1\n");
//        else if(n == 2) printf("2\n");
//        else    printf("%d\n", ab);
//        return 0;
//}@
