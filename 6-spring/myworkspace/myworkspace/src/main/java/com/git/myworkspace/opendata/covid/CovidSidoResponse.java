package com.git.myworkspace.opendata.covid;

import java.util.List;

import lombok.Data;

@Data
public class CovidSidoResponse {
	private Response response;

	@Data
	public class Response {
		private Header header;
		private Body body;
	}
	@Data
	public class Header {
		private String resultCode;
		private String resultMsg;
	}

	@Data
	public class Body {
		private Items items;
	}

	@Data
	public class Items {
		private List<Item> item;
	}
	@Data
	public class Item {

		private String stdDay;
		private String gubun;
		private String defCnt;
		private String incDec;
		private String overFlowCnt;
		private String localOccCnt;
		private String isolIngCnt;
		private String deathCnt;

	}

	// {"response": {
	// "header":{
	// "resultCode":"00",
	// "resultMsg":"NORMAL SERVICE."
	// },
	// "body":{
	// "pageNo":1,
	// "totalCount":19,
	// "items":{"item":[
	// {
	// "defCnt":6128,
	// "isolClearCnt":5917,
	// "localOccCnt":0,
	// "incDec":8,
	// "updateDt":null,
	// "createDt":"2021-10-05 09:55:20.397",
	// "gubun":"�˿�",
	// "gubunEn":"Lazaretto",
	// "deathCnt":15,
	// "stdDay":"2021�� 10�� 05�� 00��",
	// "qurRate":"-",
	// "overFlowCnt":8,
	// "gubunCn":"̰��ϡ",
	// "isolIngCnt":196,
	// "seq":12511
	// },
}