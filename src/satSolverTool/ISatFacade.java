package satSolverTool;

import java.util.List;
import java.util.Map;

public interface ISatFacade {
 
	List<String> getInconsistentWords();
	
	void updateInconsistentWords(String filename, Map<String,String> wordPolarity);
	
}
