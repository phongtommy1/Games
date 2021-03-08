/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pointandclick;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author Abdullah K
 */
public class Sudoku extends javax.swing.JFrame{
    
    private final int MAX_ROWS = 9; //rows for 9x9 grid
    private final int MAX_COLS = 9;  //columns for 9x9 grid
    private final int GRID_DIMENSION = MAX_ROWS*MAX_COLS;
    
    private int score;  //ongoing score variable
    private int quitScore = 0;
    int timesSubmitted = 0;  //to be used for the submission
    
    //for the high score functionality
    private String playerInitials;
    private int highScore1;
    private int highScore2;
    private int highScore3;
    private String highScore1Initial;
    private String highScore2Initial;
    private String highScore3Initial;
    
    public JTextField fieldGrid[][] = new JTextField[MAX_ROWS][MAX_COLS];  //create a 2D array of JTextFields to hold the numbers that the user will enter
    public String solutionGrid[][] = new String[MAX_ROWS][MAX_COLS]; //2D array to hold the sudoku solution
    /**
     * Creates new form Sudoku
     */
    public Sudoku(int Score) {
        initComponents();
        drawGrid(); //call methods to draw grid and set the solution
        setSolution();
        showDate();
        showTime();
        score = Score; //set the score in the constructor
        scoreLabel.setText("SCORE: " + score);
        scoreLabel.setToolTipText("Current Score");  //tool tip text for score label
    }
    
