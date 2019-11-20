package eg.edu.alexu.csd.oop.jdbc.cs09;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.Get;
import eg.edu.alexu.csd.oop.db.cs09.Get_set;

public class StatementClass implements Statement {
	private Get obj2 = new Get_set();
	private Database obj1 = obj2.getObject2();
	private LinkedList<String> batchList = new LinkedList();
	private int t;
	private ExecutorService executorService;
	private Connection connection;
	private int queryTimeout;
	private static Logger log = Logger.getLogger(StatementClass.class.getName());

	public StatementClass(Connection connection) {
		this.connection = connection;
		t = 1;
		executorService = Executors.newSingleThreadExecutor();
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException();
	}

	@Override
	public void addBatch(String sql) throws SQLException {
		batchList.add(sql);
		log.info("Querry added to batch");
	}

	@Override
	public void cancel() throws SQLException {
		throw new SQLException();
	}

	@Override
	public void clearBatch() throws SQLException {
		batchList.clear();
		log.info("Batch cleared");
	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new SQLException();
	}

	@Override
	public void close() throws SQLException {
		clearBatch();
		setQueryTimeout(0);
		log.info("Statement closed");
		executorService = null;
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		throw new SQLException();
	}

	@Override
	public boolean execute(String sql) throws SQLException {
		Object result = false;
		Future<Boolean> future = executorService.submit(new task(result, sql));

		try {
			result = future.get(t, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			executorService.shutdown();
			return false;

		} catch (ExecutionException e) {
			throw new SQLException();
		} catch (InterruptedException e) {
			throw new SQLException();
		}
		return (boolean) result;

	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		throw new SQLException();
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		throw new SQLException();
	}

	@Override
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		throw new SQLException();
	}

	@Override
	public int[] executeBatch() throws SQLException {
		int[] toReturn = new int[batchList.size()];
		for (int i = 0; i < batchList.size(); i++) {
			if ((batchList.get(i).toLowerCase().contains("update"))
					|| (batchList.get(i).toLowerCase().contains("insert"))
					|| (batchList.get(i).toLowerCase().contains("delete"))) {
				toReturn[i] = obj1.executeUpdateQuery(batchList.get(i));
			} else if ((batchList.get(i).toLowerCase().contains("create"))
					|| (batchList.get(i).toLowerCase().contains("drop"))) {
				Boolean x = obj1.executeStructureQuery(batchList.get(i));
				if (x) {
					toReturn[i] = 1;
				} else {
					toReturn[i] = 0;
				}

			} else if (batchList.get(i).toLowerCase().contains("select")) {
				if (obj1.executeQuery(batchList.get(i)) == null) {
					toReturn[i] = 0;
				} else {
					toReturn[i] = 1;
				}
			} else {
				toReturn[i] = 0;
			}
		}
		log.info("Batch executed Successfully");
		return toReturn;
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		ResultSet rs = new ResultsetClass();
		if (sql.toLowerCase().contains("select")) {
			obj2.setTableArray(obj1.executeQuery(sql));
		}
		log.info("Resultset created");
		return rs;
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		if ((sql.toLowerCase().contains("update")) || (sql.toLowerCase().contains("insert"))
				|| (sql.toLowerCase().contains("delete"))) {
			return obj1.executeUpdateQuery(sql);
		}
		return 0;
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		throw new SQLException();
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		throw new SQLException();
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		throw new SQLException();
	}

	@Override
	public Connection getConnection() throws SQLException {
		return null;

	}

	@Override
	public int getFetchDirection() throws SQLException {
		throw new SQLException();
	}

	@Override
	public int getFetchSize() throws SQLException {
		throw new SQLException();
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		throw new SQLException();
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		throw new SQLException();
	}

	@Override
	public int getMaxRows() throws SQLException {
		throw new SQLException();
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		throw new SQLException();
	}

	@Override
	public boolean getMoreResults(int current) throws SQLException {
		throw new SQLException();
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		return t;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		throw new SQLException();
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		throw new SQLException();
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		throw new SQLException();
	}

	@Override
	public int getResultSetType() throws SQLException {
		throw new SQLException();
	}

	@Override
	public int getUpdateCount() throws SQLException {
		throw new SQLException();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new SQLException();
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		throw new SQLException();
	}

	@Override
	public boolean isClosed() throws SQLException {
		throw new SQLException();
	}

	@Override
	public boolean isPoolable() throws SQLException {
		throw new SQLException();
	}

	@Override
	public void setCursorName(String name) throws SQLException {
		throw new SQLException();
	}

	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException {
		throw new SQLException();
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		throw new SQLException();
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		throw new SQLException();
	}

	@Override
	public void setMaxFieldSize(int max) throws SQLException {
		throw new SQLException();
	}

	@Override
	public void setMaxRows(int max) throws SQLException {
		throw new SQLException();
	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		throw new SQLException();
	}

	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		log.info("Timeout has been set");
		t = seconds;
	}

}

class task implements Callable<Boolean> {
	private Get obj2 = new Get_set();
	private Database obj1 = obj2.getObject2();

	private Object object;
	private String sql;

	public task(Object object, String sql) {
		// TODO Auto-generated constructor stub
		this.object = object;
		this.sql = sql;
	}

	@Override
	public Boolean call() throws Exception {
		if ((sql.toLowerCase().contains("create")) || (sql.toLowerCase().contains("drop"))) {
			boolean toReturn = obj1.executeStructureQuery(sql);
			object = toReturn;
			return (Boolean) object;
		} else if ((sql.toLowerCase().contains("update")) || (sql.toLowerCase().contains("insert"))
				|| (sql.toLowerCase().contains("delete"))) {
			if (obj1.executeUpdateQuery(sql) == 0) {
				return false;
			} else {
				return true;
			}
		} else if (sql.toLowerCase().contains("select")) {
			if (obj1.executeQuery(sql) == null) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
}