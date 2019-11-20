package eg.edu.alexu.csd.oop.jdbc.cs09;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;
public class DriverClass implements Driver{
	private static Logger logDriver = Logger.getLogger(DriverClass.class.getName());
	@Override
	public boolean acceptsURL(String arg0) throws SQLException {
		return true;
	}

	@Override
	public Connection connect(String arg0, Properties arg1) throws SQLException {
		Connection con = new Connection2();
		logDriver.info("Connection Created");
		return con;
	}


	@Override
	public DriverPropertyInfo[] getPropertyInfo(String arg0, Properties arg1) throws SQLException {
		DriverPropertyInfo[] DpropInfo = {(DriverPropertyInfo) arg1.get("path")};
		return DpropInfo;
	}

	@Override
	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		return false;
	}


}
