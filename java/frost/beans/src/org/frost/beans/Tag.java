package org.frost.beans;

import org.frost.core.Assert;

enum Tag {
    BEAN(AttributeName.ID,AttributeName.NAME,AttributeName.CLASS),
    PROPERTY(AttributeName.NAME,AttributeName.REF,AttributeName.VALUE),
    BEANS;

    private AttributeName[] attrs;

    /**
     * @param attrs 标签支持的属性
     */
    private Tag(AttributeName... attrs){
        this.attrs = attrs;
    }

    public String lowerName(){
        return this.name().toLowerCase();
    }

    /**
     * 获取支持的属性名
     * @param attributeName 属性名
     * @return 属性名的枚举
     */
    public AttributeName getSupportedAttributeName(String attributeName){
        Assert.notNull(attributeName,"attributeName can not be null");
        for (AttributeName aName : attrs) {
            if(aName.name().equalsIgnoreCase(attributeName)){
                return aName;
            }
        }
        return null;
    }

    /**
     * 获取支持的标签
     * @param name 标签名
     * @return 标签的枚举
     */
    public static Tag getSupportedTag(String name){
        Assert.notNull(name,"name can not be null");
        Tag tag = null;
        try{
            tag = valueOf(name);
        }catch (IllegalArgumentException e){
            try{
                tag = valueOf(name.toUpperCase());
            } catch (IllegalArgumentException e1){
                // noting to do
            }
        }
        return tag;
    }
}
