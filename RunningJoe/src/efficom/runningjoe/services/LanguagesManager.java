package efficom.runningjoe.services;

import java.util.HashMap;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class LanguagesManager {
	private static LanguagesManager instance = null;
        
    private static final String LANGUAGES_FILE = "data/languages.xml";
    private static final String DEFAULT_LANGUAGE = "fr_FR";
    private HashMap<String, String> language = null;
    private String languageName = null;
       
    private LanguagesManager() {
        // Create language map
        language = new HashMap<String, String>();
               
        // Try to load system language
        // If it fails, fallback to default language
        languageName = java.util.Locale.getDefault().toString();
        if (!loadLanguage(languageName)) {
        	loadLanguage(DEFAULT_LANGUAGE);
            languageName = DEFAULT_LANGUAGE;
            }
    }
        
    public final static LanguagesManager getInstance() {
    	if (instance == null) {
            synchronized(LanguagesManager.class) {
              if (instance == null) {
                instance = new LanguagesManager();
              }
            }
         }
                
        return instance;
    }
        
    public String getLanguage() {
    	return languageName;
    }

    public String getString(String key) {
    	String string;
                
        if (language != null) {
        	// Look for string in selected language
            string = language.get(key);
                        
            if (string != null) 
            	return string;
        }
        
        // Key not found, return the key itself
        return key;
    }
        
    public String getString(String key, Object... args) {
    	return String.format(getString(key), args);
    }
        
    public boolean loadLanguage(String languageName) {
    	try {
    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            FileHandle fileHandle = Gdx.files.internal(LANGUAGES_FILE);
            Document doc = db.parse(fileHandle.read());
                        
            Element root = doc.getDocumentElement();
                        
            NodeList languages = root.getElementsByTagName("language");
            int numLanguages = languages.getLength();
                        
            for (int i = 0; i < numLanguages; ++i) {
            	Node languageNode = languages.item(i);
                                
                if (languageNode.getAttributes().getNamedItem("name").getTextContent().equals(languageName)) {
                	language.clear();
                    Element languageElement = (Element)languageNode;
                    NodeList strings = languageElement.getElementsByTagName("string");
                    int numStrings = strings.getLength();
                                        
                    for (int j = 0; j < numStrings; ++j) {
                    	NamedNodeMap attributes = strings.item(j).getAttributes();
                        String key = attributes.getNamedItem("key").getTextContent();
                        String value = attributes.getNamedItem("value").getTextContent();
                        System.out.println(value);
                        value = value.replace("<br />", "\n");
                        language.put(key, value);
                    }
                                        
                    return true;
                }
            }
        }catch (Exception e) {
        	System.out.println("Error loading languages file " + LANGUAGES_FILE);
            return false;
        }
                
        return false;
    }
}