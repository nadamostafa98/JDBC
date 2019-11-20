package eg.edu.alexu.csd.oop.db.cs09;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eg.edu.alexu.csd.oop.db.ItemElement;


public class NodeElements implements ItemElement {
	private String dbpath;
	private String tableName;
	private File db;
	private String colName;
	private Document doc;

	public NodeElements(String dbpath, String tableName, File db, String colchange) {
		this.tableName = tableName;
		this.db = db;
		this.dbpath = dbpath;
		this.colName = colName;

	}

	public NodeList nodeList() throws ParserConfigurationException, SAXException, IOException {
		File inputFile = new File(dbpath + db.separator + tableName + ".xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("row");
		return nList;
	}
	public  String getColName() {
		return colName;
	}
	@Override
	public String[] acceptvalue(GetFromTable1 visitor) {
		// TODO Auto-generated method stub
		return visitor.visitValues(this);
	}

	@Override
	public String[] acceptcol(GetFromTable1 visitor) {
		// TODO Auto-generated method stub
		return visitor.visitCol(this);
	}

	@Override
	public String[][] accepttable(GetFromTable1 visitor) {
		// TODO Auto-generated method stub
		return visitor.visitTable(this);
	}

}
