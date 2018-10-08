package pleb;

import java.io.File;

public class Instance {
	
	public String resPath(String path) {
		File file = new File(getClass().getResource(path).getFile());
		return file.getAbsolutePath();
	}
}
