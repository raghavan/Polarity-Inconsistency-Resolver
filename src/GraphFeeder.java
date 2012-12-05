import java.util.ArrayList;
import java.util.List;


public class GraphFeeder {
private String parent;
private List<String> children = new ArrayList<String>();

public List<String> getChildren() {
	return children;
}

public void addChildren(String child) {
	children.add(child);
}

public String getParent() {
	return parent;
}

public void setParent(String parent) {
	this.parent = parent;
}



}
