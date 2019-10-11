package com.rainyalley.practice.newFeatureIn8;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;

/**
 * JavaScript 引擎 Nashorn
 */
public class NashornTest {

    private static String json = "[{name:'A',age:'18'},{name:'B',age:'19'},{name:'C',age:'30'}]";

    private static String script =
            "function parse(json){" +
            "   var names = new Array();" +
            "   for(var i in json){" +
            "       names.push(json[i].name);" +
            "   }" +
            "   return names;" +
            "};" +
            "parse(" + json + ");";

    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName( "JavaScript" );

        Map<String,String> result = (Map<String,String>)engine.eval(script);
        result.forEach((k,v) -> System.out.println(v));
    }
}
