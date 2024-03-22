package com.example.demo.dto;

import java.util.List;

import com.example.demo.domain.Challenge;

public class ChallengePagingResponse {
	private int page;			// 현재 페이지 번호
    private int size;			// 페이지당 표시할 아이템 수
    private long totalPages;	// 전체 페이지 수
    private long totalCount;	// 전체 아이템 수
    private List<Challenge> challenges;	// 현재 페이지의 챌린지 목록
    
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public List<Challenge> getChallenges() {
		return challenges;
	}
	public void setChallenges(List<Challenge> challenges) {
		this.challenges = challenges;
	}
	public ChallengePagingResponse(int page, int size, long totalPages, long totalCount, List<Challenge> challenges) {
		super();
		this.page = page;
		this.size = size;
		this.totalPages = totalPages;
		this.totalCount = totalCount;
		this.challenges = challenges;
	}
	public ChallengePagingResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

}
