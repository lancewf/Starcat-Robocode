package org.starcat.workspace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.starcat.core.Component;
import org.starcat.codelets.Codelet;
import org.starcat.structures.Entity;
import org.starcat.structures.Item;

/**
 * Workspace is a standard implementation of class-indexed storage that provides
 * random and anchored access to included structures. It can be made to enforce
 * spatial constraints and other "domain physics", but is not required to do so.
 * This will be addressed in later versions.
 * 
 * Details of the implementation are provided in the code below. It is
 * essentially a time-indexed array of treemaps whose key-value pairs contain
 * string keys that specify the fully-qualified typename that describes all
 * instances kept in the vector that is associated with that key.
 * 
 * 
 */
public class Workspace extends Component
{
	// -------------------------------------------------------------------------
    // Protected Data
	// -------------------------------------------------------------------------

   protected HashMap<Class<? extends Entity>, List<Entity>> workspaceStorage;

   protected List<Entity> allEntities;

   protected int coherence = 100;

   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public Workspace()
   {
      super();
      
      workspaceStorage = new HashMap<Class<? extends Entity>, List<Entity>>();

      allEntities = new ArrayList<Entity>();
   }

   // --------------------------------------------------------------------------
   // Component Members
   // --------------------------------------------------------------------------

   protected final void preExecuteCodelet(Codelet codelet)
   {
      codelet.preExecute(this);
   }

   public void executeCodelet(Codelet codelet)
   {
      preExecuteCodelet(codelet);
      codelet.execute(this);
      postExecuteCodelet(codelet);
   }

   protected final void postExecuteCodelet(Codelet codelet)
   {
      codelet.postExecute(this);
   }
   
   public final synchronized void update()
   {
      long sumOfAllRelevances = 0l;

      for(Entity e : allEntities)
      {
         e.update();
         sumOfAllRelevances += e.getRelevance();
      }

      int temp_coherence = 0;
      
      for(Entity e : allEntities)
      {
         temp_coherence += Math
            .round(((double) e.getRelevance() / (double) sumOfAllRelevances)
                  * (100 - e.getCompleteness()));
      }
      coherence = temp_coherence;
   }

   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------

   /*
    * equivalent to calling getEntitiesMatching(Class, false, null)
    * 
    */
   public List<Entity> getEntitiesMatching(Class<? extends Entity> c)
   {
      return getEntitiesMatching(c, false);
   }

   /*
    * With this method, one can retrieve all Entity objects in this Workspace or
    * limit by class type
    * 
    * Class c object of Entity type to restrict matches to. If c is null, it
    * will try matches on all entities regardless of type.
    * 
    * andSubclasses--if this is set to true then all Entities of type c or a
    * subtype shall be considered
    * 
    */
   public List<Entity> getEntitiesMatching(Class<? extends Entity> c,
                                           boolean andSubclasses)
   {
      List<Entity> entList;
      if (andSubclasses) {
         entList = getListForClassAndSubclasses(c);
      }
      else{
         entList = getListForClass(c);
      }
      if (entList == null){
         return new ArrayList<Entity>();
      }
      else {
         return entList;
      }
   }

   public synchronized void addEntity(Entity entity)
   {
      allEntities.add(entity);
      List<Entity> entList = getListForClass(entity.getClass());
      if (entList == null)
      {
         entList = new ArrayList<Entity>();
         workspaceStorage.put(entity.getClass(), entList);
      }
      entList.add(entity);
      workspaceStorage.put(entity.getClass(), entList);
   }

   public synchronized boolean removeEntity(Entity entity)
   {
      if (!allEntities.remove(entity))
      {
         return false;
      }
      List<Entity> entList = getListForClass(entity.getClass());
      entity.setChangedAndNotify();
      entity.deleteObservers();
      if (!entList.remove(entity))
      {
         return false;
      }
      // need to put the list back into the hashmap
      workspaceStorage.put(entity.getClass(), entList);
      return true;
   }

   public boolean containsEntity(Entity entity)
   {
	  List<Entity> entList = getListForClass(entity.getClass());
      if (entList != null)
      {
         return entList.contains(entity);
      }
      return false;
   }

   protected final synchronized void unregisterCodeletPrivate(Codelet codelet)
   {
      fireCodeletEvent(getCurrentCodelet());
      unregisterCodelet(getCurrentCodelet());
   }

   public Item getObjectBySalience(Class<? extends Entity> c)
   {
      List<Entity> list = getEntitiesMatching(c);
      return getObjectBySalienceFromList(list);
   }

   public Item getTotallyRandomObject()
   {
      return getObjectBySalienceFromList(allEntities);
   }

   public Item getObjectBySalience(Class<? extends Entity> c,
                                   boolean andSubclasses)
   {
      List<Entity> list = getEntitiesMatching(c, andSubclasses);
      return getObjectBySalienceFromList(list);
   }

   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------

   /*
    * c--Class of Entity to search for. If c == null, then all Entity objects
    * will be returned.
    * 
    * returns modifiable List of Entity objects
    */
   private List<Entity> getListForClass(Class<? extends Entity> c)
   {
      if (c == null)
      {
         return new ArrayList<Entity>(allEntities);
      }
      else if (!workspaceStorage.containsKey(c))
      {
         return null;
      }
      return new ArrayList<Entity>((List<Entity>) workspaceStorage.get(c));
   }

   private List<Entity> getListForClassAndSubclasses(Class<? extends Entity> c)
   {
      List<Entity> entities = new ArrayList<Entity>();
      if (c == null)
      {
         return new ArrayList<Entity>(allEntities);
      }

      for(Class<? extends Entity> cls : workspaceStorage.keySet())
      {
         if (c.isAssignableFrom(cls))
         {
            entities.addAll((List<Entity>) workspaceStorage.get(cls));
         }
      }
      return entities;
   }
   
   private Item getObjectBySalienceFromList(List<Entity> list)
   {
      Item wo = null;
      int sum = 0;

      for(Entity entity : list)
      {
         wo = (Item) entity;
         sum += wo.getSalience();
      }

      // Flat distribution
      Random rand = new Random();
      int stopVal = rand.nextInt(sum + 1);

      /*
       * set to use workspace iterator so that items are selected randomly form
       * list Normal iterator does not allow this.
       */
      Iterator<Entity> it = new WorkspaceIterator(list);

      wo = null;
      sum = 0;

      while (it.hasNext())
      {
         wo = (Item) it.next();
         sum += wo.getSalience();
         if (sum >= stopVal)
            break;
      }

      return wo;
   }
}