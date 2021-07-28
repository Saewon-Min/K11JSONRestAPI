package com.kosmo.jsonrestapi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class FileuploadController {
	
	@RequestMapping(method=RequestMethod.POST, value="/fileUpload/uploadAndroid.do")
	@ResponseBody
	public Map uploadAndroid(Model model, MultipartHttpServletRequest req) {
		
		//서버의 물리적경로 가져오기
		String path = 
				req.getSession().getServletContext().getRealPath("/resources/uploadsFile");
			
		//폼값과 파일명을 저장후 View로 전달하기 위한 맵 생성
		Map returnObj = new HashMap();		
		try
		{
			//업로드폼의 file속성의 필드를 가져온다.(여기서는 2개임)
			Iterator itr = req.getFileNames();
			
			MultipartFile mfile = null;			
			String fileName = "";
			List resultList = new ArrayList();
			//파일외의 폼값 받음(여기서는 제목만 있음)
			String title = req.getParameter("title");
			System.out.println("title="+title);
			
			/*
			물리적경로를 기반으로 File 객체를 생성한후 지정된 
			디렉토리가 존재하는지 확인함. 만약 없다면 생성함.
			 */
			File directory = new File(path);
			if(!directory.isDirectory()){		
				directory.mkdirs();
			}
			
			//업로드폼의  file속성의 필드갯수만큼 반복
			while(itr.hasNext())
			{		
				//전송된 파일의 이름을 읽어옴.
				fileName = (String)itr.next();
				mfile = req.getFile(fileName);				
				System.out.println("mfile="+ mfile);
				
				//한글깨짐방지 처리후 전송된 파일명을 가져옴. 
				String originalName = 
					new String(mfile.getOriginalFilename().getBytes(),"UTF-8");
				//서버로 전송된 파일이 없다면 while문의 처음으로 돌아간다. 
				if("".equals(originalName)){
					continue;
				}
				
				//파일명에서 확장자 부분을 가져옴
				String ext = 
					originalName.substring(originalName.lastIndexOf('.'));
				//UUID를 통해 생성된 문자열과 확장자를 합침
				String saveFileName = getUuid() + ext;
				//물리적경로에 새롭게 생성된 파일명으로 파일저장
				File serverFullName = 
						new File(path + File.separator + saveFileName);						
				mfile.transferTo(serverFullName);
				
				//서버에 파일업로드 완료후...
				Map file = new HashMap();
				file.put("originalName", originalName);//원본파일명 
				file.put("saveFileName", saveFileName);//저장된파일명
				file.put("serverFullName", serverFullName);//서버의 전체 경로
				file.put("title", title);//제목
				//위 4가지 정보를 저장한 Map을 ArrayList에 저장한다. 
				resultList.add(file);
			}
			
			
			//파일업로드에 성공했을때...
			returnObj.put("files", resultList);
			returnObj.put("success", 1);
			
		}		 
		catch(IOException e){
			//파일업로드에 실패했을때...
			returnObj.put("success", 0);
			e.printStackTrace();
		}
		catch(Exception e) {
			//파일업로드에 실패했을때...
			returnObj.put("success", 0);
			e.printStackTrace();
		}
		
		
		return returnObj;
	}

	/*
	UUID(Universally Unique IDentifier)
		: 범용 고유 식별자.
		randomUUID()를 통해 문자열을 생성하면
		하이픈이 4개 포함된 32자의 랜덤하고 유니크한 
		문자열이 생성된다. JDK에서 기본클래스로 제공된다.
	 */
	public static String getUuid() {
		//randomUUID() =>  기울어짐 => static타입의 메소드이기 때문
		String uuid = UUID.randomUUID().toString();
		System.out.println("생성된UUID-1 : "+uuid);
		
		// 중간에 포함된 하이픈을 제거한다.
		uuid = uuid.replaceAll("-", "");
		System.out.println("생성된UUID-2 : "+uuid);
		
		return uuid;
		
	}


}
