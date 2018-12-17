import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JPanel implements ActionListener {
	JLabel userLabel=new JLabel("Choose a username: ");
	JTextField userTF=new JTextField();
	JLabel passLabel=new JLabel("Password");
	JPasswordField passTF=new JPasswordField();
	JLabel passCLabel=new JLabel("Confirm Password");
	JPasswordField passCTF=new JPasswordField();
	JButton register=new JButton("Register");
	JButton back=new JButton("Back");
	
	public Register() {
		JPanel loginPanel=new JPanel();
		loginPanel.setLayout(new GridLayout(4,2));
		loginPanel.add(userLabel);
		loginPanel.add(userTF);
		loginPanel.add(passLabel);
		loginPanel.add(passTF);
		loginPanel.add(passCLabel);
		loginPanel.add(passCTF);
		loginPanel.add(register);
		loginPanel.add(back);
		
		register.addActionListener(this);
		back.addActionListener(this);
		
		add(loginPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource()==back) {
			Login login =(Login)getParent();		
			login.cardLayout.show(login,"login");
		}
		if(e.getSource()==register && passTF.getPassword().length>0 && userTF.getText().length()>0) {
			String pass=new String(passTF.getPassword());
			String confirm=new String(passCTF.getPassword());
			if(pass.equals(confirm)) {
				try {
					BufferedReader input=new BufferedReader(new FileReader("passwords.txt"));
					String line=input.readLine();
					while(line!=null) {
						StringTokenizer st=new StringTokenizer(line);
						if(userTF.getText().equals(st.nextToken())) {
							System.out.println("User already exists");
							return;
						}
						line=input.readLine();
					}
					input.close();
					MessageDigest md=MessageDigest.getInstance("SHA-256");
					md.update(pass.getBytes());
					byte byteData[]=md.digest();
					StringBuffer sb=new StringBuffer();
					
					for(int i=0; i<byteData.length; i++) {
						sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100,16).substring(1));
						
					}
					
					BufferedWriter output=new BufferedWriter(new FileWriter("passwords.txt",true));
					output.write(userTF.getText()+" "+sb.toString()+"\n");
					output.close();
					Login login =(Login)getParent();		
					login.cardLayout.show(login,"login");
					
					
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}

}
