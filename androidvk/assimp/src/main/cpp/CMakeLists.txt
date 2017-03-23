cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -fexceptions -Werror")

add_definitions(-DVK_USE_PLATFORM_ANDROID_KHR)
add_definitions(-DASSIMP_BUILD_NO_C4D_IMPORTER)
add_definitions(-DOPENDDL_NO_USE_CPP11)
add_definitions(-DZLIB_CONST)

get_filename_component(SHADERC_SRC
                       ${ANDROID_NDK}/sources/third_party/shaderc
                       ABSOLUTE)


add_subdirectory(${CMAKE_CURRENT_SOURCE_DIR}/contrib/zlib)

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
            jniactivity.c
            contrib/ConvertUTF/ConvertUTF.c
            contrib/irrXML/irrXML.cpp
            code/3DSConverter.cpp
            code/DefaultLogger.cpp
            code/scene.cpp
            code/Importer.cpp
            code/Version.cpp
            code/BaseProcess.cpp
            code/DefaultIOStream.cpp
            code/ValidateDataStructure.cpp
            code/BaseImporter.cpp
            code/ScenePreprocessor
            code/ProcessHelper.cpp
            code/SpatialSort.cpp
            code/XFileImporter.cpp
            code/XFileParser.cpp
            code/3DSLoader.cpp
            code/ObjFileParser.cpp
            code/ObjFileImporter.cpp
            code/AMFImporter.cpp
            code/AMFImporter_Material.cpp
            code/AMFImporter_Geometry.cpp
            code/AMFImporter_Postprocess.cpp
            code/MD3Loader.cpp
            code/MD2Loader.cpp
            code/RemoveComments.cpp
            code/SceneCombiner.cpp
            code/ObjFileMtlImporter.cpp
            code/SGSpatialSort.cpp
            code/ConvertToLHProcess.cpp
            code/ImporterRegistry.cpp  #
            code/PostStepRegistry.cpp  #
            code/DefaultIOSystem.cpp
            code/MaterialSystem.cpp)

target_include_directories(vksample PRIVATE
                          ${CMAKE_CURRENT_SOURCE_DIR}
                          ${CMAKE_CURRENT_SOURCE_DIR}/include
                          ${CMAKE_CURRENT_SOURCE_DIR}/contrib
                          ${CMAKE_CURRENT_SOURCE_DIR}/contrib/irrXML
                          ${CMAKE_CURRENT_SOURCE_DIR}/contrib/ConvertUTF)

target_link_libraries(vksample
                      android
                      zlibstatic
                      log )
