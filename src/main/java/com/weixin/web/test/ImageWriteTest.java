package com.weixin.web.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageWriteTest {

	public static void main(String[] args) throws IOException {
		File image = new File("E:\\person\\weixin\\src\\main\\resources\\resources\\123.JPG");
		System.out.println(image.length());
		FileInputStream fileInputStream = new FileInputStream(image);
		File file = new File("D:/142.JPG");
		if(!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		int c;
		while((c=fileInputStream.read())!=-1) {
			fileOutputStream.write(c);
		}
		fileOutputStream.flush();
		fileOutputStream.close();
		fileInputStream.close();
	}
}
