cmake_minimum_required(VERSION 3.10)
project(InClass4)

set(CMAKE_CXX_STANDARD 11)

add_executable(InClass4 main.cpp)

find_package(OpenMP REQUIRED)
if(OpenMP_CXX_FOUND)
    target_link_libraries(InClass4 PRIVATE OpenMP::OpenMP_CXX)
endif()