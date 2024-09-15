#include <iostream>
#include <fstream>
#include <cstring>
#include <cmath>
#include <sys/stat.h>

#define INF 0x3f3f3f3f

using namespace std;

typedef struct
{
    int weight;
    int parent, lchild, rchild;
} HTNode, *HuffmanTree;

typedef char **HuffmanCode;

typedef int *Array;

size_t GetFileSize(const char *fileName)
{
    if (fileName == nullptr)
        return 0;
    struct stat statbuf;
    stat(fileName, &statbuf);
    size_t fileSize = statbuf.st_size;
    return fileSize;
}

void StringCompressing(string &src)
{
    string res = "\0";
    string tmp = "\0";
    int num = 0, cnt = 0;
    for (int i = 0; i < src.size(); i++)
    {
        tmp += src[i];
        cnt++;
        if (cnt == 8)
        {
            for (int j = tmp.size() - 1; j >= 0; j--)
                num += (tmp[j] - '0') * pow(2, tmp.size() - 1 - j);
            res += (char)num;
            tmp = "\0";
            num = cnt = 0;
        }
        if (src.size() - i < 8 && cnt == 1)
            res += src[i];
    }
    src = (char)(cnt + '0') + res;
}

void ChooseFile(ifstream &fin)
{
    string file;
    cout << "请输入想要打开的文件路径: ";
    cin >> file;
    fin.open(file);
    if (fin.is_open() == false)
    {
        cout << "打开文件失败！" << endl;
        return;
    }
    cout << "打开文件: " << file << "完成！" << endl;
}

void Select(HuffmanTree HT, int i, int &s1, int &s2)
{
    int sm1 = INF;
    int sm2 = INF;
    s1 = s2 = 0;
    for (int j = 1; j <= i; j++)
    {
        if (HT[j].parent == 0)
        {
            if (HT[j].weight < sm1)
            {
                sm2 = sm1;
                sm1 = HT[j].weight;
                s2 = s1;
                s1 = j;
            }
            else if (HT[j].weight < sm2)
            {
                sm2 = HT[j].weight;
                s2 = j;
            }
        }
    }
}

void CreateHuffmanTree(HuffmanTree &HT, ifstream &fin, int &n, Array &map)
{
    int m;
    int s1, s2;
    int cnt[300] = {0};
    char buffer;
    n = 0;
    fin >> noskipws;
    while (fin >> buffer)
    {
        if (!cnt[(int)buffer])
            n++;
        cnt[(int)buffer]++;
    }
    fin.clear();
    fin.seekg(ios::beg);
    if (n <= 1)
        return;
    map = new int[n];
    m = 2 * n - 1;
    HT = new HTNode[m + 1];
    for (int i = 1; i <= m; i++)
    {
        HT[i].parent = 0;
        HT[i].lchild = 0;
        HT[i].rchild = 0;
    }
    for (int i = 1; i <= n; i++)
    {
        for (int j = 0; j < 300; j++)
        {
            if (cnt[j])
            {
                map[i] = j;
                HT[i].weight = cnt[j];
                cnt[j] = 0;
                break;
            }
        }
    }
    for (int i = n + 1; i <= m; i++)
    {
        Select(HT, i - 1, s1, s2);
        HT[s1].parent = i;
        HT[s2].parent = i;
        HT[i].lchild = s1;
        HT[i].rchild = s2;
        HT[i].weight = HT[s1].weight + HT[s2].weight;
    }
    cout << "建立哈夫曼树完成！" << endl;
    cout << "i: 编号"
         << ", w: 权值"
         << ", p: 双亲节点"
         << ", l: 左子节点"
         << ", r: 右字节点" << endl;
    for (int i = 1; i <= m; i++)
    {
        cout << "i: " << i
             << ", w: " << HT[i].weight
             << ", p: " << HT[i].parent
             << ", l: " << HT[i].lchild
             << ", r: " << HT[i].rchild << endl;
    }
}

