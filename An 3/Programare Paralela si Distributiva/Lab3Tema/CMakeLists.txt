cmake_minimum_required(VERSION 3.29)
project(Lab3Tema)

set(CMAKE_CXX_STANDARD 17)

find_package(MPI REQUIRED)
include_directories(SYSTEM ${MPI_INCLUDE_PATH})

add_executable(Lab3Tema main.cpp)
target_link_libraries(Lab3Tema ${MPI_LIBRARIES})