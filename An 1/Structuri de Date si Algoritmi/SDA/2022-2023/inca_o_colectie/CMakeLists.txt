cmake_minimum_required(VERSION 3.23)
project(inca_o_colectie)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(inca_o_colectie
        Colectie.cpp
        Colectie.h
        IteratorColectie.cpp
        IteratorColectie.h
        main.cpp
        TestExtins.cpp
        TestExtins.h
        TestScurt.cpp
        TestScurt.h)
