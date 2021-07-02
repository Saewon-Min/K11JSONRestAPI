package mybatis;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ParameterDTO {

	private String num; // 사용자 아이디
	private String searchField; // 게시판 일련번호
	private ArrayList<String> searchTxt; // 검색어(3차버전)
	private int start; // select의 시작
 	private int end; // 끝
	
	
}
