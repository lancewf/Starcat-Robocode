package org.starcat.structures;





/**
 * A Group is a perceptual structure that collects together
 * other perceptual structures that all share a connection to a single
 * concept node from the slipnet. For example three letters that share
 * a sameness bond may be grouped as a sameness group, using the sameness
 * node in the slipnet as its basis. This raises an interesting question
 * about whether it is the letters that are grouped in such an example, or
 * the actual bonds of sameness between them. The answer is not yet known.
 * Later, a more complex set of relationships, with different basis 
 * concepts for the different participants of the collection, may be 
 * handled
 * by another (Tile?) class. There will be there also some connection
 * with the generic provision for a tiled space and its physics...
 *
 */
public abstract class Group extends Entity {
    
    protected int groupSize;
    protected Entity[] groupedEntities;    
    
    public Group()
    {
    	groupSize = 0; //Just for now to quiet compiler; must come back to this code
    	groupedEntities = new Entity[10];
    }
    
    public int size()
    {
    	return groupSize;
    }
    
    public Entity[] Entity()
    {
    	return groupedEntities;
    }

	public int getStrength() {
		// TODO Auto-generated method stub
		return 0;
	}
    
 
    //TODO Do we want an iterator for this?
	
	public Group clone()
	{
		return (Group)super.clone();
	}


}