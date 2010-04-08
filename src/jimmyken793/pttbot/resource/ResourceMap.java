package jimmyken793.pttbot.resource;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResourceMap implements Map<String, String> {
	private HashMap<String, String> defmap;

	public ResourceMap() {
		defmap = new HashMap<String, String>();
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