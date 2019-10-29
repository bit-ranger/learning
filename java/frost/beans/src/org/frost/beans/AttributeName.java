package org.frost.beans;


enum AttributeName {
    ID,CLASS,NAME,REF,VALUE;

    public String lowerName(){
        return this.name().toLowerCase();
    }
}