void CreatHuffmanCode(HuffmanTree HT, HuffmanCode &HC, int n, Array map, ifstream &fin)
{
    ofstream fout;
    string output = "\0";
    char buffer;
    char *cd = new char[n];
    cd[n - 1] = '\0';
    HC = new char *[n + 1];
    for (int i = 1; i <= n; i++)
    {
        int start = n - 1;
        int c = i;
        int f = HT[i].parent;
        while (f != 0)
        {
            start--;
            if (HT[f].lchild == c)
                cd[start] = '0';
            else
                cd[start] = '1';
            c = f;
            f = HT[f].parent;
        }
        HC[i] = new char[n - start];
        strcpy(HC[i], &cd[start]);
    }
    delete cd;
    cout << "密码本如下:" << endl;
    for (int i = 1; i <= n; i++)
        cout << (char)map[i] << ": " << HC[i] << endl;
    fout.open("D:/Bag/password.cod", ios::binary);
    if (fout.is_open() == false)
    {
        cout << "打开文件失败！" << endl;
        return;
    }
    fin >> noskipws;
    while (fin >> buffer)
    {
        for (int i = 1; i <= n; i++)
        {
            if (map[i] == buffer)
                output += HC[i];
        }
    }
    StringCompressing(output);
    fout << output;
    cout << "生成文件" << "D:/Bag/password.cod" << "完成！" << endl;
    fin.close();
    fout.close();
}

void DecodeHuffmanCode(HuffmanTree HT, int n, Array map)
{
    ifstream fin;
    ofstream fout;
    char input[100];
    string output = "\0";
    int m = n * 2 - 1;
    int c = m;
    string buffer;
    string comSrc = "\0";
    string src = "\0";
    string tmp;
    size_t s1, s2;
    int num, tail;
    cout << "请输入想要解码的文件路径: ";
    cin >> input;
    fin.open(input);
    fout.open("D:/Bag/dec.txt", ios::binary);
    if (fin.is_open() == false || fout.is_open() == false)
    {
        cout << "打开文件失败！" << endl;
        return;
    }
    while (getline(fin, buffer))
        comSrc += buffer;
    tail = comSrc[0] - '0';
    for (int i = 1; i < comSrc.size(); i++)
    {
        if (i < comSrc.size() - tail)
        {
            tmp = "\0";
            num = (unsigned char)comSrc[i];
            while (num)
            {
                tmp += num % 2 + '0';
                num /= 2;
            }
            while (tmp.size() < 8)
                tmp += "0";
            for (int j = tmp.size() - 1; j >= 0; j--)
                src += tmp[j];
        }
        else
            src += comSrc[i];
    }
    for (int i = 0; i < src.size(); i++)
    {
        if (src[i] == '0')
            c = HT[c].lchild;
        else
            c = HT[c].rchild;
        if (HT[c].lchild == 0 && HT[c].rchild == 0)
        {
            output += (char)map[c];
            c = m;
        }
    }
    fout << output;
    cout << "生成文件"
         << "D:/Bag/dec.txt"
         << "完成！" << endl;
    fin.close();
    fout.close();
    s1 = GetFileSize(input);
    s2 = GetFileSize("D:/Bag/dec.txt");
    cout << "译码前文件大小为: " << s1 << "字节" << endl;
    cout << "译码后文件大小为: " << s2 << "字节" << endl;
    cout << "压缩比为: " << (double)s1 / s2 * 100 << '%' << endl;
}

int main()
{
    ifstream fin;
    HuffmanTree HT;
    HuffmanCode HC;
    int n;
    Array map;
    int flag;
    while (1)
    {
        cout << "*****************************************************" << endl
             << "*                 哈夫曼编码译码器                  *" << endl
             << "*   1. 选择需要进行编码的文件                       *" << endl
             << "*   2. 建立哈夫曼树                                 *" << endl
             << "*   3. 建立密码本并对文件编码                       *" << endl
             << "*   4. 选择需要进行解码的文件并解码                 *" << endl
             << "*   5. 退出程序                                     *" << endl
             << "*                                                   *" << endl
             << "*****************************************************" << endl;
        cin >> flag;
        switch (flag)
        {
        case 1:
            ChooseFile(fin);
            break;
        case 2:
            CreateHuffmanTree(HT, fin, n, map);
            break;
        case 3:
            CreatHuffmanCode(HT, HC, n, map, fin);
            break;
        case 4:
            DecodeHuffmanCode(HT, n, map);
            break;
        case 5:
            return 0;
        }
        getchar();
        getchar();
    }
    return 0;
}

/*
D:/Bag/src.txt
D:/Bag/password.cod
*/