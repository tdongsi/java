package my.java.programming.tools;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PluginGenerator {

	private final String rootName;
	private boolean enablePlugin = false;
	private String libraryName = "SS";
	private String ssType = "all";
	private String certPath = "C:\\space\\server.crt";
	private String keyPath = "C:\\space\\server.key";
	private String passphrasePath = "";
	private String cipherSuite = "AES256-SHA:RC4:AES128-SHA:ALL";

	// Hidden parameter
	private boolean enableSslAll = false;
	
	// Supporting fields
	private Document doc;
	private Transformer transformer;
	private Map<String,String> valueMap;

	public PluginGenerator(String rootName) {
		super();
		this.rootName = rootName;
		
		valueMap = new HashMap<String,String>(50);
		
		try {
			// Prepare to construct the xml file
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			
			////////////////////////////////////////////////////////

			// Prepare to write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			transformer = transformerFactory.newTransformer();

			// Set formatting for XML output
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "4");
			// Remove xml file tag <?xml version='1.0' encoding='UTF-8'?>
			transformer.setOutputProperty("omit-xml-declaration", "yes");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	/**
	 * Do the setup and output configurations for generating XML file
	 * 
	 * @param filename
	 */
	public void generateXml(String filename) {
		try {
			
			// use my XML schema to generate XML doc
			myXmlSchema(doc);
			
			////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filename));
			transformer.transform(source, result);
			System.out.println("Plugin file saved at " + filename);

			// DEBUG: Output to console for testing
			StreamResult out = new StreamResult(System.out);
			transformer.transform(source, out);

		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	/**
	 * The schema of the XML is defined here
	 * 
	 * The elements with relevant tag names and attributes are defined here
	 */
	private void myXmlSchema(Document doc) {
		Element rootElement = doc.createElement(rootName);
		doc.appendChild(rootElement);

		{
			// Plugin element
			Element plugin = doc.createElement("Plugin");
			rootElement.appendChild(plugin);
			plugin.setAttribute( "extensionPoint", "SecureSocket" );
			plugin.setAttribute( "enable", Boolean.toString(enablePlugin));
			
			{
				// CppImplementation element
				Element cppImpl = doc.createElement("CppImplementation");
				plugin.appendChild(cppImpl);
				
				String libraryFile = "oo" + libraryName + ".dll";
				String debugLibraryFile = "oo" + libraryName + "d.dll";
				cppImpl.setAttribute("library", libraryFile);
				cppImpl.setAttribute("debugLibrary", debugLibraryFile);
			}
			
			{
				// Value elements
				valueMap.put("SecureSocketType", ssType);
				valueMap.put("Certificate", certPath);
				
				for ( Map.Entry<String, String> entry: valueMap.entrySet() )
				{
					Element valueElement = doc.createElement("Value");
					plugin.appendChild(valueElement);
					
					valueElement.setAttribute("name", entry.getKey());
					valueElement.setAttribute("value", entry.getValue());
				}
				
			}
		}

	}

	public boolean isEnablePlugin() {
		return enablePlugin;
	}

	/**
	 * Specify this line
	 * <Plugin extensionPoint="SecureSocket" enable="false">
	 * 
	 * @param enablePlugin
	 * @return
	 */
	public PluginGenerator setEnablePlugin(boolean enablePlugin) {
		this.enablePlugin = enablePlugin;
		return this;
	}

	public String getLibraryName() {
		return libraryName;
	}

	public PluginGenerator setLibraryName(String libraryName) {
		this.libraryName = libraryName;
		return this;
	}

	public String getSsType() {
		return ssType;
	}

	public void setSsType(String ssType) {
		this.ssType = ssType;
	}

	public String getCertPath() {
		return certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	public String getKeyPath() {
		return keyPath;
	}

	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}

	public String getPassphrasePath() {
		return passphrasePath;
	}

	public void setPassphrasePath(String passphrasePath) {
		this.passphrasePath = passphrasePath;
	}

	public String getCipherSuite() {
		return cipherSuite;
	}

	public void setCipherSuite(String cipherSuite) {
		this.cipherSuite = cipherSuite;
	}

	public boolean isEnableSslAll() {
		return enableSslAll;
	}

	public void setEnableSslAll(boolean enableSslAll) {
		this.enableSslAll = enableSslAll;
	}

	public String getRootElem() {
		return rootName;
	}

	/**
	 * Testing
	 */
	public static void main(String[] args) {
//		PluginGenerator gen = new PluginGenerator("Plugins");
//		gen.generateXml("test.plugin");
		
		PluginGenerator gen2 = new PluginGenerator("Plugins");
		gen2.setEnablePlugin(true)
			.setLibraryName("SecureSocket112")
			.generateXml("SecureSocket.plugin");
	}

}
