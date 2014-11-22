/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NCACST376;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JButton;

/**
 *
 * @author MLSSD
 */
public class CalendarTest extends javax.swing.JFrame {

    /**
     * Creates new form CalendarTest
     */
    private int[][] grid;
    private JButton[][] buttonGrid;
    private String[] months;
    private int[] monthLength;
    private int currentMonth;
    private int currentYear;
    
    //construct
    public CalendarTest() {
        initComponents();
        makeGrid();
        startScreen();
        
    }
    //open the popup window
    public void eventPop()
    {
        //this gets called by every button whenever it is clicked, it should be where we enter the event info
        clickDay click = new clickDay();
        click.setVisible(true);
    }
    //click on previous month button
    public void prevMonth()
    {
        if (currentMonth == 1)
        {
            //if it is january then move to the previous year
            currentMonth = 12;
            currentYear = currentYear - 1;
        }
        else {
            
            currentMonth = currentMonth - 1;
            //System.out.println(currentMonth+"");
        }
        //get first day of the month on the selected month and year
        int firstDayofMonth = getFirstDayOfMonth(currentYear, currentMonth);
        //set the labels to show the current month and current year
        jLabel1.setText(months[currentMonth -1]);
        jLabel2.setText(currentYear + "");
        //repopulate the grid with the correct days of the month
        populateMonth(firstDayofMonth, monthLength[currentMonth-1]);
    }
    //clicked on the next month button
    public void nextMonth()
    {
        //change to the next month
        if (currentMonth == 12)
        {
            //if it is december then move to the next year
            currentMonth = 1;
            currentYear = currentYear + 1;
        }
        else {
            
            currentMonth = currentMonth+1;
        }
        //get first day of the month on the selected month and year
        int firstDayofMonth = getFirstDayOfMonth(currentYear, currentMonth);
        //set the labels to show the current month and current year
        jLabel1.setText(months[currentMonth -1]);
        jLabel2.setText(currentYear + "");
        //repopulate the grid with the correct days of the month
        populateMonth(firstDayofMonth, monthLength[currentMonth-1]);
        
    }
    //make them final so they are not overrideable (not like it matters)
    public final void startScreen()
    {
        //only used when the program loads, it will give the accurate month for the user
        //create months
        months = new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        monthLength = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        //construct the calendar and show the current month, and year
        int thisyear = thisYear();
        int thismonth = thisMonth();
        //will be used for changing from month to month
        currentMonth = thismonth;
        currentYear = thisyear;
        //change the label's text
        jLabel1.setText(months[thismonth -1]);
        jLabel2.setText(thisyear + "");
        //populate the calendar using the first day of this month 
        int firstDayofMonth = getFirstDayOfMonth(thisyear, thismonth);
        //System.out.println(firstDayofMonth);
        populateMonth(firstDayofMonth, monthLength[thismonth-1]);
        
    }
    //populates the jFrame's buttons 
    public final void makeGrid()
    {
        //create grid of numbers, and create grid of buttons
        grid = new int[6][7];
        buttonGrid = new JButton[6][7];
        JButton[] g1 = {jButton1, jButton2, jButton3, jButton4, jButton5, jButton6, jButton7};
        buttonGrid[0] = g1;
        JButton[] g2 = {jButton8, jButton9, jButton10, jButton11, jButton12, jButton13, jButton14};
        buttonGrid[1] = g2;
        JButton[] g3 = {jButton15, jButton16, jButton17, jButton18, jButton19, jButton20, jButton21};
        buttonGrid[2] = g3;
        JButton[] g4 = {jButton22, jButton23, jButton24, jButton25, jButton26, jButton27, jButton28};
        buttonGrid[3] = g4;
        JButton[] g5 = {jButton29, jButton30, jButton31, jButton32, jButton33, jButton34, jButton35};
        buttonGrid[4] = g5;
        JButton[] g6 = {jButton36, jButton37, jButton38, jButton39, jButton40, jButton41, jButton42};
        buttonGrid[5] = g6;
        
    }
    public void resetGrid()
    {
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[i].length; j ++)
            {
                grid[i][j] = 0; 
                buttonGrid[i][j].setText("");
                //System.out.print(grid[i][j]+ "  ");
            }
            //System.out.println("");
        }
    }
    public void populateMonth(int startDay, int end) {
        //startAdding is used for the day of the month, it will only start to increment when we find the first day of the month
        resetGrid();
        int startAdding = 1;
        //has the month started yet?
        boolean monthStarted = false;
        //this will hold the end of the month, used to stop putting days on the calendar past that day
        int endOfMonth = end;
        //calculate leap year
        GregorianCalendar gc = new GregorianCalendar(currentYear, currentMonth-1, 1);
        if (gc.isLeapYear(currentYear) && currentMonth == 2)
        {
            //account for leap year, february should end one day later
            endOfMonth = endOfMonth + 1;
        }
        //update the buttons on the calendar with the days of the month
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                //check if the month has started or j is the start day, and end once the end of the month is reached
                if ((j == startDay || monthStarted) && (startAdding <= endOfMonth)) {
                    monthStarted = true;
                    //the grid represents the day
                    grid[i][j] = startAdding;
                    //update the button to the weekday
                    
                    buttonGrid[i][j].setText(Integer.toString(startAdding));
                    //increment day
                    startAdding = startAdding + 1;
                }
                //System.out.print(grid[i][j]+ "  ");
            }
            //System.out.println("");
        }
    }
    //get the current month
    public int thisMonth()
    {
        GregorianCalendar gc = new GregorianCalendar();
        //get the current time
        Date monthStartDate = new Date(gc.getTime().getTime());
        //format the date
        SimpleDateFormat format = new SimpleDateFormat("MM");
        String s = format.format(monthStartDate);
        int month = Integer.valueOf(s);
        //return the number of month
        return month;
    }
    //get the current year
    public int thisYear()
    {
        GregorianCalendar gc = new GregorianCalendar();
        //get current time
        Date monthStartDate = new Date(gc.getTime().getTime());
        //format the date
        SimpleDateFormat format = new SimpleDateFormat("YYYY");
        String s = format.format(monthStartDate);
        int year = Integer.valueOf(s);
        //return the year number
        return year;
    }
    //get today
    public int Today()
    {
        GregorianCalendar gc = new GregorianCalendar();
        //get the current time
        Date monthStartDate = new Date(gc.getTime().getTime());
        //format to day of the week
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        String s = format.format(monthStartDate);
        //return string representation of the weekday today
        return getDay(s);
        
    }
    //get the day given from the weekday
    public int getDay(String s)
    {
        String[] days = new String[] {"Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        int count = 0;
        for (int i = 0; i <days.length; i++)
        {
            //find the weekday according to the days array
            if (s.equalsIgnoreCase(days[i]))
            {
                break;
            }
            count = count + 1;
        }
        //return weekday
        return count;
    }
    //get the first day of this month
    public int getFirstDayOfMonth(int year, int month)
    {
        //create a gregoriancalendar by passing the specified year, month, and day 1
        GregorianCalendar gc = new GregorianCalendar(year, month-1, 1);
        //get the calendar as a date
        Date monthStartDate = new Date(gc.getTime().getTime());
        //format the date to show the weekday 
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        //find the first day of that month and year and return it
        
        return getDay(format.format(monthStartDate));
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton43 = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(java.awt.Color.white);
        setForeground(java.awt.Color.white);

        jButton1.setBackground(java.awt.SystemColor.activeCaption);
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton1.setToolTipText("");
        jButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setName(""); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(java.awt.SystemColor.activeCaption);
        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton2.setToolTipText("");
        jButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton2.setName(""); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(java.awt.SystemColor.activeCaption);
        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton3.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton3.setToolTipText("");
        jButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton3.setName(""); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(java.awt.SystemColor.activeCaption);
        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton4.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton4.setToolTipText("");
        jButton4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton4.setName(""); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(java.awt.SystemColor.activeCaption);
        jButton5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton5.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton5.setToolTipText("");
        jButton5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton5.setName(""); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(java.awt.SystemColor.activeCaption);
        jButton6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton6.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton6.setToolTipText("");
        jButton6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton6.setName(""); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(java.awt.SystemColor.activeCaption);
        jButton7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton7.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton7.setToolTipText("");
        jButton7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton7.setName(""); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(java.awt.SystemColor.activeCaption);
        jButton8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton8.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton8.setToolTipText("");
        jButton8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton8.setName(""); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(java.awt.SystemColor.activeCaption);
        jButton9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton9.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton9.setToolTipText("");
        jButton9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton9.setName(""); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(java.awt.SystemColor.activeCaption);
        jButton10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton10.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton10.setToolTipText("");
        jButton10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton10.setName(""); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setBackground(java.awt.SystemColor.activeCaption);
        jButton11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton11.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton11.setToolTipText("");
        jButton11.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton11.setName(""); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setBackground(java.awt.SystemColor.activeCaption);
        jButton12.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton12.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton12.setToolTipText("");
        jButton12.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton12.setName(""); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setBackground(java.awt.SystemColor.activeCaption);
        jButton13.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton13.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton13.setToolTipText("");
        jButton13.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton13.setName(""); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setBackground(java.awt.SystemColor.activeCaption);
        jButton14.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton14.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton14.setToolTipText("");
        jButton14.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton14.setName(""); // NOI18N
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setBackground(java.awt.SystemColor.activeCaption);
        jButton15.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton15.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton15.setToolTipText("");
        jButton15.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton15.setName(""); // NOI18N
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setBackground(java.awt.SystemColor.activeCaption);
        jButton16.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton16.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton16.setToolTipText("");
        jButton16.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton16.setName(""); // NOI18N
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setBackground(java.awt.SystemColor.activeCaption);
        jButton17.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton17.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton17.setToolTipText("");
        jButton17.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton17.setName(""); // NOI18N
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setBackground(java.awt.SystemColor.activeCaption);
        jButton18.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton18.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton18.setToolTipText("");
        jButton18.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton18.setName(""); // NOI18N
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton19.setBackground(java.awt.SystemColor.activeCaption);
        jButton19.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton19.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton19.setToolTipText("");
        jButton19.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton19.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton19.setName(""); // NOI18N
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setBackground(java.awt.SystemColor.activeCaption);
        jButton20.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton20.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton20.setToolTipText("");
        jButton20.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton20.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton20.setName(""); // NOI18N
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton21.setBackground(java.awt.SystemColor.activeCaption);
        jButton21.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton21.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton21.setToolTipText("");
        jButton21.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton21.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton21.setName(""); // NOI18N
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton22.setBackground(java.awt.SystemColor.activeCaption);
        jButton22.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton22.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton22.setToolTipText("");
        jButton22.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton22.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton22.setName(""); // NOI18N
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton23.setBackground(java.awt.SystemColor.activeCaption);
        jButton23.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton23.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton23.setToolTipText("");
        jButton23.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton23.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton23.setName(""); // NOI18N
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton24.setBackground(java.awt.SystemColor.activeCaption);
        jButton24.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton24.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton24.setToolTipText("");
        jButton24.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton24.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton24.setName(""); // NOI18N
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jButton25.setBackground(java.awt.SystemColor.activeCaption);
        jButton25.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton25.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton25.setToolTipText("");
        jButton25.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton25.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton25.setName(""); // NOI18N
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jButton26.setBackground(java.awt.SystemColor.activeCaption);
        jButton26.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton26.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton26.setToolTipText("");
        jButton26.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton26.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton26.setName(""); // NOI18N
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jButton27.setBackground(java.awt.SystemColor.activeCaption);
        jButton27.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton27.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton27.setToolTipText("");
        jButton27.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton27.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton27.setName(""); // NOI18N
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jButton28.setBackground(java.awt.SystemColor.activeCaption);
        jButton28.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton28.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton28.setToolTipText("");
        jButton28.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton28.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton28.setName(""); // NOI18N
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        jButton29.setBackground(java.awt.SystemColor.activeCaption);
        jButton29.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton29.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton29.setToolTipText("");
        jButton29.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton29.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton29.setName(""); // NOI18N
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jButton30.setBackground(java.awt.SystemColor.activeCaption);
        jButton30.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton30.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton30.setToolTipText("");
        jButton30.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton30.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton30.setName(""); // NOI18N
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        jButton31.setBackground(java.awt.SystemColor.activeCaption);
        jButton31.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton31.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton31.setToolTipText("");
        jButton31.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton31.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton31.setName(""); // NOI18N
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jButton32.setBackground(java.awt.SystemColor.activeCaption);
        jButton32.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton32.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton32.setToolTipText("");
        jButton32.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton32.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton32.setName(""); // NOI18N
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        jButton33.setBackground(java.awt.SystemColor.activeCaption);
        jButton33.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton33.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton33.setToolTipText("");
        jButton33.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton33.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton33.setName(""); // NOI18N
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        jButton34.setBackground(java.awt.SystemColor.activeCaption);
        jButton34.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton34.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton34.setToolTipText("");
        jButton34.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton34.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton34.setName(""); // NOI18N
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        jButton35.setBackground(java.awt.SystemColor.activeCaption);
        jButton35.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton35.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton35.setToolTipText("");
        jButton35.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton35.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton35.setName(""); // NOI18N
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        jButton36.setBackground(java.awt.SystemColor.activeCaption);
        jButton36.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton36.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton36.setToolTipText("");
        jButton36.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton36.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton36.setName(""); // NOI18N
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });

        jButton37.setBackground(java.awt.SystemColor.activeCaption);
        jButton37.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton37.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton37.setToolTipText("");
        jButton37.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton37.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton37.setName(""); // NOI18N
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });

        jButton38.setBackground(java.awt.SystemColor.activeCaption);
        jButton38.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton38.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton38.setToolTipText("");
        jButton38.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton38.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton38.setName(""); // NOI18N
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        jButton39.setBackground(java.awt.SystemColor.activeCaption);
        jButton39.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton39.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton39.setToolTipText("");
        jButton39.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton39.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton39.setName(""); // NOI18N
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        jButton40.setBackground(java.awt.SystemColor.activeCaption);
        jButton40.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton40.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton40.setToolTipText("");
        jButton40.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton40.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton40.setName(""); // NOI18N
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });

        jButton41.setBackground(java.awt.SystemColor.activeCaption);
        jButton41.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton41.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton41.setToolTipText("");
        jButton41.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton41.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton41.setName(""); // NOI18N
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });

        jButton42.setBackground(java.awt.SystemColor.activeCaption);
        jButton42.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton42.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.disabledForeground"));
        jButton42.setToolTipText("");
        jButton42.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton42.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton42.setName(""); // NOI18N
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });

        jTextField1.setEditable(false);
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("S");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setEditable(false);
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("M");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField3.setEditable(false);
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setText("T");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextField4.setEditable(false);
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField4.setText("W");
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jTextField5.setEditable(false);
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.setText("Th");
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jTextField6.setEditable(false);
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField6.setText("F");
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jTextField7.setEditable(false);
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField7.setText("Sa");
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Vani", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Month");

        jButton43.setText("<");
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });

        jButton44.setText(">");
        jButton44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton44ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Year");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton43)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton44))))
                .addContainerGap(53, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(203, 203, 203)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton43)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton44)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        eventPop();
    }                                        

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        eventPop();
    }                                        

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        eventPop();
    }                                        

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        eventPop();
    }                                        

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        eventPop();
    }                                        

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        eventPop();
    }                                        

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        eventPop();
    }                                        

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        eventPop();
    }                                        

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        eventPop();
    }                                        

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        eventPop();
    }                                         

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        
        prevMonth();
    }                                         

    private void jButton44ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        nextMonth();
        
        
        
    }                                         

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
            java.util.logging.Logger.getLogger(CalendarTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CalendarTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CalendarTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CalendarTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CalendarTest().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration                   
}
