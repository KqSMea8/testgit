package com.hanweb.jmp.pack.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

import com.hanweb.common.util.DateUtil;

public class ImageUtil {
	
	/**
	 * color
	 */
	private Color color = Color.white;
	
	/**
	 * fontName
	 */
	private String fontName = "黑体";
	
	/**
	 * alpha
	 */
	private float alpha = 1.0f;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	/**
	 * 添加文字水印
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param sourceImg
	 *            源图片路径，如：C://myPictrue//1.jpg
	 * @param pressText
	 *            水印文字， 如：中国证券网
	 * @param fontName
	 *            字体名称， 如：宋体
	 * @param fontStyle
	 *            字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
	 * @param fontSize
	 *            字体大小，单位为像素
	 * @param color
	 *            字体颜色
	 * @param x
	 *            水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
	 * @param y
	 *            水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
	 * @param alpha
	 *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 * @return * @return boolean           
	 */
	private String pressText(String targetImg, String sourceImg, String pressText, String fontName, int fontStyle,
			int fontSize, Color color, int x, int y, float alpha) {
		try {
			File file = new File(targetImg);
			File file2 = new File(sourceImg);
			if (!file.exists() || !file.isFile()) {
				return "";
			}
			if (!file2.exists() || !file2.isDirectory()) {
				return "";
			}
			Image image = ImageIO.read(file);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setColor(color);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			int height1 = fontSize;
			g.drawString(pressText, x, y + height1);
			g.dispose();
			Random random = new Random();
			String filename = DateUtil.getCurrDate("yyyyMMdd-HHmmss") + (999 - random.nextInt(899));
			ImageIO.write(bufferedImage, "png", new File(sourceImg + "/" + filename + ".png"));
			return filename + ".png";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
 
	/**
	 * isIncludeChinese
	 * @param strValue strValue
	 * @return    设定参数 .
	 */
	private boolean isIncludeChinese(String strValue) {
		if ("".equals(strValue) || strValue == null) {
			return false;
		}
		for (int i = 0, len = strValue.length(); i < len; i++) {
			if (String.valueOf(strValue.charAt(i)).matches("[\\u4E00-\\u9FA5]+")) {
				return true;
			}
		}
		return false;
	}
 
	/**
	 * isIncludeEnglish
	 * @param strValue strValue
	 * @return    设定参数 .
	 */
	private boolean isIncludeEnglish(String strValue) {
		if ("".equals(strValue) || strValue == null) {
			return false;
		}
		for (int i = 0, len = strValue.length(); i < len; i++) {
			if (String.valueOf(strValue.charAt(i)).matches("[A-Za-z]")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * isIncludeNumber
	 * @param strValue strValue
	 * @return    设定参数 .
	 */
	private boolean isIncludeNumber(String strValue) {
		if ("".equals(strValue) || strValue == null) {
			return false;
		}
		for (int i = 0, len = strValue.length(); i < len; i++) {
			if (String.valueOf(strValue.charAt(i)).matches("[0-9]")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 添加文字水印
	 * @param targetImg targetImg
	 * @param sourceImg sourceImg
	 * @param pressText pressText
	 * @param size size 
	 * @return String
	 */
	public String pressText(String targetImg, String sourceImg, String pressText, int size) {
		String result = "";
		if (isIncludeChinese(pressText.substring(0, 1))) {
			if (size == 1) {// 小图114*114
				result = pressText(targetImg, sourceImg, pressText.substring(0, 1), fontName, Font.BOLD, 80, color, 15,
						 5, alpha);
			} else if (size == 2) {// 大图1024*1024
				result = pressText(targetImg, sourceImg, pressText.substring(0, 1), fontName, Font.BOLD, 750, color,
						 115, 16, alpha);
			}
		} else if (isIncludeEnglish(pressText.substring(0, 1))) {
			if (size == 1) {// 小图114*114
				result = pressText(targetImg, sourceImg, pressText.substring(0, 1).toUpperCase(), fontName, Font.BOLD,
						 100, color, 28, -7, alpha);
			} else if (size == 2) {// 大图1024*1024
				result = pressText(targetImg, sourceImg, pressText.substring(0, 1).toUpperCase(), fontName, Font.BOLD,
						 1000, color, 228, -135, alpha);
			}
		} else if (isIncludeNumber(pressText.substring(0, 1))) {
			if (size == 1) {// 小图114*114
				result = pressText(targetImg, sourceImg, pressText.substring(0, 1).toUpperCase(), fontName, Font.BOLD,
						 120, color, 25, -20, alpha);
			} else if (size == 2) {// 大图1024*1024
				result = pressText(targetImg, sourceImg, pressText.substring(0, 1).toUpperCase(), fontName, Font.BOLD,
						 1000, color, 250, -135, alpha);
			}
		}
		return result;
	}
	
}