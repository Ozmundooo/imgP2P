import java.util.Scanner;

public class Main
{
	
	static Client[] clients = new Client[4];
	private static Scanner scan;

    public static void main (String args[]) throws Exception
    {
        int base = 10200;

        //Client alice = new Client(10201);
        //Client bob = new Client(10202);
        //Client carl = new Client(10203);

        for(int i = 0; i < clients.length; i++) {
            clients[i] = new Client(++base);
        }

        printClients();

        //Server s1 = new Server(10205);
        //Server s2 = new Server(10206);
        //Server s3 = new Server(10207);
        //Server s4 = new Server(10208);

        Server[] servers = new Server[4];

        for(int i = 0; i < servers.length; i++) {
            servers[i] = new Server(++base);
        }

        System.out.println("Available Servers:");
        for(int i = 0; i < Server.numOfServers; i++) {
            System.out.println(servers[i].serverNum + " ------ " + servers[i].getPortNum());
        }
                

        clients[0].sendClient("ClientImgs/image.jpg", servers[0].getPortNum());
        servers[0].recServer();


        System.out.println(servers[0].directoryName);

        servers[0].sendServer("fry.jpg", clients[0].getPortNum());
        clients[0].recClient();

        System.out.println("To create a query for a jpg you must state 'clientName REQUEST Fig(i)'");
        System.out.println("Where clientName is their clients name and Fig(i) is an jpg own by the server");
        
        System.out.println("Please enter the number of the client that is requesting.");
        int chosen = checkClients();
        
        while(chosen == 0) {
        	printClients();
        	System.out.println("Please enter a client value on the list.");
        	chosen = checkClients();
        }
        
        System.out.println(chosen);

    }
    
    public static void printClients() {
    	
    	 System.out.println("Available Clients:");
         for(int i = 0; i < Client.numOfClients; i++) {
             System.out.println(clients[i].clientNum + " ------ " + clients[i].getPortNum());
         }
         
    }
    
    public static int checkClients() {
    	
    	int chosen = 0;
    	scan = new Scanner(System.in);
    	char[] check = scan.nextLine().toCharArray();
        for(int i = 0; i < Client.numOfClients; i++) {
	        while(check.length > 1) {
	        	printClients();
	        	System.out.println("Please enter only a single digit of those indicated.");
	        	check = scan.nextLine().toCharArray();
	        }
	        if(clients[i].clientNum == Character.getNumericValue(check[0]))
	        	chosen = Character.getNumericValue(check[0]);
        }
    	return chosen;
    	
    }
    
}