import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;

class TextServer extends Frame implements ActionListener, Runnable, KeyListener
{

	Label lbl=new Label("Enter Message");
	TextArea txt = new TextArea(5,30);
	Button sbtn = new Button("Send");
    
	Socket socketClient;
	Thread t;
	String str;

	TextServer()
	{
		str = "";
		setTitle("SERVER");
		setSize(300,300);
		setLayout(new FlowLayout(FlowLayout.CENTER));

		add(lbl);add(txt);add(sbtn);
		sbtn.addActionListener(this);
		txt.addKeyListener(this);

		t = new Thread(this);
		t.start();
	}

	public void run()
	{
		
		try{
			
			InetAddress add = InetAddress.getLocalHost();
			ServerSocket serverObject = new ServerSocket(6666);
			socketClient = serverObject.accept();
			
			while(true)
			{
			DataInputStream din = new DataInputStream(socketClient.getInputStream());
	
			str = din.readUTF() + "\n";
			txt.setText(str);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}	

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() ==sbtn)
		{
			String data = txt.getText();
			try
			{
				DataOutputStream dout = new DataOutputStream(socketClient.getOutputStream());
		
				dout.writeUTF(data);
				dout.flush();
			}
			catch(Exception ee)
			{
				System.out.println(ee);
			}
		}

	}

	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}

	public void keyPressed(KeyEvent e) 
	{
        	if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			String data = txt.getText();
			try
			{
				DataOutputStream dout = new DataOutputStream(socketClient.getOutputStream());
		
				dout.writeUTF(data);
				dout.flush();
			}
			catch(Exception ee)
			{
				System.out.println(ee);
			}
         	}
      	}

	public static void main(String []ar)
	{
		new TextServer().show();
	}
}