package com.myapp.core.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.jpedal.examples.html.PDFtoHTML5Converter;
import org.jpedal.exception.PdfException;
import org.jpedal.render.output.IDRViewerOptions;
import org.jpedal.render.output.html.HTMLConversionOptions;

import com.aspose.cells.Workbook;
import com.aspose.slides.Presentation;
import com.aspose.words.Document;
import com.aspose.words.PdfSaveOptions;
import com.myapp.core.enums.FileType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月28日 
 * @system:
 *
 *-----------MySong---------------
 */
public class DocConvertUtil {
	public static Map<String,FileType> FILETYPE = new HashMap<String,FileType>(){
		{
			put("doc",FileType.DOC);put("docx",FileType.DOC);
			put("xls",FileType.EXCEL);put("xlsx",FileType.EXCEL);
			put("ppt",FileType.PPT);put("pptx",FileType.PPT);
			put("pdf",FileType.PDF);
			put("zip",FileType.ZIP);put("rar",FileType.ZIP);put("jar",FileType.ZIP);
			put("png",FileType.IMG);put("jpg",FileType.IMG);put("jpeg",FileType.IMG);put("bmp",FileType.IMG);put("gif",FileType.IMG);
		}
	};
	public static String getFileNameSuffix(String fileName){
		if(!BaseUtil.isEmpty(fileName)){
			int sfIdx = fileName.lastIndexOf(".");
			if(sfIdx>=0&&fileName.length()>1){
				return fileName.substring(sfIdx+1);
			}
		}
		return null;
	}
	
	public static String getFileNamePrefx(String fileName){
		if(!BaseUtil.isEmpty(fileName)){
			int sfIdx = fileName.lastIndexOf(".");
			if(sfIdx>=0&&fileName.length()>1){
				return fileName.substring(0,sfIdx);
			}
		}
		return null;
	}
	
	public static FileType getFileType(String fileName){
		FileType type = null;
		String suffix = getFileNameSuffix(fileName);
		if(!BaseUtil.isEmpty(suffix)){
			suffix = suffix.toLowerCase();
			return FILETYPE.get(suffix);
		}
		return type;
	}
	
	public static void loadLicense(FileType type){
		if(type!=null){
			//目前只是存在excel 和ppt 需要license
			if(type.equals(FileType.EXCEL)||type.equals(FileType.PPT)){
				 ClassLoader loader = Thread.currentThread().getContextClassLoader();
			     InputStream license = loader.getResourceAsStream("doc-license.xml");// 凭证文件
			    if(type.equals(FileType.EXCEL)){
			    	com.aspose.cells.License lic = new com.aspose.cells.License();
			    	lic.setLicense(license);
			    }else if(type.equals(FileType.PPT)){
			    	com.aspose.slides.License aposeLic = new com.aspose.slides.License();
			        aposeLic.setLicense(license);
			    }
			}
		}
	}
	
	public static void checkFilePath(File file) throws IOException{
		if(file!=null){
			if(!file.exists()){
				file.mkdirs();
			}
		}
	}
	
	public static File documnt2Pdf(String srcFile,String targFilePath) throws Exception{
		if(BaseUtil.isEmpty(srcFile)) return null;
		return documnt2Pdf(new File(srcFile),targFilePath);
	}
	public static File documnt2Pdf(File sFile,String targFilePath) throws Exception{
		if(sFile==null) return null;
		File pdfFile = null;
		if(sFile.exists()&&sFile.isFile()){
			if(BaseUtil.isEmpty(targFilePath)) targFilePath = sFile.getParent();
			String srcFname = sFile.getName();
			String fnPrefix = getFileNamePrefx(srcFname);
			FileType ft = getFileType(srcFname);
			if(ft!=null){
				if(ft.equals(FileType.PDF)){
					pdfFile = sFile;
				}else{
					loadLicense(ft);
					pdfFile = new File(targFilePath+"/pdf/"+fnPrefix+".pdf");
					checkFilePath(pdfFile.getParentFile());
					if(ft.equals(FileType.DOC)){
						Document doc =new Document(sFile.getAbsolutePath());
			        	PdfSaveOptions options = new PdfSaveOptions();
			        	doc.save(pdfFile.getAbsolutePath(), options);
					}else if(ft.equals(FileType.EXCEL)){
						Workbook wb = new Workbook(sFile.getAbsolutePath());
			    	    com.aspose.cells.PdfSaveOptions options = new com.aspose.cells.PdfSaveOptions();
			    	    options.setAllColumnsInOnePagePerSheet(true);
			    	    options.setValidateMergedAreas(true);
			            wb.save(pdfFile.getAbsolutePath(), options);
					}else if(ft.equals(FileType.PPT)){
						Presentation pres = new Presentation(sFile.getAbsolutePath());
			            pres.save(pdfFile.getAbsolutePath(), com.aspose.slides.SaveFormat.Pdf);
					}
				}
			}
		}
		return pdfFile;
	}
	
	
	public static void pdf2Html(File pdfFile,String outFilePath) throws IOException, PdfException{
		if(pdfFile!=null&&pdfFile.exists()&&pdfFile.isFile()){
			if(BaseUtil.isEmpty(outFilePath)) outFilePath = pdfFile.getParent();
			File outFile = new File(outFilePath);
			checkFilePath(outFile);
			if(outFile.isDirectory()){
				HTMLConversionOptions conversionOptions = new HTMLConversionOptions();
		        conversionOptions.setDisableComments(true);
		        IDRViewerOptions viewerOptions = new IDRViewerOptions();
		        viewerOptions.setViewerUI(IDRViewerOptions.ViewerUI.Simple);
		        PDFtoHTML5Converter converter = new PDFtoHTML5Converter(pdfFile, outFile, conversionOptions, viewerOptions);
		        converter.convert();
			}
		}
	}
	
	 public static void main(String[] args) throws Exception {
		 File pdfFile = documnt2Pdf("D:/doc/jdjh.xls","D:/doc/pdf");
		 pdf2Html(pdfFile,"D:/doc/html");
	 }
}
