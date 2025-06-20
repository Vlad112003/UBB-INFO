cmake_minimum_required(VERSION 3.24)
project(TAD_Arbore_binar_main)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(TAD_Arbore_binar_main
        AB.cpp
        AB.h
        IteratorAB.cpp
        IteratorAB.h
        main.cpp
        TestExtins.cpp
        TestExtins.h
        TestScurt.cpp
        TestScurt.h)
