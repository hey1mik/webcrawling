package ticketrank;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import domain.MovieDTO;
import persistence.MovieDAO;

public class NaverTicket {
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
	public TicketDTO naverCrawler(String movie, String code) throws IOException {
		base="https://movie.naver.com/movie/bi/mi/pointWriteFormList.nhn?code="+code+"&type=after&isActualPointWriteExecute=false&isMileageSubscriptionAlready=false&isMileageSubscriptionReject=false&page=";
		url = base+page;
		System.out.println("※※※※※※※※※※※※※※ NAVER START ※※※※※※※※※※※※※※※");
		
		label:while(true) {
			page++;
			Document doc = Jsoup.connect(url).get();
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
				
				MovieDTO mDto = new MovieDTO(movie, content, writer, score, "NAVER", regdate);
				mDao.addMovie(mDto);
				
				System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
				System.out.println("영화: "+ movie);
				System.out.println("평점: "+ score);
				System.out.println("작성자: "+ writer);
				System.out.println("내용: "+ content);
				System.out.println("작성일자: "+ regdate);
				System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
				total = total + score;
				
				}
			}
			System.out.println("※※※※※※※※※※※※※※ NAVER END ※※※※※※※※※※※※※※※");
			TicketDTO tDto = new TicketDTO(count, total);
			return tDto;
	}
}
