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
	
	
	/*
	파라미터로 전달되는 아이디, 패스워드를 request객체가 아닌
	커맨드객체를 통해 한번에 받는다. 회원 인증 결과를 
	JSONObject로 반환한다.
	 */
	@RequestMapping("/android/memberLogin.do")
	@ResponseBody
	public Map<String, Object> memberLogin(MemberVO memberVO){
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		MemberVO memberInfo = 
				sqlSession.getMapper(IAndroidDAO.class).memberLogin(memberVO);
		
		if(memberInfo == null) {
			// 회원 정보 불일치로 로그인에 실패한 경우.. 결과만 0으로 내려준다.
			returnMap.put("isLogin", 0);
		}else {
			// 로그인에 성공하면 결과는 1, 해당 회원의 정보를 객체로 내려준다.
			returnMap.put("memberInfo", memberInfo);
			returnMap.put("isLogin", 1);

		}
		
		System.out.println("요청 들어옴:"+returnMap);
		return returnMap;
	}
	
	
	
	
	
	
	
}
