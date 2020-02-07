package daum;

import java.io.IOException;
import java.text.DecimalFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import domain.MovieDTO;
import persistence.MovieDAO;

public class MovieList {

	public static void main(String[] args) throws IOException{
		int count = 0;// 전체 댓글 수
		int score = 0;// 평점
		int total = 0;//평점을 모두 더하는 변수
		int number = 1;//수집한 페이지수(1page = 댓글 10개)
		double scoreAvg = 0.0; // 평균평점
		String title="";
		String writer="";
		String content="";
		int regdate=0;
		DecimalFormat df = new DecimalFormat("0.0");
		MovieDAO mDao = new MovieDAO();
		
		while(true) { // 전체 페이지 반복
		String url = "https://movie.daum.net/moviedb/grade?movieId=134091&type=netizen&page=";
		String page = url+number;
		number++;
		Document doc = Jsoup.connect(page).get();
		Elements reply = doc.select("ul.list_review div.review_info");
		
			if(reply.isEmpty()) {
				break;
			}
		
		//영화제목 수집
		Elements movieName = doc.select("h2.tit_rel");
		title = movieName.text();
		
		String basedate, subdate = "";
		
		for(Element one : reply) { // 한 페이지 내 리뷰 반복

			count++;
			writer = one.select("em.link_profile").text();
			score = Integer.parseInt(one.select("em.emph_grade").text());
			content = one.select("p.desc_review").text();
			basedate = one.select("span.info_append").text();
			subdate = basedate.substring(0,10);
			regdate = Integer.parseInt(subdate.replace(".", ""));
			
			//DB에 저장!
			MovieDTO mDto = new MovieDTO(title, content, writer, score, "Daum", regdate);
			mDao.addMovie(mDto);
			
			System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
			System.out.println("영화: "+ title);
			System.out.println("평점: "+ score);
			System.out.println("작성자: "+ writer);
			System.out.println("내용: "+ content);
			System.out.println("작성일자: "+ regdate);
			System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
			total = total + score;

				}
		
			scoreAvg = (double)total / count;
			double result = scoreAvg;
			
			System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
			System.out.println("영화 " + title + " Daum영화 평점 수집 결과");
			System.out.println("총 " + count + "건 검색되었습니다.");
			System.out.println("영화 "+ title + "의 평점평균은 " + df.format(result) + "점입니다.");
			System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
			}
	}
}

