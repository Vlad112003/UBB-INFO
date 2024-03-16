cmake_minimum_required(VERSION 3.23)
project(colectie_cu_main)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(colectie_cu_main
        App.cpp
        Colectie.cpp
        Colectie.h
        IteratorColectie.cpp
        IteratorColectie.h
        main.cpp
        TestExtins.cpp
        TestExtins.h
        TestScurt.cpp
        TestScurt.h)
