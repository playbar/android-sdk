cmake_minimum_required(VERSION 3.4.1)

add_definitions(-DHAVE_NEON=1)

add_library(jnilib SHARED
            hello-jni.c
            helloneon.c
            cpu-features.c
            helloneon-intrinsics.c
            load.c)

# Include libraries needed for hello-jni lib
target_link_libraries(jnilib
                      android
                      log)
