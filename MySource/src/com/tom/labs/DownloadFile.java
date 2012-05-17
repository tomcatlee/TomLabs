package com.tom.labs;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadFile extends Thread {

	URL url;
	long startPos;
	long endPos;
	final static int bufferSize = 1024 * 3;
	public static ExecutorService pool = Executors.newCachedThreadPool();

	public DownloadFile(URL url, long startPos, long endPos) {
		this.url = url;
		this.startPos = startPos;
		this.endPos = endPos;
	}

	public static void main(String[] args) {
		try {
			download(new URL("http://hiphotos.baidu.com/dazhu1115/pic/item/fb8eccffa223a373d6887dfc.jpg"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void download(URL url) throws IOException {
		String fileName = url.getFile();
		final long size = 8096;
		if (fileName.equals("")) {
			throw new IOException("download webfile \"" + fileName + "\" failed");
		}
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		long total = con.getContentLength();
		System.out.println("file size is " + total);
		con.disconnect();
		if (total <= size) {
			
		} else {
			long part = total % size != 0 ? total / size + 1 : total / size;
			long len;
			byte[] b = null;
			for (int i = 0; i < part; i++) {
				len = i != part - 1 ? size : total % size;
				long startPos = size * i;
				long endPos = startPos + len - 1;
				con = (HttpURLConnection) url.openConnection();
				con.setAllowUserInteraction(true);
				con.setReadTimeout(30000);
				con.setRequestProperty("RANGE", "bytes=" + startPos + "-" + endPos);
				System.out.println("Request Data from " + startPos + " to " + endPos);
				con.connect();
				
				DownloadFile task = new DownloadFile(url,startPos,endPos);
				pool.execute(task);
			}
		}
	}

	public void run() {
		byte[] b = new byte[bufferSize];
		HttpURLConnection con;
		try {
			con = (HttpURLConnection) url.openConnection();
			con.setAllowUserInteraction(true);
			con.setReadTimeout(30000);
			con.setRequestProperty("RANGE", "bytes=" + startPos + "-" + endPos);
			con.connect();
			BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
			RandomAccessFile oSavedFile = new RandomAccessFile("D:/bbb.jpg", "rw");
			oSavedFile.seek(startPos);
			System.out.println("Request Data from " + startPos + " to " + endPos);
			while (startPos < endPos) {
				int wlen = bis.read(b, 0, bufferSize);
				oSavedFile.write(b, 0, wlen);
				startPos += wlen;
			}
			bis.close();
			oSavedFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
