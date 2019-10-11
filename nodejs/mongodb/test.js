var MongoClient = require('mongodb').MongoClient
    , assert = require('assert')
    , crud = require("./crud");
// Connection URL
var url = 'mongodb://localhost:27017/test';
// Use connect method to connect to the Server
MongoClient.connect(url, function (err, db) {
    assert.equal(null, err);
    console.log("Connected correctly to server");

    crud.insertDocuments(db, function () {
        crud.updateDocument(db, function () {
            crud.removeDocument(db, function () {
                crud.findDocuments(db, function () {
                    db.close();
                });
            });
        });
    });
});

