cmake_minimum_required(VERSION 3.3)
project(learning_c)

set(CMAKE_C_STANDARD 11)

#set (learning_c_VERSION_MAJOR 1)
#set (learning_c_VERSION_MINOR 0)



# configure a header file to pass some of the CMake settings
# to the source code
#configure_file (
#        "${PROJECT_SOURCE_DIR}/config.h.in"
#        "${PROJECT_SOURCE_DIR}/config.h"
#)


include_directories("${PROJECT_BINARY_DIR}")

add_subdirectory (basic)
add_subdirectory (practice)

set (EXTRA_LIBS ${EXTRA_LIBS} basic practice)

link_libraries(basic practice)

add_executable(TestHashMap TestHashMap.c)
target_link_libraries (TestHashMap  ${EXTRA_LIBS})





