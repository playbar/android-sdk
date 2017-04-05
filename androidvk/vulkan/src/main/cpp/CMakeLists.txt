cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -fexceptions -Werror")

#add_definitions(-DGLM_ENABLE_EXPERIMENTAL)
add_definitions(-DVK_USE_PLATFORM_ANDROID_KHR)
add_definitions(-DVK_NO_PROTOTYPES)
#add_definitions(-DGLM_LANG_STL11_FORCED)

get_filename_component(SHADERC_SRC
                       ${ANDROID_NDK}/sources/third_party/shaderc
                       ABSOLUTE)

add_subdirectory(${CMAKE_CURRENT_SOURCE_DIR}/assimp)

# Import shaderc lib in NDK
add_library(shaderc_lib STATIC IMPORTED)
set_target_properties(shaderc_lib PROPERTIES IMPORTED_LOCATION
                      ${SHADERC_SRC}/libs/${ANDROID_STL}/${ANDROID_ABI}/libshaderc.a)

add_library(assimp_lib STATIC IMPORTED)
set_target_properties(assimp_lib PROPERTIES IMPORTED_LOCATION
                      ${CMAKE_CURRENT_SOURCE_DIR}/assimp/libassimp.a)

add_library(vksample SHARED
            android_native_app_glue.c
            cpu-features.c
            jniactivity.c
            base/vulkanandroid.cpp
            base/VulkanDebug.cpp
            base/VulkanDevice.cpp
            base/vulkanexamplebase.cpp
            base/VulkanModel.cpp
            base/VulkanTexture.cpp
            base/VulkanTools.cpp
            src/triangle.cpp
            )

target_include_directories(vksample PRIVATE
                          ${CMAKE_CURRENT_SOURCE_DIR}
                          ${CMAKE_CURRENT_SOURCE_DIR}/assimp/include
                          ${CMAKE_CURRENT_SOURCE_DIR}/stb
                          ${CMAKE_CURRENT_SOURCE_DIR}/base
                          ${ANDROID_NDK}/sources/third_party/shaderc/include)

target_link_libraries(vksample
                      android
                      log
                      z
                      assimp_lib )
