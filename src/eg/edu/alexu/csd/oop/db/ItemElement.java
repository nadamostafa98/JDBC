package eg.edu.alexu.csd.oop.db;

import eg.edu.alexu.csd.oop.db.cs09.GetFromTable1;

public interface ItemElement {

	public String[] acceptvalue(GetFromTable1 visitor);
	public String[] acceptcol(GetFromTable1 visitor);
	public String[][] accepttable(GetFromTable1 visitor);
	
}
