package com.rainyalley.practice.jvm;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;



public class JvmMessage{
    /*public static void main(String[] args){
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        String name = bean.getName();
        int index = name.indexOf('@');
        String pid = name.substring(0, index);
        //这里要区分操作系统
        HotSpotVirtualMachine machine = (HotSpotVirtualMachine) new sun.tools.attach.WindowsAttachProvider().attachVirtualMachine(pid);
        InputStream is = machine.heapHisto("-all");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int readed;
        byte[] buff = new byte[1024];
        while((readed = is.read(buff)) > 0)
            os.write(buff, 0, readed);
        is.close();

        machine.detach();
        System.out.println(os);
    }*/
}
