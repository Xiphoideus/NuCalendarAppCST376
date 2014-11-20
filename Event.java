/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NCACST376;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author MLSSD
 */
public class Event {
    
    //fields
    private String Name;
    private Date StartDate;
    private Date EndDate;
    private ArrayList<String> AvailableTo;
    //constructor
    public Event(String name, Date startDate, Date endDate, ArrayList<String> availableTo)
    {
        this.Name = name;
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.AvailableTo = availableTo;
    }
    //edit the event info
    public void editName(String name)
    {
        this.Name = name;
    }
    
    public void editStartDate(Date date)
    {
        this.StartDate = date;
    }
    
    public void editEndDate(Date date)
    {
        this.EndDate = date;
    }
    public void addUser(String userName)
    {
        if (!AvailableTo.contains(userName))
        {
            this.AvailableTo.add(userName);
        }
    }
    
    public void removeUser(String userName)
    {
        if (AvailableTo.contains(userName))
        {
            this.AvailableTo.remove(userName);
        }
    }

    public ArrayList<String> getAvailableTo() {
        return AvailableTo;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public String getName() {
        return Name;
    }

    public Date getStartDate() {
        return StartDate;
    }
    public void removeAllUsers()
    {
        this.AvailableTo = new ArrayList<String>();
    }
    public String toString()
    {
        return this.Name + ", " + this.StartDate + ", " + this.EndDate;
    }
    public static void main(String[] args)
   {
       //This is all for testing purposes.
       EventList e = new EventList();
       Date date = new Date("1994/08/09 02:02:02");
       ArrayList<String> as = new ArrayList<String>();
       as.add("John");
       as.add("Luke");
       Event l = new Event("hello", date, date, as);
       e.add(l);
       System.out.println(l.getName());
       
   }
}
