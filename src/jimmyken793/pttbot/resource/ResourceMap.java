package jimmyken793.pttbot.resource;

import java.io.BufferedReader;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ResourceMap implements Map<String, String> {
	private HashMap<String, String> defmap;

	String rcfile = ".ztermrc";
	
	public ResourceMap(String filename) {
		rcfile=filename;
		File rc = getRcFile();
		defmap = new HashMap<String, String>();
		
		// 載入預設值
		loadDefault();
		if( rc.exists() ) {
			readFile();
		} else {
			try {
				rc.createNewFile();
				// System.out.println("rcfile: " + rc.getName() + " created.");
			} catch (IOException e) {
				System.out.println("catch IOException when create new rcfile.");
			}
		}
	}

	protected void loadDefault() {

	}

	private File getRcFile() {
		File f;
		String home = System.getProperty("user.home");
		f = new File(home + File.separator + rcfile);
		// System.out.println( f );
		return f;
	}

	private void parseLine(String line) {
		String[] argv;

		// 用 "::" 隔開參數名與值
		argv = line.split("::");

		if (argv.length != 2) {
			return;
		}

		if (argv[0].length() > 0) {
			defmap.put(argv[0], argv[1]);
		}
	}

	public void readFile() {
		File rc = getRcFile();
		BufferedReader br;
		String buf;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(rc), "UTF8"));

			while ((buf = br.readLine()) != null) {
				parseLine(buf);
			}

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeFile() {
		File rc = getRcFile();
		TreeSet ts;
		Iterator iter;
		String str;
		PrintWriter pw;

		// TreeSet 才會排序
		ts = new TreeSet(defmap.keySet());
		iter = ts.iterator();

		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(rc), "UTF8"));

			while (iter.hasNext()) {
				str = iter.next().toString();
				pw.println(str + "::" + defmap.get(str));
			}

			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String read(String key) {
		return (String) defmap.get(key);
	}

	public void clear() {
		defmap.clear();
	}

	public boolean containsKey(Object arg0) {
		return defmap.containsKey(arg0);
	}

	public boolean containsValue(Object arg0) {
		return defmap.containsValue(arg0);
	}

	public Set<java.util.Map.Entry<String, String>> entrySet() {
		return defmap.entrySet();
	}

	public String get(Object arg0) {
		return defmap.get(arg0);
	}

	public boolean isEmpty() {
		return defmap.isEmpty();
	}

	public Set<String> keySet() {
		return defmap.keySet();
	}

	public String put(String arg0, String arg1) {
		return defmap.put(arg0, arg1);
	}

	public void putAll(Map<? extends String, ? extends String> arg0) {
		defmap.putAll(arg0);
	}

	public String remove(Object arg0) {
		return defmap.remove(arg0);
	}

	public int size() {
		return defmap.size();
	}

	public Collection<String> values() {
		return defmap.values();
	}

}