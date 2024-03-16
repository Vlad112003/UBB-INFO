#include <iostream>

#include "TestScurt.h"
#include "TestExtins.h"
#include "Testuletz.h"
int main() {
    testAll();
    testAllExtins();
    myTestuletz();
    std::cout << "Finished Tests!" << std::endl;
}