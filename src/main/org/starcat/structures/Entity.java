package org.starcat.structures;

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
   // #region Protected Data
   // --------------------------------------------------------------------------

	//INSTANCE VARIABLES
	protected Set<Descriptor> descriptors;
	protected PublicStarcatObject sObjDelegate =
		new PublicStarcatObject(this);
	protected int relevance;
	protected int completeness;
	protected int salience;
	protected long age;
	
	// #endregion
	
   // --------------------------------------------------------------------------
   // #region Constructor
   // --------------------------------------------------------------------------
	
	//CONSTRUCTORS
	public Entity()
	{
		age = BehaviorCodelet.getTotalAmountOfTimesCodeletWereExecuted();
		update();
	}
	
	// #endregion
	
   // --------------------------------------------------------------------------
   // #region Object Members
   // --------------------------------------------------------------------------
   
	  //CLONING
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
   
   // #endregion
   
   // --------------------------------------------------------------------------
   // #region StarcatObject Members
   // --------------------------------------------------------------------------
   
   //IDENTITY AND LOGGING
   public Object getId()
   {
      return sObjDelegate.getId();
   }
   
   // #endregion
	
   // --------------------------------------------------------------------------
   // #region Local Protected Members
   // --------------------------------------------------------------------------
   
	//DESCRIPTOR ACCESSORS
	protected void setDescriptors(Set<Descriptor> descriptors) {
		this.descriptors = descriptors;
	}
	
	  // These methods need to be provided by the child
   // objects and will define how relevance and 
   // completeness are calculated for the object
   // Relevance derives its computation from the slipnet
   // which is known to be initialized by the XML file
   // Completeness, on the other hand, is application-
   // specific and so must be implemented in such a way as
   // to guarantee that the computation is meaningful even
   // upon immediate creation of the object.
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
	
	// #endregion

   // --------------------------------------------------------------------------
   // #region Local Public Members
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
	
    //COMPARATORS
	public boolean equals(Entity anotherEntity)
	{
		return super.equals(anotherEntity); 
		//|| isDescribedBy(anotherEntity.getType());
	}
	

	//CHANGE NOTIFICATION
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
	
	//SALIENCE, RELEVANCE AND COMPLETENESS ACCESSORS
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
    
    // #endregion
}
