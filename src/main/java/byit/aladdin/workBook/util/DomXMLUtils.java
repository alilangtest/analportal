package byit.aladdin.workBook.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DomXMLUtils {
	private static final Logger logger = Logger.getLogger(DomXMLUtils.class);
	/**
	 * 解析xml并存储到集合中
	 * @param document
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static List<KeyMap<String, String>> readDomXML(Document document) throws DocumentException{
		List<KeyMap<String, String>> list = new ArrayList<KeyMap<String,String>>();
		//获取顶级节点
		Element root =  document.getRootElement();
		logger.debug("root："+root.getName()+"---"+root.getText());
		//获取顶级节点下的所有子节点
		List<Element> elements = root.elements();
		for (Element element : elements) {
			String name = element.getName();
			String text = element.getText();
			KeyMap<String, String> map = new KeyMap<String, String>();
			map.setKey(name);
			map.setValue(text);
			list.add(map);
		}
		return list;
	}
	
	public static List<KeyMap<String, String>> readDomXML(String xmlpath) throws Exception{
		Document document = DomXMLUtils.doGetDocument(xmlpath);
		return DomXMLUtils.readDomXML(document);
	}
	
	public static int queryNodesCount(String xmlpath) throws Exception{
		Document document = DomXMLUtils.doGetDocument(xmlpath);
		//获取顶级节点
		Element root =  document.getRootElement();
		//获取顶级节点下的所有子节点个数
		return root.elements().size();
	}
	
	/**
	 * 获取指定节点
	 * @param document
	 * @param node
	 * @return
	 */
	public static KeyMap<String, String> querySingNode(Document document,String node){
		KeyMap<String, String> map = new KeyMap<String, String>();
		Element root =  document.getRootElement();
		Element nodeElement = (Element)root.selectSingleNode(node);
		String name =  nodeElement.getName();
		String text =  nodeElement.getText();
		map.setKey(name);
		map.setValue(text);
		logger.debug("节点--"+name+"----,内容--"+text);
		return map;
	}
	
	public static KeyMap<String, String> querySingNode(String xmlPath,String node) throws Exception{
		KeyMap<String, String> map = new KeyMap<String, String>();
		Document document = DomXMLUtils.doGetDocument(xmlPath);
		Element root =  document.getRootElement();
		Element nodeElement = (Element)root.selectSingleNode(node);
		String name =  nodeElement.getName();
		String text =  nodeElement.getText();
		map.setKey(name);
		map.setValue(text);
		return map;
	}
	
	/**
	 * 获取指定节点的下一个节点
	 * @param document
	 * @param node
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static KeyMap<String, String> queryNextNode(Document document,String node){
		KeyMap<String, String> map = new KeyMap<String, String>();
		Element root =  document.getRootElement();
		Element nodeElement = (Element)root.selectSingleNode(node);
		int index = root.indexOf(nodeElement);
		//收尾占两个位置，所以获取位置需要除以2，并且加1
		if(index>0)index = index/2 +1;
		logger.debug("index-------"+index);
		List<Element> list = root.elements();
		if(list.size()>0 && index < list.size()){
			Element element = list.get(index);
			String name =  element.getName();
			String text =  element.getText();
			map.setKey(name);
			map.setValue(text);
		}
		logger.debug("下一个节点----"+map);
		return map;
	}
	@SuppressWarnings("unchecked")
	public static KeyMap<String, String> queryNextNode(String xmlPath,String node) throws Exception{
		KeyMap<String, String> map = new KeyMap<String, String>();
		Document document = DomXMLUtils.doGetDocument(xmlPath);
		Element root =  document.getRootElement();
		Element nodeElement = (Element)root.selectSingleNode(node);
		int index = root.indexOf(nodeElement);
		//收尾占两个位置，所以获取位置需要除以2，并且加1
		if(index>0)index = index/2 +1;
		logger.debug("index-------"+index);
		List<Element> list = root.elements();
		if(list.size()>0 && index < list.size()){
			Element element = list.get(index);
			String name =  element.getName();
			String text =  element.getText();
			map.setKey(name);
			map.setValue(text);
		}
		logger.debug("下一个节点----"+map);
		return map;
	}
	/**
	 * 获取document
	 * @param xmlData
	 * @return
	 * @throws DocumentException
	 */
	public static Document doGetDocument(String xmlData) throws Exception{
		//读取xml,获取document对象
		SAXReader reader = new SAXReader();
		
		InputStream is = new ByteArrayInputStream(xmlData.getBytes("UTF-8"));
		Document document = reader.read(is);
		return document;
	}
	
	public static void main(String[] args) throws Exception {
		String xmlData = "<?xml version='1.0' encoding='UTF-8'?>"
				+ "<tsResponse xmlns=\"http://tableau.com/api\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://tableau.com/api http://tableau.com/api/ts-api-2.4.xsd\">"
				+ "<credentials token=\"h49VdHb4toeIpSQZXxOlsNcl8qWiXTpH\">"
				+ "<site id=\"25713daf-d1ec-4b89-87f4-aef3e2ca6dea\" contentUrl=\"\" />"
				+ "<user id=\"4304958d-22b4-49aa-93cc-143526737bc6\" /></credentials></tsResponse>";
		Document document =doGetDocument(xmlData);
		Element root =  document.getRootElement();
		logger.debug("root---"+root);
		Element element = root.element("credentials");
		logger.debug(element.attributeValue("token"));
	}
}
