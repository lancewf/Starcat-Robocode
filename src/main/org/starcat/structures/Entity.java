package org.starcat.structures;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Random;
import java.util.Set;
import org.starcat.core.PublicStarcatObject;
import org.starcat.core.StarcatObject;
import org.starcat.structures.Descriptor;
import org.starcat.codelets.BehaviorCodelet;

/**
 * This is a common root class of something that can be in the 
 * Workspace.  It is at this topmost level that abstract methods exist to 
 * force application-specific subclasses of either Item, Descriptor, Bond 
 * or Group to implement protected methods getRelevance() and
 * getCompleteness().
 * 
 */
public abstract class Entity extends Observable 
   implements Cloneable, StarcatObject
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------

	private Set<Descriptor> descriptors = new HashSet<Descriptor>();
	private PublicStarcatObject sObjDelegate = new PublicStarcatObject(this);
	private int relevance;
	private int completeness;
	private int salience;
	private long age;
	
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
	
	public Entity() {
		age = BehaviorCodelet.getTotalAmountOfTimesCodeletWereExecuted();
		update();
	}
	
   // --------------------------------------------------------------------------
   // Object Members
   // --------------------------------------------------------------------------

   public Entity clone()
   {
      Entity newEntity;
      try {
         newEntity = (Entity)super.clone();
      } catch (CloneNotSupportedException e) {
         // Auto-generated catch block
         e.printStackTrace();
         throw new InternalError();
      }
      return newEntity;
   }
   
   // --------------------------------------------------------------------------
   //  StarcatObject Members
   // --------------------------------------------------------------------------
   
   public Object getId() {
      return sObjDelegate.getId();
   }
	
   // --------------------------------------------------------------------------
   // Local Protected Members
   // --------------------------------------------------------------------------
	
   protected abstract void computeRelevance();
   protected abstract void computeCompleteness();
   
   // This method contains the standard calculation for salience.
   // Children could overloaded if needed.
   protected void computeSalience() 
   {
      int temp_salience = Math.round( (getRelevance() + (100 - getCompleteness()) ) / 2.0f );
      salience = temp_salience;
   }

   //Clients will override this if there is additional update behavior
   protected void additionalUpdate() {}

   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------
   
	public void addDescriptor(Descriptor descriptor)
	{
		descriptors.add(descriptor);
	}
	
	public boolean removeDescriptor(Descriptor descriptor)
	{
		return descriptors.remove(descriptor);
	}
	
	public Set<Descriptor> getDescriptors() {
		return descriptors;
	}
	
	public Iterator<Descriptor> descriptorIterator()
	{
		return descriptors.iterator();
	}

	public boolean hasDescriptor(Descriptor des)
	{
		return descriptors.contains(des);
	}
	
	public Descriptor getRandomDescriptor()
	{
		int toPick = new Random().nextInt(descriptors.size());
		Iterator<Descriptor> i = descriptors.iterator();
		while (toPick > 0)
		{
			i.next();
			toPick--;
		}
		return i.next();
	}

	public boolean equals(Entity anotherEntity)
	{
		return super.equals(anotherEntity); 
	}
	
	public void setChangedAndNotify()
	{
		setChanged();
		notifyObservers();
	}
	
	public void setChangedAndNotify(Object arg)
	{
		setChanged();
		notifyObservers(arg);
	}
	
	public int getRelevance()
	{
		return relevance;
	}

	public int getCompleteness()
	{
		return completeness;
	}
	
	public final void update()
	{
		computeRelevance();
		computeCompleteness();
		computeSalience();
		additionalUpdate();
	}
	
    public int getSalience()
    {
    	return salience;
    }
    
    /**
     *  This method provides the workspace update routine to determine a 
     *  workspace entity's age to determine if it is old enough to be removed 
     *  from the workspace
     * @return
     */
    public long getAge()
    {
    	return BehaviorCodelet.getTotalAmountOfTimesCodeletWereExecuted() - age;
    }
}
