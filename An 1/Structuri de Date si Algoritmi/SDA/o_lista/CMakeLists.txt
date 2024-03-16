cmake_minimum_required(VERSION 3.25)
project(o_lista)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(o_lista
        Iterator.cpp
        Iterator.h
        LO.cpp
        LO.h
        main.cpp
        TestExtins.cpp
        TestExtins.h
        TestScurt.cpp
        TestScurt.h)
