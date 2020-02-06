package naver;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import domain.MovieDTO;
import persistence.MovieDAO;

public class NaverMovie {
	public static void main(String[] args) throws IOException {
		// 하이큐! 땅 vs 하늘
		String title = "하이큐! 땅 vs 하늘";
		int score, regdate, total = 0;
		int count = 0;
		String writer, content, basedate, subdate = "";
		int number = 1;
		double scoreAvg = 0.0;
		String compare = "";
		MovieDAO mDao = new MovieDAO();
		
		label:while(true) {
		String url ="https://movie.naver.com/movie/bi/mi/pointWriteFormList.nhn?code=191431&type=after&isActualPointWriteExecute=false&isMileageSubscriptionAlready=false&isMileageSubscriptionReject=false&page="; 
		String page = url + number;
		number++;
		Document doc = Jsoup.connect(page).get();
		Elements reply = doc.select("div.score_result > ul > li");
		
		int i;		
		for(i = 0 ; i < reply.size(); i++) {
			count++;
			writer = reply.get(i).select("div.score_reple dl span").text();
			score = Integer.parseInt(reply.get(i).select("div.star_score em").text());
			content = reply.get(i).select("div.score_reple > p").text();
			basedate = reply.get(i).select("div.score_reple em:eq(1)").text();
			subdate = basedate.substring(0,10);
			regdate = Integer.parseInt(subdate.replace(".", ""));
			
			//마지막 페이지 종료
			if(i == 0) {
				if(writer.equals(compare)) {
					break label;
				} else {
					compare = writer;
				}
			}
			
			
			//FOR EACH 문에서 돌때 저장해야 한건한건 전부 DB에 들어감
			MovieDTO mDto = new MovieDTO(title, content, writer, score, "NAVER", regdate);
			mDao.addHighQ(mDto);
			
			System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
			System.out.println("영화: "+ title);
			System.out.println("평점: "+ score);
			System.out.println("작성자: "+ writer);
			System.out.println("내용: "+ content);
			System.out.println("작성일자: "+ regdate);
			System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
			total = total + score;
			
			}
			
		} 
		
		scoreAvg = (double)total / count;
		double result = Math.floor(scoreAvg);
		
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
		System.out.println("영화 <" + title + "> NAVER 영화 평점 수집 결과");
		System.out.println("총 " + count + "건 검색되었습니다.");
		System.out.println("영화 "+ title + "의 평점평균은 " + result + "점입니다.");
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");	
		
	}
}
