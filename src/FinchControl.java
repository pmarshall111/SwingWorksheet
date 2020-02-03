//All these imports are needed for the different parts of the interface
//Read up in the Javadocs on each of these classes...
import edu.cmu.ri.createlab.terk.robot.finch.Finch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;

//Our class needs to inherit functionality from 'JPanel' and 'JActionListener'
public class FinchControl extends JPanel implements ActionListener
{
    //All forms need a unique ID - ignore this!!!
    private static final long serialVersionUID = 1862962349L;
    //We are going to have three buttons
    private JButton startbutton,endbutton,exitbutton;
    //For this version of the program we are going to have two labels
    private JLabel timeValue, timeCaption,tempCaption, tempValue, lightCaption, lightValue,
            accelCaption, accelValue, orientationCaption, orientationValue;
    //The timer is for updating the labels at a regular interval
    private Timer timer;
    //Constructor for our form
    private Finch finch;

    public FinchControl()
    {
        finch = new Finch();
        //Set up the three buttons
        //Caption for first button
        startbutton = new JButton("Start Logging");
        //Centre the text vertically
        startbutton.setVerticalTextPosition(SwingConstants.CENTER);
        //Centre the text horizontally
        startbutton.setHorizontalTextPosition(AbstractButton.CENTER);
        //Short cut key of 'S'
        startbutton.setMnemonic(KeyEvent.VK_S);
        //Create the event/action 'Start' when clicked
        startbutton.setActionCommand("Start");

        //Caption for second button
        endbutton = new JButton("Stop Logging");
        //Centre the text vertically
        endbutton.setVerticalTextPosition(AbstractButton.CENTER);
        //Centre the text horizontally
        endbutton.setHorizontalTextPosition(AbstractButton.CENTER);
        //Short cut key of 'T'
        endbutton.setMnemonic(KeyEvent.VK_T);
        //Create the event/action 'Stop' when clicked
        endbutton.setActionCommand("Stop");
        //Initially the stop button is disabled
        endbutton.setEnabled(false);

        //Caption for third button
        exitbutton = new JButton("Exit");
        //Centre the text vertically
        exitbutton.setVerticalTextPosition(AbstractButton.CENTER);
        //Centre the text horizontally
        exitbutton.setHorizontalTextPosition(AbstractButton.CENTER);
        //Short cut key of 'X'
        exitbutton.setMnemonic(KeyEvent.VK_X);
        //Create the event/action 'Exit' when clicked
        exitbutton.setActionCommand("Exit");

        //Listen for actions from the three buttons
        //The current instance of 'this' class will process the actions for the buttons
        startbutton.addActionListener(this);
        endbutton.addActionListener(this);
        exitbutton.addActionListener(this);

        //This is the text that is displayed when we hover the mouse over the buttons
        startbutton.setToolTipText("Click this button to start logging...");
        endbutton.setToolTipText("Click this button to stop logging...");
        exitbutton.setToolTipText("This button is the only way you can exit this application...");

        //Create the labels
        timeCaption = new JLabel("Time: ");
        timeValue = new JLabel();
        //Only the contents label will have a border
        timeValue.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        setupCustomCaptions();
        addObjectsToPanel();

        //Create a timer every second (1000 m/s)
        //The current instance of 'this' class will process the actions for the timer
        timer = new Timer(1000, this);
        //Create the event/action 'Timer' every second
        timer.setActionCommand("Timer");
        //Start the timer straight away
        timer.setInitialDelay(0);

    }

    private void addObjectsToPanel() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(grid);

        constraints.gridy = 0;
        constraints.gridx = 0;
        add(startbutton, constraints);

        constraints.gridy = 0;
        constraints.gridx = 1;
        add(endbutton, constraints);

        constraints.gridy = 0;
        constraints.gridx = 2;
        add(exitbutton, constraints);

        constraints.gridy = 1;
        constraints.gridx = 0;
        add(timeCaption, constraints);

