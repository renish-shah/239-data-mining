package com.spamfilter.naivebayes.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class WriteFile {
	private String fname;
	  
	  public WriteFile(String name) {
	    fname = name;
	  }
	  
	  public void writeContent(String content) throws IOException {
	    // File output stream
	    FileOutputStream outputstream = new FileOutputStream(fname);
	    FileChannel filechannel = outputstream.getChannel();
	 
	    ByteBuffer buffer = ByteBuffer.wrap(content.getBytes());
	    filechannel.write(buffer);
	    filechannel.close();
	  }	  
}
