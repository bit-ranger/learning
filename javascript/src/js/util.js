/**
 * Created by since on 2016/4/6.
 */

(function (global){

    //日期转换
    var SimpleDateFormat = function (fmt) {

        if(typeof(fmt) != "string"){
            throw new TypeError(fmt + " is not a string");
        }

        var position = {

            "Y+": {s: Date.prototype.setFullYear, g: Date.prototype.getFullYear}, //年
            "M+": {o: -1, s: Date.prototype.setMonth, g: Date.prototype.getMonth}, //月
            "D+": {s: Date.prototype.setDate, g: Date.prototype.getDate}, //日
            "h+": {s: Date.prototype.setHours, g: Date.prototype.getHours}, //时
            "m+": {s: Date.prototype.setMinutes, g: Date.prototype.getMinutes}, //分
            "s+": {s: Date.prototype.setSeconds, g: Date.prototype.getSeconds}  //秒

        };

        for (var p in position) {
            var rst = new RegExp("(" + p + ")").exec(fmt);
            if (!rst) {
                continue;
            }
            var curr = position[p];
            curr.b = rst.index;
            curr.l = rst[0].length;
        }

        this.parse = function (str) {
            if (!(typeof(str) == "string")) {
                throw new TypeError(str + " is not a string");
            }

            var date = new Date();
            for (p in position) {
                var curr = position[p];
                if (curr.b == null || curr.l == null) {
                    continue;
                }

                var val = str.slice(curr.b, curr.b + curr.l);
                if (isNaN(val)) {
                    throw new TypeError(val + " is not a number");
                }
                var o = curr.o ? curr.o : 0;
                curr.s.call(date, parseInt(val) + o);
            }
            return date;
        }


        this.format = function (date) {
            if (!(date instanceof Date)) {
                throw new TypeError(date + " is not a Date");
            }

            var str = fmt;
            for (p in position) {
                var curr = position[p];
                if (curr.b == null || curr.l == null) {
                    continue;
                }

                var val = curr.g.call(date);
                var o = curr.o ? curr.o : 0;
                val = (parseInt(val) - o).toString();
                val = insert(val, "0", curr.l - val.length);
                str = change(str, val, curr.b);
            }
            return str;
        }

        var insert = function (o, m, n) {
            for (var i = 0; i < n; i++) {
                o = m + o;
            }
            return o;
        }

        var change = function (o, m, s) {
            var h = o.slice(0, s);
            var t = o.slice(s + m.length, o.length);
            return h + m + t;
        }

    }

    var reduce = function (object, callback){
        var length = object.length, reduce = null, index = 0;
        if ( length === undefined ) {
            for (var key in object ){
                if(index == 0){
                    reduce = object[key];
                } else {
                    reduce = callback.call( object[key], reduce, object[key] );
                }
                index ++;
            }
        } else{
            for (; index < length; ){
                if(index == 0){
                    reduce = object[index];
                } else {
                    reduce = callback.call( object[index], reduce, object[index] );
                }
                index ++;
            }
        }
    };


    var UrlParser = function (url){

        if(typeof(url) != "string"){
            throw new TypeError(url + " is not a string");
        }

        this.toStringParam = function (json){
            if(typeof(json) != "object"){
                throw new TypeError(json + " is not a json");
            }
            var str = "";
            for(var key in json){
                str += "&" + key + "=" + json[key];
            }
            if(str.length > 0){
                str = "?" + str.substring(1);
            }
            return str;
        }

        this.toJsonParam = function (str){

            if(str == undefined){
                var i = url.indexOf("?");
                str =  i == -1 ? "" : url.substring(i + 1)
            }

            if(typeof(str) != "string"){
                throw new TypeError(str + " is not a string");
            }

            if(str == ""){
                return {};
            }

            var kvPairs = str.split("&");

            var json = {};

            for(var i=0; i<kvPairs.length; i++){
                var kv = kvPairs[i].split("=");
                var k = kv[0];
                var v = kv[1];
                if(!notEmpty(k)){
                    continue;
                }
                v = notEmpty(v) ? v : "";
                json[k] = v;
            }

            return json;

        }

    }


    var valid = function (obj){
        return obj != null && obj != undefined;
    }

    var assert = function (predicate, error){
        if (!predicate) {
            if(typeof (error) == "function"){
                error();
            } else if(typeof (error) == "string"){
                throw new Error(error)
            }
        }
        return predicate;
    }

    var notEmpty = function (obj){
        if(!valid(obj)){
            return false;
        }

        if(!((typeof(obj) == "string") || (obj instanceof Array))){
            throw new TypeError(obj + " is not a String or a Array");
        }

        return obj.length > 0;
    }





    //~~ set up

    var util = {};
    window[global] = util;

    util.newDateFormat = function (fmt){
        return new SimpleDateFormat(fmt);
    };

    util.reduce = reduce;

    util.assert = assert;

    util.notEmpty = notEmpty;

    util.newUrlParser = function (url){
        return new UrlParser(url);
    }



})("$$");



