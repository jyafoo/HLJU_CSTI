#include <iostream>
#include <vector>
#include <string>
#include <cmath>
using namespace std;

// board表示棋盘上每行皇后所在的列。第i行再第board[i]列
// 当前要放置皇后的行数row、列数col。
// 判断是否存在同列、同对角线的皇后。（行的差等于列的差 | 斜率绝对值相同）
bool is_valid(vector<int>& board, int row, int col) {
    for (int i = 0; i < row; ++i) { // 检查同列是否有皇后
        if (board[i] == col) return false;
        // 检查对角线是否有皇后
        if (abs(row - i) == abs(col - board[i])) return false;
    }
    return true;
}

// res存储结果
void n_queens_helper(vector<vector<string>>& res, vector<int>& board, int row, int n) {
    if (row == n) { // 找到一组解
        vector<string> solution;
        for (int i = 0; i < n; ++i) {
            string row(n, '.');
            row[board[i]] = 'Q';
            solution.push_back(row);
        }
        // 将一组解放入res中
        res.push_back(solution);
        return;
    }
    for (int col = 0; col < n; ++col) {
        if (is_valid(board, row, col)) { // 找到一个可行的位置
            board[row] = col;
            n_queens_helper(res, board, row + 1, n); // 继续搜索下一行
            board[row] = -1; // 回溯到上一层
        }
    }
}

// 表示棋盘大小
vector<vector<string>> n_queens(int n) {
    vector<vector<string>> res;
    vector<int> board(n, -1);
    n_queens_helper(res, board, 0, n);
    return res;
}

int main()
{
    int counts = 0;
    vector<vector<string>> res = n_queens(8);
    for(int i = 0; i < res.size(); i++) {
        for(int j = 0; j < res[i].size(); j++) {
            cout << res[i][j] << endl;
        }
        counts++;
        cout << "" << endl;
    }
    cout << counts;
    return 0;
}
