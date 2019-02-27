package com.hanweb.jmp.util;

import java.io.*;
import java.util.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.util.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import sun.misc.BASE64Decoder;
import javax.servlet.http.HttpServletRequest;
/**
 * UEditor文件上传辅助类
 *
 */
public class Uploader {
	
	/**
	 * url
	 */
	private String url = "";

    /**
     * fileName
     */
	private String fileName = "";

    /**
     * state
     */
	private String state = "";
	
	/**
	 * type
	 */
	private String type = "";
	
	/**
	 * originalName
	 */
	private String originalName = "";
	
	/**
	 * size
	 */
	private String size = "";

	/**
	 * request
	 */
	private HttpServletRequest request = null;

	/**
	 * param
	 */
	private HashMap<String, String> param = new HashMap<String, String>();
	
	/**
	 * isRename
	 */
	private boolean isRename = true;

	/**
	 * savePath
	 */
	private String savePath = "tmp";
	
	/**
	 * allowFiles
	 */
	private String[] allowFiles = { ".rar", ".doc", 
			".docx", ".zip", ".pdf", ".txt", ".swf", ".wmv", 
			".gif", ".png", ".jpg", ".jpeg", ".bmp"};

    /**
     * maxSize
     */
	private int maxSize = 10000;
	
	/**
	 * errorInfo
	 */
	private HashMap<String, String> errorInfo = new HashMap<String, String>();

	public Uploader(HttpServletRequest request) {
		this.request = request;
		HashMap<String, String> tmp = this.errorInfo;
		tmp.put("SUCCESS", "SUCCESS"); //默认成功
		tmp.put("NOFILE", "未包含文件上传域");
		tmp.put("TYPE", "不允许的文件格式");
		tmp.put("SIZE", "文件大小超出限制");
		tmp.put("ENTYPE", "请求类型ENTYPE错误");
		tmp.put("REQUEST", "上传请求异常");
		tmp.put("IO", "IO异常");
		tmp.put("DIR", "目录创建失败");
		tmp.put("UNKNOWN", "未知错误");
	
	}

	/**
	 * upload:(这里用一句话描述这个方法的作用).
	 *
	 * @throws Exception    设定参数 .
	*/
	public void upload() throws Exception {
		boolean isMultipart = ServletFileUpload.isMultipartContent(this.request);
		if (!isMultipart) {
			initParam();
			this.state = this.errorInfo.get("NOFILE");
			return;
		}
		DiskFileItemFactory dff = new DiskFileItemFactory();
		String savePath = this.getFolder(this.savePath);
		dff.setRepository(new File(savePath));
		try {
			ServletFileUpload sfu = new ServletFileUpload(dff);
			sfu.setSizeMax(this.maxSize * 1024);
			sfu.setHeaderEncoding("UTF-8");
			FileItemIterator fii = sfu.getItemIterator(this.request);
			boolean hasFile = false;
			while (fii.hasNext()) {
				FileItemStream fis = fii.next();
				if (!fis.isFormField() && !hasFile) {
					this.originalName = fis.getName().substring(
							fis.getName().lastIndexOf(System.getProperty("file.separator")) + 1);
					if (!this.checkFileType(this.originalName)) {
						this.state = this.errorInfo.get("TYPE");
						continue;
					}
					if(this.isRename){
						this.fileName = this.getName(this.originalName);
					}else{
						this.fileName = this.originalName;
					}
					this.type = this.getFileExt(this.fileName);
					this.url = savePath + "/" + this.fileName;
					BufferedInputStream in = new BufferedInputStream(fis.openStream());
					FileOutputStream out = new FileOutputStream(
							new File(this.getPhysicalPath(this.url)));
					BufferedOutputStream output = new BufferedOutputStream(out);
					Streams.copy(in, output, true);
					this.state=this.errorInfo.get("SUCCESS");
					hasFile = true;
				} else {
					String fname = fis.getFieldName();
                    BufferedInputStream in = new BufferedInputStream(fis.openStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer result = new StringBuffer();  
                    String line = null;
                    while ((line = reader.readLine()) != null) {                 	
                        result.append(line);
                    }
                    this.param.put(fname, new String(result.toString().getBytes(), "utf-8"));
                    reader.close();  
                    
				}
			}
		} catch (SizeLimitExceededException e) {
			this.state = this.errorInfo.get("SIZE");
		} catch (InvalidContentTypeException e) {
			this.state = this.errorInfo.get("ENTYPE");
		} catch (FileUploadException e) {
			this.state = this.errorInfo.get("REQUEST");
		} catch (Exception e) {
			e.printStackTrace();
			this.state = this.errorInfo.get("UNKNOWN");
		}
	}
	/**
	 * initParam:(这里用一句话描述这个方法的作用).
	 *    设定参数 .
	*/
	@SuppressWarnings("unchecked")
	private void initParam(){
		try{
			Map<String, String[]> map = this.request.getParameterMap();
			if(map != null && map.size() > 0){
				for(Map.Entry<String, String[]> entry : map.entrySet()){
					this.param.put(entry.getKey(), map.get(entry.getKey())[0]);
				}
				
			}
		}catch (Exception e) {
			this.state = this.errorInfo.get("REQUEST");
		}
		
	}
	/**
	 * 接受并保存以base64格式上传的文件
	 * @param fieldName fieldName
	 */
	public void uploadBase64(String fieldName){
		String savePath = this.getFolder(this.savePath);
		String base64Data = this.request.getParameter(fieldName);
		this.fileName = this.getName("test.png");
		this.url = savePath + "/" + this.fileName;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			File outFile = new File(this.getPhysicalPath(this.url));
			OutputStream ro = new FileOutputStream(outFile);
			byte[] b = decoder.decodeBuffer(base64Data);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			ro.write(b);
			ro.flush();
			ro.close();
			this.state=this.errorInfo.get("SUCCESS");
		} catch (IOException e) {
			this.state = this.errorInfo.get("IO");
		}
	}

