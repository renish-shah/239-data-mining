package com.spamfilter.naivebayes.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadFile {

	private String fname;
	private String data;

	public ReadFile(String name) throws IOException {
		fname = name;
		readData();
	}
	
	public ReadFile() {
		// TODO Auto-generated constructor stub
	}

	public void readData() throws IOException {
		// File input stream
		FileInputStream inputstream = new FileInputStream(fname);
		FileChannel filechannel = inputstream.getChannel();

		// Reading datas of a file
		ByteBuffer buffer = ByteBuffer.allocate((int) filechannel.size());
		filechannel.read(buffer);
		filechannel.close();
		data = new String(buffer.array());
	}

	public void readDataFromFile(File file) throws IOException {
		// File input stream
		FileInputStream inputstream = new FileInputStream(file);
		FileChannel filechannel = inputstream.getChannel();

		// Reading datas of a file
		ByteBuffer buffer = ByteBuffer.allocate((int) filechannel.size());
		filechannel.read(buffer);
		filechannel.close();
		data = new String(buffer.array());
	}

	
	public String getContent() {
		return data;
	}

}
