/*
 * 系统名称: 
 * 模块名称: webpasser.project
 * 类 名 称: SpiderTaskResultMapFixTrigger.java
 *   
 */
package com.hxt.webpasser.trigger.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hxt.webpasser.module.CrawlUrl;
import com.hxt.webpasser.module.PageResult;
import com.hxt.webpasser.parser.common.JsoupSelector;
import com.hxt.webpasser.trigger.SpiderTaskTrigger;
import com.hxt.webpasser.utils.ChineseUtil;
import com.hxt.webpasser.utils.StringUtil;
import com.hxt.webpasser.utils.TrimUtil;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class SpiderTaskResultMapFixTrigger implements SpiderTaskTrigger{

	private String taskName;  
	
	
	public SpiderTaskResultMapFixTrigger(String taskName){
		this.taskName=taskName;
	
	}
	
	public void beforeDownload(CrawlUrl crawlUrl) {
		// TODO Auto-generated method stub
		
	}

	public void successDownloadAfter(CrawlUrl crawlUrl, PageResult pageResult) {
		// TODO Auto-generated method stub
		
	}

	public void tryOneFailDownload(CrawlUrl crawlUrl) {
		// TODO Auto-generated method stub
		
	}

	public void reTryAllFailDownload(CrawlUrl crawlUrl) {
		// TODO Auto-generated method stub
		
	}

	public void afterParseToMap(Map<String, Object> returnValue) {

	/*	if(returnValue.get("performers")!=null){
			checkNameList(returnValue.get("performers"));
			
		}
		if(returnValue.get("director")!=null){
			checkNameList(returnValue.get("director"));
			
		}*/
		String content=(String) returnValue.get("content");
		int indexXia=content.indexOf("下载地址");
		if(indexXia!=-1){
			content=content.substring(0, indexXia);
			
		}
		int indexp=content.lastIndexOf("<p");
		if(indexp!=-1){
			content=content.substring(0, indexp);
		}
		returnValue.put("content", content);
		
		String tempCon=getStringToEndContainEnd(content, "简介");
		String[] preKeys=new String[]{"导演"};
		//String[] endKeys=new String[]{"编剧","主演","演员"};
		String[] endKeys=new String[]{"<br","<p"};
		addFieldValueFromCont(returnValue,tempCon,"director",preKeys,endKeys);
		preKeys=new String[]{"主演","演员"};
		//endKeys=new String[]{"简介"};
		endKeys=new String[]{"<br","<p"};
		addFieldValueFromCont(returnValue,tempCon,"performers",preKeys,endKeys);
	/*	preKeys=new String[]{"国家"};
		endKeys=new String[]{"<br"};
		addFieldValueFromCont(returnValue,tempCon,"place",preKeys,endKeys);*/
	}
	
	private void addFieldValueFromCont(Map<String, Object> returnValue,String tempCon,String fieldName,String[] preKeys,String[] endKeys){
		//String content=(String) returnValue.get("content");
		//String[] preKeys=new String[]{"导演"};
		//String[] endKeys=new String[]{"主演","演员"};
		String performersStr=findTheFieldValueStringWithEnd(tempCon, preKeys, endKeys);
		performersStr=trimOtherMoreChar(performersStr);
		System.out.println(performersStr);
		List<String> performersList=new ArrayList<String>();
		performersList.add(performersStr);
		checkNameList(performersList);
		returnValue.put(fieldName, performersList);
	}

	public void afterPersistent(Map<String, Object> returnValue) {
		// TODO Auto-generated method stub
		
	}
	
	private String findTheFieldValueString(String con,String[] preKeys,String[] endKeys){
		int preIndex=-1;
		for(String preWord:preKeys){
			preIndex=findTheFirstIndex( con, preWord,true);
			if(preIndex!=-1){
				break;
			}
		}
		if(preIndex!=-1){
			int endIndex=-1;
			con=con.substring(preIndex);
			for(String endWord:endKeys){
				endIndex=findTheFirstIndex( con, endWord,false);
				if(endIndex!=-1){
					break;
				}
			}
			if(endIndex!=-1){
				return con.substring(0, endIndex);
			}
		}
		
		return "";
	}
	
	/**
	 * 
	 * @param con
	 * @param preKeys
	 * @param endKeys  会完全按照 end
	 * @return
	 */
	private String findTheFieldValueStringWithEnd(String con,String[] preKeys,String[] endKeys){
		int preIndex=-1;
		for(String preWord:preKeys){
			preIndex=findTheFirstIndex( con, preWord,true);
			if(preIndex!=-1){
				break;
			}
		}
		if(preIndex!=-1){
			int endIndex=-1;
			con=con.substring(preIndex);
			for(String endWord:endKeys){
				endIndex=con.indexOf(endWord);
				if(endIndex!=-1){
					break;
				}
			}
			if(endIndex!=-1){
				return con.substring(0, endIndex);
			}
		}
		
		return "";
	}
	
	private String getStringToEndContainEnd(String con,String endWord){
		int endIndex=findTheFirstIndex(con, endWord, false);
		if(endIndex!=-1){
			return con.substring(0, endIndex)+endWord;
		}
		return "";
	}
	
	private int findTheFirstIndex(String con,String preWord,Boolean isPre){
		//String preWord="";
		String[] preKeyArr=preWord.split("");
		String preKeyFirst=preKeyArr[1];
		String preKeySecond=preKeyArr[2];
		int preKeyFirstIndex=con.indexOf(preKeyFirst);
		int tempIndex=preKeyFirstIndex;
		while(preKeyFirstIndex!=-1){
			//  例： preWord=主演         主      下一个的位置
			int rpoint=preKeyFirstIndex+preKeyFirst.length();
			String beginChar = con.substring(rpoint, rpoint+1);  
			while(beginChar.equals(" ")||beginChar.equals("　")){
				rpoint++;
				beginChar = con.substring(rpoint, rpoint+1); 
			}
			//rpoint++;
			String preCon=con.substring(rpoint);
			if(preCon.startsWith(preKeySecond)){
				if(isPre){
					return rpoint+preKeySecond.length();
				}else{
					return tempIndex;
				}
				
			}
			//preKeyFirstIndex=preCon.indexOf(preKeyFirst)+rpoint;
			preKeyFirstIndex=preCon.indexOf(preKeyFirst);
			if(preKeyFirstIndex==-1){
				break;
			}
			preKeyFirstIndex=preKeyFirstIndex+rpoint;
			tempIndex=preKeyFirstIndex;
		}
		
		return -1;
	}
	
	
/*	private int findTheEndIndex(String con,String endWord){
		//String preWord="";
		String[] endKeyArr=endWord.split("");
		String endKeyFirst=endKeyArr[0];
		String endKeySecond=endKeyArr[1];
		int preKeyFirstIndex=con.indexOf(preKeyFirst);
		if(preKeyFirstIndex!=-1){
			int rpoint=preKeyFirstIndex+preKeyFirst.length();
			String beginChar = con.substring(rpoint, 1);  
			while(beginChar.equals(" ")||beginChar.equals("　")){
				rpoint++;
				beginChar = con.substring(rpoint, 1); 
			}
			rpoint++;
			String preCon=con.substring(rpoint, rpoint+4);
			if(preCon.startsWith(preKeySecond)){
				
				return rpoint+preKeySecond.length();
			}
			
		}
		
		return -1;
	}
	*/

	private void checkNameList(Object listOb){
		
		if(listOb!=null&&listOb instanceof List){
			List<String> list=(List<String>) listOb;
	/*		boolean hasBr=false;
			if(list.size()==1){
				String nameStr=list.get(0);
				if(nameStr.indexOf("<br")>0){
					nameStr=nameStr.replaceAll("<br[^>]*>", "#br#");
					list.set(0, nameStr);
					hasBr=true;

				}
				
			}
			*/
			for(int i=0;i<list.size();i++){
				String item=list.get(i);
				if(item.indexOf("<")>-1){  //需要去除html标签
					item=trimOtherMoreChar(item);
					item=JsoupSelector.getTextFromHtml(item);
					list.set(i, item);
				}

			}
			
			/*if(hasBr){
				String nameStr=list.get(0);
				putArrayToListBySplit(list,nameStr,"#br#");
			}*/
			
			//   针对 路易斯·德·埃斯波西托 Louis D'Esposito / 安东尼·卢素 Anthony Russo  的情况
			if(list.size()==1){
				String nameStr=list.get(0);
				if(nameStr.indexOf("/")>0){
				/*	String[] nameArr=nameStr.split("/");
					list.clear();
					for(String name:nameArr){
						list.add(name);
					}*/
					nameStr=trimOtherMoreChar(nameStr);
					putArrayToListBySplit(list,nameStr,"/");
				}
				
			}
			
			// 对 路易斯·德·埃斯波西托 Louis D'Esposito   的截取前面的
			for(int i=0;i<list.size();i++){
				String item=list.get(i);
				String tempValue=checkReturnName(item);
				if(tempValue.length()!=item.length()){
					list.set(i, tempValue);
				}
			}
			
			checkListHasNullValue(list);
		}
		
	}
	
	/**
	 * 清除为空的项
	 * @param list
	 */
	private void checkListHasNullValue(List<String> list){
		List<String> tempList=new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			String item=list.get(i);
			if(item.length()>1){
				tempList.add(item);
			}	
		}
		if(tempList.size()!=list.size()){
			list.clear();
			list.addAll(tempList);
		}
		
	}
	
	private String trimOtherMoreChar(String str){
		if(!StringUtil.isEmpty(str)){
			
			str=str.replaceAll("&bull;", "");
			str=TrimUtil.trim(str, '】');
			str=TrimUtil.trim(str, ']');
			str=TrimUtil.trim(str, '：');
			str=TrimUtil.trim(str, ':');
			str=TrimUtil.trimStr(str);
		}
		
		return str;
	}
	
	
	private void putArrayToListBySplit(List<String> list,String con,String spilt){
		String[] nameArr=con.split(spilt);
		list.clear();
		for(String name:nameArr){
			if(name.length()>1){
				name=trimOtherMoreChar(name);
				list.add(name);
			}
		}

	}
	
	private String checkReturnName(String name){
		if(name!=null){
			name=TrimUtil.trimStr(name);
			String[] names=name.split(" ");
			if(names.length>1){
				if(ChineseUtil.isChinese(names[0])){  //如果前半段是中文，截取空格前的
					
					return names[0];
				}else{
					
					
					return names[0]+" "+names[1];
					
				}
				
			}
			
			
		}
		return name;
	}
	
	public static void main(String[] args) {
		String nameStr="mmmm◎导　　演　路易斯·德·埃斯波西托 Louis D'Esposito / 安东尼·卢素 Anthony Russo / 乔·卢素 Joe Russo / 乔·庄斯顿 Joe Johnston</span><br style=\"WIDOWS: 1; TEXT-TRANSFORM: none; BACKGROUND-COLOR: rgb(244,246,246); TEXT-INDENT: 0px; FONT: 12px tahoma, arial, helvetica, sans-serif; WHITE-SPACE: normal; LETTER-SPACING: normal; COLOR: rgb(0,0,0); WORD-SPACING: 0px; -webkit-text-stroke-width: 0px\" /><span style=\"WIDOWS: 1; TEXT-TRANSFORM: none; BACKGROUND-COLOR: rgb(244,246,246); TEXT-INDENT: 0px; DISPLAY: inline !important; FONT: 12px tahoma, arial, helvetica, sans-serif; WHITE-SPACE: normal; FLOAT: none; LETTER-SPACING: normal; COLOR: rgb(0,0,0); WORD-SPACING: 0px; -webkit-text-stroke-width: 0px\">◎主　　演　海莉·阿特维尔 Hayley Atwell</span><br style=\"WIDOWS: 1; TEXT-TRANSFORM: none; BACKGROUND-COLOR: rgb(244,246,246); TEXT";
		SpiderTaskResultMapFixTrigger spiderTaskResultMapFixTrigger=new SpiderTaskResultMapFixTrigger("");
		String[] preKeys=new String[]{"导演"};
		String[] endKeys=new String[]{"主演","演员"};
		String str=spiderTaskResultMapFixTrigger.findTheFieldValueString(nameStr, preKeys, endKeys);
		System.out.println(str);
	}
	
	
}
