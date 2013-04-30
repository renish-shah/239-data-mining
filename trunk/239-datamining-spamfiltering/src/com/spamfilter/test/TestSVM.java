/**
 * 
 */
package com.spamfilter.test;

import java.io.File;


import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 * @author RENISH
 * 
 */

public class TestSVM {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			
			TextDirectoryLoader loader = new TextDirectoryLoader();
			loader.setDirectory(new File(
					"C:\\Users\\Ax\\Desktop\\KNNDatasets\\data"));
			Instances dataRaw = loader.getDataSet();
			// System.out.println("\n\nImported data:\n\n" + dataRaw);

			// apply the StringToWordVector
			// (see the source code of setOptions(String[]) method of the filter
			// if you want to know which command-line option corresponds to
			// which
			// bean property)
			StringToWordVector filter = new StringToWordVector();
			filter.setInputFormat(dataRaw);
			Instances dataFiltered = Filter.useFilter(dataRaw, filter);
			
			System.out.println("\n\nFiltered data:\n\n" + dataFiltered);
		} catch (Exception e) {
			System.out.println("Exception :" + e);
		}

	}

}
