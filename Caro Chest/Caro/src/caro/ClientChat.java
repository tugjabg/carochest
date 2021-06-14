package caro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


import java.util.Scanner;
import java.io.*;
import java.net.*;
public class ClientChat {
    static Scanner in = new Scanner(System.in);
    static DataOutputStream dos;
    static DataInputStream dis;
    static Socket s;
    static BossServer server;
    static String username;


    //-------For File sharing------------
    static FileInputStream fis = null;
    static BufferedInputStream bis = null;
    static OutputStream os = null;


    //-------------------------------





    static JTextPane chatMessages = new JTextPane();
    static JScrollPane JPchatMessages = new JScrollPane(chatMessages);

    static String msgHistory = "";


    public static void main(final String args[]) throws IOException
    {
        JFrame frame = new JFrame("Trò chuyện");
        username = args[0];
        System.out.println(username);


//----------------------------------------------------------------
//---------------------- FRAME 2----------------------------------
//----------------------------------------------------------------

        frame.setSize(400,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());


        JLabel helloUser = new JLabel("Xin chào !");
        frame.add(helloUser, new GridBagConstraints(0,0,1,1,3,1,GridBagConstraints.CENTER,            GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));


        JButton logOutButton = new JButton("Đăng xuất");
        frame.add(logOutButton, new GridBagConstraints(2,0,1,1,.25,1,GridBagConstraints.CENTER,            GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));


        chatMessages.setEditable(false);
        frame.add(JPchatMessages, new GridBagConstraints(0,1,3,1,1.0,100.0,GridBagConstraints.CENTER,            GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));



        JTextField message = new JTextField(20);
        frame.add(message, new GridBagConstraints(0,2,1,1,.5,1,GridBagConstraints.CENTER,            GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));



        JButton send = new JButton("Gửi");
        frame.add(send, new GridBagConstraints(1,2,
                1,1,50,
                .25,GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));
        send.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent sendButtonClick)
            {
                String msg = message.getText();
                message.setText(null);
                try
                {
                    dos.writeUTF(username + " : " + msg);
                }
                catch(IOException e){}
            }
        });

        helloUser.setText("Xin chào "+username+"!");

        try
        {
            s = new Socket("localhost", 7777);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());

            server = new BossServer(dis);
            Thread t = new Thread(server);
            t.start();

            frame.setVisible(true);
        }catch (Exception e){
            e.printStackTrace();
        }

        JFileChooser fileSelector = new JFileChooser();
        fileSelector.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent fileChooserEvent)
            {
                String command = fileChooserEvent.getActionCommand();
                if(command.equals(JFileChooser.APPROVE_SELECTION))
                {
                    File fileToBeSent = fileSelector.getSelectedFile();

                    try{
                        byte [] fileByteArray  = new byte [(int)fileToBeSent.length()];


                        fis = new FileInputStream(fileToBeSent);
                        bis = new BufferedInputStream(fis);
                        bis.read(fileByteArray,0,fileByteArray.length);
                        os = s.getOutputStream();
                        dos.flush();
                        os.flush();
                        dos.flush();
                        dos.writeUTF("46511231dsfdsfsd#@$#$#@^$%#@*$#^");
                        os.write(fileByteArray,0,fileByteArray.length);
                        os.flush();
                        System.out.println("Done.");
                        updateMessageArea("File Successfully Sent.");

                        if (os != null)
                            os.close();


                    }
                    catch(Exception e){}

                }
            }
        });


    }



    public static void updateMessageArea(String msg)
    {
        msgHistory = msgHistory + "\n";
        msgHistory = msgHistory + msg;
        chatMessages.setText(msgHistory);
    }


    public static void reconnect()
    {
        try
        {
            s.close();
            s = new Socket("192.168.0.101", 7777);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            server = new BossServer(dis);
            Thread newConnection = new Thread(server);
            newConnection.start();

        }
        catch(Exception e)
        {
            System.out.println("Exception caught in reconnect().");
        }
    }



}



class BossServer extends Thread
{
    DataInputStream disServer;
    String secretCode = new String("46511231dsfdsfsd#@$#$#@^$%#@*$#^");

    public BossServer(DataInputStream z)
    {
        disServer = z;
    }

    public void run()
    {



        while(true)
        {
            try
            {
                String str = disServer.readUTF();
                if(str.equals(secretCode))
                { }
                else
                    ClientChat.updateMessageArea(str);
            }

            catch(IOException e)
            {
                System.out.println("Exception in run method. Reconnecting..");

                ClientChat.reconnect();
                break;
            }

        }
    }
}
