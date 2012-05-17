package com.tom.labs;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class DownloadUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		download("http://hiphotos.baidu.com/dazhu1115/pic/item/fb8eccffa223a373d6887dfc.jpg");
	}

	public static void download(String imgPath) {
		URL imgUrl;
		try {
			imgUrl = new URL(imgPath);
			ReadableByteChannel rbc = Channels.newChannel(imgUrl.openStream());
			FileOutputStream fos = new FileOutputStream("d:/vvv.gif");
			fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
