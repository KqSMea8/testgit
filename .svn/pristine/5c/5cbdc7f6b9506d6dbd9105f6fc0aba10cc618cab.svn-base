package com.hanweb.jmp.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 
  
import com.hanweb.common.BaseInfo; 
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.ImageHandle;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;  
import com.hanweb.jmp.constant.Configs; 
import com.hanweb.jmp.constant.InfoConfig;
import com.hanweb.jmp.global.service.ConfigService;

public class ImageUtil {
	
    /**
     * imgWidth:图片宽度.
     */
	private int imgWidth = 0;
	
	/**
     * imgHeight:图片高度.
     */
	private int imgHeight = 0;
	
	/**
	 * logger
	 */
	private static final Log LOGGER = LogFactory.getLog(ImageUtil.class);
	
    /**
     * 
     * 基本功能：替换指定的标签
     * <p>
     * @param strcontent  原字符串
     * @param beforeTag
     *            要替换的标签
     * @param tagAttrib
     *            要替换的标签属性值
     * @param startTag
     *            新标签开始标记
     * @param endTag
     *            新标签结束标记
     * @param ctype
     *            类型
     * @param url
     *            链接地址
     * @return String
     * @如：替换img标签的src属性值为[img]属性值[/img]
     */
    public String replaceHtmlTag(String strcontent, String beforeTag, String tagAttrib, String startTag, 
    		                     String endTag, String ctype, String url) {
        String strreturn = "";
        if (strcontent == null || "".equals(strcontent.trim()) || beforeTag == null
                || tagAttrib == null || startTag == null || endTag == null) {
            return strcontent;
        }
        try {
        	StrUtil strUtil=new StrUtil();
            StringBuffer sb = new StringBuffer();
            String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";
            String regxpForTagAttrib = tagAttrib
                    + "\\s*=[\"|']?([^\"'\\s]*)[\"|'|\\s]";
            Pattern patternForTag = Pattern.compile(regxpForTag, Pattern.DOTALL
                    | Pattern.CASE_INSENSITIVE);
            Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib,
                    Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
            Matcher matcherForTag = patternForTag.matcher(strcontent);
            boolean result = matcherForTag.find();
            while (result) {
                StringBuffer sbreplace = new StringBuffer();
                Matcher matcherForAttrib = patternForAttrib
                        .matcher(matcherForTag.group(1));
                if (matcherForAttrib.find()) {
                    String picPath = matcherForAttrib.group(1);
                    //picPath = getPicPath(c_type, picPath);
                    
                    //补齐相对路径
                    if(picPath!=null && !picPath.startsWith("http")){
                        if(picPath!=null && picPath.startsWith("/")){
                            picPath = "http://"+strUtil.getMainURL(url) + picPath;
                        }else{
                            picPath = strUtil.getCurURL(url) + picPath;
                        }
                    }
                    
                    matcherForAttrib.appendReplacement(sbreplace, startTag
                            + picPath + endTag);
                    matcherForTag.appendReplacement(sb, sbreplace.substring(
                            sbreplace.toString().indexOf(startTag),
                            sbreplace.toString().length()).toString());
                }
                result = matcherForTag.find();
            }
            matcherForTag.appendTail(sb);
            strreturn = sb.toString();
            sb.delete(0, sb.length());
            return strreturn;
        } catch (Exception e) {
        	LOGGER.error("replaceHtmlTag()替换指定标签  Error:", e);    
            return "";
        }
    }
    
