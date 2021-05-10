//package com.beanframework.modulegen;
//
//import java.io.File;
//import java.io.IOException;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.OutputKeys;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;
//
//public class Test {
//	public static void main(String[] args) throws IOException {
//		String filePath = "../../pom.xml";
//		File xmlFile = new File(filePath);
//		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder dBuilder;
//		try {
//			dBuilder = dbFactory.newDocumentBuilder();
//			Document doc = dBuilder.parse(xmlFile);
//			doc.getDocumentElement().normalize();
//
////			// update attribute value
////			updateAttributeValue(doc);
////
////			// update Element value
////			updateElementValue(doc);
////
////			// delete element
////			deleteElement(doc);
//
//			// add new element
//			addElement(doc);
//
//			// write the updated document to file or console
//			doc.getDocumentElement().normalize();
//			TransformerFactory transformerFactory = TransformerFactory.newInstance();
//			Transformer transformer = transformerFactory.newTransformer();
//			DOMSource source = new DOMSource(doc);
//			StreamResult result = new StreamResult(new File(filePath));
//			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//			transformer.transform(source, result);
//			System.out.println("XML file updated successfully");
//
//		} catch (SAXException | ParserConfigurationException | IOException | TransformerException e1) {
//			e1.printStackTrace();
//		}
//	}
//
//	private static void addElement(Document doc) {
//		NodeList modules = doc.getElementsByTagName("modules");
//		Element ele = null;
//
//		// loop for each employee
//		for (int i = 0; i < modules.getLength(); i++) {
//			ele = (Element) modules.item(i);
//			Element salaryElement = doc.createElement("module");
//			salaryElement.appendChild(doc.createTextNode("custom/test"));
//			ele.appendChild(salaryElement);
//		}
//	}
//
//	private static void deleteElement(Document doc) {
//		NodeList employees = doc.getElementsByTagName("Employee");
//		Element emp = null;
//		// loop for each employee
//		for (int i = 0; i < employees.getLength(); i++) {
//			emp = (Element) employees.item(i);
//			Node genderNode = emp.getElementsByTagName("gender").item(0);
//			emp.removeChild(genderNode);
//		}
//
//	}
//
//	private static void updateElementValue(Document doc) {
//		NodeList employees = doc.getElementsByTagName("Employee");
//		Element emp = null;
//		// loop for each employee
//		for (int i = 0; i < employees.getLength(); i++) {
//			emp = (Element) employees.item(i);
//			Node name = emp.getElementsByTagName("name").item(0).getFirstChild();
//			name.setNodeValue(name.getNodeValue().toUpperCase());
//		}
//	}
//
//	private static void updateAttributeValue(Document doc) {
//		NodeList employees = doc.getElementsByTagName("Employee");
//		Element emp = null;
//		// loop for each employee
//		for (int i = 0; i < employees.getLength(); i++) {
//			emp = (Element) employees.item(i);
//			String gender = emp.getElementsByTagName("gender").item(0).getFirstChild().getNodeValue();
//			if (gender.equalsIgnoreCase("male")) {
//				// prefix id attribute with M
//				emp.setAttribute("id", "M" + emp.getAttribute("id"));
//			} else {
//				// prefix id attribute with F
//				emp.setAttribute("id", "F" + emp.getAttribute("id"));
//			}
//		}
//	}
//
//}
