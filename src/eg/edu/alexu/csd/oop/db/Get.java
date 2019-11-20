package eg.edu.alexu.csd.oop.db;

import java.io.File;

import eg.edu.alexu.csd.oop.db.cs09.DB;
import eg.edu.alexu.csd.oop.db.cs09.ParserData;

public interface Get {
	public ParserData getObject();

	public void setObject(ParserData obj);

	public Database getObject2();

	public void setObject2(Class<? extends DB> class1);

	public File getFile();

	public void setFile(File f);

	public Object[][] getTableArray();

	public void setTableArray(Object[][] tableArray);
}
