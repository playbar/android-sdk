package com.bar.test;

public class FileState {
	String fileName;// 文件名字
	int completeSize;// 完成的长度
	boolean state;// 文件状态,true为已经完成，false为未完成

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getCompleteSize() {
		return completeSize;
	}

	public void setCompleteSize(int completeSize) {
		this.completeSize = completeSize;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
}