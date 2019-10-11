var exec = require("child_process").exec;
var querystring = require("querystring");
var formidable = require("formidable");
var fs = require("fs");
var util = require("util");
var url = require("url");

function find(request, response) {
    console.log("Request handler 'find' was called.");

    var query = url.parse(request.url, true).query;

    var path = query.path; //query中存放url键值对

    exec("find /" + path,
        {timeout: 1000, maxBuffer: 2000 * 1024},
        function (error, stdout, stderr) {
            response.writeHead(200, {"Content-Type": "text/plain"});
            response.write(stdout);
            response.end();
        });
}

function start(request, response) {
    console.log("Request handler 'start' was called.");

    var body = '<html>'+
        '<head>'+
        '<meta http-equiv="Content-Type" content="text/html; '+
        'charset=UTF-8" />'+
        '</head>'+
        '<body>'+
        '<form action="/upload" enctype="multipart/form-data" '+
        'method="post">'+
        '<input type="file" name="upload" multiple="multiple">'+
        '<input type="submit" value="Upload file" />'+
        '</form>'+
        '</body>'+
        '</html>';

    response.writeHead(200, {"Content-Type": "text/html"});
    response.write(body);
    response.end();
}

function upload(request, response) {
    console.log("Request handler 'upload' was called.");

    var form = new formidable.IncomingForm();
    form.uploadDir='/tmp';

    form.parse(request, function (error, fields, files) {
        console.log("Parsing done.");

        fs.renameSync(files.upload.path, "/tmp/test.png");
        response.writeHead(200, {"Content-Type": "text/html"});
        response.write("Received image : <br/>");
        response.write("<img src='/show' />");

        //response.writeHead(302, {
        //    'Location': '/show'
        //    //add other headers here...
        //});

        response.end();
    });
}

function show(request, response) {
    console.log("Request handler 'show' was called.");

    fs.readFile("/tmp/test.png", "binary", function (error, file) {
        if(error){
            response.writeHead(500, {"Content-Type" : "text/plain"});
            response.write(error + "\n");
        } else {
            response.writeHead(200, {"Content-Type" : "image/png"});
            response.write(file, "binary");
        }

        response.end();
    });
}

function edit(request, response) {
    console.log("Request handler 'start' was called.");

    var body = '<html>'+
        '<head>'+
        '<meta http-equiv="Content-Type" content="text/html; '+
        'charset=UTF-8" />'+
        '</head>'+
        '<body>'+
        '<form action="/commit" method="post">'+
        '<textarea name="something" rows="20" cols="60"></textarea>'+
        '<input type="submit" value="Submit text" />'+
        '</form>'+
        '</body>'+
        '</html>';

    response.writeHead(200, {"Content-Type": "text/html"});
    response.write(body);
    response.end();
}

function commit(request, response) {
    console.log("Request handler 'show' was called.");

    request.setEncoding("utf8");

    var postData = '';

    request.addListener("data", function(postDataChunk) {
        postData += postDataChunk;
        console.log("Received POST data chunk '"+
        postDataChunk + "'.");
    });

    request.addListener("end", function() {
        var qs =querystring.parse(postData); //qs中存放 post 键值对
        response.writeHead(200,{"Content-Type" : "text/plain"});
        response.write(qs.something.toString());
        response.end();
    });

}


exports.find = find;
exports.start = start;
exports.upload = upload;
exports.show = show;
exports.commit = commit;
exports.edit = edit;
exports[""] = start;


