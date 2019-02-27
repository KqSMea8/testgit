package com.hanweb.jmp.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import com.hanweb.complat.exception.OperationException;
import com.swetake.util.Qrcode;

public class QRCodeUtil {
	  
    /**
     * createQRCode:生成二维码(QRCode)图片 .
     *
     * @param content 二维码图片的内容
     * @param imgPath 生成二维码图片完整的路径
     * @param ccbPath 二维码图片中间的logo路径
     * @return 0 - 成功<br/>
	 *         -1 - 超过200字
	 *         -100 - 失败
     * @throws OperationException    设定参数 .
    */
    public static int createQRCode(String content, String imgPath, String ccbPath)
    			throws OperationException {  
        try {
            Qrcode qrcodeHandler = new Qrcode();
            //设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储
            //的信息越少，但对二维码清晰度的要求越小  
            qrcodeHandler.setQrcodeErrorCorrect('Q');  
            //N代表数字,A代表字符a-Z,B代表其他字符
            qrcodeHandler.setQrcodeEncodeMode('B');
            // 设置设置二维码版本，取值范围1-40，值越大尺寸越大，可存储的信息越大  
            qrcodeHandler.setQrcodeVersion(10);  
            byte[] contentBytes = content.getBytes("gb2312");  
            BufferedImage bufImg = new BufferedImage(310, 310, BufferedImage.TYPE_INT_RGB);  
            Graphics2D gs = bufImg.createGraphics();  
  
            gs.setBackground(Color.WHITE);  
            gs.clearRect(0, 0, 310, 310);  
  
            // 设定图像颜色 > BLACK  
            gs.setColor(Color.BLACK);  
            // 设置偏移量 不设置可能导致解析出错  
            int pixoff = 12;  
            // 输出内容 > 二维码  
            if (contentBytes.length > 0 && contentBytes.length <200) {  
                boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);  
                for (int i = 0; i < codeOut.length; i++) {  
                    for (int j = 0; j < codeOut.length; j++) {  
                        if (codeOut[j][i]) {  
                            gs.fillRect(j * 5 + pixoff, i * 5 + pixoff, 5, 5);  
                        }  
                    }  
                }  
            } else {
                return -1;
            }
            if(ccbPath!=null){
            	//实例化一个Image对象。
            	Image img = ImageIO.read(new File(ccbPath));
            	gs.drawImage(img, 100, 100, null);
            	gs.dispose();  
            	bufImg.flush();              	
            }else{
            	Image img = null;
            	gs.drawImage(img, 100, 100, null);
            	gs.dispose();  
            	bufImg.flush();
            }
  
            // 生成二维码QRCode图片  
            File imgFile = new File(imgPath);  
            ImageIO.write(bufImg, "png", imgFile);
  
        } catch (Exception e) {  
        	e.printStackTrace();
        }  
        
        return 0;
    } 

}
