package org.frost.beans;

import org.frost.core.Assert;

import java.io.Serializable;

public class Attribute implements Serializable{

    private static final long serialVersionUID = -3957819204568415798L;

    private AttributeName name;

    private String value= null;

    public Attribute(AttributeName name, String value) {

        Assert.notNull(name,"Attribute name cannot be null ");

        this.name = name;
        this.value = value;
    }

    public AttributeName getName()  {
        return name;
    }

    public String getValue()  {
        return value;
    }

    public boolean equals(Object object)  {
        if (!(object instanceof Attribute)) {
            return false;
        }
        Attribute val = (Attribute) object;

        if (value == null) {
            if (val.getValue() == null) {
                return name.equals(val.getName());
            } else {
                return false;
            }
        }

        return ((name.equals(val.getName())) &&
                (value.equals(val.getValue())));
    }

    public int hashCode() {
        return name.hashCode() ^ (value == null ? 0 : value.hashCode());
    }

    public String toString() {
        return getName().lowerName() + " = " + getValue();
    }
}
