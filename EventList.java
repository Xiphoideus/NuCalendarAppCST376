/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NCACST376;

/**
 *
 * @author MLSSD
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.*;

public class EventList<AnyType> implements Iterable{
    
    private EventNode<AnyType> head;
    
    public EventList()
    {
        head = null;
    }
    
    public boolean isEmpty()
    {
        return head == null;
    }
    //add event to the list (will go at the end of this linked list)
    public void add(Event e)
    {
        if (head == null)
        {
            addFirst(e);
        }
        else
        {
            EventNode<AnyType> tmp = head;
            while (tmp.NextEvent != null)
            {
                tmp = tmp.NextEvent;
            }
            tmp.NextEvent = new EventNode(e, null);
        }
    }
    //add event to the start of list
    public void addFirst(Event e)
    {
        head = new EventNode(e, head);
    }
    //get first name
    public Event getFirstEvent()
    {
        if(head == null) throw new NoSuchElementException();
        
        return head.event;
    }
    
    //remove first event on the list
    public Event removeFirst()
    {
        Event tmp = getFirstEvent();
        head = head.NextEvent;
        return tmp;
    }
    
    //remove a certain event (in string) from the list
    public Event remove(String s)
    {
        Event e = this.getEvent(s);
        return remove(e);
    }
    //remove a certain event from the list
    public Event remove(Event e)
    {
        EventNode<AnyType> tmp = head;
        EventNode<AnyType> ahead = head;
        if (ahead.event.equals(e))
        {
            return removeFirst();
        }
        ahead = ahead.NextEvent;
        if (ahead == null )
        {
            throw new NoSuchElementException();
        }
        while (!ahead.event.equals(e) && ahead != null)
        {
            tmp = tmp.NextEvent;
            ahead = ahead.NextEvent;
        }
        //got to the end and didnt find it
        
        if (ahead == null )
        {
            throw new NoSuchElementException();
        }
        else{
            //Event<AnyType> tmpRemove = tmp.NextEvent;
            if (ahead.NextEvent == null)
            {
                tmp.NextEvent = null;
                Event n = ahead.event;
                ahead = null;
                return n;
            }
            else{
                tmp.NextEvent = ahead.NextEvent;
                Event n = ahead.event;
                return n;
            }
        }
    }
    
    //remove all events from list
    public void clear()
    {
        head = null;
    }
    //check if event list contains this event
    public boolean contains(Event x)
    {
        for (Object tmp : this)
        {
            if (tmp.equals(x))
            {
                return true;
            }
        }
        return false;
    }
    
    public boolean contains(String s)
    {
        ArrayList<Event> allEvents = new ArrayList<Event>();
        allEvents = this.getAllEvents();
        for (Event e : allEvents)
        {
            if (e.getName().equals(s))
            {
                return true;
            }
        }
        return false;
    }
    
    //get the info only for the specified event
    public Event getEvent(String key)
    {
        EventNode<AnyType> tmp = head;
        Event e = (Event)tmp.getEvent();
        String s = e.getName();
        while(tmp != null && !key.equals(s))
        {
            tmp = tmp.NextEvent; //this is the node
            if (tmp != null){
                e = (Event)tmp.getEvent(); //this is the event inside the node
                s = e.getName(); //name of the event
            }
            
        }
        if (tmp != null)
        {
            return e;
        }
        else
        {
            throw new NoSuchElementException();
        }
    }
    //get the info for ALL the events
    public ArrayList<Event> getAllEvents()
    {
        EventNode<AnyType> tmp = head;
        ArrayList<Event> allEvents = new ArrayList<Event>();
        while(tmp != null)
        {
            allEvents.add(tmp.event);
            tmp = tmp.NextEvent;
        }
        return allEvents;
    }
    
    //return all the event names as a string using the iterator
    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        for(Object x : this)
        {
            result.append(x).append(" ");
        }
        return result.toString();
    }
    /////insert before and after 
    
    //event inner class
    private static class EventNode<AnyType>
    {
        private Event event;
        private EventNode<AnyType> NextEvent;
        
        public EventNode(Event e, EventNode<AnyType> nextEvent)
        {
            this.event = e;
            this.NextEvent = nextEvent;
            
        }
        
        public Event getEvent()
        {
            return this.event;
        }
        
        
    }
    //iterator
    @Override
    public Iterator<Event> iterator()
    {
        return new EventListIterator();
    }
    private class EventListIterator implements Iterator<Event>
    {
        private EventNode<AnyType> Next;
        
        public EventListIterator()
        {
            Next = head;
        }
        @Override
        public boolean hasNext()
        {
            return Next != null;
        }
        
        @Override
        public Event next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }
            Event r = Next.event;
            Next = Next.NextEvent;
            return r;
        }
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
    public static void main(String[] args)
   {
       //This is all for testing purposes.
       EventList<Event> e = new EventList<Event>();
       Date date = new Date("1994/08/09 02:02:02");
       ArrayList<String> as = new ArrayList<>();
       as.add("John");
       as.add("Luke");
       
       Event o = new Event("hello", date, date, as);
       Event p = new Event("world", date, date, as);
       Event q = new Event("again", date, date, as);
       e.add(o);
       e.add(p);
       e.add(q);
       System.out.println(e.getAllEvents());
       System.out.println(e.remove(q));
       
       //System.out.println(e.getAllEvents());
       //Event k = e.getEvent("hello");
       System.out.println(e.remove(o));
       //System.out.println(e.remove(q));
       System.out.println(e.remove("world"));
       System.out.println(e.getAllEvents());
       e.add(o);
       System.out.println(e.getAllEvents());
       //System.out.println(e.contains("again"));
       //System.out.println(k.toString());
       
   }
}
