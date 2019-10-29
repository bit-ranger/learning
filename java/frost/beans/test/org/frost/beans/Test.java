package org.frost.beans;

import org.dom4j.DocumentException;

/**
 * Created by sllx on 14-12-8.
 */
public class Test {

    public static void main(String[] args) throws DocumentException {
        Configuration configuration = new XMLConfigReader().read("out/test/beans/org/frost/beans/test.xml");
        BeanFactory factory = new BeanFactory(configuration);
        new Assembler(configuration,factory).assemble();
    }

}
