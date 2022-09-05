package com.javaex.vo;

public class BoardVo {

	private int no;
	private String title;
	private String content;
	private int hit;
	private int user_no;
	private String reg_date;
	private String user_name;
	//depth나 답글기능(부모글번호 등등...)까지 하면 best
	
	
	public BoardVo() {
		super();
	}
	
	public BoardVo(int no, String title, String content, int hit, int user_no, String reg_date) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.user_no = user_no;
		this.reg_date = reg_date;
	}
	
	public BoardVo(int no, String title, int hit, String reg_date, int user_no, String user_name) {
		super();
		this.no = no;
		this.title = title;
		this.hit = hit;
		this.user_no = user_no;
		this.reg_date = reg_date;
		this.user_name = user_name;
	}

	public BoardVo(int no, String title, String content, int hit, String reg_date, int user_no, String user_name) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.user_no = user_no;
		this.reg_date = reg_date;
	}

	public BoardVo(int no, String title, String content) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
	}

	public BoardVo(String title, String content, int user_no) {
		super();
		this.title = title;
		this.content = content;
		this.user_no = user_no;
	}

	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	
	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", user_no="
				+ user_no + ", reg_date=" + reg_date + ", user_name=" + user_name + "]";
	}


}
