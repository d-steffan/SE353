import javax.swing.JOptionPane;

public class Start {
/**
 * Creates login and application window if login is successful
 * @param args not used
 */
	public static void main(String[] args) {
		
		/**
		 * Launch the application.
		 */
		try {
			Process p;
			p = Runtime.getRuntime().exec("powershell if ($PSVersionTable.PSVersion.Major -lt 5){exit 1} ");
			p.waitFor();
			if (p.exitValue()==1)
			{
				JOptionPane.showMessageDialog(null, "PowerShell 5.1.0.0 or higher is required to execute the tokenization script. Please upgrade PowerShell and try again.", "PowerShell Version too low", JOptionPane.ERROR_MESSAGE);
			} else {
				
				LoginWindow lwindow = new LoginWindow();
				lwindow.frame.setLocationRelativeTo(null);
				lwindow.frame.setVisible(true);
				while (!lwindow.getSuccesfulLogin())
					Thread.sleep(1000);
						ApplicationWindow window = new ApplicationWindow(lwindow.Username);
				lwindow.frame.dispose();
				window.frame.setLocationRelativeTo(null);
				window.frame.setVisible(true);
			}
		}
		
		
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		//*/
		
	}

}
