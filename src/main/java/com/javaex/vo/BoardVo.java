package com.javaex.vo;

public class BoardVo {

	private int no;
	private String user_name;
	private String title;
	private String content;
	private int hit;
	private int user_no;
	private String reg_date;
	
	//DB에 추가된 컬럼
	private int pos;	//게시물의 상대적인 위치 값을 저장하여 화면에 순서대로 뿌려주는 역할을 담당하는 칼럼입니다.
	private int depth;	//게시물이 답변 글일 경우 답변의 깊이를 저장하는 칼럼
	private int ref;	//게시물이 답변 글일 경우 소속된 부모 글을 가리키는 번호를 저장하는 칼럼
	private String filename; //업로드된 파일의 이름을 저장하는 칼럼
	private int filesize;	//업로드 된 파일의 크기를 저장하는 칼럼
	
	//페이징을 위해 BoardVo에만 추가한 컬럼
	private int totalRecord; 	// 전체레코드수
	private int numPerPage; 	// 페이지당 레코드 수
	private int pagePerBlock; 	// 블럭당 페이지수
	private int totalPage; 		// 전체 페이지 수
	private int totalBlock; 	// 전체 블럭수
	private int nowPage; 		// 현재페이지
	private int nowBlock; 		// 현재블럭
	private int start; 			// 디비의 select 시작번호
	private int end; 			// 시작번호로 부터 가져올 select 갯수
	private int listSize; 		// 현재 읽어온 게시물의 수
	private String keyWord;
	private String keyField;
	private int pageStart;
	private int pageEnd;
	
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

	//추가된 컬럼을 포함한 생성자
	public BoardVo(int no, String user_name, String title, String content, int hit, int user_no, String reg_date,
			int pos, int depth, int ref, String filename, int filesize) {
		super();
		this.no = no;
		this.user_name = user_name;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.user_no = user_no;
		this.reg_date = reg_date;
		this.pos = pos;
		this.depth = depth;
		this.ref = ref;
		this.filename = filename;
		this.filesize = filesize;
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getFilesize() {
		return filesize;
	}

	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}

	//페이징을 위해 BoardVo에만 추가한 컬럼
	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getPagePerBlock() {
		return pagePerBlock;
	}

	public void setPagePerBlock(int pagePerBlock) {
		this.pagePerBlock = pagePerBlock;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalBlock() {
		return totalBlock;
	}

	public void setTotalBlock(int totalBlock) {
		this.totalBlock = totalBlock;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getNowBlock() {
		return nowBlock;
	}

	public void setNowBlock(int nowBlock) {
		this.nowBlock = nowBlock;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getKeyField() {
		return keyField;
	}

	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public int getPageEnd() {
		return pageEnd;
	}

	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}


	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", user_name=" + user_name + ", title=" + title + ", content=" + content + ", hit="
				+ hit + ", user_no=" + user_no + ", reg_date=" + reg_date + ", pos=" + pos + ", depth=" + depth
				+ ", ref=" + ref + ", filename=" + filename + ", filesize=" + filesize + "]";
	}

}
