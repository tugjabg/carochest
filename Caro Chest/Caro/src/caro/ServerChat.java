package caro;
import java.util.Scanner;
import java.io.*;
import java.net.*;
public class ServerChat {
    static ServerSocket ss;
    public static User users[] = new User[10];
    public static int totalClientsOnline=0;


    public static void main(String args[]) throws Exception
    {
        try
        {
            ss = new ServerSocket(7777);

            for(int i=0;i<10;i++)
            {
                users[i] = new User(i+1,ss.accept());
                totalClientsOnline++;
            }

        }

        catch(Exception e)
        {
        }
    }


    public void sendMessageToAll(String msg)
    {
        for(int c=0;c<totalClientsOnline;c++)
        {
            try
            {
                users[c].sendMessage(msg);
            }

            catch(Exception e){}
        }
    }

    public void receiveAndSendFileToAll(Socket uSocket, int userID)
    {
        String workingDir = System.getProperty("user.dir");
        String FILE_TO_RECEIVED = workingDir+ "/imageReceived.jpg";
        int FILE_SIZE = 70000;
        int bytesRead;
        int current = 0;

        FileOutputStream fos;
        BufferedOutputStream bos;
        byte [] mybytearray  = new byte [FILE_SIZE];

        try {
            InputStream is = uSocket.getInputStream();
            fos = new FileOutputStream(FILE_TO_RECEIVED);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;

            bos.write(mybytearray, 0 , current);
            bos.close();
        }
        catch(Exception e){}
    }

}


class User extends Thread
{
    ServerChat tirth = new ServerChat();
    int userID;
    public Socket userSocket;
    public DataInputStream userDIS;
    public DataOutputStream userDOS;
    public Thread t;
    OutputStream os;


    public User(int id,Socket a)
    {
        try
        {
            userID = id;
            userSocket = a;
            userDIS = new DataInputStream(userSocket.getInputStream());
            userDOS = new DataOutputStream(userSocket.getOutputStream());
            System.out.println(userID+ " client connected.");

            t = new Thread(this);
            t.start();
        }
        catch(Exception e)
        {
            System.out.println("Exception caught in constructor.");
        }
    }


    public void run()
    {
        Scanner in = new Scanner(System.in);
        String message;
        while(true)
        {
            try
            {
                message = userDIS.readUTF();
                // chuỗi ký tự dùng để thông báo từ client là muốn tham gia chat
                if(message.equals("46511231dsfdsfsd#@$#$#@^$%#@*$#^"))
                    tirth.receiveAndSendFileToAll(userSocket, userID);
                else
                    tirth.sendMessageToAll(message);
            }

            catch(Exception e)
            {

            }

        }
    }


    public void sendMessage(String s)
    {
        try
        {
            userDOS.writeUTF(s);
        }

        catch(Exception e){}
    }


    public void sendFile(byte [] mybytearray)
    {
        try{

            os = userSocket.getOutputStream();
            userDOS.flush();
            os.flush();
            userDOS.writeUTF("46511231dsfdsfsd#@$#$#@^$%#@*$#^");
            os.write(mybytearray,0,mybytearray.length);

            userDOS.flush();

            os.flush();
            System.out.println("File forwarded.");

            //if (os != null)
            //   os.close();

        }
        catch(Exception e)
        {
            System.out.println("Exception in sendFile() method.");
        }

    }

}