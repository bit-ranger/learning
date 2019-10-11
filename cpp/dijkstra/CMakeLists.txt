project(dijkstra)

aux_source_directory(. DIR_SRCS)

# 指定生成目标
add_executable(dijkstra ${DIR_SRCS})