        constraints.gridy = 1;
        constraints.gridx = 1;
        add(timeValue, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        add(tempCaption, constraints);

        constraints.gridy = 2;
        constraints.gridx = 1;
        add(tempValue, constraints);

        constraints.gridy = 3;
        constraints.gridx = 0;
        add(lightCaption, constraints);

        constraints.gridy = 3;
        constraints.gridx = 1;
        add(lightValue, constraints);

        constraints.gridy = 4;
        constraints.gridx = 0;
        add(accelCaption, constraints);

        constraints.gridy = 4;
        constraints.gridx = 1;
        add(accelValue, constraints);

        constraints.gridy = 5;
        constraints.gridx = 0;
        add(orientationCaption, constraints);

        constraints.gridy = 5;
        constraints.gridx = 1;
        add(orientationValue, constraints);
    }

    private void setupCustomCaptions() {
        tempCaption = new JLabel("Temperature: ");
        tempValue = new JLabel();
        lightCaption = new JLabel("Light: ");
        lightValue = new JLabel();
        accelCaption = new JLabel("Acceleration: ");
        accelValue = new JLabel();
        orientationCaption = new JLabel("Orientation: ");
        orientationValue = new JLabel();
        setDefaultLabelVals();
    }

    private void setDefaultLabelVals() {
        tempValue.setText("N/A");
        lightValue.setText("N/A");
        accelValue.setText("N/A");
        orientationValue.setText("N/A");
    }

    private void updateLabelVals() {
        tempValue.setText(finch.getTemperature()+"");
        lightValue.setText((finch.getLeftLightSensor()+finch.getRightLightSensor())/2 + "");
        accelValue.setText("X: " + finch.getXAcceleration() + ", Y: " + finch.getYAcceleration() + ", Z: " + finch.getZAcceleration());
        orientationValue.setText(getFinchOrientation());
    }

    private String getFinchOrientation() {
        if (finch.isBeakUp()) {
            return "Pointing up";
        } else if (finch.isBeakDown()) {
            return "Pointing down";
        } else if (finch.isLeftWingDown()) {
            return "Left wing is down";
        } else if (finch.isRightWingDown()) {
            return "Right wing is down";
        } else if (finch.isFinchUpsideDown()) {
            return "Upside down";
        } else if (finch.isFinchLevel()) {
            return "Level";
        } else {
            return "Undefined";
        }
    }

    //The method must be implemented as we have inherited from 'JActionListener'
    //Every time a button is clicked or a timer event occurs this method is run
    //A total of four events are catered for here
    public void actionPerformed(ActionEvent e)
    {
        //Check for the 'Start' event
        if (e.getActionCommand().equals("Start"))
        {
            //Log that something has happened
            System.out.println("Start pressed");
            //Disable the 'Start' button
            startbutton.setEnabled(false);
            //Enable the 'Stop' button
            endbutton.setEnabled(true);
            //Start the 'Timer'
            timer.start();
        }
        //Check for the 'Stop' event
        if (e.getActionCommand().equals("Stop"))
        {
            //Log that something has happened
            System.out.println("Stop pressed");
            //Enable the 'Start' button
            startbutton.setEnabled(true);
            //Disable the 'Stop' button
            endbutton.setEnabled(false);
            //Stop the 'Timer'
            timer.stop();
            //Reset the text for the display label
            timeValue.setText("There is never enough time...");
            setDefaultLabelVals();
        }
        //Check for the 'Timer' event
        if (e.getActionCommand().equals("Timer"))
        {
            //Log that something has happened
            System.out.println("Timer");
            //Get the current time and date
            Calendar date = Calendar.getInstance();
            //Write it to the display label
            timeValue.setText(date.getTime().toString());
            updateLabelVals();
        }
        //Test for the 'Exit' action
        if (e.getActionCommand().equals("Exit"))
        {
            //Log that something has happened
            System.out.println("Exit");
            //Stop the timer
            timer.stop();
            //Get the parent JFrame of this panel
            JFrame parent = (JFrame)SwingUtilities.getWindowAncestor(this);
            //Hide the Window
            parent.setVisible(false);
            //Get rid of the Window
            parent.dispose();
        }
    }
}