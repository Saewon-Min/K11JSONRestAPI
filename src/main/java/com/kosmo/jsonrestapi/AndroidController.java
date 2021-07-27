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

import mybatis.IAndroidDAO;
import mybatis.MemberVO;

@Controller
public class AndroidController {


	// myBatis 자동주입 받기
	@Autowired
	private SqlSession sqlSession;
	
	// 매개변수가 필요없이 회원 리스트 전체를 JSONObject로 반환
	@RequestMapping("/android/memberObject.do")
	@ResponseBody
	public Map<String, Object> memberObject(HttpServletRequest req){
		
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<MemberVO> lists =
				sqlSession.getMapper(IAndroidDAO.class).memberList();
		
		map.put("memberList", lists);
		return map;
		
	}
	
	// JSONArray로 데이터 반환
	@RequestMapping("/android/memberList.do")
	@ResponseBody
	public ArrayList<MemberVO> memberList(HttpServletRequest req){		
		
		ArrayList<MemberVO> lists =
				sqlSession.getMapper(IAndroidDAO.class).memberList();
		
		return lists;
		
	}
	
	
}
