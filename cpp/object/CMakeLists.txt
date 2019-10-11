project(object)

aux_source_directory(. DIR_SRCS)

# 指定生成目标
add_executable(object ${DIR_SRCS})
