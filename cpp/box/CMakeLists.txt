project(box)

aux_source_directory(. DIR_SRCS)

# 指定生成目标
add_executable(box ${DIR_SRCS})
