
// CPP program to illustrate
// push_back() function
#include <iostream>
#include <vector>
using namespace std;
  
int main()
{
    vector<int> myvector{ 1, 2, 3, 4, 5 };
    myvector.push_back(6);
  
    // Vector becomes 1, 2, 3, 4, 5, 6
  
    for (auto it = myvector.begin(); it != myvector.end(); ++it)
        cout << ' ' << *it;
}