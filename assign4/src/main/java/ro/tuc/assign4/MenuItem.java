package ro.tuc.assign4;

import java.io.Serializable;

public abstract class MenuItem implements Serializable{
	public abstract float computePrice();
	public abstract String getName();
}
