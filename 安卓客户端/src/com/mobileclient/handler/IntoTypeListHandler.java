package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.IntoType;
public class IntoTypeListHandler extends DefaultHandler {
	private List<IntoType> intoTypeList = null;
	private IntoType intoType;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (intoType != null) { 
            String valueString = new String(ch, start, length); 
            if ("typeId".equals(tempString)) 
            	intoType.setTypeId(new Integer(valueString).intValue());
            else if ("infoTypeName".equals(tempString)) 
            	intoType.setInfoTypeName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("IntoType".equals(localName)&&intoType!=null){
			intoTypeList.add(intoType);
			intoType = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		intoTypeList = new ArrayList<IntoType>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("IntoType".equals(localName)) {
            intoType = new IntoType(); 
        }
        tempString = localName; 
	}

	public List<IntoType> getIntoTypeList() {
		return this.intoTypeList;
	}
}
