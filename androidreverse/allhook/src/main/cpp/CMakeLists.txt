cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} \
                    -std=c++11 -fpermissive -Wno-missing-declarations -DDEBUG -O0")


add_library(lib_utils SHARED IMPORTED)
set_target_properties(lib_utils PROPERTIES IMPORTED_LOCATION
    ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libutils.so)

add_library(lib_android_runtime SHARED IMPORTED)
set_target_properties(lib_android_runtime PROPERTIES IMPORTED_LOCATION
    ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libandroid_runtime.so)


add_library(lib_art SHARED IMPORTED)
set_target_properties(lib_art PROPERTIES IMPORTED_LOCATION
    ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libart.so)

add_library(lib_binder SHARED IMPORTED)
set_target_properties(lib_binder PROPERTIES IMPORTED_LOCATION
    ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libbinder.so)

add_library(lib_cutils SHARED IMPORTED)
set_target_properties(lib_cutils PROPERTIES IMPORTED_LOCATION
    ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libcutils.so)

add_library(lib_dvm SHARED IMPORTED)
set_target_properties(lib_dvm PROPERTIES IMPORTED_LOCATION
    ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libdvm.so)

add_library(lib_nativehelper SHARED IMPORTED)
set_target_properties(lib_nativehelper PROPERTIES IMPORTED_LOCATION
    ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libnativehelper.so)

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
                           ${CMAKE_SOURCE_DIR}/include
                           )

target_link_libraries(onehook
                      log
                      lib_android_runtime
                      lib_art
                      lib_utils
                      lib_binder
                      lib_cutils
                      lib_dvm
                      lib_nativehelper
                      android
                      )
