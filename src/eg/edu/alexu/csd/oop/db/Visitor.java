package eg.edu.alexu.csd.oop.db;

import eg.edu.alexu.csd.oop.db.cs09.NodeElements;

public interface Visitor {
	
		 public String[] visitValues(NodeElements node);
		 public String[] visitCol(NodeElements node);
		 public String[][] visitTable(NodeElements node);

	}