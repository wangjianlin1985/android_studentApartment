package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.LiveInfo;
public class LiveInfoListHandler extends DefaultHandler {
	private List<LiveInfo> liveInfoList = null;
	private LiveInfo liveInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (liveInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("liveInfoId".equals(tempString)) 
            	liveInfo.setLiveInfoId(new Integer(valueString).intValue());
            else if ("studentObj".equals(tempString)) 
            	liveInfo.setStudentObj(valueString); 
            else if ("roomObj".equals(tempString)) 
            	liveInfo.setRoomObj(new Integer(valueString).intValue());
            else if ("liveDate".equals(tempString)) 
            	liveInfo.setLiveDate(Timestamp.valueOf(valueString));
            else if ("liveMemo".equals(tempString)) 
            	liveInfo.setLiveMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("LiveInfo".equals(localName)&&liveInfo!=null){
			liveInfoList.add(liveInfo);
			liveInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		liveInfoList = new ArrayList<LiveInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("LiveInfo".equals(localName)) {
            liveInfo = new LiveInfo(); 
        }
        tempString = localName; 
	}

	public List<LiveInfo> getLiveInfoList() {
		return this.liveInfoList;
	}
}
