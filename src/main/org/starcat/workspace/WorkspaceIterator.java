package org.starcat.workspace;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.starcat.structures.Entity;

/*
 * This class will perform the same actions as the Java iterator but removes
 * the objects from the list in a random order instead of the stored order
 * that the Java iterator does
 */
public class WorkspaceIterator implements Iterator<Entity>
{
    private List<Entity> list;
    private boolean[] beenSelected;
    private int selectedCount;
    private Random rand = new Random();

    public WorkspaceIterator(List<? extends Entity> l)
    {
       list = new ArrayList<Entity>(l);

       beenSelected = new boolean[list.size()];
       selectedCount = 0;

       for (int i = 0; i < beenSelected.length; i++){
          beenSelected[i] = false;
       }
    }

    public boolean hasNext()
    {
       if (selectedCount < beenSelected.length){
          return true;
       }
       else {
          return false;
       }
    }

    public Entity next()
    {
       Entity obj = null;

       if (rand != null){
          int index = rand.nextInt(list.size());
          while (beenSelected[index])
             index = rand.nextInt(list.size());
          obj = list.get(index);
          beenSelected[index] = true;
          selectedCount++;
       }
       else {
          System.out.println("NULL random class object!");
       }

       return obj;
    }

    public void remove()
    {
    }
}
