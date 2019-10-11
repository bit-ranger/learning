project(smart-ptr)

aux_source_directory(. DIR_SRCS)

# 指定生成目标
add_executable(smart-ptr ${DIR_SRCS})
