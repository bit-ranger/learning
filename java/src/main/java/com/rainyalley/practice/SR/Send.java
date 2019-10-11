/**   
 * @fileName Send.java
 * @package org.sllx.SR
 * @copyright (c) 2014, sincerebravefight@gmail.com All Rights Reserved. 
 * @description TODO
 * @author sllx  
 * @date 2014-8-18 下午2:36:59
 * @modifiedBy sllx
 * @ModifiedDate 2014-8-18 下午2:36:59
   * Why & What is modified
 * @version V1.0
 */
package com.rainyalley.practice.SR;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @className Send
 * @description TODO
 * @author sllx
 * @date 2014-8-18 下午2:36:59
 * @modifiedBy sllx
 * @ModifiedDate 2014-8-18 下午2:36:59
 * Why & What is modified
 */
public class Send extends Thread{
	@Override
	public void run() {
		try {
			DatagramSocket ds = new DatagramSocket();
			InetAddress ip = InetAddress.getLocalHost();
			while(true){
				byte[] buf = ("hello：" + new SimpleDateFormat("HH:mm:ss").format(new Date())).getBytes();
				DatagramPacket sdp = new DatagramPacket(buf, buf.length, ip, 8888);
				ds.send(sdp);
				
				byte[] recvBuf = new byte[2048];
				DatagramPacket rdp = new DatagramPacket(recvBuf,recvBuf.length);
				ds.receive(rdp);
				System.out.println("==============发送方=============");
				System.out.println("接收方反馈信息：" + new String(recvBuf, 0, rdp.getLength()));
				System.out.println();
				try {
					sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
