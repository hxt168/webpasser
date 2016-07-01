package com.hxt.webpasser.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

public class FileUtil {

	 /**
     * 读取文件的内容
     * @param filePath
     */
	  public static String readTxtFile(String filePath){
		  
	        return readTxtFile(filePath,"utf-8");
	    }
	  
	    
	  /**
	     * 读取文件的内容
	     * @param filePath
	     */
		  public static String readTxtFile(String filePath,String  encoding){
			  
			  StringBuffer buf=new StringBuffer();
		        try {
		                File file=new File(filePath);
		                if(file.isFile() && file.exists()){ //判断文件是否存在
		                    InputStreamReader read = new InputStreamReader(
		                    new FileInputStream(file),encoding);//考虑到编码格式
		                    BufferedReader bufferedReader = new BufferedReader(read);
		                    String lineTxt = null;
		                    while((lineTxt = bufferedReader.readLine()) != null){
		                    	buf.append(lineTxt+"\n");
		                    }
		                    read.close();
		        }else{
		            System.out.println("找不到指定的文件");
		        }
		        } catch (Exception e) {
		            System.out.println("读取文件内容出错");
		            e.printStackTrace();
		        }
		     
		        return buf.toString();
		    }

	    
	  /**
	   * 
	   * @param absoluteFilename 文件绝对路径
	   * @return
	   */
	    public static Properties readPropertiesFile(String absoluteFilename)  
	    {  
	        Properties properties = new Properties();  
	        InputStream inputStream=null;
	        try  
	        {  
	        	inputStream = new FileInputStream(absoluteFilename);  
	            properties.load(inputStream);  
	          
	        }  
	        catch (IOException e)  
	        {  
	          //  e.printStackTrace();
	            System.out.println(e.getMessage()+" 读取PropertiesFile失败");
	            return null;
	        }finally{
	        	if(inputStream!=null)
	        	{
	        		  try {
	  					inputStream.close();
	  				} catch (IOException e) {
	  					e.printStackTrace();
	  				} 
	        	}//关闭流  
	        	
	        }
	        return properties;
	    } 
	    
	    //写资源文件  
	    public static void writePropertiesFile(String absoluteFilename,Properties properties )  
	    {  
	    	OutputStream outputStream = null;
	        try  
	        {  
	        	outputStream=new FileOutputStream(absoluteFilename);  
	            properties.store(outputStream, "");  
	          
	        }  
	        catch (IOException e)  
	        {  
	            e.printStackTrace();  
	        }finally{
	        	  try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
	        }
	    }
	    
	    /*public static void checkFile(String fileName)
	    {
	    	File file=new File(fileName);
	    	if(!file.exists())
	    	{
	    		
	    	}
	    }*/
	    
	    public static boolean Move(String srcFile, String destPath) 
	    {
	           // File (or directory) to be moved
	           File file = new File(srcFile);
	           System.out.println("Move方法 "+srcFile);
	           // Destination directory
	           File dest = new File(destPath);
	
			   File filedir=dest.getParentFile();
			   if(!filedir.exists()) 
			    {
				   filedir.mkdirs();	
			    }
			   
			   if (dest.exists()) {
				   
				   dest.delete();	   
			   }
			   
			  // System.out.println(srcFile+" to "+destPath);
	          // boolean success = file.renameTo(new File(dir, file.getName()));
	           boolean success = file.renameTo(dest);
		          
	           return success;
	       }
	    
	    
	    /**
		 * 
		 * @param destFileName  创建文件  
		 * @return
		 */
		public static boolean CreateFile(String absoluteFilename) {
		    File file = new File(absoluteFilename);
		  
		   File filedir=file.getParentFile();
		   if(!filedir.exists()) 
		    {
			   filedir.mkdirs();	
		    }
		    	
		   if (file.exists()) {
			   
			   file.delete();	   
		   }		    
		    try {
		     if (file.createNewFile()) {
		      return true;
		     } else {
		      return false;
		     }
		    } catch (IOException e) {
		     e.printStackTrace();
		     return false;
		    }
		}
		
	/**
	 * 
	 * @param absoluteFilename 文件绝对地址
	 * @param con  文件内容
	 * @throws Exception 
	 */
    public static void createFile(String absoluteFilename,String con) throws Exception
    {
    	
       File file = new File(absoluteFilename);
		  
	   File filedir=file.getParentFile();
	   if(!filedir.exists()) 
	    {
		   filedir.mkdirs();	
	    }
    	
    	BufferedWriter writer=null;
    	try{
    		writer= new BufferedWriter(new FileWriter(file));

    	     writer.write(con);
    	     writer.close();

    	}catch(Exception e){
    		e.printStackTrace();
    		throw e;
    	   }finally{
    		   
    		   try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		   
    	   }
    	
    	
    }
    
    
    public static void createFile(String absoluteFilename, String content, String encoding) throws Exception  
            {  
        File file = new File(absoluteFilename);  
		  
        File filedir=file.getParentFile();
 	   	if(!filedir.exists()) 
 	    {
 		   filedir.mkdirs();	
 	    }
        try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}  
        