    /**
     * readUsingImageReader:图片截取
     * @param oldpach 源路径
     * @param newpach 目标路径
     * @param w 宽
     * @param h 高
     * @return  是否截取成功.
     */
    public boolean readUsingImageReader(String oldpach, String newpach, int w, int h) {
        try {
            // 取得图片读入器
            Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpg");
            ImageReader reader = (ImageReader) readers.next();
            // 取得图片读入流
            InputStream source;
            source = new FileInputStream(oldpach);
            ImageInputStream iis = ImageIO.createImageInputStream(source);
            reader.setInput(iis, true);
            // 图片参数
            ImageReadParam param = reader.getDefaultReadParam();
            // 100，200是左上起始位置，300就是取宽度为300的，就是从100开始取300宽，就是横向100~400，同理纵向200~350的区域就取高度150
            // Rectangle rect = new Rectangle(100, 200, 300, 150);//
            Rectangle rect = new Rectangle(0, 0, w, h); // x , y , w , h
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            return ImageIO.write(bi, "jpg", new File(newpach));
        }catch (Exception e) {
        	LOGGER.error("readUsingImageReader()图片截取出错  Error:", e);     
            return false;
        }
    }  
    
    /**
     * buildMiniature:图片处理
     * @param strFilePath 带路径的图片名
     * @return    设定参数 .
     */
    public String buildMiniature(String strFilePath) { 
        strFilePath = StringUtil.getString(strFilePath);
        if (strFilePath.trim().length() == 0){
            return "0"; //表示图片没有
        }
        File file = new File(strFilePath);
        if (!file.exists()){
            return "0"; //表示图片没有
        }
        /* 得到源图片的宽度,高度 */
        BufferedImage imgBuf = null;
        int nSrcWedth = 0; // 源图片的宽度
        int nSrcHeight = 0; // 源图片的宽度
        try {
            imgBuf = ImageIO.read(file);
            nSrcWedth = imgBuf.getWidth();
            nSrcHeight= imgBuf.getHeight();
        } catch (Exception e) {
        	LOGGER.error("得到图片的宽度有误:", e);    
        } finally {
            if (imgBuf != null){
                imgBuf.flush();
            }
        }

        if (imgWidth > nSrcWedth) {
        	LOGGER.warn("要产生成的缩略图的宽度>原图宽度");     
            return "1";
        }
        
        float f = ((float) imgWidth) / ((float) nSrcWedth);
        int iWideth = (int) (nSrcWedth * f);
        int iHeight = (int) (nSrcHeight * f);  
        try {
        	ImageHandle imageUtil=ImageHandle.getInstance(file);  
        	// 缩略图
            if(iWideth>0 && iHeight>0){
            	//设定图片缩放比例
            	imageUtil.setScale(f);
            	imageUtil.save(); 
            }
            
        } catch (Exception ex) {
        	ex.printStackTrace();
        	LOGGER.error("生成缩略图出错:", ex);      
        }

        return "0";
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }
    /**
	 * 判断网络图片的宽度
	 * @param imgurl imgurl
	 * @return boolean
	 */
	public static boolean isWidthForNetwork(String imgurl){
		boolean flag = false;
        //得到文件
        java.io.File file = new java.io.File(imgurl);
        BufferedImage bi = null;
        boolean imgwrong=false;
        if(file.exists()) {
            try {
                 //读取图片
                 bi = javax.imageio.ImageIO.read(file);
                 imgwrong=true;
            } catch (IOException ex) {
                imgwrong=false;
                ex.printStackTrace();   
                ex.printStackTrace();
            }
        } else {
        	LOGGER.debug("网络图片不存在:"+imgurl);  
        }
        
        if(imgwrong && bi!=null){
           float picwidth = bi.getWidth();
           float picheight = bi.getHeight();
           if(picwidth < 100 || picheight < 100 
        		   || picwidth/picheight <= 0.4 || picwidth/picheight >= 2.5){
            	flag = false;
           }else{
            	flag = true;
           }
        }
        return flag;
	}
	
	/**
	 * isInfoWidthForNetwork:(这里用一句话描述这个方法的作用).
	 *
	 * @param imgurl imgurl
	 * @return    设定参数 .
	*/
	public static boolean isInfoWidthForNetwork(String imgurl){
        //得到文件
        java.io.File file = new java.io.File(imgurl);  
        boolean imgwrong=false;
        if(file.exists()) {
            try {
                //读取图片
                 javax.imageio.ImageIO.read(file);
                 imgwrong=true;
            } catch (IOException ex) {
                imgwrong=false; 
                ex.printStackTrace();
            }
        } else {
        	System.out.println("网络图片不存在:"+imgurl); 
        }
        return imgwrong;
	}
	
