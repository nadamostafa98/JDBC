package eg.edu.alexu.csd.oop.db.cs09;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;

import org.xml.sax.SAXException;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.Get;
import eg.edu.alexu.csd.oop.db.ItemElement;
import eg.edu.alexu.csd.oop.db.Visitor;

public class DB implements Database {
	private File f;
	private LinkedList<String> tables = new LinkedList<>();
	private Get obj2 = new Get_set();
	private static Logger log = Logger.getLogger(DB.class.getName());

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		Create c = new Create(databaseName);
		if (dropIfExists) {
			c.drop();
			String path = c.create();
			f = new File(path);
			obj2.setFile(f);
			obj2.setObject2(getClass());
			tables.clear();
			log.info("Database created");
			return path;
		} else {
			String path = c.create();
			f = new File(path);
			obj2.setFile(f);
			obj2.setObject2(getClass());
			log.info("Database created");
			return path;
		}
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		ParserData x = new ParserData(query);
		x.intepreter(query);
		obj2.setObject(x);
		if (x.getcheckcreate() && x.getOperation().equalsIgnoreCase("create")) {
			log.warning("Error");
			throw new SQLException();
		}
		String operation = x.getOperation();
		BuildTab y = new BuildTable();
		XsdCreator xsd = new XsdCreator();
		boolean check = x.getbooleancheck();
		if (operation.equalsIgnoreCase("CREATE") && check) {

			if (x.getdatabasename() != null && x.getdatabasename().length() != 0) {
				String path = createDatabase(x.getdatabasename(), true);
				f = new File(path);
				obj2.setFile(f);
				return true;
			} else if (!tables.contains(x.getTable().toLowerCase())) {
				y.buildT(f.getAbsolutePath(), f, x.getTable(), x.getColumns());
				xsd.create(f.getAbsolutePath() + f.separator + x.getTable() + ".xml");
				tables.add(x.getTable().toLowerCase());
				log.info("Table Created");
				return true;
			} else {
				log.warning("Error");
				return false;
			}
		}
		if (operation.equalsIgnoreCase("DROP")) {
			if (x.getdeleteDatabase() != null) {
				Create c = new Create(x.getdeleteDatabase());
				c.drop();
				f = null;
				log.info("Database Dropped");
				log.info("Statement Dropped");
				return true;
			} else if (x.getdeletetable() != null && tables.size() != 0) {
				y.buildT(f.getAbsolutePath(), f, x.getdeletetable(), x.getColumns());
				File file = new File(f.getAbsolutePath() + f.separator + x.getdeletetable() + ".xml");
				file.delete();
				xsd.delete(f.getAbsolutePath() + f.separator + x.getdeletetable() + ".xsd");
				tables.remove(x.getdeletetable());
				log.info("Table Dropped");
				return true;
			}

		}
		log.warning("Error");
		return false;
	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {
		ParserData x = new ParserData(query);
		BuildTab y = new BuildTable();
		Selected z = new Selected();
		XsdCreator xsd = new XsdCreator();
		if (f != null && tables.size() != 0) {
			x.intepreter(query);
			obj2.setObject(x);
		} else {
			log.warning("Condition is not found!");
			return new Object[0][0];
		}
		String operation = x.getOperation();
		boolean check = x.getbooleancheck();
		String s1 = x.getTable().toLowerCase();
		if (tables.contains(s1) && f != null) {
			if (operation.equalsIgnoreCase("SELECT") && check) {
				ItemElement s = new NodeElements(f.getAbsolutePath(), x.getTable(), f, x.getcolchange());
				Visitor get = new GetFromTable1();
				s.accepttable((GetFromTable1) get);
				Map<String, String> map1 = x.getToSelect1();
				Map<String, String[]> map2 = x.getToSelect2();
				Map<String, Boolean> map3 = x.getToSelect3();

				Object[][] a = z.fillselectarray(map1.get("colchange"), s.acceptcol((GetFromTable1) get),
						s.accepttable((GetFromTable1) get), map1.get("operation"), map1.get("value"), map1.get("logic"),
						map2.get("values"), map2.get("operations"), map2.get("colsChange"), map3.get("where"),
						map3.get("star"), map3.get("select"), map1.get("colSelect"));
				if ((z.getRows().length == s.accepttable((GetFromTable1) get).length) && !map3.get("where")) {
					log.info("Successful selection");
					return s.accepttable((GetFromTable1) get);
				}
				log.info("Successful selection");
				return a;
			}
		}
		log.warning("Error!");
		return null;
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		ParserData x = new ParserData(query);
		Selected k = new Selected();
		BuildTab y = new BuildTable();
		if (f != null && tables.size() != 0) {
			x.intepreter(query);
			obj2.setObject(x);
		} else {
			log.warning("Database or table not found!");
			return 0;
		}
		XsdCreator xsd = new XsdCreator();
		String operation = x.getOperation();
		String s1 = x.getTable().toLowerCase();
		if (tables.contains(s1) && f != null && tables.size() != 0) {
			boolean check = x.getbooleancheck();
			if (operation.equalsIgnoreCase("INSERT") && check) {
				try {
					ItemElement s = new NodeElements(f.getAbsolutePath(), x.getTable(), f, x.getcolchange());
					Visitor get = new GetFromTable1();
					y.insertT(x.getTable(), f.getAbsolutePath(), f, x.getcolumns(), x.getvalueinsert(),
							s.acceptcol((GetFromTable1) get));
					xsd.delete(f.getAbsolutePath() + f.separator + x.getTable() + ".xsd");
					xsd.create(f.getAbsolutePath() + f.separator + x.getTable() + ".xml");
					log.info("Successful Insert");
					return 1;
				} catch (SAXException | IOException e) {
					// TODO Auto-generated catch block
					log.warning("Error in insert!");
				}
			}
			if (operation.equalsIgnoreCase("DELETE") && check) {
				try {
					ItemElement s = new NodeElements(f.getAbsolutePath(), x.getTable(), f, x.getcolchange());
					Visitor get = new GetFromTable1();
					Map<String, String> map1 = x.getToSelect1();
					Map<String, String[]> map2 = x.getToSelect2();
					Map<String, Boolean> map3 = x.getToSelect3();
					Object[][] a = k.fillselectarray(map1.get("colchange"), s.acceptcol((GetFromTable1) get),
							s.accepttable((GetFromTable1) get), map1.get("operation"), map1.get("value"),
							map1.get("logic"), map2.get("values"), map2.get("operations"), map2.get("colsChange"),
							map3.get("where"), map3.get("star"), map3.get("select"), map1.get("colSelect"));
					y.delete(x.getTable(), k.getRows(), f.getAbsolutePath(), f, x.theColumns);
					xsd.delete(f.getAbsolutePath() + f.separator + x.getTable() + ".xsd");
					xsd.create(f.getAbsolutePath() + f.separator + x.getTable() + ".xml");
					log.info("Rows deleted successfully!");
					return k.getRows().length;
				} catch (SAXException | IOException e) {
					// TODO Auto-generated catch block
					log.warning("Error in deleting !");
				}
			}
			if (operation.equalsIgnoreCase("UPDATE") && check) {
				try {
					ItemElement s = new NodeElements(f.getAbsolutePath(), x.getTable(), f, x.getcolchange());
					Visitor get = new GetFromTable1();
					Map<String, String> map1 = x.getToSelect1();
					Map<String, String[]> map2 = x.getToSelect2();
					Map<String, Boolean> map3 = x.getToSelect3();
					Object[][] a = k.fillselectarray(map1.get("colchange"), s.acceptcol((GetFromTable1) get),
							s.accepttable((GetFromTable1) get), map1.get("operation"), map1.get("value"),
							map1.get("logic"), map2.get("values"), map2.get("operations"), map2.get("colsChange"),
							map3.get("where"), map3.get("star"), map3.get("select"), map1.get("colSelect"));
					boolean b = y.update(f.getAbsolutePath(), f, x.getTable(), k.getRows(), x.getcolumns(),
							x.getvalueinsert());
					xsd.delete(f.getAbsolutePath() + f.separator + x.getTable() + ".xsd");
					xsd.create(f.getAbsolutePath() + f.separator + x.getTable() + ".xml");
					if (b) {
						log.info("Successful update!");
						return k.getRows().length;
					} else {
						log.warning("Error in updating!");
						return 0;
					}
				} catch (SAXException | IOException e) {
					log.warning("Error in updating!");
				}
			}
		}
		log.warning("Error!");
		throw new SQLException();
	}

}