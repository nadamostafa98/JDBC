package eg.edu.alexu.csd.oop.db.cs09;

import java.io.File;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.Get;

public class Get_set implements Get {
	private static ParserData obj = new ParserData(null);
	private static Database obj2 = new DB();
	private static File f;
	private static Object[][] tableArray;

	@Override
	public ParserData getObject() {
		return obj;
	}

	@Override
	public void setObject(ParserData obj) {
		this.obj = obj;
	}

	@Override
	public File getFile() {
		return f;
	}

	@Override
	public void setFile(File f) {
		this.f = f;
	}

	@Override
	public Object[][] getTableArray() {
		return tableArray;
	}

	@Override
	public void setTableArray(Object[][] tableArray) {
		this.tableArray = tableArray;
	}

	@Override
	public Database getObject2() {
		return obj2;
	}

	@Override
	public void setObject2(Class<? extends DB> class1) {
		this.obj2 = obj2;
	}

}
