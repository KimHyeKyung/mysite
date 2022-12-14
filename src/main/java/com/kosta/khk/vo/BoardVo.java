package com.kosta.khk.vo;

public class BoardVo {

	private int no;
	private String user_name;
	private String title;
	private String content;
	private int hit;
	private int user_no;
	private String reg_date;
	
	//DB에 추가된 컬럼
	private int pos;				//게시물의 상대적인 위치 값을 저장하여 화면에 순서대로 뿌려주는 역할을 담당하는 칼럼입니다.
	private int depth;				//게시물이 답변 글일 경우 답변의 깊이를 저장하는 칼럼
	private int ref;				//게시물이 답변 글일 경우 소속된 부모 글을 가리키는 번호를 저장하는 칼럼
	private String filename1; 		//업로드된 파일의 이름을 저장하는 칼럼
	private String filename2; 		//업로드된 파일의 이름을 저장하는 칼럼
	private String new_filename1;	//업로드된 파일 이름 재 설정
	private String new_filename2;	//업로드된 파일 이름 재 설정
	private int filesize;			//업로드 된 파일의 크기를 저장하는 칼럼
	
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

	public BoardVo(int user_no, String title, String content, String filename1, String filename2,
			String new_filename1, String new_filename2) {
		this.user_no = user_no;
		this.title = title;
		this.content = content;
		this.filename1 = filename1;
		this.filename2 = filename2;
		this.new_filename1 = new_filename1;
		this.new_filename2 = new_filename2;
	}

	public BoardVo(int bno, String title, String content, int hit, String reg_date, int user_no, String user_name, String filename1, String filename2) {
		this.no = bno;
		this.title = title;
		this.content = content;
		this.reg_date = reg_date;
		this.user_no = user_no;
		this.user_name = user_name;
		this.content = content;
		this.filename1 = filename1;
		this.filename2 = filename2;
	}

	public BoardVo(int bno, String title, String content, int hit, String reg_date, int user_no, String user_name,
			String filename1, String filename2, int ref, int pos, int depth) {
		this.no = bno;
		this.title = title;
		this.content = content;
		this.reg_date = reg_date;
		this.user_no = user_no;
		this.user_name = user_name;
		this.content = content;
		this.filename1 = filename1;
		this.filename2 = filename2;
		this.ref = ref;
		this.pos = pos;
		this.depth = depth;
		
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

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getRef() {
		return ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public int getFilesize() {
		return filesize;
	}

	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}

	public String getFilename1() {
		return filename1;
	}

	public void setFilename1(String filename1) {
		this.filename1 = filename1;
	}

	public String getFilename2() {
		return filename2;
	}

	public void setFilename2(String filename2) {
		this.filename2 = filename2;
	}

	public String getNew_filename1() {
		return new_filename1;
	}

	public void setNew_filename1(String new_filename1) {
		this.new_filename1 = new_filename1;
	}

	public String getNew_filename2() {
		return new_filename2;
	}

	public void setNew_filename2(String new_filename2) {
		this.new_filename2 = new_filename2;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", user_name=" + user_name + ", title=" + title + ", content=" + content + ", hit="
				+ hit + ", user_no=" + user_no + ", reg_date=" + reg_date + ", pos=" + pos + ", depth=" + depth
				+ ", ref=" + ref + ", filename1=" + filename1 + ", filename2=" + filename2 + ", new_filename1="
				+ new_filename1 + ", new_filename2=" + new_filename2 + ", filesize=" + filesize + "]";
	}


}
