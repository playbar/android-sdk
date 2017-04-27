cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} \
                    -std=c++11 -fpermissive -DDEBUG -O0")

add_library(onehook SHARED
        JavaHook/JavaMethodHook.cpp
        JavaHook/ArtMethodHook.cpp
        JavaHook/DalvikMethodHook.cpp
        JavaHook/art_quick_proxy.S
        ElfHook/elfhook.cpp
        ElfHook/elfio.cpp
        ElfHook/elfutils.cpp
        main.cpp)

target_include_directories(onehook PRIVATE
                           ${CMAKE_SOURCE_DIR}
                           )

target_link_libraries(onehook
                      log
                      android_runtime
                      utils
                      cutils
                      art
                      dvm
                      android
                      log)
