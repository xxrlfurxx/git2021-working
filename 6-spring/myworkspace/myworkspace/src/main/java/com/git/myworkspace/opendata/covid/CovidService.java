package com.git.myworkspace.opendata.covid;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class CovidService {

	private final String SERVICE_KEY = "yDpU2UqOZf2Neb%2FxmD4yBeY%2Ba4HYU1rPbCd4SjXYOQPpHeH3mRbHx0qhXx69kyX1lK%2FwFUMChtvjEO57UNpbOg%3D%3D";

	private CovidSidoRepository repo;

	@Autowired
	public CovidService(CovidSidoRepository repo) {
		this.repo = repo;

	}
	// 시도별 코로나 조회
	@Scheduled(cron = " 0 10 10 * * *")
	@SuppressWarnings("deprecation")
	public void requestCovidSido() throws IOException {
		System.out.println(new Date().toLocaleString());

		/* --------------------- 데이터 요청하고 XML 받아오기 ---------------- */
		// http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey=yDpU2UqOZf2Neb%2FxmD4yBeY%2Ba4HYU1rPbCd4SjXYOQPpHeH3mRbHx0qhXx69kyX1lK%2FwFUMChtvjEO57UNpbOg%3D%3D&pageNo=1&numOfRows=10
		// 문자열을 빌더방식으로 생성하는 클래스

		// 1. 요청 URL 만들기
		StringBuilder builder = new StringBuilder();
		builder.append("http://openapi.data.go.kr/openapi"); // 호스트/게이트웨이
		builder.append("/service/rest/Covid19"); // 서비스
		builder.append("/getCovid19SidoInfStateJson"); // 기능(시도)
		builder.append("?serviceKey=" + SERVICE_KEY); // 서비스 키
		builder.append("&pageNo=1&numOfRows=19"); // 17시도 +

		System.out.println(builder.toString());

		// 2. URL 객체 생성
		URL url = new URL(builder.toString());

		// 3. Http 접속 생성
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		// 4. byte[]배열로 데이터를 읽어옴
		byte[] result = con.getInputStream().readAllBytes();

		// 5. 문자열 변환
		String data = new String(result, "UTF-8");
		System.out.println(data);
		/* --------------------- 데이터 요청하고 XML 받아오기 끝---------------- */

		/* ---------------- XML -> JSON -> Object(Java) 시작 -------------- */
		// XML(문자열) -> JSON(객체)
		JSONObject jsonObj = XML.toJSONObject(data);
		// JSON(객체) -> JSON(문자열)
		String json = jsonObj.toString();
		// System.out.println(json);

		// JSON(문자열) -> Java(object)
		CovidSidoResponse response = new Gson().fromJson(json,
				CovidSidoResponse.class);
		System.out.println(response);

		/* --------------------- XML -> JSON -> Object(Java) 끝 -------------- */

		/* ----------------응답 객체 -> 엔티티 시작 ------------------ */
		List<CovidSido> list = new ArrayList<CovidSido>();
		for (CovidSidoResponse.Item item : response.getResponse().getBody()
				.getItems().getItem()) {
			CovidSido record = CovidSido.builder().stdDay(item.getStdDay())
					.gubun(item.getGubun()).defCnt(item.getDefCnt())
					.incDec(item.getIncDec()).overFlowCnt(item.getOverFlowCnt())
					.localOccCnt(item.getLocalOccCnt())
					.isolIngCnt(item.getIsolIngCnt())
					.deathCnt(item.getDeathCnt()).build();
			list.add(record);
		}
		/* ----------------응답 객체 -> 엔티티 끝 ------------------ */

		/* ----------------엔티티객체 저장 시작 ------------------ */
		repo.saveAll(list);
		/* ----------------엔티티객체 저장 끝 ------------------ */
	}
}
