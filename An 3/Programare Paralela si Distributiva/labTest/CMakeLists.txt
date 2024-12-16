cmake_minimum_required(VERSION 3.30)
project(labTest)

set(CMAKE_CXX_STANDARD 17)

find_package(MPI REQUIRED)
include_directories(SYSTEM ${MPI_INCLUDE_PATH})

add_executable(labTest main.cpp)
target_link_libraries(labTest ${MPI_LIBRARIES})
