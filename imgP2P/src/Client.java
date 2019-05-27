import java.net.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class Client
{

    static int numOfClients = 0;

    byte sentArray[];
    byte recArray[];
    int portNum;
    InetAddress ip;
    DatagramSocket clientSocket;
    DatagramPacket clientSend;
    DatagramPacket clientRec;
    String dirName;
    int clientNum;

    public Client() throws Exception
    {
        this.sentArray = new byte[65535];
        this.recArray = new byte[65535];
        this.portNum = 10200;
        this.ip = InetAddress.getLocalHost();
        this.clientSocket = new DatagramSocket();
        this.clientSend =
                new DatagramPacket(sentArray, sentArray.length, ip, portNum);
        this.clientRec = new DatagramPacket(recArray, recArray.length);
        this.clientNum = ++numOfClients;
        //System.out.println("Client has been creatred with port number: " + portNum);
    }

    public Client(int port) throws Exception
    {
        this.sentArray = new byte[65535];
        this.recArray = new byte[65535];
        this.portNum = port;
        this.ip = InetAddress.getLocalHost();
        this.clientSocket = new DatagramSocket(port);
        this.clientSend =
                new DatagramPacket(sentArray, sentArray.length, ip, portNum);
        this.clientRec =
                new DatagramPacket(recArray, recArray.length);
        this.dirName = "";
        this.clientNum = ++numOfClients;
        //System.out.println("Client has been created with port number: " + portNum);
    }

    public Client(int port, String dirName) throws Exception
    {
        this.sentArray = new byte[65535];
        this.recArray = new byte[65535];
        this.portNum = port;
        this.ip = InetAddress.getLocalHost();
        this.clientSocket = new DatagramSocket(port);
        this.clientSend =
                new DatagramPacket(sentArray, sentArray.length, ip, portNum);
        this.clientRec =
                new DatagramPacket(recArray, recArray.length);
        this.dirName = dirName;
        this.clientNum = ++numOfClients;
        //System.out.println("Client has been created with port number: " + portNum);
    }

    public Client(int port, String dirName, InetAddress ip) throws Exception
    {
        this.sentArray = new byte[65535];
        this.recArray = new byte[65535];
        this.portNum = port;
        this.ip = ip;
        this.clientSocket = new DatagramSocket(port);
        this.clientSend =
                new DatagramPacket(sentArray, sentArray.length, ip, portNum);
        this.clientRec =
                new DatagramPacket(recArray, recArray.length);
        this.dirName = dirName;
        this.clientNum = ++numOfClients;
        //System.out.println("Client has been created with port number: " + portNum);
    }

    public int getNumOfClients(){return clientNum;}

    public InetAddress getIp()
    {
        return ip;
    }

    public DatagramSocket getClientSocket()
    {
        return clientSocket;
    }

    public int getPortNum() {return portNum;}

    public byte[] getSentArray()
    {
        return sentArray;
    }

    public void sendClient(String filename, int recPortNum)throws Exception
    {
        sentArray = outputITBA(filename);
        clientSend = new DatagramPacket
                (sentArray, sentArray.length,ip,recPortNum);
        clientSocket.send(clientSend);
        System.out.println("Img has been sent");
    }

    public void recClient() throws Exception
    {
        while (true)
        {
            clientRec = new DatagramPacket(recArray, recArray.length);
            clientSocket.receive(clientRec);
            outputBATI(recArray);

            if(!(recArray == null))
            {
                System.out.println("Img Recieved");
                break;
            }
            // Clear the buffer after every message.
            recArray = new byte[65535];
            //Socket client = new Socket();
        }
    }

    public byte[] outputITBA(String filename) throws Exception
    {
        BufferedImage image = ImageIO.read(new File(filename));
        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",outputStream);
        return outputStream.toByteArray();
    }

    public void outputBATI(byte[] bytesArr) throws Exception
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytesArr);
        BufferedImage bImage2 = ImageIO.read(bis);
        ImageIO.write(bImage2, "jpg", new File("ClientImgs/output.jpg") );
        System.out.println("Img created");
    }
}
