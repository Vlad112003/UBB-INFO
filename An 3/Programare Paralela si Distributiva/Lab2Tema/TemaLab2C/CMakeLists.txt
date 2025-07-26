cmake_minimum_required(VERSION 3.29)
project(TemaLab2C)

set(CMAKE_CXX_STANDARD 20)

find_package(OpenMP REQUIRED)

add_executable(TemaLab2C main.cpp)

if(OpenMP_CXX_FOUND)
    target_link_libraries(TemaLab2C PRIVATE OpenMP::OpenMP_CXX)
endif()