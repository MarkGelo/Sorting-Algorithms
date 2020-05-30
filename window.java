import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Objects;

public class window extends JPanel {
    //declaring gui stuff as instance variables for easy referencing
    JFrame frame;
    JButton start;
    JButton stop;
    JCheckBox compare;
    JComboBox algoBox;
    JTextField numberField;
    //window size
    int windowWidth = 1000;
    int windowHeight = 600;
    int numPlays = 0; // just so at start of gui, the array thats painted already dont get changed
    //init algo object which initializes arrays
    sortingAlgorithm algo = new sortingAlgorithm();
    paintIntegers[] currentArr = algo.origArray.clone();
    //arraylist for the animation
    ArrayList<paintIntegers[]> sorted;
    //for the animation
    boolean stopBool = false;
    int count = 0; //inner count for the animation
    int swaps = 0; // for the swap in gui
    boolean comparing;
    boolean showTime = false;
    Timer tm = new Timer(10, new ActionListener(){ // timer for the animation and painting
        public void actionPerformed(ActionEvent e){
            if(stopBool){ // if the user clicked stop
                ((Timer)e.getSource()).stop();
                //sets these buttons to false/true
                start.setEnabled(true);
                stop.setEnabled(false);
            }else if(count == sorted.size()){ // if finished animating
                //resets the count
                count = 0;
                start.setEnabled(true); // returns the buttons and stuff back to what they supposed to be
                stop.setEnabled(false);
                algoBox.setEnabled(true);
                numberField.setEnabled(true);
                showTime = true; // shows the time
                tm.stop();
            }else{
                // makes currentArr to be the next one in the arraylist
                // for the animation
                currentArr = sorted.get(count).clone();
                //check for red bars
                //if has red bar, then not swapping
                boolean checkRed = false;
                for(int i = 0; i < currentArr.length; i++){
                    if(currentArr[i].col == Color.red){
                        checkRed = true;
                    }
                }
                if(!checkRed){
                    swaps += 1;
                }
                count += 1;
            }
            repaint();
        }
    });

    public void setup(){
        //sets the frame and rules
        frame = new JFrame();
        frame.setSize(windowWidth, windowHeight);
        frame.setResizable(false); //user cant resize
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //when exit on window, stops the program
        frame.setTitle("Homework - CS 430"); //sets the window title

        //add a drop down box for the algorithms
        algoBox = new JComboBox(algo.algos);
        add(algoBox);

        //compare checkbox
        // keeps the arrays the same for each one to compare
        compare = new JCheckBox("Compare");
        compare.setFocusPainted(false); //looks ugly without
        add(compare);

        JLabel numberToSort = new JLabel("N: "); //description text
        add(numberToSort);

        numberField = new JTextField("", 5); //text field for user input for amount
        numberField.setToolTipText("Enter the amount of numbers you want to sort");
        add(numberField);

        //button to start sorting
        start = new JButton("Sort");
        start.setFocusPainted(false); //remove the ugly border around the text
        start.setToolTipText("Click to start sorting");
        add(start);

        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(e.getSource() == start){
                    //first few if/else statements are for stop button and for comparing
                    //if comparing then keeps the arrays the same
                    if(compare.isSelected()){
                        comparing = true;
                    }else{
                        comparing = false;
                    }
                    if(stopBool){ //for if user clicked stop, then sort, starts animation again
                        stopBool = false;
                        start.setEnabled(false);
                        tm.start();
                        stop.setEnabled(true);
                        return;
                        // with the numberfield so it doesnt make a new array when starting animation each time
                    }else if(isNumber(numberField.getText()) && !comparing){ // non default n
                        algo.setSize(Integer.parseInt(numberField.getText()));
                    }else if(isNumber(numberField.getText()) && comparing){ // compare selected and number
                        if(algo.n != Integer.parseInt(numberField.getText())){ //if same n, dont shuffle cuz should keep same
                            // if not same n, then makes a new n array and shuffle
                            algo.setSize(Integer.parseInt(numberField.getText()));
                        }
                        numberField.setEnabled(false);
                    }else if(numPlays == 0){ // so when user first clicks sort, doesnt change the current painted graph
                        //do nothing
                        numPlays += 1;
                    }
                    else if(!comparing) { // default n and not comparing
                        //for when the user input non default but then removed so back to default
                        algo.setSize(100);
                        comparing = false;
                    }
                    sorted = algo.sort((String) Objects.requireNonNull(algoBox.getSelectedItem()));
                    // dont want null
                    //for the buttons and stuff to be disabled/enabled when clicked sort
                    showTime = false;
                    stop.setEnabled(true);
                    start.setEnabled(false);
                    numberField.setEnabled(false);
                    algoBox.setEnabled(false);
                    swaps = 0; //restarts # of swap
                    tm.start();
                }
            }
        });

        //button to stop the sorting
        stop = new JButton("Stop");
        stop.setFocusPainted(false); //remove the ugly border around the text
        stop.setToolTipText("Stop sorting");
        add(stop);
        stop.setEnabled(false);

        //stop action listnere
        stop.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //opposite of what it was, so stops
                stopBool = !stopBool;
            }
        });

        //adds all of the things added above to the frame.
        frame.add(this);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {//for the paintin and for the animation
        super.paintComponent(g);
        Graphics2D main = (Graphics2D) g;//for the double, makes it easier
        double width = (double)(windowWidth - 100) / algo.origArray.length;
        //for the number of swaps to show
        g.drawString((String) algoBox.getSelectedItem() + " Swaps: " + Integer.toString(swaps), 25,25);
        //paints the array as bar graphs
        for(int i = 0; i < currentArr.length; i++) {
            double height = currentArr[i].val * ((double) (windowHeight - 100) / algo.getMax());
            double xPos = 50 + width * i;
            double yPos = 50;
            main.setColor(currentArr[i].col);
            main.fill(new Rectangle2D.Double(xPos, yPos, width, height));
        }
        if(showTime){
            g.drawString("(Backend) Algorithm Time: " + algo.timeElapsed + " ns", 700, 25);
        }
    }

    public boolean isNumber(String in){//for the nubmberfield, checks if the string is a number
        try{
            int test = Integer.parseInt(in);
            return true;
        }catch(Exception ie){
            return false;
        }
    }
    public static void main(String[] args){
        //starts the window
        window init = new window();
        init.setup();
    }
}