	/**
     * readUsingImageReader:图片截取.
     * @param oldpach 源路径
     * @param newpach 目标路径
     * @return  是否截取成功.
    */
    public boolean readUsingImageReaderChange(String oldpach, String newpach) {
        try {
        	String format = oldpach.substring(oldpach.lastIndexOf(".")+1);
            // 取得图片读入器
            Iterator<?> readers = ImageIO.getImageReadersByFormatName(format);
            ImageReader reader = (ImageReader) readers.next();
            // 取得图片读入流
            InputStream source;
            source = new FileInputStream(oldpach);
            ImageInputStream iis = ImageIO.createImageInputStream(source);
            reader.setInput(iis, true);
            // 图片参数
            ImageReadParam param = reader.getDefaultReadParam();
            // 100，200是左上起始位置，300就是取宽度为300的，就是从100开始取300宽，就是横向100~400，同理纵向200~350的区域就取高度150
            // Rectangle rect = new Rectangle(100, 200, 300, 150);//
            Rectangle rect = null;
            /* 得到源图片的宽度,高度 */
            BufferedImage imgBuf = null;
            int nSrcWedth = 0; // 源图片的宽度
            int nSrcHeight = 0; // 源图片的宽度
            try {
                imgBuf = ImageIO.read(new File(oldpach));
                nSrcWedth = imgBuf.getWidth();
                nSrcHeight= imgBuf.getHeight();
            } catch (Exception e) {
            	e.printStackTrace(); 
            } finally {
                if (imgBuf != null){
                    imgBuf.flush();
                }
            }
            if(nSrcHeight > nSrcWedth){
            	rect = new Rectangle(0, 0, nSrcWedth, nSrcWedth); // x , y , w , h
            }
            if(nSrcWedth >= nSrcHeight){
            	// x , y , w , h
            	rect = new Rectangle(((int) (nSrcWedth - nSrcHeight) / 2),
            			0, nSrcHeight, nSrcHeight); 
            }
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            return ImageIO.write(bi, "jpg", new File(newpach));
        }catch (Exception e) {
        	e.printStackTrace(); 
            return false;
        }
    }
	 
