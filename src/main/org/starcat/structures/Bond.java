package org.starcat.structures;


/**
 * A Bond is used to join two perceptual structures 
 */
public abstract class Bond extends Entity {    
    
	protected Entity from;
	protected Entity to;
	
    public Bond(){}
   
    /*
     * Constructor.  Also adds two "tag" Descriptors to this bond.  
     * The tags are "fromTarget" and "toTarget" and point to the 
     * relevant objects (object the bond comes from and the one it goes to)
     */
    public Bond(Entity from, Entity to)
    {
    	this.from = from;
    	this.to = to;
    }

	public int getStrength() 
	{
		return 0;
	}
	
	public Entity getFromTarget() { return from; }

	public Entity getToTarget() { return to; }
	
	public boolean equals(Bond bond)
	{
		return super.equals(bond) 
		&& this.from.equals(bond.getFromTarget())
		&& this.to.equals(bond.getToTarget());
	}

}