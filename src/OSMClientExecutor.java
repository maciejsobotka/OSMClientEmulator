import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pwr.osm.data.representation.MapPosition;

/**
* Emulator klientów aplikacji łączących sie przez UDP z serwerem buforowym.
* @author Sobot
*  
*/
class OSMClientExecutor
{
	private static ExecutorService execService = Executors.newFixedThreadPool(10);
	
	public static void main(String args[])
	{
		List<MapPosition> points1 = new ArrayList<MapPosition>();
		List<MapPosition> points2 = new ArrayList<MapPosition>();
	   	List<MapPosition> points3 = new ArrayList<MapPosition>();
	   	for(int i=0; i<2; ++i){
	   		points1.add(new MapPosition(3516.12415+i, 17.042345+i));
	   		points2.add(new MapPosition(4513.823442425+i, 17.0411231345+i));
	   		points3.add(new MapPosition(625.1235435415+i, 17.04345345345+i));
	   		}
   		Thread clientOne = new Thread(new ClientThread(points1));
	   	Thread clientTwo = new Thread(new ClientThread(points2));
	   	Thread clientThree = new Thread(new ClientThread(points3));
	   	clientOne.setName("clientOne");
	   	clientTwo.setName("clientTwo");
	   	clientThree.setName("clientThree");
	   	execService.execute(clientOne);
	   	execService.execute(clientTwo);
	   	execService.execute(clientThree);
	   	execService.shutdown();
	}
}
