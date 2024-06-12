#include <iostream>
#include <vector>
using namespace std;

vector<vector<int>> result;
vector<int> path;

void backTracking(vector<int>& nums, int startIndex) {
        result.push_back(path); // 收集子集，要放在终止添加的上面，否则会漏掉自己
        if (startIndex >= nums.size()) { // 终止条件
            return;
        }
        for (int i = startIndex; i < nums.size(); i++) {
            path.push_back(nums[i]); // 单个结果
            backTracking(nums, i + 1);
            path.pop_back(); // 回溯
        }
}

vector<vector<int>> subsets(vector<int>& nums) {
        result.clear();
        path.clear();
        backTracking(nums, 0);
        return result;
}

int main()
{
    int sum = 0;
    int M = 9;
    vector<int> nums = { 7,5,1,2,10 };
    vector<int> temp = {};
    vector<vector<int>> res = subsets(nums);
    cout << "全部子集：" << endl;
    for(int i = 0; i < res.size(); i++) {
        cout << "{";
        for(int j = 0; j < res[i].size(); j++) {
            cout << res[i][j] << ",";
        }
        cout<< "}";
        cout << endl;
    }

    cout << "结果：" << endl;
    for(int i = 0; i < res.size(); i++) {
        for(int j = 0; j < res[i].size(); j++) {
            sum += res[i][j];
            temp.push_back(res[i][j]);
        }
        if(sum == 9) {
            cout << "{";
            for(int k = 0; k < temp.size(); k++) {
                cout << temp[k] << ",";
            }
            cout << "}" << endl;
        }
        sum = 0;
        temp.clear();
    }

    return 0;
}















