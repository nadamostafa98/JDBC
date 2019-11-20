package eg.edu.alexu.csd.oop.jdbc.cs09;

import java.io.File;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Logger;

import eg.edu.alexu.csd.oop.db.Get;
import eg.edu.alexu.csd.oop.db.ItemElement;
import eg.edu.alexu.csd.oop.db.Visitor;
import eg.edu.alexu.csd.oop.db.cs09.GetFromTable1;
import eg.edu.alexu.csd.oop.db.cs09.Get_set;
import eg.edu.alexu.csd.oop.db.cs09.NodeElements;
import eg.edu.alexu.csd.oop.db.cs09.ParserData;

public class ResultSetMetaDataClass implements ResultSetMetaData{

	private Get getter = new Get_set();
	private ParserData x = getter.getObject();
	private File f = getter.getFile();
	private static Logger log = Logger.getLogger(ResultSetMetaDataClass.class.getName());

	@Override
	public int getColumnCount() throws SQLException {
		return getter.getTableArray()[0].length;
	}

	@Override
	public String getColumnLabel(int column) throws SQLException {
		column--;
		ItemElement s = new NodeElements(f.getAbsolutePath(), x.getTable(), f, x.getcolchange());
		Visitor get = new GetFromTable1();
		String[] cols = s.acceptcol((GetFromTable1) get);
		if(column < 0 || column >= cols.length) {
			log.warning("Index out of bound");
			throw new SQLException();
		}
		return cols[column];
	}

	@Override
	public String getColumnName(int column) throws SQLException {
		column--;
		ItemElement s = new NodeElements(f.getAbsolutePath(), x.getTable(), f, x.getcolchange());
		Visitor get = new GetFromTable1();
		String[] cols = s.acceptcol((GetFromTable1) get);
		if(column < 0 || column >= cols.length) {
			log.warning("Index out of bound");
			throw new SQLException();
		}
		return cols[column];
	}

	@Override
	public int getColumnType(int column) throws SQLException {
		column--;
		ItemElement s = new NodeElements(f.getAbsolutePath(), x.getTable(), f, x.getcolchange());
		Visitor get = new GetFromTable1();
		String[][] cols = x.getColumns();
		if(column < 0 || column >= cols.length) {
			log.warning("Index out of bound");
			throw new SQLException();
		}
		if(cols[column][1].contains("varchar")) {
			return Types.VARCHAR;
		}
		else if(cols[column][1].contains("int")) {
			return Types.INTEGER;
		}
		else {
			throw new SQLException();
		}
	}

	@Override
	public String getTableName(int column) throws SQLException {
		return x.getTable();
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCatalogName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnClassName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getColumnDisplaySize(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getColumnTypeName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPrecision(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getScale(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSchemaName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAutoIncrement(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCaseSensitive(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCurrency(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDefinitelyWritable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int isNullable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isReadOnly(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSearchable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSigned(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWritable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
}
