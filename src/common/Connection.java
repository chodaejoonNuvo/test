package common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Connection {

	private Map<String, List<String>> files;

	public Map<String, List<String>> getFiles() {
		return files;
	}

	public void setFiles(Map<String, List<String>> files) {
		this.files = files;
	}

	public void commit() {
		if (null != this.files) {
			for (String key : this.files.keySet()) {
				File file = new File(key);
				file.delete();
				file = new File(key);
				try (FileWriter filewriter = new FileWriter(file)){
					List<String> lines = files.get(key);
					for (String line : lines) {
						filewriter.write(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void rollback() {
		
	}
}
