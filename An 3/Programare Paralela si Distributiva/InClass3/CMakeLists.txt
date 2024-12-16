cmake_minimum_required(VERSION 3.29)
project(InClass3)

set(CMAKE_CXX_STANDARD 17)

find_package(MPI REQUIRED)

add_executable(InClass3 main.cpp)
add_executable(part2 part2.cpp)

target_link_libraries(InClass3 MPI::MPI_CXX)
target_link_libraries(part2 MPI::MPI_CXX)