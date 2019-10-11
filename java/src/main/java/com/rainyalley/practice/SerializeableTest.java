package com.rainyalley.practice;


import java.io.*;

/**
 * Created by sllx on 7/18/15.
 */
public class SerializeableTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/home/sllx/po"));
        oos.writeObject(new Po());
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/home/sllx/po"));
        Po po = (Po)ois.readObject();
        System.out.println(po.i);


        ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream("/home/sllx/pojo"));
        oos2.writeObject(new Pojo());
        ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream("/home/sllx/pojo"));
        Pojo pojo = (Pojo)ois2.readObject();
        System.out.println(pojo.i);
    }
}


class SPO implements Serializable{

    private void readObjectNoData() throws InvalidObjectException
    {
        System.out.println("readObjectNoData") ;
    }
}

class Po  implements Serializable{

    private static final long serialVersionUID = 1L;

    int i = 20;

    transient long j = 0L;

    Po(){}

    Po(int i){
        this.i = i;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        System.out.println("readObject") ;
        this.i = ois.readInt();
    }

    private void writeObject(ObjectOutputStream oos)throws IOException {
        oos.defaultWriteObject();
        System.out.println("writeObject") ;
        oos.writeInt(this.i);
    }



    private Object writeReplace(){
        System.out.println("writeReplace before write");
        return new SerializationProxy(this.i);
    }

    private static class SerializationProxy implements Serializable{
        int i;
        SerializationProxy(int i){
            this.i = i;
        }

        private Object readResolve(){
            System.out.println("readResolve after read");
            return new Po(i);
        }
    }

}



class Pojo implements Externalizable {

    private final static long serialVersionUID = 1l;

    int i = 10;

    /**
     * required
     */
    public Pojo(){}

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("writeExternal");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("readExternal");
    }
}