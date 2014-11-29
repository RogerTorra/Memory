import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
   The Memory class creates the GUI for the
   MemoryUI House application.
*/

public class Memory extends JFrame
{
	//Back End
	int rows = 2;
   	int columns = 6;
   	int counter = 0;   	
	
	//Buttons and Labels and More!
   	private MemoryButton[][] memButtons = new MemoryButton[rows][columns];
	private JLabel[][] imgLabel = new JLabel[rows][columns];
	private JPanel[][] memPanel = new JPanel[rows][columns];
	
	//Math Stuff  
	int[] cards = new int[columns*rows];
	int compareCards = 0;
	int holdCardRow = 0;
	int holdCardColumn= 0;	
	boolean isFirst = true;
	int win = 0;

   	/**
      Constructor 
      Very simple, creates a 2x6 panels in grid Layout
   	 */

   public Memory() 
   {	   
	   //Math
	   Random rgen = new Random();  // Random number generator	   
	   int z = 1;
	   //--- Initialize the array to the ints 0-11
	   for (int i=0; i < cards.length; i++) 
	   {		   
		   cards[i] = z;
		   if( i%2 == 1)
		   {
			   z++;
		   }		   
	   }	    
	   //--- Shuffle by exchanging each element randomly
	   for (int i=0; i < cards.length; i++) 
	   {
		   int randomPosition = rgen.nextInt(cards.length);	   
		   int temp = cards[i];		   
		   cards[i] = cards[randomPosition];
		   cards[randomPosition] = temp;		   
	   }	   
	   
	   // Display a title.
      setTitle(MemoryUI.getLabels().getString("msg.title"));

      // Specify an action for the close button.
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      //Set layout
      setLayout(new GridLayout(rows, columns));          
      
      // Create the memPanelsAdd items buttons to the panel      
      for( int i = 0; i < rows; i++)
      {    	 
    	  for (int j = 0; j < columns; j++)
    	  {
    		  buildMemPanel(i, j);
    		  add(memPanel[i][j]);
    	  }    	  
      } 
      
      //Pack it and show it      
      pack();
      setVisible(true);
   }

   /**
      The buildMemPanel method builds the grid for the game Memory
   */
   
   private void buildMemPanel(int _i, int _j)
   {
	   //New Panel
	   memPanel[_i][_j] = new JPanel();
	   
	   //Set Layout
	   memPanel[_i][_j].setLayout(new GridLayout(2,1));	   
	  
	   //Fill with a button
	   memButtons[_i][_j] = new MemoryButton(_i, _j, cards[counter], false);
	   counter++;
	   
	   //Add an actionListener
	   memButtons[_i][_j].addActionListener(new FlipListener());
	   memButtons[_i][_j].setText(MemoryUI.getLabels().getString("msg.action"));
	  
	   //Create the Images
	   imgLabel[_i][_j] = new JLabel( new ImageIcon("Card" + 0 + ".jpg"));
	   
	   //Set up a border 
	   memPanel[_i][_j].setBorder(BorderFactory.createTitledBorder(" "));
	   
	   //Add them to the panel
	   memPanel[_i][_j].add(imgLabel[_i][_j]);
	   memPanel[_i][_j].add(memButtons[_i][_j]);	   
   }
   
   private class FlipListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
    	  Object obj = e.getSource();
    	  if(obj instanceof MemoryButton)
    	  {
    		  MemoryButton clicked = (MemoryButton)obj;
    		  int r = clicked.getRow();
    		  int c = clicked.getColumn();    		  
    		  ImageIcon tempImg = new ImageIcon("Card" + memButtons[r][c].getImageNumber() + ".jpg");
    		  imgLabel[r][c].setIcon(tempImg);
    		  
    		  //This fills a bunch of variables to compare later, 
    		  //turns off the card you pressed, and gets ready for the next flip
    		  if( isFirst)
    		  {
    			  compareCards = memButtons[r][c].getImageNumber();    			  
    			  holdCardRow = memButtons[r][c].getRow();
    			  holdCardColumn = memButtons[r][c].getColumn();
    			  memButtons[holdCardRow][holdCardColumn].setEnabled(false);    			
    			  isFirst = false;    			  
    		  }
    		  else
    		  {
    			  //If you got it right we will win if it is the last one, 
    			  //or we will disable and keep revealed for future play
    			  //If you get it right, we reset the pictures and we re-enable any buttons that were disabled
    			  if (compareCards == memButtons[r][c].getImageNumber())
    			  {
    				  win++;
    				  if (win == columns)
    				  {
    					  JOptionPane.showMessageDialog(null, MemoryUI.getLabels().getString("msg.win"));
    					  win = 0;    					  
    					  //Messy, but it gets the job done
    					  setVisible(false); //you can't see me!
    					  dispose(); //Destroy the JFrame object
    					  new Memory();
    				  }
    				  else
    				  {
    					  JOptionPane.showMessageDialog(null, MemoryUI.getLabels().getString("msg.one"));
    					  memButtons[holdCardRow][holdCardColumn].correct = true;
	    				  memButtons[r][c].correct = true;
	    				  memButtons[holdCardRow][holdCardColumn].setEnabled(false);
	    				  memButtons[r][c].setEnabled(false);
    				  }      				  
    			  }
    			  else
    			  {
    				  JOptionPane.showMessageDialog(null, MemoryUI.getLabels().getString("msg.notone"));
    				  memButtons[holdCardRow][holdCardColumn].setEnabled(true);
    				  tempImg = new ImageIcon("Card0.jpg");
    				  imgLabel[holdCardRow][holdCardColumn].setIcon(tempImg);
    				  imgLabel[r][c].setIcon(tempImg);    				     				  
    			  }
    			  isFirst = true;    			  
    		  }    		  
    	  }
      }      
   }
   
   /**
   The MemoryButton knows where it is, if it correct, 
   and what img is should display 
    */
   
   class MemoryButton extends JButton 
   {
	    private int x;
	    private int y;
	    private int imgNum;
	    boolean correct;

	    public MemoryButton(int _x, int _y, int _imgNum, boolean _correct) 
	    {
	        //super(text);
	        this.x = _x;
	        this.y = _y;
	        this.imgNum = _imgNum;
	        this.correct = _correct;
	    }

	    public int getRow() 
	    {
	        return x;
	    }

	    public int getColumn() 
	    {
	        return y;
	    }
	    public int getImageNumber() 
	    {
	        return imgNum;
	    }
	}   
}