	/**
	 * 文件类型判断
	 *  
	 * @param fileName fileName
	 * @return boolean
	 */
	private boolean checkFileType(String fileName) {
		Iterator<String> type = Arrays.asList(this.allowFiles).iterator();
		while (type.hasNext()) {
			String ext = type.next();
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}
 
	/**
	 * getFileExt:获取文件扩展名.
	 *
	 * @param fileName fileName
	 * @return    设定参数 .
	*/
	private String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
 
	/**
	 * getName:依据原始文件名生成新文件名.
	 *
	 * @param fileName fileName
	 * @return    设定参数 .
	*/
	private String getName(String fileName) {
		Random random = new Random();
		return this.fileName = "" + random.nextInt(10000)
				+ System.currentTimeMillis() + this.getFileExt(fileName);
	}

	/**
	 * 根据字符串创建本地目录 并按照日期建立子目录返回
	 * @param path  path
	 * @return  String
	 */
	private String getFolder(String path) {
		File dir = new File(this.getPhysicalPath(path));
		if (!dir.exists()) {
			try {
				dir.mkdirs();
			} catch (Exception e) {
				this.state = this.errorInfo.get("DIR");
				return "";
			}
		}
		return path;
	}

	/**
	 * 根据传入的虚拟路径获取物理路径
	 * 
	 * @param path path
	 * @return String
	 */
	private String getPhysicalPath(String path) {
		String realPath = this.request.getSession().getServletContext()
				.getRealPath("");
		return realPath +"/" +path;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public void setAllowFiles(String[] allowFiles) {
		this.allowFiles = allowFiles;
	}

	public void setMaxSize(int size) {
		this.maxSize = size;
	}

	public String getSize() {
		return this.size;
	}

	public String getUrl() {
		return this.url;
	}

	public String getFileName() {
		return this.fileName;
	}

	public String getState() {
		return this.state;
	}
	
	public HashMap<String, String> getParam() {
		return param;
	}

	public String getType() {
		return this.type;
	}

	public String getOriginalName() {
		return this.originalName;
	}

	public void setRename(boolean isRename) {
		this.isRename = isRename;
	}
}
