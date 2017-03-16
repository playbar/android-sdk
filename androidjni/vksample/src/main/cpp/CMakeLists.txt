cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11  -Werror -DVK_USE_PLATFORM_ANDROID_KHR")

get_filename_component(SHADERC_SRC
                       ${ANDROID_NDK}/sources/third_party/shaderc
                       ABSOLUTE)

# Import shaderc lib in NDK
add_library(shaderc_lib STATIC IMPORTED)
set_target_properties(shaderc_lib PROPERTIES IMPORTED_LOCATION
                      ${SHADERC_SRC}/libs/${ANDROID_STL}/${ANDROID_ABI}/libshaderc.a)

file(GLOB UTILS_SOURCE *.cpp)
if(ANDROID)
file(GLOB GLUE_SOURCE *.c)
endif()

add_library(vksample SHARED
            ${UTILS_SOURCE}
            ${GLUE_SOURCE})

target_include_directories(vksample PRIVATE
                          ${CMAKE_CURRENT_SOURCE_DIR}/data
                          ${CMAKE_CURRENT_SOURCE_DIR}
                          ${ANDROID_NDK}/sources/third_party/shaderc/include)

target_link_libraries(vksample
                      android
                      log
                      shaderc_lib)
