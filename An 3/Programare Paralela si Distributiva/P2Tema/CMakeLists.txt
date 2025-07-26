cmake_minimum_required(VERSION 3.30)
project(P2Tema LANGUAGES CXX CUDA)

set(CMAKE_CXX_STANDARD 17)

include_directories("C:/Program Files/NVIDIA GPU Computing Toolkit/CUDA/v12.6/include")
link_directories("C:/Program Files/NVIDIA GPU Computing Toolkit/CUDA/v12.6/lib/x64")

# Add CUDA runtime library for linking
find_package(CUDA REQUIRED)
set(CUDA_SEPARABLE_COMPILATION ON)
set(CUDAToolkit_ROOT "C:/Program Files/NVIDIA GPU Computing Toolkit/CUDA/v12.6")

# Add main.cpp and ensure it's compiled as CUDA
add_executable(P2Tema main.cu)
target_link_libraries(P2Tema cuda cudart)
