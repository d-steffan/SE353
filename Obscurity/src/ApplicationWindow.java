import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
/**Options bitmask:<br>
 * 0b00000001 : Remove Comments<br>
 * 0b00000010 : Remove NewLines<br>
 * 0b00000100 : Randomize Variable Names 8 digits<br>
 * 0b00001000 : Randomize Variable Names 1 digit<br>
 * 0b00010000 : Obfuscate Members<br>
 * 0b00100000 : Obfuscate Strings<br>
 * 0b01000000 : currently not in use<br>
 * 0b10000000 : currently not in use<br>
 * 
 * @author Dominik Steffan
 *
 */
public class ApplicationWindow implements ActionListener{

	JFrame frame;
	JButton btnNewButton,SaveFile,Obscure;
	JLabel lblNewLabel,lblNewLabel2,lblUsername;
	String OpenFileName = ".\\src\\HelloWorld.ps1",SaveFileName = "C:\\temp\\newScript.ps1",username;
	JCheckBox RemoveComments,RemoveNewLine,ObfuscateMembers,ObfuscateStrings;
	JPanel RandomizeVar;
	JRadioButton RandVar0,RandVar1,RandVar8;

	/**
	 * Create the application.
	 */
	public ApplicationWindow(String user) {
		username = user;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 520, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblUsername = new JLabel("Username: "+username);
		lblUsername.setBounds(10,11,400,14);
		frame.getContentPane().add(lblUsername);
		
		lblNewLabel = new JLabel(OpenFileName);
		lblNewLabel.setBounds(10, 30, 400, 14);
		frame.getContentPane().add(lblNewLabel);
		
		lblNewLabel2 = new JLabel(SaveFileName);
		lblNewLabel2.setBounds(10, 54, 400, 14);
		frame.getContentPane().add(lblNewLabel2);
		
		btnNewButton = new JButton("Open File");
		btnNewButton.setBounds(400, 31, 100, 23);
		btnNewButton.addActionListener(this);
		frame.getContentPane().add(btnNewButton);
		
		SaveFile = new JButton("Output File");
		SaveFile.setBounds(400, 54, 100, 23);
		SaveFile.addActionListener(this);
		frame.getContentPane().add(SaveFile);
		
		Obscure = new JButton("Obfuscate");
		Obscure.setBounds(400, 227, 100, 23);
		Obscure.addActionListener(this);
		frame.getContentPane().add(Obscure);
		
		RemoveComments = new JCheckBox("Remove Comments");
		RemoveComments.setBounds(5,85,150,14);
		RemoveComments.setSelected(true);
		frame.getContentPane().add(RemoveComments);
		
		RemoveNewLine = new JCheckBox("Remove NewLines");
		RemoveNewLine.setBounds(5,105,150,14);
		RemoveNewLine.setSelected(true);
		frame.getContentPane().add(RemoveNewLine);
		
		RandomizeVar = new JPanel();
		RandomizeVar.setBounds(155,85,180,90);
		RandomizeVar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Randomize Variable Names"));
		frame.getContentPane().add(RandomizeVar);
		
		RandVar0 = new JRadioButton("No");
		RandVar1 = new JRadioButton("1 digit");
		RandVar8 = new JRadioButton("8 digits", true);
		ButtonGroup bg = new ButtonGroup();
		bg.add(RandVar0);
		bg.add(RandVar1);
		bg.add(RandVar8);
		RandomizeVar.setLayout(new GridLayout(3,1));
		RandomizeVar.add(RandVar0);
		RandomizeVar.add(RandVar1);
		RandomizeVar.add(RandVar8);
		
		ObfuscateMembers = new JCheckBox("Obfuscate Members");
		ObfuscateMembers.setBounds(5,125,150,14);
		ObfuscateMembers.setSelected(true);
		frame.getContentPane().add(ObfuscateMembers);
		
		ObfuscateStrings = new JCheckBox("Obfuscate Strings");
		ObfuscateStrings.setBounds(5,125,150,14);
		ObfuscateStrings.setSelected(true);
		frame.getContentPane().add(ObfuscateStrings);
		
	}

	/**
	 * @param options bitmask that is compiled of all checkboxes and radiobuttons.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(btnNewButton)) {
			JFileChooser jfc = new JFileChooser();
			jfc.setCurrentDirectory(new File(".\\src"));
			int result = jfc.showOpenDialog(btnNewButton);
			if (result==JFileChooser.APPROVE_OPTION) {
				OpenFileName = jfc.getSelectedFile().getAbsolutePath();
				lblNewLabel.setText(OpenFileName);
			}
		}
		
		if (e.getSource().equals(SaveFile)) {
			JFileChooser jfc = new JFileChooser();
			jfc.setFileFilter(new FileNameExtensionFilter("PowerShell Script","ps1"));
			int result = jfc.showSaveDialog(SaveFile);
			if (result==JFileChooser.APPROVE_OPTION) {
				SaveFileName = jfc.getSelectedFile().getAbsolutePath();
				lblNewLabel2.setText(SaveFileName);
			}
		}
		
		if (e.getSource().equals(Obscure)) {
			ArrayList<Token> Tokens = null;
			
			int options = 0;
			
			if (RemoveComments.isSelected())
				options+=0b1;
			if (RemoveNewLine.isSelected())
				options+=0b10;
			if (RandVar8.isSelected())
				options+=0b100;
			if (RandVar1.isSelected())
				options+=0b1000;
			if (ObfuscateMembers.isSelected())
				options+=0b10000;
			if (ObfuscateMembers.isSelected())
				options+=0b100000;

			
			
			Process p;
			try {
				p = Runtime.getRuntime().exec("powershell .\\src\\Export-TokensToCSV.ps1 -Argumentlist \"-Path "+OpenFileName+" -Destination $env:TEMP\\Tokenized.csv\"");
			
				p.waitFor();
				String temppath = System.getenv("TEMP")+"\\Tokenized.csv";
				//System.out.println(temppath);
				Tokens = ReadCSV.GetTokens(temppath);
				ProcessTokens.Process(Tokens, options);
				ProcessTokens.WriteProcessedTokens(SaveFileName);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
