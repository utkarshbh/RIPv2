import java.rmi.ConnectException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Utkarsh Bhatia
 *
 */
public class ForwardTable implements Runnable{

	//Declaring the variables
	String linkAddress;
	String localAddress;
	Integer linkWeight;
	Registry lookup;
	Boolean registered;
	ProcessListenInterface processListenInter;
	
	/**
	 * @param linkAddress
	 * @param linkWeight
	 * @param localAddress
	 * @param registered
	 */
	public ForwardTable(String linkAddress, Integer linkWeight, String localAddress, Boolean registered) {
		super();
		//System.out.println("updating!!");
		this.linkAddress = linkAddress;
		this.localAddress = localAddress;
		this.linkWeight = linkWeight;
		this.registered = registered;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			lookup = LocateRegistry.getRegistry(linkAddress, 6364);
			processListenInter = (ProcessListenInterface) lookup.lookup("processRIPListen");
			if(registered == false){
				processListenInter.addToRoutingTable(localAddress, linkWeight, linkAddress);
			}
			new Thread(new AddToRouting(linkAddress, linkWeight)).start();
			//System.out.println("Connected to: " + linkAddress+ " from: " + localAddress);
			while(true){
				synchronized (Process.class) {
					synchronized (this) {
						processListenInter.updateRouting(localAddress, Process.routingTable);
					}
				}
				//System.out.println("Connected to: " + linkAddress);
				Thread.sleep(1000);
			}
		} catch (ConnectException e) {
			//exception to know when a neighbor router fails. Starts the trigger update
			System.err.println("Router " + linkAddress + " is down");
			new Thread(new ProcessDown(linkAddress)).start();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
