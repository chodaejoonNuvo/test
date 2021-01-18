package common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBAccess extends Connection {

	/****************************************************
	 * 仮実装開始
	 ***************************************************/
	private Connection con;
	private String tableNM;
	/**
	 * DAOコンストラクタ
	 * @param con コネクション
	 * @param tableNM テーブル名
	 */
	protected DBAccess(Connection con, String tableNM) {
		this.con = con;
		this.tableNM = tableNM;
	}

	/****************************************************
	 * 仮実装終了
	 ***************************************************/
	public void connect() {
	}
	
	protected void addFileInfo(List<String> lines) {
		Map<String, List<String>> files = this.con.getFiles();
		if (null == files) {
			files = new HashMap<>();
		}
		files.put(this.tableNM, lines);
		this.con.setFiles(files);
	}
	
	protected List<String> getFileInfo() {
		Map<String, List<String>> files = this.con.getFiles();
		if (null == files) {
			files = new HashMap<>();
		}
		return files.get(this.tableNM);
	}
}
