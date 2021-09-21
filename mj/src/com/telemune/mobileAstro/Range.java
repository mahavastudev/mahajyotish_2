

package com.telemune.mobileAstro;

public class Range
{

private String name;
private long start;
private long end;
private boolean forward;


public String getName()
{
return this.name;
}

public void setName(String name)
{
	this.name=name;
}

public long getStart()
{

return this.start;
}

public void setStart(long start)
{

this.start=start;
}



public long getEnd()
{

return this.end;
}

public void setEnd(long end)
{

this.end=end;
}



public boolean isForward()
{
return forward;
}

public void setForward(boolean forward)
{
this.forward= forward;
}



public String toString()
{
return "name  "+name+"strtt  "+start+" end  "+end+" forward  "+forward;
}



}
