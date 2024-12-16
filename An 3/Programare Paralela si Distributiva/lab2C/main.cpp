#include <iostream>
#include <vector>
#include <thread>
#include <cassert>
#include <chrono>
using namespace std;

int generateRandomNumber(int upperBoundary) {
    return rand() % upperBoundary + 1;
}

void printArray(vector<int> a, int n) {
    for (int i = 0; i < n; i ++)
        cout << a[i] << " ";
    cout << "\n";
}

int operation(int a, int b) {
    return a*a*a+b*b*b;
}

void task(vector<int>& a, vector<int>& b, vector<int>& c, int start, int end) {
    for (int i = start; i < end; i++)
        c[i] = operation(a[i], b[i]);
}



int main()
{
    int n=10000000;
    int upperBound = 1000;
    vector<int> a(n);
    vector<int> b(n);
    vector<int> c(n);

    for(int i=0; i<n; i++) {
        a[i] = generateRandomNumber(upperBound);
        b[i] = generateRandomNumber(upperBound);
    }

    auto t_start = std::chrono::high_resolution_clock::now();


    task(a,b,c,0, n);

    auto t_end = std::chrono::high_resolution_clock::now();
    double elapsed_time_ms = std::chrono::duration<double, std::milli>(t_end-t_start).count();

    std::cout << elapsed_time_ms << "\n";

    // printArray(a,n);
    // printArray(b,n);
    // printArray(c,n);

    //threads
    int p = 4;
    vector<thread> threads(p);

    int start = 0;
    int end = n/p;
    int rest = n%p;

    vector<int> c2(n);

    auto t_start2 = std::chrono::high_resolution_clock::now();

    for(int i=0; i<p; i++) {
        if(rest>0) {
            end++;
            rest--;
        }
        threads[i] = thread(task, ref(a), ref(b), ref(c2), start, end);
        start = end;
        end = start + n/p;
    }

    for(int i=0; i<p;i++) {
        threads[i].join();
    }

    assert(c==c2);

    auto t_end2 = std::chrono::high_resolution_clock::now();
    double elapsed_time_ms2 = std::chrono::duration<double, std::milli>(t_end2-t_start2).count();

    std::cout << elapsed_time_ms2 << "\n";

}
