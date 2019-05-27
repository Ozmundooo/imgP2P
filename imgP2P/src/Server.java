import java.net.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server
{
    static int numOfServers = 0;

    byte[]  recArray;
    byte[] sentArray;
    InetAddress ip;
    int portNum;
    DatagramSocket serverSocket;
    DatagramPacket sendPacket;
    DatagramPacket recPacket;
    String dirName;
    int serverNum;
    String directoryName;

    public Server(int port) throws Exception
    {
        this.sentArray = new byte[65535];
        this.recArray = new byte[65535];
        this.portNum = port;
        this.ip = InetAddress.getLocalHost();
        this.ip = InetAddress.getLocalHost();
        this.serverSocket = new DatagramSocket(portNum);
        this.serverNum = ++numOfServers;
        this.directoryName = "ServerImgs/"+serverNum+"/";
        //System.out.println("Server has been created with port number: " + portNum);
    }

    public Server(int port, String dirName) throws Exception
    {
        this.sentArray = new byte[65535];
        this.recArray = new byte[65535];
        this.portNum = port;
        this.ip = InetAddress.getLocalHost();
        this.serverSocket = new DatagramSocket(portNum);
        this.serverNum = ++numOfServers;
        this.directoryName = "ServerImgs/"+serverNum+"/";
        //System.out.println("Server has been created with port number: " + portNum);
    }

    public Server(int port, String dirName, InetAddress ip) throws Exception
    {
        this.sentArray = new byte[65535];
        this.recArray = new byte[65535];
        this.portNum = port;
        this.ip = ip;
        this.serverSocket = new DatagramSocket(portNum);
        this.serverNum = ++numOfServers;
        this.directoryName = "ServerImgs/"+serverNum+"/";
        //System.out.println("Server has been created with port number: " + portNum);
    }

    public int getNumOfServer(){return serverNum;}

    public String getDirectoryName(){return directoryName;}

    public int getPortNum(){return portNum;}

    public void sendServer(String filename, int recPortNum) throws Exception
    {
        sentArray = outputITBA(filename);
        sendPacket =
                new DatagramPacket(sentArray, sentArray.length, ip, recPortNum);
        serverSocket.send(sendPacket);
        System.out.println("Sending data to port number: " + sendPacket.getPort());
    }

    public void recServer() throws Exception
    {
        while (true)
        {
            // Step 2 : create a DatagramPacket to receive the data.
            recPacket = new DatagramPacket(recArray, recArray.length);

            // Step 3 : receive the data in byte buffer.
            serverSocket.receive(recPacket);
            outputBATI(recArray, directoryName);

            if(!(recArray == null))
            {
                System.out.println("Img Received");
                break;
            }
            // Clear the buffer after every message.
            recArray = new byte[65535];
        }
    }

    public boolean jpgExist(String filename)
    {
        if(!(Files.exists(Paths.get(directoryName+filename))))
            return true;
        else
            return false;
    }

    public static void outputBATI(byte[] bytesArr, String dirName) throws Exception
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytesArr);
        BufferedImage bImage2 = ImageIO.read(bis);
        ImageIO.write(bImage2, "jpg", new File(dirName +"/Fig(i).jpg") );
        System.out.println("image created(Located at " + dirName +"Fig(i).jpg)");
    }

    public byte[] outputITBA(String filename) throws Exception
    {
        BufferedImage image = ImageIO.read(new File(directoryName +filename));
        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",outputStream);
        return outputStream.toByteArray();
    }
}
