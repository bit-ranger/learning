const gulp = require('gulp'),
    connect = require('gulp-connect'),
    minifycss=require('gulp-minify-css'),   //css压缩
    concat=require('gulp-concat'),   //合并文件
    uglify=require('gulp-uglify'),   //js压缩
    rename=require('gulp-rename'),   //文件重命名
    jshint=require('gulp-jshint');   //js检查


gulp.task('build', function (){

    gulp.src('./src/js/*.js')
        .pipe(concat('./src/jquery-1.11.1.min.js'))   //合并js
        .pipe(rename({suffix:'.min'}))                //重命名
        .pipe(uglify())                               //压缩
        .pipe(gulp.dest('./build/js'));                //输出

});


gulp.task('serve', function() {
    connect.server();
});

gulp.task('default', ['serve']);