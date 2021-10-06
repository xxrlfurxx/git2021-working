package com.git.myworkspace.opendata.covid;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(indexes = @Index(name = "idx_covid_sido", columnList = "gubun"))
@IdClass(CovidSidoId.class)
public class CovidSido {

	@Id
	private String stdDay;
	@Id
	private String gubun;
	// °ª
	private String defCnt;
	private String incDec;
	private String overFlowCnt;
	private String localOccCnt;
	private String isolIngCnt;
	private String deathCnt;
}