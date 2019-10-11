project(friend)

aux_source_directory(. DIR_SRCS)

# 指定生成目标
add_executable(friend ${DIR_SRCS})