    public void showDate()
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        dateLabel.setText(dateFormat.format(date));
    }

    public void showTime()
    {
        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Date date = new Date();
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
                timeLabel.setText(timeFormat.format(date));
            }
        }).start();
    }
     
    public void drawGrid()  //the plan is to later compare all the array values of the user entered array with the sudoku key array to determine if player wins or not
    {
        sudokuPanel.setSize(350,350);  //set the dimensions of the panel
        sudokuPanel.setLayout(new GridLayout(9,9)); //make the grid with GridLayout
        sudokuPanel.setBackground(Color.BLACK);
        sudokuPanel.setToolTipText("ENTER A NUMBER FROM 1-9 SO THAT THERE ARE NO DUPLICATE NUMBERS IN ANY ROW OR COLUMN"); //set tool tip text for the panel
        
        //JTextField fieldGrid[][] = new JTextField[MAX_ROWS][MAX_COLS];  //create a 2D array of JTextFields to hold the numbers that the user will enter
        for (int i = 0; i < MAX_ROWS; i++)  //iterate through the rows and columns of the grid
        {
            for(int j = 0; j < MAX_COLS; j++)
            {
                fieldGrid[i][j] = new JTextField(); //set the field grid box at that row and column index
                fieldGrid[i][j].setHorizontalAlignment(JTextField.CENTER);  //set the text to be centered inside the JTextField
                //fieldGrid[i][j].setToolTipText("ENTER A NUMBER FROM 1-9 SO THAT THERE ARE NO DUPLICATE NUMBERS IN ANY ROW OR COLUMN");  //set the tool tip text
                
                addListener(fieldGrid[i][j]); //add the listener for input validation
                sudokuPanel.add(fieldGrid[i][j]);  //add the field grid box to the panel
            }    
        }
        
        //set the initial boxes (hints)
        
        //row 0
        fieldGrid[0][0].setText("8");
        fieldGrid[0][0].setEditable(false);
        fieldGrid[0][3].setText("4");
        fieldGrid[0][0].setEditable(false);
        fieldGrid[0][5].setText("6");
        fieldGrid[0][0].setEditable(false);
        fieldGrid[0][8].setText("7");
        fieldGrid[0][0].setEditable(false);
        
        //row 1
        fieldGrid[1][6].setText("4");
        fieldGrid[1][6].setEditable(false);
        
        //row 2
        fieldGrid[2][1].setText("1");
        fieldGrid[2][1].setEditable(false);
        fieldGrid[2][6].setText("6");
        fieldGrid[2][6].setEditable(false);
        fieldGrid[2][7].setText("5");
        fieldGrid[2][7].setEditable(false);
        
        //row 3
        fieldGrid[3][0].setText("5");
        fieldGrid[3][0].setEditable(false);
        fieldGrid[3][2].setText("9");
        fieldGrid[3][2].setEditable(false);
        fieldGrid[3][4].setText("3");
        fieldGrid[3][4].setEditable(false);
        fieldGrid[3][0].setText("5");
        fieldGrid[3][0].setEditable(false);
        fieldGrid[3][6].setText("7");
        fieldGrid[3][6].setEditable(false);
        fieldGrid[3][7].setText("8");
        fieldGrid[3][7].setEditable(false);
        
        //row 4
        fieldGrid[4][4].setText("7");
        fieldGrid[4][4].setEditable(false);
        
        //row 5
        fieldGrid[5][1].setText("4");
        fieldGrid[5][1].setEditable(false);
        fieldGrid[5][2].setText("8");
        fieldGrid[5][2].setEditable(false);
        fieldGrid[5][4].setText("2");
        fieldGrid[5][4].setEditable(false);
        fieldGrid[5][6].setText("1");
        fieldGrid[5][6].setEditable(false);
        fieldGrid[5][8].setText("3");
        fieldGrid[5][8].setEditable(false);
        
        //row 6
        fieldGrid[6][1].setText("5");
        fieldGrid[6][1].setEditable(false);
        fieldGrid[6][2].setText("2");
        fieldGrid[6][2].setEditable(false);
        fieldGrid[6][7].setText("9");
        fieldGrid[6][7].setEditable(false);
        
        //row 7
        fieldGrid[7][2].setText("1");
        fieldGrid[7][2].setEditable(false);
        
        //row 8
        fieldGrid[8][0].setText("3");
        fieldGrid[8][0].setEditable(false);
        fieldGrid[8][3].setText("9");
        fieldGrid[8][3].setEditable(false);
        fieldGrid[8][5].setText("2");
        fieldGrid[8][5].setEditable(false);
        fieldGrid[8][8].setText("5");
        fieldGrid[8][8].setEditable(false);
        
        //later we can copy the user entered JTextField array to a string or int array like: copyArray[i][j] = fieldGrid[i][j].getText();
        //this way we can compare the copy array with the solution array     
    }
    
    public void addListener(JTextField box)  //THIS WORKS NOW!
    {
        //add a keyListener to the JTextField to handle key events
        box.addKeyListener(new KeyAdapter() {
                
            public void keyPressed(KeyEvent key)  //call the keyPresed method and pass a KeyEvent as a parameter
            {
                char c = key.getKeyChar();  //get the char that the user enters when pressing a key
                
                if(box.isEditable())  //make sure that the condition check only applies to boxes that are editable (we don't want the hints to be altered)
                {
                    if(c < '1' || c > '9')  //if user enters a number less than 1 or greater than 9
                    {
                        if(c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_ENTER)  //also mustcheck if user pressed the backspac or enter key because we will allow those
                        {
                            key.consume();  //consume the key
                            JFrame errorFrame = new JFrame("ERROR");  //show the error dialog box
                            JOptionPane.showMessageDialog(errorFrame,"ENTER A NUMBER FROM 1-9");
                            box.setText("");  //reset the JTextField to empty
                        } 
                    }
                    else
                    {
                        box.setText("");  //no action must be done
                    }
                }
            }
        });  //end the keyAdapter method
    }
       
    public void setSolution()
    {
        //set the completed solution grid
        
        //row 0
        solutionGrid[0][0] = "8";
        solutionGrid[0][1] = "3";
        solutionGrid[0][2] = "5";
        solutionGrid[0][3] = "4";
        solutionGrid[0][4] = "1";
        solutionGrid[0][5] = "6";
        solutionGrid[0][6] = "9";
        solutionGrid[0][7] = "2";
        solutionGrid[0][8] = "7";
       
        
        //row 1
        solutionGrid[1][0] = "2";
        solutionGrid[1][1] = "9";
        solutionGrid[1][2] = "6";
        solutionGrid[1][3] = "8";
        solutionGrid[1][4] = "5";
        solutionGrid[1][5] = "7";
        solutionGrid[1][6] = "4";
        solutionGrid[1][7] = "3";
        solutionGrid[1][8] = "1";
        
        
        //row 2
        solutionGrid[2][0] = "4";
        solutionGrid[2][1] = "1";
        solutionGrid[2][2] = "7";
        solutionGrid[2][3] = "2";
        solutionGrid[2][4] = "9";
        solutionGrid[2][5] = "3";
        solutionGrid[2][6] = "6";
        solutionGrid[2][7] = "5";
        solutionGrid[2][8] = "8";
        
        
        //row 3
        solutionGrid[3][0] = "5";
        solutionGrid[3][1] = "6";
        solutionGrid[3][2] = "9";
        solutionGrid[3][3] = "1";
        solutionGrid[3][4] = "3";
        solutionGrid[3][5] = "4";        
        solutionGrid[3][6] = "7";        
        solutionGrid[3][7] = "8";
        solutionGrid[3][8] = "2";
        
        
        //row 4
        solutionGrid[4][0] = "1";
        solutionGrid[4][1] = "2";
        solutionGrid[4][2] = "3";
        solutionGrid[4][3] = "6";
        solutionGrid[4][4] = "7";
        solutionGrid[4][5] = "8";
        solutionGrid[4][6] = "5";
        solutionGrid[4][7] = "4";
        solutionGrid[4][8] = "9";
        
        
        //row 5
        solutionGrid[5][0] = "7";  
        solutionGrid[5][1] = "4";      
        solutionGrid[5][2] = "8";
        solutionGrid[5][3] = "5";  
        solutionGrid[5][4] = "2";
        solutionGrid[5][5] = "9";  
        solutionGrid[5][6] = "1";
        solutionGrid[5][7] = "6";  
        solutionGrid[5][8] = "3";
        
        
        //row 6
        solutionGrid[6][0] = "6";
        solutionGrid[6][1] = "5";        
        solutionGrid[6][2] = "2";
        solutionGrid[6][3] = "7";
        solutionGrid[6][4] = "8";
        solutionGrid[6][5] = "1";
        solutionGrid[6][6] = "3";
        solutionGrid[6][7] = "9";
        solutionGrid[6][8] = "4";
        
        
        //row 7
        solutionGrid[7][0] = "9";
        solutionGrid[7][1] = "8";
        solutionGrid[7][2] = "1";
        solutionGrid[7][3] = "3";
        solutionGrid[7][4] = "4";
        solutionGrid[7][5] = "5";
        solutionGrid[7][6] = "2";
        solutionGrid[7][7] = "7";
        solutionGrid[7][8] = "6";
        
        
        //row 8
        solutionGrid[8][0] = "3";
        solutionGrid[8][1] = "7"; 
        solutionGrid[8][2] = "4"; 
        solutionGrid[8][3] = "9";
        solutionGrid[8][4] = "6"; 
        solutionGrid[8][5] = "2";
        solutionGrid[8][6] = "8"; 
        solutionGrid[8][7] = "1"; 
        solutionGrid[8][8] = "5";
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SudokuTitle = new javax.swing.JLabel();
        dateTimePanel = new javax.swing.JPanel();
        dateLabel = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        submitButton = new javax.swing.JButton();
        quitButton = new javax.swing.JButton();
        sudokuPanel = new javax.swing.JPanel();
        scoreLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        SudokuTitle.setBackground(new java.awt.Color(51, 204, 255));
        SudokuTitle.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        SudokuTitle.setForeground(new java.awt.Color(153, 0, 153));
        SudokuTitle.setText("SUDOKU");
        SudokuTitle.setToolTipText("SUDOKU! (press esc to close the program or press F1 to display credits)");
        SudokuTitle.setOpaque(true);

        dateTimePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dateTimePanel.setToolTipText("Current date and time");

        dateLabel.setText("DATE");

        timeLabel.setText("TIME");

        javax.swing.GroupLayout dateTimePanelLayout = new javax.swing.GroupLayout(dateTimePanel);
        dateTimePanel.setLayout(dateTimePanelLayout);
        dateTimePanelLayout.setHorizontalGroup(
            dateTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dateTimePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dateLabel)
                .addGap(18, 18, 18)
                .addComponent(timeLabel)
                .addGap(32, 32, 32))
        );
        dateTimePanelLayout.setVerticalGroup(
            dateTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dateTimePanelLayout.createSequentialGroup()
                .addGroup(dateTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeLabel)
                    .addComponent(dateLabel))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        submitButton.setText("SUBMIT");
        submitButton.setToolTipText("Press to check answers");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        quitButton.setText("QUIT");
        quitButton.setToolTipText("Press to quit the game");
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });

        sudokuPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sudokuPanel.setMaximumSize(new java.awt.Dimension(350, 350));
        sudokuPanel.setMinimumSize(new java.awt.Dimension(350, 350));

        javax.swing.GroupLayout sudokuPanelLayout = new javax.swing.GroupLayout(sudokuPanel);
        sudokuPanel.setLayout(sudokuPanelLayout);
        sudokuPanelLayout.setHorizontalGroup(
            sudokuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 349, Short.MAX_VALUE)
        );
        sudokuPanelLayout.setVerticalGroup(
            sudokuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 357, Short.MAX_VALUE)
        );

        scoreLabel.setText("SCORE");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SudokuTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(submitButton))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateTimePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sudokuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(quitButton)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SudokuTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(quitButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(submitButton, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dateTimePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(sudokuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        // TODO add your handling code here:
        timesSubmitted++;  //increment timesSubmitted to make sure that points only get deducted for first submission
        int initialScore = 540;  //initialize the max points to be gained in the sudoku game
        int sudokuScore = 0;  //initialize the user's sudoku score to 0 (will be updated based on how many incorrect fields there are)
        int numWrong = 0;  //stores the number of incorrect fields
        int pointsLost = 0;  //to hold the points that the user will lose for every incorrect field
        
        for (int i = 0; i < MAX_ROWS; i++)  //iterate through the rows and columns of the grid
        {
            for(int j = 0; j < MAX_COLS; j++)
            {           
                if(!fieldGrid[i][j].getText().equals(solutionGrid[i][j]))  //compare each element in the 2D fieldGrid array to its corresponding element in the answer key array and check for non-matches
                {
                    numWrong++; //increment numWrong for each non-match
                    fieldGrid[i][j].setBackground(Color.red);  //set the background color of that field to red so that the user knows which one they got wrong
                }
                else
                {
                    fieldGrid[i][j].setBackground(Color.white);  //set the background color back to white if the field is corrected
                }
            }    
        }
        
        if(timesSubmitted == 1)  //only remove points if it is the first submission
        {
           pointsLost = numWrong * 10;
           sudokuScore = initialScore - pointsLost;
           score += sudokuScore;
           scoreLabel.setText("SCORE: " + score);
        }
         if(numWrong == 0)
        {
           scoreLabel.setText("SCORE: " + score);
           JFrame winFrame = new JFrame("CORRECT");  //show the error dialog box
           JOptionPane.showMessageDialog(winFrame,"YOU SOLVED SUDOKU CORRECTLY!");
           new EndGame(score).setVisible(true); //send the user to the EndGame
           dispose();
        }
        else
        {
           JFrame loseFrame = new JFrame("INCORRECT");  //show the error dialog box
           JOptionPane.showMessageDialog(loseFrame,"YOUR SOLUTION IS INCORRECT!");
        } 
        
    }//GEN-LAST:event_submitButtonActionPerformed

    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitButtonActionPerformed
        // TODO add your handling code here:
        
        score = quitScore + score;  //player gets no points if they quit
        new EndGame(score);
        dispose();
    }//GEN-LAST:event_quitButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sudoku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sudoku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sudoku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sudoku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sudoku(0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel SudokuTitle;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JPanel dateTimePanel;
    private javax.swing.JButton quitButton;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JButton submitButton;
    private javax.swing.JPanel sudokuPanel;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables
}
