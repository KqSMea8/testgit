package com.hanweb.jmp.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 产生缩略图的方法
 * 
 */
public class Miniature {
	/**
	 * logger
	 */
    Log logger = LogFactory.getLog(getClass());

	/**
	 * 宽度
	 */
	private int nWidth = 0;

	/**
	 * 高度
	 */
	private int nHeight = 0;

	/**
	 * 
	 * getnHeight:(这里用一句话描述这个方法的作用).
	 *
	 * @return    设定参数 .
	 */
	public int getnHeight() {
		return nHeight;
	}

	/**
	 * 
	 * setnHeight:(这里用一句话描述这个方法的作用).
	 *
	 * @param nHeight    设定参数 .
	 */
	public void setnHeight(int nHeight) {
		this.nHeight = nHeight;
	}

	public Miniature() {

	}

	public void setNWidth(int width) {
		nWidth = width;
	}

	public void setNHeight(int height) {
		nHeight = height;
	} 
	
    /**
     * reduceImg2:(这里用一句话描述这个方法的作用).
     *
     * @param imgsrc imgsrc
     * @return    设定参数 .
    */
    public boolean reduceImg2(String imgsrc) {
    	File file = new File(imgsrc);
    	/* 得到源图片的宽度,高度 */
		BufferedImage imgBuf = null;
		// 源图片的宽度
		int nSrcWedth = 0;
		// 源图片的高度
		int nSrcHeight = 0;
		try {
			imgBuf = ImageIO.read(file);
			nSrcWedth = imgBuf.getWidth();
			nSrcHeight = imgBuf.getHeight();
			if(nSrcWedth < nSrcHeight){
				if(nWidth>nSrcWedth){
					return false;
				}
			}else{
				if(nWidth>nSrcHeight){
					return false;
				}
			} 
		} catch (Exception e){
			logger.error("得到图片的宽度有误!", e);  
		} finally {
			if (imgBuf != null){
				imgBuf.flush();
			}
		}
		int height = 0;
		int width = 0;
		String strSourcePath = file.getParent();
		String fileName = file.getName();
		String newFileName = "s" + fileName;
		if(nSrcWedth >= nSrcHeight){
			float f = ((float) nWidth) / ((float) nSrcHeight);
			width = (int) (f * nSrcWedth) ; 
			reduceImg(imgsrc, strSourcePath + 
					java.io.File.separatorChar + newFileName, width, nWidth);
		}else{
			float f = ((float) nWidth) / ((float) nSrcWedth);
			height = (int) (f * nSrcHeight) ; 
			reduceImg(imgsrc, strSourcePath + 
					java.io.File.separatorChar + newFileName, nWidth, height);
		}
		return true;
    }
    
    /**
     * 
     * reduceImg:(这里用一句话描述这个方法的作用).
     *
     * @param imgsrc 图片源地址  
     * @param imgdist  图片缩放后存放的地址
     * @param widthdist  要缩放的图片宽度
     * @param heightdist    要缩放的图片高度
     */
    public static void reduceImg(String imgsrc, String imgdist, int widthdist, int heightdist) {
        try {
            File srcfile = new File(imgsrc);
            if (!srcfile.exists()) {
                return;
            }
            Image src = javax.imageio.ImageIO.read(srcfile);

            BufferedImage tag = new BufferedImage((int) widthdist,
            		(int) heightdist, BufferedImage.TYPE_INT_RGB);

            tag.getGraphics().drawImage(src.getScaledInstance(
            		widthdist, heightdist, Image.SCALE_SMOOTH), 0, 0, null);

            FileOutputStream out = new FileOutputStream(imgdist);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(tag);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * reduceImg3:(这里用一句话描述这个方法的作用).
     *
     * @param imgsrc imgsrc
     * @return    设定参数 .
    */
    public String reduceImg3(String imgsrc) {
    	File file = new File(imgsrc);
    	/* 得到源图片的宽度,高度 */
		BufferedImage imgBuf = null;
		
		// 源图片的宽度
		int nSrcWedth = 0;
		
		// 源图片的高度
		int nSrcHeight = 0;
		try {
			imgBuf = ImageIO.read(file);
			nSrcWedth = imgBuf.getWidth();
			nSrcHeight = imgBuf.getHeight();
			if(nSrcWedth < nSrcHeight){
				if(nWidth>nSrcWedth){
					// 源图片的宽度
					return "0";
				}
			}else{
				if(nWidth>nSrcHeight){
					// 源图片的宽度
					return "0";
				}
			} 
		} catch (Exception e){
			logger.error("得到图片的宽度有误!", e); 
		} finally {
			if(imgBuf != null){
				imgBuf.flush();
			}
		}
		int height = 0;
		int width = 0;
		if(nSrcWedth >= nSrcHeight){
			float f = ((float) nWidth) / ((float) nSrcHeight);
			width = (int) (f * nSrcWedth) ; 
			reduceImg(imgsrc, imgsrc, width, nWidth);
		}else{
			float f = ((float) nWidth) / ((float) nSrcWedth);
			height = (int) (f * nSrcHeight) ; 
			reduceImg(imgsrc, imgsrc, nWidth, height);
		}
		return "1";
    } 
    
    /**
     * reduceImg4:按宽度和高度最小者缩小图片.
     *
     * @param imgsrc 图片路径
     * @return    设定参数 .
    */
    public boolean reduceImg4(String imgsrc) {
    	File file = new File(imgsrc);
    	/* 得到源图片的宽度,高度 */
		BufferedImage imgBuf = null;
		
		// 源图片的宽度
		int nSrcWedth = 0;
		
		// 源图片的高度
		int nSrcHeight = 0;
		try {
			imgBuf = ImageIO.read(file);
			nSrcWedth = imgBuf.getWidth();
			nSrcHeight = imgBuf.getHeight();
			if(nSrcWedth < nSrcHeight){
				if(nWidth>nSrcWedth){
					String strSourcePath = file.getParent();
					String fileName = file.getName();
					String newFileName = "s" + fileName;
					reduceImg(imgsrc, strSourcePath + java.io.File.separatorChar 
							+ newFileName, nSrcWedth, nSrcHeight);
					return true; 
				}
			}else if(nWidth>nSrcHeight){
				String strSourcePath = file.getParent();
				String fileName = file.getName();
				String newFileName = "s" + fileName;
				reduceImg(imgsrc, strSourcePath + java.io.File.separatorChar 
						+ newFileName, nSrcWedth, nSrcHeight);
				return true;
			}
			 
		} catch (Exception e){
			logger.error("得到图片的宽度有误!", e); 
		} finally {
			if(imgBuf != null){
				imgBuf.flush();
			}
		}
		int height = 0;
		int width = 0;
		String strSourcePath = file.getParent();
		String fileName = file.getName();
		String newFileName = "s" + fileName;
		if(nSrcWedth >= nSrcHeight){
			float f = ((float) nWidth) / ((float) nSrcHeight);
			width = (int) (f * nSrcWedth) ; 
			reduceImg(imgsrc, strSourcePath + java.io.File.separatorChar 
					+ newFileName, width, nWidth);
		}else{
			float f = ((float) nWidth) / ((float) nSrcWedth);
			height = (int) (f * nSrcHeight) ; 
			reduceImg(imgsrc, strSourcePath + java.io.File.separatorChar 
					+ newFileName, nWidth, height);
		}
		return true;
    }
}
