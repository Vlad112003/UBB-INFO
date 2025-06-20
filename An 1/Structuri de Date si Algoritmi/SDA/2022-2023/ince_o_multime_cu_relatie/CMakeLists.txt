cmake_minimum_required(VERSION 3.24)
project(ince_o_multime_cu_relatie)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(ince_o_multime_cu_relatie
        IteratorMultime.cpp
        IteratorMultime.h
        main.cpp
        Multime.cpp
        Multime.h
        TestExtins.cpp
        TestExtins.h
        TestScurt.cpp
        TestScurt.h myTestuletz.cpp)
