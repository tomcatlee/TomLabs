package com.tom.labs;

import java.io.File;

public class CreateFolderHierarchy {
	public static void main(String[] args) {
		create();
	}

	public static void create() {
		File root = new File("d:/root");
		root.mkdir();
		treeDirCreate(root,10,10);
	/*	for(int i=1;i<10;i++) {
		for(int j=1;j<10;j++) {
		   File file = new File("d:/root/"+j+"/"+j+"/"+j+"/"+j+"/"+j+"/"+j+"/"+j+"/"+i);
				file.mkdirs();
				file = new File("d:/root/"+j+"/"+j+"/"+j+"/"+j+"/"+j+"/"+j+"/"+i+"/"+j);
				file.mkdirs();
				file = new File("d:/root/"+j+"/"+j+"/"+j+"/"+j+"/"+j+"/"+j+"/"+i+"/"+j);
				file.mkdirs();
				file = new File("d:/root/"+j+"/"+j+"/"+j+"/"+j+"/"+j+"/"+i+"/"+j+"/"+j);
				file.mkdirs();
				file = new File("d:/root/"+j+"/"+j+"/"+j+"/"+j+"/"+i+"/"+j+"/"+j+"/"+j);
				file.mkdirs();
				file = new File("d:/root/"+j+"/"+j+"/"+j+"/"+i+"/"+j+"/"+j+"/"+j+"/"+j);
				file.mkdirs();
				file = new File("d:/root/"+j+"/"+j+"/"+i+"/"+j+"/"+j+"/"+j+"/"+j+"/"+j);
				file.mkdirs();
				file = new File("d:/root/"+j+"/"+i+"/"+j+"/"+j+"/"+j+"/"+j+"/"+j+"/"+j);
				file.mkdirs();
				file = new File("d:/root/"+i+"/"+j+"/"+j+"/"+j+"/"+j+"/"+j+"/"+j+"/"+j);
			}
		}*/
		//File root =  new File("d:/root/1/2/3/4/5/6/7/8/9");
		//root.mkdirs();
	/*	String path = "d:/root";
		for(int i=1;i<10;i++) {//层数
			for(int j=1;j<10;j++) {
				File file = new File("d:/root/"+j);
			}
		}*/
		/*while(true) {
			File parent = root.getParentFile();
			if(parent!=null) {
				String path = parent.getAbsolutePath();
				for(int i=1;i<10;i++) {
					path = path+"/"+1;
					File file = new File(path);
					file.mkdirs();
				}
			}
		}*/
		
	}
	
	public static void treeDirCreate(File rootDir, int range, int deep) {
		for(int i=1;i<range;i++) {
			String path =rootDir.getAbsolutePath()+"/"+i;
			File file = new File(path);
			file.mkdirs();
			if(deep>1) treeDirCreate(file,range,deep-1);
		}
	}
}
