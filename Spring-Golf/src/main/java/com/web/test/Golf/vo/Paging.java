package com.web.test.Golf.vo;
//게시판 하단의 페이징
public class Paging extends Criteria{
	private int totalCount;	//게시판 전체 데이터 개수
	private int displayPageNum = 5; //게시판 화면에서 한번에 보여질 페이지 번호의 개수
	/*
	displayPageNum
	이부분에 대한 설정이 하단 페이징 목록의 1 2 3 ... 10 을 결정한다.
	내가 10으로 설정했으면 [1~10 다음]까지만 하단에 나오고
	11페이지 이후부터 [이전 11 ~ 20 다음] 이런식으로 출력된다. 
	 */
	private int startPage; //화면의 시작 번호
	private int endPage; //화면의 끝번호
	private boolean prev;	//페이징 이전 버튼 활성화 여부
	private boolean next;	//페이징 다음 버튼 활성화 여부
	
	private int pageNum;
	private Criteria cri;
	
	private int pageSize; // 한 페이지당 보여줄 게시글 개
	
	
	 
	public Paging(Criteria cri, int totalCount) {
	    this.cri = cri;
	    this.totalCount = totalCount;
	    this.endPage = (int) (Math.ceil(cri.getPage() / (double) displayPageNum) * displayPageNum);
	    this.startPage = this.endPage - displayPageNum + 1;
	    if (this.startPage <= 0) { // startPage가 0보다 작거나 같으면 1로 설정
	        this.startPage = 1;
	    }
	    int totalPage = (int) (Math.ceil(totalCount / (double) cri.getPerPageNum()));
	    if (this.endPage > totalPage) {
	        this.endPage = totalPage;
	    }
	    this.prev = this.startPage == 1 ? false : true;
	    this.next = this.endPage * cri.getPerPageNum() >= totalCount ? false : true;
	}

	public Paging() {}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		
		pagingData();
		setPageNum(cri.getPage());
	}

	private void pagingData() {
	    if (cri.getPage() == 1) { // 페이지 번호를 처음 계산하는 경우
	        startPage = 1;
	    } else { // 이전 페이지에서 넘어온 경우
	        int prevEndPage = ((int) Math.floor((cri.getPage() - 2) / displayPageNum)) * displayPageNum + displayPageNum;
	        startPage = prevEndPage + 1;
	    }
	    int endPage = startPage + displayPageNum - 1;
	    int tempEndPage = (int)(Math.ceil(totalCount / (double)cri.getPerPageNum()));
	    if(endPage > tempEndPage) {
	        endPage = tempEndPage;
	    }
	    prev = startPage == 1 ? false : true;
	    next = endPage * cri.getPerPageNum() >= totalCount ? false : true;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	@Override
	public String toString() {
		return "Paging [totalCount=" + totalCount + ", displayPageNum=" + displayPageNum + ", startPage=" + startPage
				+ ", endPage=" + endPage + ", prev=" + prev + ", next=" + next + ", cri=" + cri + "]";
	}
}
