project(size)

aux_source_directory(. DIR_SRCS)

# 指定生成目标
add_executable(size ${DIR_SRCS})
