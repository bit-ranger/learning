/**
 * 日期格式化
 * 注意：对于给定的格式，该方法只处理捕获到的第一个匹配点
 * 如 yyyy-yyyy 格式，当年份为2015年时，该格式将被解析为 2015-yyyy
 */
Date.prototype.format = function (fmt) {
    var map = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };

    if (/(y+)/.test(fmt)) {
        var yearStr = this.getFullYear().toString();
        //去头留尾
        fmt = fmt.replace(RegExp.$1, yearStr.substring(yearStr.length - RegExp.$1.length));
    }

    for (var k in map){
        if (new RegExp("(" + k + ")").test(fmt)){
            //只有1位时返回原值（毫秒数只捕获1位），否则补零凑齐两位
            var hit = RegExp.$1.length==1 ? map[k] : ("00" + map[k]).substring(map[k].toString().length);
            fmt = fmt.replace(RegExp.$1, hit);
        }
    }

    return fmt;
};

var SimpleDateFormat = function(fmt){
    var positon = {
        "Y+": {h: Date.prototype.setFullYear}, //年
        "M+": {o:-1, h: Date.prototype.setMonth}, //月
        "D+": {h: Date.prototype.setDate}, //日
        "h+": {h: Date.prototype.setHours}, //时
        "m+": {h: Date.prototype.setMinutes}, //分
        "s+": {h: Date.prototype.setSeconds}  //秒
    };

    for(p in positon){
        var rst = new RegExp("(" + p + ")").exec(fmt);
        if(!rst){
            break;
        }
        var curr = positon[p];
        curr.b = rst.index;
        curr.l = rst[0].length;
    }

    this.parse = function(str){
        var date = new Date();
        for(p in positon){
            var curr = positon[p];
            if(curr.b && curr.l){
                var val = str.slice(curr.b, curr.b + curr.l);
                if(isNaN(val)){
                    throw new TypeError(val + " is not a number");
                }
                var o = curr.o ? curr.o : 0;
                curr.h.call(date, parseInt(val) + o);
            }
        }
        return date;
    }
}


function TimeRange(listener, now){
    if(listener == null){
        listener = function(){};
    }

    if(now == null){
        now = new Date();
    }

    if(!(listener instanceof Function)){
        throw new TypeError(listener + " is not a function");
    }

    if(!(now instanceof Date)){
        throw new TypeError(now + " is not a date");
    }

    var year = now.getFullYear();
    var month = now.getMonth();
    var quarter = Math.floor((month + 3) / 3);
    var dayMS = 1000*60*60*24;

    function final(begin,end,context){
        listener.call(this,begin, end, context);
        return {begin:begin, end:end, context:context};
    }

    this.thisMonth = function(context){
        var begin,end;
        begin = new Date(year,month,1);
        end = now;
        return final(begin, end, context);
    };

    this.lastMonth = function(context){
        var begin,end;
        begin = new Date(year, month-1, 1);
        end = new Date(new Date(year,month,1).getTime() - dayMS);
        return final(begin, end, context);
    };

    this.thisQuarter = function(context){
        var begin,end;
        begin = new Date(year, (quarter-1)*3, 1);
        end = now;
        return final(begin, end, context);
    };

    this.lastQuarter = function(context){
        var begin,end;
        begin = new Date(year, (quarter-2)*3, 1);
        end = new Date(new Date(year, (quarter-1)*3, 1).getTime() - dayMS);
        return final(begin, end, context);
    };

    this.thisYear = function(context){
        var begin,end;
        begin = new Date(year, 0, 1);
        end = now;
        return final(begin, end, context);
    };

    this.lastYear = function(context){
        var begin,end;
        begin = new Date(year-1, 0, 1);
        end = new Date(year-1, 11, 31);
        return final(begin, end, context);
    };

}
