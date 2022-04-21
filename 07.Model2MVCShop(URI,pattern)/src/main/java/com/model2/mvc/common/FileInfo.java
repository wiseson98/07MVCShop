package com.model2.mvc.common;

public class FileInfo {

	/// Filed
	private String saveFolder;
	private String originFile;
	private String savaFile;
	
	/// Constructor
	public FileInfo() {
	}

	/// Method
	public String getSaveFolder() {
		return saveFolder;
	}

	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}

	public String getOriginFile() {
		return originFile;
	}

	public void setOriginFile(String originFile) {
		this.originFile = originFile;
	}

	public String getSavaFile() {
		return savaFile;
	}

	public void setSavaFile(String savaFile) {
		this.savaFile = savaFile;
	}	

}//end of class