	/**
	 * cutImage:图片截取.
	 *
	 * @param oldpach oldpach
	 * @param newpach newpach
	 * @param w w
	 * @param h h
	 * @return    设定参数 .
	*/
	public boolean cutImage(String oldpach, String newpach, int w, int h) {
		try {
			// 取得图片读入器
			Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpg");
			ImageReader reader = (ImageReader) readers.next();
			
			/* 得到源图片的宽度,高度 */
			BufferedImage imgBuf = null;
			// 源图片的宽度
			int nSrcWedth = 0;
			
			// 源图片的高度
			int nSrcHeight = 0;
			
			//截图x坐标
			int zeroX = 0;
			try {
				File file = new File(oldpach);
				if(file.exists()){
					imgBuf = ImageIO.read(file);
					nSrcWedth = imgBuf.getWidth();
					nSrcHeight = imgBuf.getHeight();
					if(nSrcWedth >= nSrcHeight){
						zeroX = (nSrcWedth-w)/2;
					}
				}
			} catch (Exception e){
				//LogWriter.debug("得到图片的宽度有误!", Miniature.class);
				e.printStackTrace();
			} finally {
				if (imgBuf != null){
					imgBuf.flush();
				}
			}
			
			// 取得图片读入流
			InputStream source;
			source = new FileInputStream(oldpach);
			ImageInputStream iis = ImageIO.createImageInputStream(source);
			reader.setInput(iis, true);
			
			// 图片参数
			ImageReadParam param = reader.getDefaultReadParam();
			
			// 100，200是左上起始位置，300就是取宽度为300的，就是从100开始取300宽，就是横向100~400，同理纵向200~350的区域就取高度150
			// Rectangle rect = new Rectangle(100, 200, 300, 150);//
			Rectangle rect = new Rectangle(zeroX, 0, w, h); // x , y , w , h
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			return ImageIO.write(bi, "jpg", new File(newpach));
		}catch(IOException e){
			return false;
		}catch (Exception e) {
			//LogWriter.error("生成iphone专用小图片出错");
			return false;
		}
 }
	/**
	 * 将图片处理为边长为length的正方形，
	 * @param filepath	   图片相对地址，类似(/web/site1/.....)
	 * @param length	 length	
	 * @param clientType   客户端类型，用于命名新图片
	 * @return  新图片网络地址，类似（http://127.0.0.1/jmportal/.....）
	 */
	public String cutImg(String filepath, int length, int clientType){
		/* 得到源图片的宽度,高度 */
		BufferedImage imgBuf = null;
		String syspath = BaseInfo.getRealPath();   //系统发布路径
		filepath = syspath + filepath;
		String sysUrl = Configs.getConfigs().getJmpUrl().replace("\\", "/");
		if(sysUrl.endsWith("/")){
			sysUrl=sysUrl.substring(0, sysUrl.length()-1);
		} 
		try {
			File file = new File(filepath);
			if(file.exists()){
				imgBuf = ImageIO.read(file);
				int nSrcWedth = 0;   // 源图片的宽度
				int nSrcHeight = 0; // 源图片的高度
				if(imgBuf != null){
					 nSrcWedth = imgBuf.getWidth();   // 源图片的宽度
					 nSrcHeight = imgBuf.getHeight(); // 源图片的高度
					imgBuf.flush();
				}
				String newpath = "";
				switch (clientType) {
				case 2:
					newpath = filepath.replace("source", "iphone_"+length);
					break;
				case 3:
					newpath = filepath.replace("source", "android_"+length);
					break;
				case 4:
					newpath = filepath.replace("source", "ipad_"+length);
					break;
				}
				File newfile = new File(newpath);
				if(newfile.exists()){
					return newpath.replace(syspath, sysUrl);
				}else{
					if(nSrcHeight>length&&nSrcWedth>length){    //高宽均大于指定边长，先切割，再缩放
						if(nSrcHeight>=nSrcWedth){				//假如高大于等于宽，从图片顶部开始切成一个边长为宽度的正方形
							Thumbnails.of(filepath)  
							.sourceRegion(Positions.TOP_LEFT, nSrcWedth, nSrcWedth)  
							.size(nSrcWedth, nSrcWedth)  
							.keepAspectRatio(false)   
							.toFile(newpath);  
						}else{									//假如宽大于等于高，取图片中心部分一个边长为高的正方形
							Thumbnails.of(filepath)  
							.sourceRegion(Positions.CENTER, nSrcHeight, nSrcHeight)  
							.size(nSrcHeight, nSrcHeight)  
							.keepAspectRatio(false)   
							.toFile(newpath);
						}
						Thumbnails.of(newpath)   
						.size(length, length)  
						.toFile(newpath);
					}else if(nSrcHeight>length||nSrcWedth>length){
						Thumbnails.of(filepath)   
						.size(length, length)  
						.toFile(newpath); 
					}else{
						FileUtil.copyFile(new File(filepath), newfile);
					}
				}
				return newpath.replace(syspath, sysUrl);
			}
			return "";
		} catch (Exception e) {
			//LogWriter.debug("得到图片的宽度有误!", Miniature.class);
			return "";
		}
	}
	 
