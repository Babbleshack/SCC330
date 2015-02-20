/**
 * Observable Abstract Class
 * Dominic Lindsay
 */
package org.sunspotworld.homePatterns; 
import com.sun.spot.service.Task;
import java.util.Vector;
import org.sunspotworld.data.SensorData;
//import org.sunspotworld.Patterns.Observer;


public abstract class TaskObservable extends Task {
	private final static int INNITIAL_SIZE = 10;
	private final static int INCREMENT_SIZE = 5;
	private static boolean hasChanged = false;

 	Vector observers; //our observers
 	/**
 	 * Instantiate the observers Vector class, 
 	 * set to innitial size of 10 and increment paramerter
 	 * of 5. This is because i dont expect there to be more
 	 * than ten observers for any object, and this is an easy
 	 * fix if that turns out to not be the case.
         * @param sampleRate
 	 */
	public TaskObservable(long sampleRate)
	{
            super(sampleRate);
		observers = new Vector(INNITIAL_SIZE, INCREMENT_SIZE);
	}
	/**
	 * Add observer object to vector of observers
	 * type safery problem i cant use, interface.class.isAssignableFrom(o)
	 * to test for interface equality becuase i would have to downcast it back
	 * to te object which would couple the observable class to its implementation.
	 */
	public void addObserver(Object o)
	{
		this.observers.addElement(o);
	}
	/**
	 * Set hasChanged bool to false
	 */
	public void clearChanged()
	{
		this.hasChanged = false;
	}
	/**
	 * returns the number of objects observing this object
	 * @return Observers int
	 */
	public int countObservers()
	{
		return observers.size();
	}
	/**
	 * delete Observer from observers list
	 */
	public void deleteObserver(Object o)
	{
		if(observers.indexOf(o) == -1)
			return; //object does not exist
		observers.removeElement(o);
	}
	/**
	 * Test if an object has changed, as specified by the
	 * set Changed method
	 */
	public boolean hasChanged()
	{
		return this.hasChanged;
	}
	/**
	 * notify observers that this object has changed and then call
	 * clear changed.
	 */
	public void notifyObservers()
	{
		for(int i= 0; i<observers.size(); i++)
		{
			((TaskObserver)observers.elementAt(i)).update(this);
		}
		this.clearChanged();
	}
	/**
	 * notify observers that this object has changed and then call
	 * clear changed.
	 */
	public void notifyObservers(Object arg)
	{
            System.out.println("OBSERVABLE PRINT: " + ((SensorData)arg).getDataAsDouble());
		for(int i= 0; i<observers.size(); i++)
		{
			((TaskObserver)observers.elementAt(i)).update(this, arg);
		}
		this.clearChanged();
	}
	/**
	 * marks the observable object as having changed
	 */
	public void setChanged()
	{
		this.hasChanged = true;
	}
}