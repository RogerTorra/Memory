import java.util.Locale;
import java.util.ResourceBundle;


/**
   This program creates an instance of the Memory class
   which displays the GUI for the Memory application.
*/

public class MemoryUI
{
	private static Locale CAT = new Locale("ca", "CA");
	private Locale[] supportedLocales = {
		    Locale.ENGLISH,
		    CAT
		};
	private static ResourceBundle labels = ResourceBundle.getBundle("ca_CA", CAT);
    public static ResourceBundle getLabels() {
    	return labels;
    }
public static void main(String[] args) 
   {
      new Memory();
   }
}