	/**
	 * 将图片处理为边长为length的正方形，
	 * @param filepath	   图片相对地址，类似(/web/site1/.....)
	 * @param length		
	 * @param clientType   客户端类型，用于命名新图片
	 * @return  新图片网络地址，类似（http://127.0.0.1/jmportal/.....）
	 */
	public String cutImg1(String filepath, int length, int clientType){
		/* 得到源图片的宽度,高度 */
		BufferedImage imgBuf = null;
		String syspath = BaseInfo.getRealPath();   //系统发布路径
		filepath = syspath + filepath;
		String sysUrl = Configs.getConfigs().getJmpUrl().replace("\\", "/");
		if(sysUrl.endsWith("/")){
			sysUrl=sysUrl.substring(0, sysUrl.length()-1);
		}
		try {
			File file = new File(filepath);
			if(file.exists()){
				imgBuf = ImageIO.read(file);
				int nSrcWedth = 0;   // 源图片的宽度
				int nSrcHeight = 0; // 源图片的高度
				if(imgBuf != null){
					 nSrcWedth = imgBuf.getWidth();   // 源图片的宽度
					 nSrcHeight = imgBuf.getHeight(); // 源图片的高度
					imgBuf.flush();
				}
				String newpath = "";
				switch (clientType) {
				case 2:
					newpath = filepath.replace("first_", "iphone"+length+"_");
					break;
				case 3:
					newpath = filepath.replace("first_", "android"+length+"_");
					break;
				case 4:
					newpath = filepath.replace("first_", "ipad"+length+"_");
					break;
				}
				File newfile = new File(newpath);
				if(newfile.exists()){
					return newpath.replace(syspath, sysUrl);
				}else{
					if(nSrcHeight>length&&nSrcWedth>length){    //高宽均大于指定边长，先切割，再缩放
						if(nSrcHeight>=nSrcWedth){				//假如高大于等于宽，从图片顶部开始切成一个边长为宽度的正方形
							Thumbnails.of(filepath)  
							.sourceRegion(Positions.TOP_LEFT, nSrcWedth, nSrcWedth)  
							.size(nSrcWedth, nSrcWedth)  
							.keepAspectRatio(false)   
							.toFile(newpath);  
						}else{									//假如宽大于等于高，取图片中心部分一个边长为高的正方形
							Thumbnails.of(filepath)  
							.sourceRegion(Positions.CENTER, nSrcHeight, nSrcHeight)  
							.size(nSrcHeight, nSrcHeight)  
							.keepAspectRatio(false)   
							.toFile(newpath);
						}
						Thumbnails.of(newpath)   
						.size(length, length)
						.toFile(newpath);
					}else if(nSrcHeight>length||nSrcWedth>length){
						Thumbnails.of(filepath)   
						.size(length, length)
						.toFile(newpath); 
					}else{
						FileUtil.copyFile(new File(filepath), newfile);
					}
				}
				return newpath.replace(syspath, sysUrl);
			}
			return "";
		} catch (Exception e) {
			//LogWriter.debug("得到图片的宽度有误!", Miniature.class);
			return "";
		}
	}
	
	/**
	 * 图片处理
	 * @param oldpach  原图地址
	 * @param newpach 目标图片地址
	 * @param w 宽度
	 * @param h 高度
	 * @return boolean
	 */
	public static boolean cutColPic(String oldpach, String newpach, int w, int h) {
		try {
			Thumbnails.of(oldpach).size(w, h).
			toFile(newpach);
		}catch (Exception e) {  
			return false;
		}
		return true;
     }
	
