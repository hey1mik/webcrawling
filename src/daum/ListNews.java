/*
 * 다음 뉴스 목록의 각 기사마다 제목과 본문을 수집
 */
package daum;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ListNews {
	public static void main(String[] args) throws IOException {
		String url = "https://news.daum.net/breakingnews/digital";
		Document doc = Jsoup.connect(url).get();
		Elements urls = doc.select(".review_info");
		int count = 0;
		for (Element element : urls) {
			// attr()을 사용하여 원하는 속성값 추출
			count++;
			
			String href = element.attr("href");
			
			Document docNews = Jsoup.connect(href).get();
			
			Elements title = docNews.select("h3.tit_view"); // 제목 추출
			Elements content = docNews.select("div#harmonyContainer");
			//.text() >>> 태그 및 속성은 지우고 content 내용만 추출
			System.out.println("▒▒▒▒▒▒▒▒▒▒▒"+count+"건 ▒▒▒▒▒▒▒▒▒▒▒");
			System.out.println("제목: "+title.text()); // 제목 출력
			System.out.println("본문: "+content.text());
		}
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
		System.out.println("▒▒▒▒▒ Daum에서 수집한 뉴스 총"+count+"건");
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
	}
}
