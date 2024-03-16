# CMakeList.txt : CMake project for L6, include source and define
# project specific logic here.
#
cmake_minimum_required (VERSION 3.8)

project ("L6")

# Add source to this project's executable.
add_executable (L6  "main.cpp" IteratorMDO.cpp MDO.cpp TestExtins.cpp TestScurt.cpp)

# TODO: Add tests and install targets if needed.
