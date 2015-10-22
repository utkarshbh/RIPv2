import java.net.Inet4Address;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


/**
 * @author Utkarsh Bhatia
 *
 */
public class Process extends UnicastRemoteObject implements ProcessInterface{

	//Declaring the variables
	Scanner scan;
	private Registry rgs;
	public static String localIP;
	Registry lookup;
	ProcessInterface processInter;
	public static ArrayList<RouteValues> routingTable;
	public static Integer update;
	public static HashMap<String, Integer> neighbourList;
	public static ArrayList<String> neighbourIP;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * constructor
	 * @throws RemoteException
	 */
	protected Process() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		try {
			scan = new Scanner(System.in);
			//binding the registry
			rgs = LocateRegistry.createRegistry(6363);
			rgs.rebind("processRIP", this);
			localIP = Inet4Address.getLocalHost().getHostAddress();
			routingTable = new ArrayList<RouteValues>();
			neighbourList = new HashMap<String, Integer>();
			neighbourIP = new ArrayList<String>();
			update = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * main method for the RIP Router
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		System.out.println("Starting RIP Router at this system");
		Process process = new Process();
		process.initiateRouter();
	}

	/**
	 * initiateRouter method which starts the router process 
	 */
	private void initiateRouter() {
		// TODO Auto-generated method stub
		try {
			String inputVal, inputWeight;
			System.out.println("RIP Router has been started at: " + localIP);
			ProcessListen processListen = new ProcessListen();
			while (true) {
				System.out.println("Add other Link('y') or Print Routing Table('p')");
				inputVal = scan.nextLine();
				if (inputVal.equalsIgnoreCase("y")) {
					if(neighbourIP.size() <3){
						System.out.println("Enter Router IP:");
						inputVal = scan.nextLine();
						System.out.println("Enter link weight");
						inputWeight = scan.nextLine();
						new Thread(new ForwardTable(inputVal,Integer.parseInt(inputWeight), localIP, false)).start();
					} else{
						System.out.println("You cannot add more than 2 neighbours to one router");
					}
				} else if(inputVal.equalsIgnoreCase("p")){
					//System.out.println("Size: " + routingTable.size());
					System.out.println("DestinationIP  -  Subnet  Mask  -  Next Hop   -   Distance");
					for (int i = 0; i < routingTable.size(); i++) {
						System.out.println(routingTable.get(i).DestIpAddr + " - " + routingTable.get(i).subnetMask + " - " + routingTable.get(i).nextHop + " - " + routingTable.get(i).distance);
					}
				}  else{
					System.out.println("Incorrect input. Try again.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see ProcessInterface#replyToconnect(java.lang.String)
	 */
	@Override
	public String replyToconnect(String IP) throws RemoteException{
		// TODO Auto-generated method stub
		return localIP;
	}

}
