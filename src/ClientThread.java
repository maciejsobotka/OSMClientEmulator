//==============================================================================
//	Wątek klienta łączącego sie przez UDP
//  z serwerem WWW
//==============================================================================

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

import pwr.osm.data.representation.MapPosition;

public class ClientThread implements Runnable{
	
	private List<MapPosition> points;
	
	public ClientThread(List<MapPosition> points){
		this.points = points;
	}
	public void run(){
		try {
			sendPacket();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void sendPacket() throws Exception{
	      DatagramSocket clientSocket = new DatagramSocket();
	      InetAddress IPAddress = InetAddress.getByName("localhost");
          // tworzenie DatagramPacket
	      ByteArrayOutputStream byteStream = new ByteArrayOutputStream(5000);
          ObjectOutputStream os = new ObjectOutputStream(new
	                                  BufferedOutputStream(byteStream));
          os.flush();
          os.writeObject(points);
          os.flush();    
	      byte[] sendData = byteStream.toByteArray();
	      byte[] receiveData = new byte[5000];
	      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
	      clientSocket.send(sendPacket);
	      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	      clientSocket.receive(receivePacket);
	      
	      ByteArrayInputStream inputStream = new ByteArrayInputStream(receiveData);
	      ObjectInputStream is = new ObjectInputStream(new 
    		  						BufferedInputStream(inputStream));
	      List<MapPosition> newPoints = (List<MapPosition>) is.readObject();
	      System.out.println(Thread.currentThread().getName() + " - [From Server WWW] Received data: " + newPoints);
	      clientSocket.close();		
	}

}
