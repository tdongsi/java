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

/**
 * Generate SSL plugin-specification file in XML format
 * 
 * @author cuongd
 *
 */
public class PluginGenerator {
	
	// Two pre-built XML template. See 
	// Server template
	private static PluginGenerator serverTemplate;
	// Client template
	private static PluginGenerator clientTemplate;

	// private attributes
	private final String rootName;
	private boolean enablePlugin = false;
	private String libraryName = "SS";
	private String ssType = "all";
	private String certPath = "C:\\\\space\\\\server.crt";
	private String keyPath = "C:\\\\space\\\\server.key";
	private String passphrasePath = "";
	private String cipherSuite = "AES256-SHA:RC4:AES128-SHA:ALL";
	
	// attributes for client
	private boolean verifyCert = false;
	private String verifyCertCAFile = "C:\\\\OpenSSL-Win64\\\\caroot\\\\ObjyCA\\\\root-1-ca.crt";
	private String verifyCertCAPath = "C:\\\\OpenSSL-Win64\\\\caroot\\\\ObjyCA";
	private String verifyCertSubCN = "";
	private String verifyCertSubO = "";
	private String verifyCertSubOU = "";

	// Hidden parameter
	private boolean enableSslAll = false;
	
	// Supporting fields
	private DocumentBuilder docBuilder;
	private Transformer transformer;
	private Map<String,String> valueMap;