        BufferedWriter writer=null;
        OutputStreamWriter out=null;
		try {
			out=new OutputStreamWriter(new FileOutputStream(file),encoding);
			
			writer = new BufferedWriter(out);
			
			writer.write(content);
	
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally{
			
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
        
        
    }  
		
    
    
	    /** 
	     * 移动文件到指定目录 
	     * @param oldPath String 如：c:/fqf.txt 
	     * @param newPath String 如：d:/fqf.txt 
	     * @throws Exception 
	     */ 
	   public static void moveFile(String oldPath, String newPath) throws Exception { 
	       copyFile(oldPath, newPath); 
	       delFile(oldPath);
	
	   }
   
	   /** 
	    * 复制单个文件 
	    * @param oldPath String 原文件路径 如：c:/fqf.txt 
	    * @param newPath String 复制后路径 如：f:/fqf.txt 
	    * @return boolean 
	 * @throws Exception 
	    */ 
	  public static void copyFile(String oldPath, String newPath) throws Exception { 
		  InputStream inStream=null;
		  FileOutputStream fs=null;
	      try { 
	          int bytesum = 0; 
	          int byteread = 0; 
	          File oldfile = new File(oldPath); 
	         
	          
	          if (oldfile.exists()) { //文件存在时 
	              inStream = new FileInputStream(oldPath); //读入原文件 
	              
	              File newFile = new File(newPath);  
	   		   File filedir=newFile.getParentFile();
	   		   if(!filedir.exists()) 
	   		    {
	   			   filedir.mkdirs();	
	   		    }
	   			
	   		   newFile.createNewFile();
	              fs = new FileOutputStream(newFile); 
	              byte[] buffer = new byte[1444]; 
	             // int length; 
	              while ( (byteread = inStream.read(buffer)) != -1) { 
	                  bytesum += byteread; //字节数 文件大小 
	                  fs.write(buffer, 0, byteread); 
	              } 
	            
	          } 
	      } 
	      catch (Exception e) { 
	          System.out.println("复制单个文件操作出错"); 
	          e.printStackTrace();
	          throw e;
	      }finally{
	    	  try {
				inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	  fs.close();
	    	  
	      }
	
	  }
    
  
	  /** 
	   * 删除文件 
	   * @param filePathAndName String 文件路径及名称 如c:/fqf.txt 
	   * @param fileContent String 
	   * @return boolean 
	   */ 
	 public static void delFile(String filePathAndName) { 
	     try { 
	    
	         java.io.File myDelFile = new java.io.File(filePathAndName); 
	         if(myDelFile.exists())
	        	 myDelFile.delete();
	
	     } 
	     catch (Exception e) { 
	         System.out.println("删除文件操作出错"); 
	         e.printStackTrace();
	
	     }
	
	 }
 
	 public static void deleteDir(String dirPath) { 
		 File dir=new File(dirPath);
		    if (!dir.exists())  
		        return;  
		    if (dir.isFile()) {  
		    	dir.delete();  
		        return;  
		    }  
		    File[] files = dir.listFiles();  
		    for (int i = 0; i < files.length; i++) {  
		    	deleteDir(files[i].getAbsolutePath());  
		    }  
		    dir.delete();  
	}  
	 

	 /**
	  * 判断文件是否存在
	  * @param filePath
	  * @return
	  */
	 public static boolean checkIsExist(String filePath)
	 {
		 File file=new File(filePath);
		 
		 if(file.exists())
		 {
			 return true;
		 }else{
			 
			 return false;
		 }
		 
		 
	 }
	 
	 
	/**
	 * 生成目录 
	 * @param dirPath
	 */
  public static void createDir(String dirPath)
  {
	  File filedir = new File(dirPath);
		
	   //File filedir=dest.getParentFile();
	   if(!filedir.exists()) 
	    {
		   filedir.mkdirs();
		   System.out.println(dirPath+" 创建");
	    }
	  
  }
	    public static void main(String[] args) {
			
	    	/*try {
				Move("E:\\upload\\7\\9\\13824@err_2014-06-20 16-15-351.xls","E:\\hasSend\\7\\9\\13824@err_2014-06-20 16-15-351.xls");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	    	String p="E:\\upload\\Other Linux 2.6.x kernel.vmdk";
	    	System.out.println(p.hashCode()+"");
	    	
	    	System.out.println("E:\\upload\\Other Linux 2.6.x kernel.vmdk".hashCode());
	    	//File file=new File("E:\\upload\\Other Linux 2.6.x kernel.vmdk");
	    	//System.out.println(file.getAbsolutePath()+" "+file.length());
	    	
	    	
		}
	
}
