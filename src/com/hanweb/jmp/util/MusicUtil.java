package com.hanweb.jmp.util;

import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;

public class MusicUtil {

	public String findTime(String path){
		String musicTime="";
		try{
			File file = new File(path);
			FileInputStream fis=new FileInputStream(file);
			int b=fis.available();
			Bitstream bt=new Bitstream(fis);
			Header h = bt.readFrame();
			int time = (int) h.total_ms(b); 
			int i = time/1000;
			if(i/60<10){
				musicTime=("0"+i/60 + ":" + i%60); 
			}else{
				musicTime=(i/60 + ":" + i%60); 
			} 
		}catch(Exception e){
			e.printStackTrace(); 
		}
		return musicTime;
	}
}
