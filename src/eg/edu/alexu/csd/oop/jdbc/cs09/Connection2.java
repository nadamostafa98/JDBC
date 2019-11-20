package eg.edu.alexu.csd.oop.jdbc.cs09;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs09.DB;

public class Connection2 extends ConnectionClass {
	private int flag = 0;
	private Connection con;
	private Database db = new DB();
	private static Logger log = Logger.getLogger(Connection2.class.getName());

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	@Override
	public void close() throws SQLException {
		if (flag == 1) {
			flag = 0;
			log.info("Connection closed successfully");
		} else {
			log.warning("There is no Connection to close");
			throw new SQLException();
		}
	}

	@Override
	public Statement createStatement() throws SQLException {
		setFlag(1);
		Statement st = new StatementClass(con);
		log.info("Statement Created successfully");
		return st;
	}

}
