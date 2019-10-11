/**
 * Created by since on 2016/2/24.
 */
/**
 * 标签构建器
 * @param name dom标签名称
 * @param attr dom属性，json格式
 * @param selfClose 属于自闭合标签
 */
function TagBd(name, attr, selfClose){
    var children = [];

    this.append = function(child){
        if(selfClose){
            throw new Error("can not append anything into self-closing tag");
        } else if(child instanceof TagBd ||  typeof child == "string"){
            children.push(child);
        } else {
            throw new Error("unsupported type : " + typeof child);
        }

        return this;
    };

    this.toString = function(){
        var tagLeft="",tagRight="",attrText="",childrenText="";

        for(var i in children){
            childrenText += children[i].toString();
        }

        for(var key in attr){
            attrText += key + "=\"" + attr[key] + "\"";
        }

        if(selfClose){
            tagLeft = "<" + name + " " + attrText + "/>";
            tagRight = "";
        } else {
            tagLeft = "<" + name + " " + attrText + ">";
            tagRight = "</" + name + ">";
        }

        return tagLeft + childrenText + tagRight;
    };
}
