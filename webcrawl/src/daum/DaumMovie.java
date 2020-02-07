/*
 * 다음 뉴스 1건에서 제목과 본문을 수집
 * */

package daum;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DaumMovie {
	public static void main(String[] args) throws IOException {
		// 데이터를 수집 할 사이트 주소
		String url="https://entertain.v.daum.net/v/20200205081551752";
		// URL 문서에 전체 태그를 select함
		Document doc = Jsoup.connect(url).get();
		// 전체 태그에서 원하는 항목만 select함
		Elements title = doc.select("h3.tit_view"); // 제목 추출
		Elements content = doc.select("div#harmonyContainer");
		//.text() >>> 태그 및 속성은 지우고 content 내용만 추출
		System.out.println(title.text()); // 제목 출력
		System.out.println(content.text());
	}
}
