package mybatis;

import lombok.Data;

@Data
public class BoardDTO {

	// 멤버변수
    private String num;
    private String title; 
    private String content ;
    private String id ;
    private java.sql.Date postdate;
    private String visitcount;
    // member테이블과 조인 후 회원 이름을 출력해야 할때 사용
    private String name;
    
    
	
    
    
}