	public PluginGenerator(String rootName) {
		super();
		this.rootName = rootName;
		
		valueMap = new HashMap<String,String>(50);
		
		try {
			// Prepare to construct the xml file
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			
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
			Document doc = myXmlSchema(docBuilder);
			
			////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filename));
			transformer.transform(source, result);
			System.out.println("Plugin file saved at " + filename);

		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
	
	/**
	 * Output to console for testing
	 */
	public void printXml() {
		try {

			// use my XML schema to generate XML doc
			Document doc = myXmlSchema(docBuilder);

			// //////////////////////////////////////////////////////
			// //////////////////////////////////////////////////////

			DOMSource source = new DOMSource(doc);
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
	private Document myXmlSchema(DocumentBuilder docBuilder) {
		Document doc = docBuilder.newDocument();
		
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
				
				String libraryFile = libraryName + ".dll";
				String debugLibraryFile = libraryName + "d.dll";
				cppImpl.setAttribute("library", libraryFile);
				cppImpl.setAttribute("debugLibrary", debugLibraryFile);
			}
			
			{
				// Value elements
				for ( Map.Entry<String, String> entry: valueMap.entrySet() )
				{
					Element valueElement = doc.createElement("Value");
					plugin.appendChild(valueElement);
					
					valueElement.setAttribute("name", entry.getKey());
					valueElement.setAttribute("value", entry.getValue());
				}
				
			}
		}
		
		return doc;

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

	/**
	 * Specify this line
	 * <CppImplementation library="SecureSocket.dll" debugLibrary="SecureSocketd.dll"/>
	 * 
	 * @param libraryName
	 * @return
	 */
	public PluginGenerator setLibraryName(String libraryName) {
		this.libraryName = libraryName;
		return this;
	}

	public String getSsType() {
		return ssType;
	}

	/**
	 * Specify this line
	 * <Value name="SecureSocketType"    value="all"/>
	 * 
	 * @param ssType
	 * @return
	 */
	public PluginGenerator setSsType(String ssType) {
		this.ssType = ssType;
		valueMap.put("SecureSocketType", ssType);
		return this;
	}

	public String getCertPath() {
		return certPath;
	}

	/**
	 * Specify this line
	 * <Value name="Certificate"         value="C:\\objy\\server.crt"/>
	 * 
	 * @param certPath
	 * @return
	 */
	public PluginGenerator setCertPath(String certPath) {
		this.certPath = certPath;
		valueMap.put("Certificate", certPath);
		return this;
	}

	public String getKeyPath() {
		return keyPath;
	}

	/**
	 * Specify this line
	 * <Value name="PrivateKey"         value="C:\\objy\\server.key"/>
	 * 
	 * @param keyPath
	 * @return
	 */
	public PluginGenerator setKeyPath(String keyPath) {
		this.keyPath = keyPath;
		valueMap.put("PrivateKey", keyPath);
		return this;
	}

	public String getPassphrasePath() {
		return passphrasePath;
	}

	/**
	 * Specify this line
	 * <Value name="PassphraseFile"      value=""/>
	 * 
	 * @param passphrasePath
	 */
	public PluginGenerator setPassphrasePath(String passphrasePath) {
		this.passphrasePath = passphrasePath;
		valueMap.put("PassphraseFile", passphrasePath);
		return this;
	}

	public String getCipherSuite() {
		return cipherSuite;
	}

	/**
	 * Specify this line
	 * <Value name="ciphersuite"         value="AES256-SHA:RC4:AES128-SHA:ALL"/>
	 * 
	 * @param cipherSuite
	 */
	public PluginGenerator setCipherSuite(String cipherSuite) {
		this.cipherSuite = cipherSuite;
		valueMap.put("ciphersuite", cipherSuite);
		return this;
	}

	public boolean isEnableSslAll() {
		return enableSslAll;
	}

	/**
	 * Specify this line
	 * <Value name="SslAll"             value="1"/>
	 * 
	 * @param enableSslAll
	 */
	public PluginGenerator setEnableSslAll(boolean enableSslAll) {
		this.enableSslAll = enableSslAll;
		if ( enableSslAll )
		{
			valueMap.put("SslAll", "1");
		} else
		{
			valueMap.put("SslAll", "0");
		}
		return this;
	}

	public String getRootElem() {
		return rootName;
	}
	
	public boolean isVerifyCert() {
		return verifyCert;
	}

	/**
	 * Specify this line and add default values for other VerifyCert fields
	 * <Value name="VerifyCert"                   value="true"/>
	 * 
	 * @param verifyCert
	 */
	public PluginGenerator setVerifyCert(boolean verifyCert) {
		this.verifyCert = verifyCert;
		
		valueMap.put("VerifyCert", Boolean.toString(verifyCert));
		// Once you set VerifyCert, all these fields take default values
		// Use the setter methods below to set a custom value
		valueMap.put("VerifyCertCAFile", verifyCertCAFile);
		valueMap.put("VerifyCertCAPath", verifyCertCAPath);
		valueMap.put("VerifyCertSubCN", verifyCertSubCN);
		valueMap.put("VerifyCertSubO", verifyCertSubO);
		valueMap.put("VerifyCertSubOU", verifyCertSubOU);
		
		return this;
	}

	public String getVerifyCertCAFile() {
		return verifyCertCAFile;
	}

	public PluginGenerator setVerifyCertCAFile(String verifyCertCAFile) {
		this.verifyCertCAFile = verifyCertCAFile;
		
		valueMap.put("VerifyCertCAFile", verifyCertCAFile);
		
		return this;
	}

	public String getVerifyCertCAPath() {
		return verifyCertCAPath;
	}

	public PluginGenerator setVerifyCertCAPath(String verifyCertCAPath) {
		this.verifyCertCAPath = verifyCertCAPath;
		
		valueMap.put("VerifyCertCAPath", verifyCertCAPath);
		
		return this;
	}

	public String getVerifyCertSubCN() {
		return verifyCertSubCN;
	}

	public PluginGenerator setVerifyCertSubCN(String verifyCertSubCN) {
		this.verifyCertSubCN = verifyCertSubCN;
		valueMap.put("VerifyCertSubCN", verifyCertSubCN);
		return this;
	}

	public String getVerifyCertSubO() {
		return verifyCertSubO;
	}

	public PluginGenerator setVerifyCertSubO(String verifyCertSubO) {
		this.verifyCertSubO = verifyCertSubO;
		valueMap.put("VerifyCertSubO", verifyCertSubO);
		return this;
	}

	public String getVerifyCertSubOU() {
		return verifyCertSubOU;
	}

	public PluginGenerator setVerifyCertSubOU(String verifyCertSubOU) {
		this.verifyCertSubOU = verifyCertSubOU;
		valueMap.put("VerifyCertSubOU", verifyCertSubOU);
		return this;
	}

	/**
	 * Return a minimum server plugin-specification file.
	 * According to IG SSL plug-in doc.
	 * 
	 * TODO: Maybe the more efficient way is to
	 * 1. implement a copy constructor for PluginGenerator (DO NOT use clone)
	 * 2. pre-build the private static variable serverTemplate
	 * 3. return a copy of the pre-built template in this method
	 * 
	 * @return
	 */
	public static PluginGenerator createServerTemplate()
	{
		return new PluginGenerator("Plugins").setEnablePlugin(true)
				.setLibraryName("ooSecureSocket112")
				.setSsType("all")
				.setCertPath("C:\\\\objy\\\\server.crt")
				.setKeyPath("C:\\\\objy\\\\server.key")
				.setPassphrasePath("");
	}
	
	/**
	 * Return a minimum client plugin-specification file.
	 * 
	 * @return
	 */
	public static PluginGenerator createClientTemplate()
	{
		// For testing purpose, certificate by CA is skipped
		return new PluginGenerator("Plugins").setEnablePlugin(true)
				.setLibraryName("ooSecureSocket112")
				.setSsType("all")
				.setVerifyCert(false);
	}

	/**
	 * Example usages.
	 * Use generateXml() instead of printXml() to create the XML plugin-spec file 
	 * 
	 */
	public static void main(String[] args) {
		
		// Creating XML plugin file from scratch
		PluginGenerator gen = new PluginGenerator("Plugins");
		gen.setEnablePlugin(true)
			.setLibraryName("OpenSSL")
			.printXml();
		
		System.out.println();
		
		// Pre-built server template
		PluginGenerator serverTemplate = PluginGenerator.createServerTemplate();
		serverTemplate.printXml();
		
		System.out.println();
		
		// Modify the server template to have a custom server plugin-spec file
		PluginGenerator customServerSpec = serverTemplate;
		customServerSpec.setCertPath("C:\\\\ig\\\\server.crt")
			.setKeyPath("C:\\\\ig\\\\server.key");
		customServerSpec.printXml();
		
		// Use this method to generate the actual file
		customServerSpec.generateXml("Secure.plugin");
		
		System.out.println();
		
		// Pre-built client template
		PluginGenerator clientTemplate = PluginGenerator.createClientTemplate();
		clientTemplate.printXml();
		
		System.out.println();
		
		// Modify the client template
		PluginGenerator customClientSpec = PluginGenerator.createClientTemplate();
		customClientSpec.setVerifyCert(true)
			.setVerifyCertCAFile("C:\\\\newca.crt")
			.setVerifyCertCAPath("C:\\\\");
		customClientSpec.printXml();
	}

}
