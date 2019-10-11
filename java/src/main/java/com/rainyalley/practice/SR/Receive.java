/**   
 * @fileName Recive.java
 * @package org.sllx.SR
 * @copyright (c) 2014, sincerebravefight@gmail.com All Rights Reserved. 
 * @description TODO
 * @author sllx  
 * @date 2014-8-18 下午2:37:38
 * @modifiedBy sllx
 * @ModifiedDate 2014-8-18 下午2:37:38
   * Why & What is modified
 * @version V1.0
 */
package com.rainyalley.practice.SR;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @className Recive
 * @description TODO
 * @author sllx
 * @date 2014-8-18 下午2:37:38
 * @modifiedBy sllx
 * @ModifiedDate 2014-8-18 下午2:37:38
 * Why & What is modified
 */
public class  Receive extends Thread{
	@Override
	public void run() {
		try {
			DatagramSocket ds = new DatagramSocket(8888);
			while(true){
				byte[] recvBuf = new byte[2048];
				DatagramPacket rdp = new DatagramPacket(recvBuf,recvBuf.length);
				ds.receive(rdp);
				System.out.println("==============接收方=============");
				System.out.println("接收信息：" + new String(recvBuf, 0, rdp.getLength()));
				System.out.println("发送方地址：" + rdp.getAddress().getHostAddress() + ":" + rdp.getPort());
				
				byte[] buf = ("信息接收成功:" + new SimpleDateFormat("HH:mm:ss").format(new Date())).getBytes();
				DatagramPacket sdp = new DatagramPacket(buf, buf.length, rdp.getSocketAddress());
				ds.send(sdp);
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