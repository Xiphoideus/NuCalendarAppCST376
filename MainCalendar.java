package NCACST376;
/**
 * @author Alec Freeman
 */
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MainCalendar {
    private static JLabel labelMonth, labelYear;
    private static JButton buttonPrevious, buttonNext;
    private static JTable tableCalendar;
    private static JComboBox comboYear;
    private static JFrame frameMain;
    private static Container pane;
    private static DefaultTableModel tableModelCalendar;
    private static JScrollPane scrollCalendar;
    private static JPanel panelCalendar;
    private static int realYear, realMonth, realDay, currentYear, currentMonth;
    
    public static void main (String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe);
        } catch (InstantiationException ie) {
            System.out.println(ie);
        } catch (IllegalAccessException iae) {
            System.out.println(iae);
        } catch (UnsupportedLookAndFeelException ulafe) {
            System.out.println(ulafe);
        }
        
        frameMain = new JFrame ("Calendar"); //Frame creation.
        frameMain.setSize(320, 375); //Setting to 400x400 pixels.
        pane = frameMain.getContentPane(); //Receive content pane.
        pane.setLayout(null); //Apply single null layout.
        frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Complete build on X.
        
        labelMonth = new JLabel("January");
        labelYear = new JLabel ("Year:");
        comboYear = new JComboBox();
        buttonPrevious = new JButton ("<<");
        buttonNext = new JButton (">>");
        tableModelCalendar = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        tableCalendar = new JTable(tableModelCalendar);
        scrollCalendar = new JScrollPane(tableCalendar);
        panelCalendar = new JPanel(null);
        
        //Setting borderline.
        panelCalendar.setBorder(BorderFactory.createTitledBorder("Calendar"));
        
        //Register action listeners.
        buttonPrevious.addActionListener(new buttonPrevious_Action());
        buttonNext.addActionListener(new buttonPrevious_Action());
        comboYear.addActionListener(new comboYear_Action());
        
        //App controls to pane.
        pane.add(panelCalendar);
        panelCalendar.add(labelMonth);
        panelCalendar.add(labelYear);
        panelCalendar.add(comboYear);
        panelCalendar.add(buttonPrevious);
        panelCalendar.add(buttonNext);
        panelCalendar.add(scrollCalendar);
        
        //Set boundaries.
        panelCalendar.setBounds(0, 0, 320, 335);
        labelMonth.setBounds(160-labelMonth.getPreferredSize().width/2, 25, 100, 25);
        labelYear.setBounds(10, 305, 80, 20);
        buttonPrevious.setBounds(10, 25, 50, 25);
        buttonNext.setBounds(260, 25, 50, 25);
        scrollCalendar.setBounds(10, 50, 300, 250);
        
        //Make frame visible.
        frameMain.setResizable(false);
        frameMain.setVisible(true);
        
        //Get real month/year.
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar.
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH);
        realMonth = cal.get(GregorianCalendar.MONTH);
        realYear = cal.get(GregorianCalendar.YEAR);
        currentMonth = realMonth;
        currentYear = realYear;
        
        //Add headers.
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        
        for (int i=0; i<7; i++) {
            tableModelCalendar.addColumn(headers[i]);
        }
        
        //Set background color.
        tableCalendar.getParent().setBackground(tableCalendar.getBackground());
        
        //No resize or reordering.
        tableCalendar.getTableHeader().setResizingAllowed(false);
        tableCalendar.getTableHeader().setReorderingAllowed(false);
        
        //Single cell selection.
        tableCalendar.setColumnSelectionAllowed(true);
        tableCalendar.setRowSelectionAllowed(true);
        tableCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //Set row and column count.
        tableCalendar.setRowHeight(38);
        tableModelCalendar.setColumnCount(7);
        tableModelCalendar.setRowCount(6);
        
        //Populating table...
        for (int i=realYear-100; i<=realYear+100; i++) {
            comboYear.addItem(String.valueOf(i));
        }
        
        refreshCalendar (realMonth, realYear);
    }
    
    public static void refreshCalendar(int month, int year) {
        //Month variables.
        String[] months = {"January", "February", "March", "April", "May", "June", 
            "July", "August", "September", "October", "November", "December"};
        int numberOfDays, startOfMonth;
        
        //Allow and disallow buttons.
        buttonPrevious.setEnabled(true);
        buttonNext.setEnabled(true);
        
        if (month == 0 && year <= realYear-10) {
            buttonPrevious.setEnabled(false); //Too early.
        }
        if (month == 11 && year >= realYear+100) {
            buttonNext.setEnabled(false); // Too late.
        }
        
        labelMonth.setText(months[month]); 
        //Refreshing month labels...
        
        labelMonth.setBounds(160-labelMonth.getPreferredSize().width/2, 25, 180, 25); 
        //Realignment with calendar!
        
        comboYear.setSelectedItem(String.valueOf(year));
        
        //Clearing table...
        for (int i=0; i<6; i++) {
            for (int j=0; j<7; j++) {
                tableModelCalendar.setValueAt(null, i, j);
            }
        }
        
        //Get the first day of the month and number of days.
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        numberOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        startOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);
        
        for (int i=1; i<numberOfDays; i++) {
            int row = new Integer((i+startOfMonth-2)/7);
            int column = (i+startOfMonth-2)%7;
            tableModelCalendar.setValueAt(i, row, column);
        }
        
        //Apply renderers...
        tableCalendar.setDefaultRenderer(tableCalendar.getColumnClass(0), new tableCalendarRenderer());
    }
    //Fuzzy coding; please ignore.
    static class tableCalendarRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, 
                boolean focused, int row, int column) {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            
            if (column == 0 || column == 6) { //Weekend rendering.
                setBackground (new Color(210, 210, 210));
            } else { //Week rendering.
                setBackground(new Color(255, 255, 255));
            }
            if (value != null) {
                if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear) {
                    setBackground (new Color(220, 220, 225));
                }
            }
            setBorder(null);
            setForeground(Color.black);
            return this;
        }
    }
    static class buttonPrevious_Action implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            if (currentMonth == 0) { //Back one year.
                currentMonth = 11;
                currentYear -= 1;
            } else { //Back one month.
                currentMonth -= 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    static class buttonNext_Action implements ActionListener { 
        public void actionPerformed (ActionEvent e) {
            if (currentMonth == 11) { //Forward one year.
                currentMonth = 0;
                currentYear += 1;
            } else { //Foward one month.
                currentMonth +=1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    static class comboYear_Action implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            if (comboYear.getSelectedItem() != null) {
                String b = comboYear.getSelectedItem().toString();
                currentYear = Integer.parseInt(b);
                refreshCalendar(currentMonth, currentYear);
            }
        }
    }
}
