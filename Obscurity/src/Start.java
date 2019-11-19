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
		
		
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		//*/
		
	}

}
