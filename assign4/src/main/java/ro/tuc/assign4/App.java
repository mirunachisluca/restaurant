package ro.tuc.assign4;

import java.io.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
        Restaurant r = new Restaurant();
		AdministratorGUI gui = new AdministratorGUI(r);
		WaiterGUI gui2 = new WaiterGUI(r);
	
    }
}
