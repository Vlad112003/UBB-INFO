#include <iostream>
#include "TestExtins.h"
#include "TestScurt.h"
using namespace std;


int main() {
    testDiferenta();
    cout<<"testDiferenta passed!\n";
	testAll();
    cout<<"testScurt passed!\n";
	testAllExtins();
	cout<<"End";

    return 0;
}
