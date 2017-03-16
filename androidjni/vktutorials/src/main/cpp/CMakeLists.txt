cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -fexceptions  -Werror -DVK_USE_PLATFORM_ANDROID_KHR")

get_filename_component(SHADERC_SRC
                       ${ANDROID_NDK}/sources/third_party/shaderc
                       ABSOLUTE)

# Import shaderc lib in NDK
add_library(shaderc_lib STATIC IMPORTED)
set_target_properties(shaderc_lib PROPERTIES IMPORTED_LOCATION
                      ${SHADERC_SRC}/libs/${ANDROID_STL}/${ANDROID_ABI}/libshaderc.a)

#file(GLOB UTILS_SOURCE *.cpp)
#file(GLOB GLUE_SOURCE *.c)

#add_library(vksample SHARED
#            ${UTILS_SOURCE}
#            ${GLUE_SOURCE})

add_library(vksample SHARED
            android_native_app_glue.c
            jniactivity.c
            #LoadVK.cpp
            #TriangleMain.cpp
            #TriangleVK.cpp
            #PrebuildLayer.cpp
            #PrebuildLayerVK.cpp
            #TraceableLayer.cpp
            #TraceableLayerVK.cpp
            #FirstWindow.cpp
            #FirstWindowVK.cpp
            Tutorial.cpp
            TutorialVK.cpp
            vulkan_wrapper.cpp)

target_include_directories(vksample PRIVATE
                          ${CMAKE_CURRENT_SOURCE_DIR}/data
                          ${CMAKE_CURRENT_SOURCE_DIR}
                          ${ANDROID_NDK}/sources/third_party/shaderc/include)

target_link_libraries(vksample
                      android
                      log
                      shaderc_lib)
