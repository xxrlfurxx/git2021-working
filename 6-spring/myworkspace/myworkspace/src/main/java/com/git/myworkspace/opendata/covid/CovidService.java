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
	// �õ��� �ڷγ� ��ȸ
	@Scheduled(cron = " 0 10 10 * * *")
	@SuppressWarnings("deprecation")
	public void requestCovidSido() throws IOException {
		System.out.println(new Date().toLocaleString());

		/* --------------------- ������ ��û�ϰ� XML �޾ƿ��� ---------------- */
		// http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey=yDpU2UqOZf2Neb%2FxmD4yBeY%2Ba4HYU1rPbCd4SjXYOQPpHeH3mRbHx0qhXx69kyX1lK%2FwFUMChtvjEO57UNpbOg%3D%3D&pageNo=1&numOfRows=10
		// ���ڿ��� ����������� �����ϴ� Ŭ����

		// 1. ��û URL �����
		StringBuilder builder = new StringBuilder();
		builder.append("http://openapi.data.go.kr/openapi"); // ȣ��Ʈ/����Ʈ����
		builder.append("/service/rest/Covid19"); // ����
		builder.append("/getCovid19SidoInfStateJson"); // ���(�õ�)
		builder.append("?serviceKey=" + SERVICE_KEY); // ���� Ű
		builder.append("&pageNo=1&numOfRows=19"); // 17�õ� +

		System.out.println(builder.toString());

		// 2. URL ��ü ����
		URL url = new URL(builder.toString());

		// 3. Http ���� ����
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		// 4. byte[]�迭�� �����͸� �о��
		byte[] result = con.getInputStream().readAllBytes();

		// 5. ���ڿ� ��ȯ
		String data = new String(result, "UTF-8");
		System.out.println(data);
		/* --------------------- ������ ��û�ϰ� XML �޾ƿ��� ��---------------- */

		/* ---------------- XML -> JSON -> Object(Java) ���� -------------- */
		// XML(���ڿ�) -> JSON(��ü)
		JSONObject jsonObj = XML.toJSONObject(data);
		// JSON(��ü) -> JSON(���ڿ�)
		String json = jsonObj.toString();
		// System.out.println(json);

		// JSON(���ڿ�) -> Java(object)
		CovidSidoResponse response = new Gson().fromJson(json,
				CovidSidoResponse.class);
		System.out.println(response);

		/* --------------------- XML -> JSON -> Object(Java) �� -------------- */

		/* ----------------���� ��ü -> ��ƼƼ ���� ------------------ */
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
		/* ----------------���� ��ü -> ��ƼƼ �� ------------------ */

		/* ----------------��ƼƼ��ü ���� ���� ------------------ */
		repo.saveAll(list);
		/* ----------------��ƼƼ��ü ���� �� ------------------ */
	}
}
