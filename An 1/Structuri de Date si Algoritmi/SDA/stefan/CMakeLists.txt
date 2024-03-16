cmake_minimum_required(VERSION 3.23)
project(stefan)

set(CMAKE_CXX_STANDARD 14)

include_directories(Lab1)
include_directories("TAD Colectie/Proiect C++/Colectie")
include_directories("TAD Colectie/Proiect C++/Colectie (cu rel)")
include_directories("TAD Colectie/Teste")
include_directories("TAD Colectie/Teste (cu relatie)")

add_executable(stefan
        Lab1/App.cpp
        Lab1/Colectie.cpp
        Lab1/Colectie.h
        Lab1/IteratorColectie.cpp
        Lab1/IteratorColectie.h
        Lab1/TesteleMele.cpp
        Lab1/TesteleMele.h
        Lab1/TestExtins.cpp
        Lab1/TestExtins.h
        Lab1/TestScurt.cpp
        Lab1/TestScurt.h
        "TAD Colectie/Proiect C++/Colectie/App.cpp"
        "TAD Colectie/Proiect C++/Colectie/Colectie.cpp"
        "TAD Colectie/Proiect C++/Colectie/Colectie.h"
        "TAD Colectie/Proiect C++/Colectie/IteratorColectie.cpp"
        "TAD Colectie/Proiect C++/Colectie/IteratorColectie.h"
        "TAD Colectie/Proiect C++/Colectie/TestExtins.cpp"
        "TAD Colectie/Proiect C++/Colectie/TestExtins.h"
        "TAD Colectie/Proiect C++/Colectie/TestScurt.cpp"
        "TAD Colectie/Proiect C++/Colectie/TestScurt.h"
        "TAD Colectie/Proiect C++/Colectie (cu rel)/App.cpp"
        "TAD Colectie/Proiect C++/Colectie (cu rel)/Colectie.cpp"
        "TAD Colectie/Proiect C++/Colectie (cu rel)/Colectie.h"
        "TAD Colectie/Proiect C++/Colectie (cu rel)/IteratorColectie.cpp"
        "TAD Colectie/Proiect C++/Colectie (cu rel)/IteratorColectie.h"
        "TAD Colectie/Teste/TestExtins.cpp"
        "TAD Colectie/Teste/TestExtins.h"
        "TAD Colectie/Teste/TestScurt.cpp"
        "TAD Colectie/Teste/TestScurt.h"
        "TAD Colectie/Teste (cu relatie)/TestExtins.cpp"
        "TAD Colectie/Teste (cu relatie)/TestExtins.h"
        "TAD Colectie/Teste (cu relatie)/TestScurt.cpp"
        "TAD Colectie/Teste (cu relatie)/TestScurt.h")