	/**
	 * 等比缩放
	 * 
	 * @param sFilePath sFilePath
	 * @param toFilepath toFilepath
	 * @param width width 
	 * @return boolean
	 */
	public static boolean scaleMiniature(String sFilePath, String toFilepath,
			int width){
		try {
			Thumbnails.of(sFilePath).size(width, 99999).outputQuality(0.8f).toFile(toFilepath);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 等比缩放
	 * 
	 * @param sFilePath sFilePath
	 * @param toFilepath toFilepath
	 * @param width width
	 * @param height height
	 * @return boolean
	 */
	public static boolean scaleMiniature(String sFilePath, String toFilepath,
			int width, int height, int nSrcWedth, int nSrcHeight) {
		if(nSrcWedth==0 || nSrcHeight==0){
			BufferedImage imgBuf = null; 
	        File file1=new File(sFilePath); 
            try {
				imgBuf = ImageIO.read(file1);
			} catch (IOException e) { 
				return false;
			}
			if(imgBuf==null){
				return true;
			}
            nSrcWedth = imgBuf.getWidth();
            nSrcHeight= imgBuf.getHeight(); 
		} 
        if(nSrcWedth<width){
        	FileUtil.copyFile(new File(sFilePath), new File(toFilepath));
        	return true;
        }
		try {
			float disheight=NumberUtil.getFloat(nSrcWedth)/NumberUtil.getFloat(nSrcHeight)*width;
			Thumbnails.of(sFilePath).size(width, 
					NumberUtil.getInt(disheight)).outputQuality(0.9f).toFile(toFilepath);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 按照原图大小缩放
	 * 
	 * @param sFilePath  sFilePath
	 * @param toFilepath   toFilepath
	 * @return boolean
	 */
	public static boolean scaleAutoMiniature(String sFilePath, String toFilepath) {
		if(!sFilePath.endsWith(".jpg") && !sFilePath.endsWith(".JPG")){
			return true;
		}
		BufferedImage imgBuf = null;
        int nSrcWedth = 0; // 源图片的宽度
        int nSrcHeight = 0; // 源图片的宽度 
        File file1=new File(sFilePath);
         
            try {
				imgBuf = ImageIO.read(file1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			if(imgBuf==null){
				return true;
			}
            nSrcWedth = imgBuf.getWidth();
            nSrcHeight= imgBuf.getHeight(); 
		try {
			Thumbnails.of(sFilePath).size(nSrcWedth, nSrcHeight).
				outputQuality(0.8f).toFile(toFilepath);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 对信息首图缩放
	 * 
     * @param siteId  siteId
	 * @param sFilePath  sFilePath
	 * @param toFilepath   toFilepath
	 * @return boolean
	 */
	public static boolean scaleFirstMiniature(int siteId, String sFilePath, String toFilepath) {
		BufferedImage imgBuf = null;
        int nSrcWedth = 0; // 源图片的宽度
        int nSrcHeight = 0; // 源图片的宽度 
        File file1=new File(sFilePath);
        
        //小图宽度 
    	int minWidth = 176;
    	//小图高度 
    	int minHeight = 132;
    	//中图宽度 
    	int middleWidth = 266;
    	//中图高度 
    	int middleHeight = 200;
    	//大图宽度 
    	int bigWidth = 426;
    	//大图高度 
    	int bigHeight = 320;
    	
        if(NumberUtil.getInt(siteId)>0){
        	InfoConfig infoConfig = ConfigService.findInfoConfig(siteId);
        	minWidth=infoConfig.getMinWidth();
        	minHeight=infoConfig.getMinHeight();
        	middleWidth=infoConfig.getMiddleWidth();
        	middleHeight=infoConfig.getMiddleHeight();
        	bigWidth=infoConfig.getBigWidth();
        	bigHeight=infoConfig.getBigHeight();
        }
//        else{
//        	GoodConfig goodConfig = new GoodConfig();
//        	minWidth=goodConfig.getMinWidth();
//        	minHeight=goodConfig.getMinHeight();
//        	middleWidth=goodConfig.getMiddleWidth();
//        	middleHeight=goodConfig.getMiddleHeight();
//        	bigWidth=goodConfig.getBigWidth();
//        	bigHeight=goodConfig.getBigHeight();
//        }
        String sourcePath=toFilepath;
        String bigPath=sFilePath.replace("_source", "_big");
        String middlePath=sFilePath.replace("_source", "_middle");
        String miniPath=sFilePath.replace("_source", "_mini");
        try {
			imgBuf = ImageIO.read(file1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if(imgBuf==null){
			return true;
		}
		nSrcWedth = imgBuf.getWidth();
	    nSrcHeight= imgBuf.getHeight(); 
	        
		//原图压缩
		try { 
			Thumbnails.of(sFilePath).size(nSrcWedth, nSrcHeight).
				outputQuality(0.8f).toFile(toFilepath);   
		} catch (IOException e) {
			return false;
		}
		
		 
        //大图
	    if(nSrcWedth>bigWidth){ 
	    	ImageUtil.scaleMiniature(sFilePath, bigPath
	        		, bigWidth, bigHeight, nSrcWedth, nSrcHeight); 
	    }else{ 
	    	FileUtil.copyFile(sFilePath, bigPath); 
	    } 
	    //中图
	    if(nSrcWedth>middleWidth){ 
	    	ImageUtil.scaleMiniature(sFilePath, middlePath
	        		, middleWidth, middleHeight, nSrcWedth, nSrcHeight); 
	    }else{
	    	FileUtil.copyFile(sFilePath, middlePath); 
	    } 
	    //小图
	    if(nSrcWedth>minWidth){  
	    	ImageUtil.scaleMiniature(sFilePath, miniPath
	        		, minWidth, minHeight, nSrcWedth, nSrcHeight);
	    }else{  
	    	FileUtil.copyFile(sFilePath, miniPath);
	    } 
	    HadoopUtil.fileUpload(new File(sourcePath), sourcePath.replace(BaseInfo.getRealPath(), ""));
	    HadoopUtil.fileUpload(new File(bigPath), bigPath.replace(BaseInfo.getRealPath(), ""));
	    HadoopUtil.fileUpload(new File(middlePath), middlePath.replace(BaseInfo.getRealPath(), ""));
	    HadoopUtil.fileUpload(new File(miniPath), miniPath.replace(BaseInfo.getRealPath(), ""));
		return true; 
	}
	/**
	 * 
	 * zoomBrokeImg:报料图片处理.
	 *
	 * @param filepath 图片路径
	 * @param filenamm 图片名称
	 * @return    设定参数 .
	 */
	public static boolean zoomBrokeImg(String filepath, String filenamm){
		/* 得到源图片的宽度,高度 */
		BufferedImage imgBuf = null;  
		try {
			File file = new File(filepath);
			if(file.exists()){
				imgBuf = ImageIO.read(file);
				int nSrcWedth = 0;   // 源图片的宽度
				int nSrcHeight = 0; // 源图片的高度
				if(imgBuf != null){
					 nSrcWedth = imgBuf.getWidth();   // 源图片的宽度
					 nSrcHeight = imgBuf.getHeight(); // 源图片的高度
					 imgBuf.flush();
				}else{ 
					return true;
				}
				String newpath = filepath.replace(filenamm, filenamm+"_middle");
				
				File newfile = new File(newpath);
				if(newfile.exists()){
					return true;
				}else{
					Thumbnails.of(filepath).size(nSrcWedth, nSrcHeight).outputQuality(0.8f).toFile(filepath);
					if(nSrcHeight<=nSrcWedth){
						FileUtil.copyFile(new File(filepath), new File(newpath));
						
						if(nSrcHeight > 400){
							Thumbnails.of(filepath).size(400, 400).outputQuality(0.8f).toFile(newpath);
						}
					}else
						Thumbnails.of(filepath)  
						.sourceRegion(Positions.TOP_CENTER,nSrcWedth,nSrcWedth)  
						.size(nSrcWedth,nSrcWedth)  
						.keepAspectRatio(false)   
						.toFile(newpath);  
						if(nSrcHeight > 400){
							Thumbnails.of(filepath).size(400, 400).outputQuality(0.8f).toFile(newpath);
						}
					} 
				HadoopUtil.fileUpload(new File(filepath), filepath.
									replace(BaseInfo.getRealPath(), ""));
				HadoopUtil.fileUpload(new File(newpath), newpath.replace(BaseInfo.getRealPath(), ""));
			    }
			return true;
		} catch (Exception e) { 
			//LogWriter.debug("得到图片的宽度有误!", Miniature.class);
			return false;
		}
	}
	
}