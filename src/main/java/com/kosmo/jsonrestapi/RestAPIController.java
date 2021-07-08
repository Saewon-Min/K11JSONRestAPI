package com.kosmo.jsonrestapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mybatis.BoardDTO;
import mybatis.IBoardDAO;
import mybatis.ParameterDTO;

@Controller
public class RestAPIController {

	/*
	Mybatis를 사용하기 위해 빈을 자동주입 받는다.
	 */
	@Autowired
	private SqlSession sqlSession;
	
	// 모델1 방식의 게시판인 board테이블의 목록을 JSON으로 얻어옴
	@RequestMapping("/restapi/boardList.do")
	@ResponseBody // 반환타입이 List컬렉션므로 JSON배열로 출력한다.
	public ArrayList<BoardDTO> boardList(HttpServletRequest req){
	
		// 파라미터를 저장할 용도의 DTO객체 생성
		ParameterDTO parameterDTO = new ParameterDTO();
		// 검색 필드를 DTO객체에 저장
		parameterDTO.setSearchField(req.getParameter("searchField"));
		ArrayList<String> searchLists = null;
		
		/*
		검색어를 스페이스로 구분하여 2개이상 입력하면 split() 메소드를
		통해 분리하여 List컬렉션에 저장한다.
		 */
		if(req.getParameter("searchTxt")!=null) {
			searchLists = new ArrayList<String>();
			String[] sTxtArray = req.getParameter("searchTxt").split(" ");
			for(String str : sTxtArray) {
				searchLists.add(str);
			}
		}
	
		// 검색어를 저장한 List컬렉션을 DTO객체에 저장
		parameterDTO.setSearchTxt(searchLists);
		System.out.println("검색어 : "+searchLists);
		
		// 게시물의 개수를 카운트 한다.
		int totalRecordCount = 
		sqlSession.getMapper(IBoardDAO.class).getTotalCount(parameterDTO);
	
		// 출력할 페이지의 범위를 계산하는 부분 START  //////////////////////////////
		int pageSize = 10;
		//int blockPage = 2;
		int totalPage =
			(int)Math.ceil((double)totalRecordCount/pageSize);
		int nowPage = req.getParameter("nowPage")==null ? 1:
		Integer.parseInt(req.getParameter("nowPage"));
		int start = (nowPage-1)*pageSize +1;
		int end = nowPage*pageSize;
		// 계산된 start,end를 DTO에 저장
		parameterDTO.setStart(start);
		parameterDTO.setEnd(end);
		// 출력할 페이지의 범위를 계산하는 부분 END  ///////////////////////////////
		
		// JSON으로 출력할 레코드 가져오기
		ArrayList<BoardDTO> lists 
		= sqlSession.getMapper(IBoardDAO.class).listPage(parameterDTO);
		
		// 가져온 데이터는 줄바꿈 처리
		for (BoardDTO dto : lists) {
		String temp=
				dto.getContent().replace("\r\n", "<br/>");
				dto.setContent(temp);
		}
		
		
		return lists;
	}
	
	
	// 게시물 내용을 JSON으로 출력
	@RequestMapping("/restapi/boardView.do")
	@ResponseBody
	public Map<String, String> view(ParameterDTO parameterDTO){
		
		ArrayList<BoardDTO> record = sqlSession.getMapper(IBoardDAO.class)
				.view(parameterDTO);
		Map<String, String> map = new HashMap<String, String>();
		
		/*
		Mapper의 경우 select한 결과를 List컬렉션으로 반환하므로 이를
		읽어서 Map컬렉션에 다시 저장해 준다.
		 */
		for(BoardDTO dto : record) {
			
			map.put("num", dto.getNum());
			map.put("title", dto.getTitle());
			map.put("content", dto.getContent().replace("\r\n", "\\r\\n")); //줄바꿈처리
			map.put("id", dto.getId());
			map.put("postdate", dto.getPostdate().toString());
			map.put("visitcount", dto.getVisitcount());
			
		}
		
		// Map컬렉션을 반환하면 JSON객체로 출력된다.
		return map;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
