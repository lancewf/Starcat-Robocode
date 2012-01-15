package org.starcat.structures;

import java.util.ArrayList;
import java.util.List;
import org.starcat.slipnet.SlipnetNode;
import org.starcat.core.PublicStarcatObject;
import org.starcat.core.StarcatObject;
import org.starcat.structures.Entity;
import org.starcat.structures.Item;

/**
 * Instances of this class can be used to "attach" a description
 * to an existing Entity in the Workspace. Typically such a description
 * will have a type and a value; for example, type=color and value=red.
 * Utilizing the architecture appropriately (and also thereby providing
 * access to meta-information for the executing program) these types
 * and values are expressed with corresponding slipnet nodes. Hence,
 * there are references here to those. If an application chooses to
 * specify those as null, then there is the option of using a string/
 * object pairing for generic tagging. As such two different constructors
 * are provided. When used in the typical fashion, the string (name)
 * is set to the toString() value of the SlipnetNode and the value is
 * set to the node itself. Despite the use of this typing information
 * within the node (that facilitates other aspects of system functioning),
 * it is usually the case that an application developer will provide
 * situation-specific subclasses of this Descriptor.
 */
public class Descriptor
implements StarcatObject
{
	protected SlipnetNode descriptorType;
	protected SlipnetNode descriptorValue;
	protected String name;
	protected Object value;
	protected Item object;
	protected PublicStarcatObject sObjDelegate = 
		new PublicStarcatObject(this);
	
	public Descriptor(SlipnetNode type, SlipnetNode value)
	{
		this.descriptorType = type;
		this.descriptorValue = value;
	}

	public Descriptor(String name, Object value)
	{
		this.name = name;
		this.value = value;
	}

	/**
	 * true if the two objects reference are the 
	 * same (identity) or their respective DescriptionType 
	 * and Descriptor objects are equal
	 */
	public boolean equals(Descriptor desc)
	{
		if (this == desc)
		{
			return true;
		}
		if (desc.getDescriptorType() == null)
		{
			return getDescriptorValue().equals(desc.getDescriptorValue());
		}
		if (desc.getDescriptorValue() == null)
		{
			return getDescriptorType().equals(desc.getDescriptorType());
		}
		return
		((getDescriptorType().equals(desc.getDescriptorType()) 
		&& getDescriptorValue().equals(desc.getDescriptorValue())));
	}

	
	public int hashCode()
	{
		return getDescriptorValue().hashCode() 
		+ getDescriptorType().hashCode();
	}
	
	public int getConceptualDepth()
	{
		return descriptorValue.getConceptualDepth();
	}
	
	public boolean isRelevant()
	{
		return descriptorType.isActive();
	}
	
	public SlipnetNode getDescriptorType() {
		return descriptorType;
	}
	public void setDescriptorType(SlipnetNode descriptorType) {
		this.descriptorType = descriptorType;
	}
	public SlipnetNode getDescriptorValue() {
		return descriptorValue;
	}
	public void setDescriptorValue(SlipnetNode descriptorValue) {
		this.descriptorValue = descriptorValue;
	}
	public Item getObject() {
		return object;
	}
	public void setObject(Item object) {
		this.object = object;
	}
	
	public String getName() { return name; }
	public Object getValue() { return value; }
	
	public boolean isBond() 
	{
		return false;
	}

	public boolean isDescription() 
	{
		return true;
	}
	
	public boolean hasDescriptorValue(SlipnetNode descriptorValue)
	{
		return getDescriptorValue().equals(descriptorValue);
	}
	
	public boolean hasDescriptorType(SlipnetNode descriptorType)
	{
		return getDescriptorType().equals(descriptorType);
	}

	public boolean describes(Entity entity) 
	{
		List<Descriptor> descriptors = new ArrayList<Descriptor>();
		descriptors.addAll(entity.getDescriptors());
		return descriptors.contains(this);
	}

	public Object getId() {
		return sObjDelegate.getId();
	}
}
