package com.git.myworkspace.opendata.covid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/opendata/covid")
public class CovidController {
	private CovidSidoDailyRepository repo;
	private final String cachName = "covid-current";

	@Autowired
	public CovidController(CovidSidoDailyRepository repo) {
		this.repo = repo;
		setGubuns();
	}

	// @Cacheable(value = "캐시이름", key = "키이름")
	@Cacheable(value = cachName, key = "'all'")
	@GetMapping(value = "/gubun/current")
	public List<CovidSidoDaily> getCovidSidocurrent() {
		return repo
				.findAll(PageRequest.of(0, 19, Sort.by("std_day").descending()))
				.toList();
	}
	// 1. 전국 데이터 조회
	// page size 19개, 정렬은 std_day desc

	// 2. 특정 시도의 데이터 조회
	// 검색조건에 gubun, page size(limit)를 7, 정렬은 stdDay desc
	// 예) WHERE gubun=서울 ORDER BY std_day Des LIMIT 7;
	@Cacheable(value = cachName, key = "#city")
	@GetMapping(value = "/gubun/current/{city}")
	public List<CovidSidoDaily> getCovidSidoCurrent(@PathVariable String city) {
		Pageable page = PageRequest.of(0, 7, Sort.by("std_day").descending());
		return repo.findByGubun(page, city);
	}

	private void setGubuns() {
	}
}