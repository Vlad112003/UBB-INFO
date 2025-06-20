cmake_minimum_required(VERSION 3.30)
project(Proiect)

set(CMAKE_CXX_STANDARD 17)

add_executable(Proiect App.cpp
        Matrice.cpp
        Matrice.h
        TestScurt.h
        TestScurt.cpp
        TestExtins.h
        TestExtins.cpp
        IteratorMatrice.cpp
        IteratorMatrice.h
)
