package com.szu.twowayradio.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.util.Log;

public class UdpHelper {
	
	private String serverIp;
	private int serverPort;
	private int timeout;
    private DatagramSocket socket;
    private InetAddress address;
    
    //use the default network configuration 
    public UdpHelper()
    {
    	this(NetWorkConfig.DEFAULT_SERVER_IP,NetWorkConfig.DEFAULT_PORT
    		,NetWorkConfig.DEFAULT_TIMEOUT);
    }
    public UdpHelper(String ip,int port)
    {
    	this(ip,port,NetWorkConfig.DEFAULT_TIMEOUT);
    }
    public UdpHelper(String ip,int port,int timeout)
    {
    	this.serverIp=ip;
    	this.serverPort=port;
    	this.timeout=timeout;
    }
    /**
     * should be called after get a UdpHelper instance
     */
    public boolean initNetWork()
    {
        try{
    	address=InetAddress.getByName(serverIp);
    	socket=new DatagramSocket(serverPort);
    	socket.setSoTimeout(timeout);
        }catch(SocketException exception)
        {
        	exception.printStackTrace();
        	Log.e("UdpClient in init", "SocketException");
        	return false;
        }catch(UnknownHostException exception)
        {
        	exception.printStackTrace();
        	Log.e("UdpClient in init", "UnknownHostException");
        	return false;
        }
        return true;
        
    }
    
    /**
     * 
     * @param content
     * @return return true if send successful ,others return false
     */
    public boolean send(byte[] content)
    {
    	DatagramPacket packet=new DatagramPacket(content, content.length,address,serverPort);
    	try{
    	    if(socket!=null) 
    		    socket.send(packet);
    	    else
    	    {
    	    	Log.e("UdpClient in send", "NullPointerException");
    	    	throw new NullPointerException("socket is null"); 
    	    }
    	}catch(IOException e)
    	{
    		Log.e("UdpClient in send", "IOException");
    		return false;
    	}
    	return true;
    }
    /**
     * 
     * @return return DatagramPacket when socket receive successful,others return null
     */
    public DatagramPacket receive()
    {
    	DatagramPacket pack=null;
    	if(socket!=null)
    	{
    		try {
				socket.receive(pack);

				return pack;
				
			} catch (IOException e) {
				
				e.printStackTrace();
				Log.e("UdpClient in receive", "IOException");
				throw new NullPointerException("socket is null"); 
			}
    	}else
    	{
    		return null;
    	}
    	
    }
    public void close()
    {
    	if(null!=socket)
    	{
    		socket.close();
    		socket=null;
    	}
    }
    
    
    /**
     * only for test
     */
    public void test(byte[] content)
    {
    	
    	DatagramPacket packet=new DatagramPacket(content, content.length,address,serverPort);
    	try{
    	socket.send(packet);
    	}catch(IOException e)
    	{
    		Log.e("UdpClient in test", "IOException");
    	}
    	
    }
}