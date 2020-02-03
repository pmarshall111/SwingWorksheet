import javax.swing.JFrame;

//This class creates our Finch Control Form!
public class FinchGUI implements Runnable
{
    //Since the class 'Runnable' is an implementation we have to define this method
    public void run()
    {
        //Create and set up the Window
        JFrame frame = new JFrame("Finch Sensors");
        //Disable the exit 'X' and close menu
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //Create and set up the content Pane based on our FinchControl class
        FinchControl newContentPane = new FinchControl();
        //All content Panes must be opaque apparently...
        newContentPane.setOpaque(true);
        //Put the Pane in the Window!
        frame.setContentPane(newContentPane);
        //Move to point 250,250 (offset from top left) and size to 400,400 pixels
        frame.setBounds(250,250,400,400);
        //Un-comment the following line to resize the Window so that all the controls fit... 
        frame.pack();
        //Display the frame.
        frame.setVisible(true);
    }
}