package com.example.util;

import java.io.IOException;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.springframework.core.io.Resource;

public class XlsDataSetLoader {
	
	   protected IDataSet createDataSet(Resource resource) throws IOException, DataSetException {
//	        try (InputStream inputStream = resource.getInputStream()) {
//	            return new XlsDataSet(inputStream);
//	        }
	    	return new XlsDataSet(resource.getFile());
	    }
	}

