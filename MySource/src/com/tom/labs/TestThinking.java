package com.tom.labs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class TestThinking {
	TestThinking(String name, int n) throws Exception {
		DataOutputStream out = new DataOutputStream(new FileOutputStream(name));
		int[] b = new int[30];

		b[0] = 1;
		b[1] = 1;
		for (int i = 2; i < n; i++) {
			b[i] = b[i - 2] + b[i - 1];
		}
		for (int j = 0; j < n; j++) {
			out.write(b[j]);
			System.out.printf("%d ", b[j]);
		}
		out.close();
	}

	public static void main(String[] args) throws Exception {

		TestThinking tt = new TestThinking("Fei.txt", 20);
	}
}

class TestThinking_6 {
	TestThinking_6(String name) throws Exception {
		DataInputStream in = new DataInputStream(new FileInputStream(name));
		int x;
		while (in.available() > 0) {
			x = in.read();
			System.out.printf("%d ", x);
		}
		in.close();
	}

	public static void main(String[] args) throws Exception {
		TestThinking_6 tt = new TestThinking_6("Fei.txt");
	}
}