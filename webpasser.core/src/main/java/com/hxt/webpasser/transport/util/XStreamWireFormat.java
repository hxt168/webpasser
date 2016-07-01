
package  com.hxt.webpasser.transport.util;

import java.io.IOException;
import java.io.Reader;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong@rongji.com <br>
 * 开发时间: 2014-9-16 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class XStreamWireFormat {
	  
	  /**
	   * 从字符串转换成xml
	   * @param text
	   * @return
	   */
	    public static <T> T unmarshalText(String text,Class<T> cls) {
	    	XStream xstream=getxStream();
	    	xstream.processAnnotations(cls);
	    	@SuppressWarnings("unchecked")
			T obj=(T)xstream.fromXML(text); 
	        return obj;
	    }

	    
	    public static <T> T unmarshalText(Reader reader,Class<T> cls) {
	    	XStream xstream=getxStream();
	    	xstream.processAnnotations(cls);
	    	@SuppressWarnings("unchecked")
			T obj=(T)xstream.fromXML(reader); 
	        return obj;
	    }
	    
	    /**
	     * 转换类为xml
	     * @param command
	     * @return
	     * @throws IOException
	     */
	    public static String marshalText(Object command) throws IOException {
	    	XStream xstream=getxStream();
	    	xstream.processAnnotations(command.getClass()); 
	    	
	        return xstream.toXML(command);
	    }

	    
		private static XStream getxStream() {

			XStream xstream = new XStream(new DomDriver("utf-8"));
	        xstream.ignoreUnknownElements();
		            // make it work in OSGi env
	        xstream.setClassLoader(XStreamWireFormat.class.getClassLoader());
			return xstream;
		}
	
	
		
		public static void main(String[] args) {
			
			/*FileInfoMessage fileInfo=new FileInfoMessage();
			
			fileInfo.setFileId("2222");
			fileInfo.setWantRelativePath("sdds.flv");
		//	XStreamWireFormat xsf=new XStreamWireFormat();
			String str=null;
			try {
				str =XStreamWireFormat.marshalText(fileInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(str);
			
					
		str="<FileInfoMessage><fileId>2222</fileId><wantRelativePath>sdds.flv</wantRelativePath>" +
				"<isMd5Check>true</isMd5Check>" +
				"<isTransit>false</isTransit>" +
				"</FileInfoMessage>";			
		FileInfoMessage ob=XStreamWireFormat.unmarshalText(str,FileInfoMessage.class);
			
			FileInfoMessage fileInfo2=(FileInfoMessage)ob;
			System.out.println(fileInfo2.getFileId());
			*/
		}
		
}
