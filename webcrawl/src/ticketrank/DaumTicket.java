package ticketrank;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import domain.MovieDTO;
import persistence.MovieDAO;

public class DaumTicket {
	String base ="";
	int page = 1;
	String url = "";
	String writer = "";
	String content = "";
	String basedate = "";
	String subdate = "";
	String movie = "";
	int regdate = 0;
	int score = 0;
	int count = 0; 
	int total = 0;
	String compare = "";
	MovieDAO mDao = new MovieDAO();
	TicketDTO tDto = new TicketDTO();

		
		// 네이버 영화 댓글(평점)을 수집하는 메서드
	public TicketDTO daumCrawler(String movie, String code) throws IOException {
		System.out.println("※※※※※※※※※※※※※※ DAUM START ※※※※※※※※※※※※※※※");
		
			while(true) {
			base="https://movie.daum.net/moviedb/grade?movieId="+code+"&type=netizen&page="; 
			url = base+page;
			page++;
			Document doc = Jsoup.connect(url).get();
			Elements reply = doc.select("ul.list_review div.review_info");
			
			if(reply.isEmpty()) {
				break;
			}
				
			for(Element one : reply) {
				count++;
				writer = one.select("em.link_profile").text();
				score = Integer.parseInt(one.select("em.emph_grade").text());
				content = one.select("p.desc_review").text();
				basedate = one.select("span.info_append").text();
				subdate = basedate.substring(0,10);
				regdate = Integer.parseInt(subdate.replace(".", ""));
				
				//누적 평점을 계산
				total = total + score;
				
				MovieDTO mDto = new MovieDTO(movie, content, writer, score, "DAUM", regdate);
				mDao.addMovie(mDto);
				
				System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
				System.out.println("영화: "+ movie);
				System.out.println("평점: "+ score);
				System.out.println("작성자: "+ writer);
				System.out.println("내용: "+ content);
				System.out.println("작성일자: "+ regdate);
				System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
				
				
				}
			}
			System.out.println("※※※※※※※※※※※※※※ DAUM END ※※※※※※※※※※※※※※※");
			TicketDTO tDto = new TicketDTO(count, total);
			return tDto;
			
	}
}
