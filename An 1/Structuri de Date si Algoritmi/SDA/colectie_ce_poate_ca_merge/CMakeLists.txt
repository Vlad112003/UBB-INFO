cmake_minimum_required(VERSION 3.23)
project(colectie_ce_poate_ca_merge)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(colectie_ce_poate_ca_merge
        Colectie.cpp
        Colectie.h
        IteratorColectie.cpp
        IteratorColectie.h
        main.cpp
        TestExtins.cpp
        TestExtins.h
        TestScurt.cpp
        TestScurt.h)
