var server = require("./server");
var router = require("./router");
var requestHandlers = require("./requestHandlers");

var handle = {}
for (var propertyName in requestHandlers) {
    handle["/" + propertyName] = requestHandlers[propertyName];
}

server.start(router.route, handle);
