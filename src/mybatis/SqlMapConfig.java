package mybatis;


import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class SqlMapConfig {
	private static SqlSessionFactory sqlSessionFactory;
	
	//정적블록 static{}, 클래스 로딩시 1회만 실행되는 코드
	static {
		String resource = "mybatis/Configuration.xml";
		
		try {
			Reader reader = Resources.getResourceAsReader(resource);
					if(sqlSessionFactory == null) {
						sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader); //빌드패턴
						//빌드패턴이란? sqlSession공장을 만들때, 공장을 객체생성하기엔 너무 복잡해서 일반적인 객체생성으로는 만들 수가 없다.
						//그래서 빌드패턴이라는 전문가를 통해 객체를 생성하는 것!
					}
			
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
	
	public static SqlSessionFactory getSqlSession() {
		return sqlSessionFactory;
	}

}
