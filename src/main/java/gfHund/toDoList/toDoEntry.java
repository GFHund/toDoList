package gfHund.toDoList;
import java.util.*;


class toDoEntry
{
	private boolean mActive;
	private String mName;
	private String mDescription;
	private Date mEndDate;
	private Date mCriticalDate;

	public toDoEntry(boolean active,String name,String description,Date endDate,Date criticalDate)
	{
		this.mActive = active;
		this.mName = name;
		this.mDescription = description;
		this.mEndDate = endDate;
		this.mCriticalDate = criticalDate;
	}

	public void setActive(boolean active)
	{
		this.mActive = active;
	}
	public void setName(String name)
	{
		this.mName = name;
	}
	public void setDescription(String description)
	{
		this.mDescription = description;
	}
	public void setEndDate(Date endDate)
	{
		this.mEndDate = endDate;
	}
	public void setCriticalDate(Date criticalDate)
	{
		this.mCriticalDate = criticalDate;
	}

	public boolean getActive()
	{
		return this.mActive;
	}

	public String getName()
	{
		return this.mName;
	}
	public String getDesctiption()
	{
		return this.mDescription;
	}
	public Date getEndDate()
	{
		return this.mEndDate;
	}
	public Date getCriticalDate()
	{
		return this.mCriticalDate;
	}
}
