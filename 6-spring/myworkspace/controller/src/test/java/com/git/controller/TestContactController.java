package com.git.controller;

// 값이 같은지를 비교하는 static 메서드
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.git.controller.contact.Contact;
import com.git.controller.contact.ContactController;

// 단위 테스트(Unit Test)
// - 자바 관점으로 있을 때 클래스의 메서드들을 테스트하는 것
//
// integration test
//- 백엔드관점으로 봤을 때는 API를 테스트 하는 것
//- 서버를 띄우고, 네트워크로 데이터를 보내고 처리 결과까지 확인

@SpringBootTest
public class TestContactController {

	// test case: 할 말 추가
	// event flow(처리흐림): 할 일 1건을 추가함
	// pre-condition(사전조건): 따로 없음..
	// expected result(예상결과): 할 일 목록에 추가한 데어터가 존재해야함

	@Test
	void addContact() {
		// given - 테스트 데어터 및 객체 준비
		ContactController controller = new ContactController();
		Contact expected = Contact.builder().id(1).name("홍길동")
				.phone("010-1234-5678").email("google@naver.com").build();

		// when - 테스트 케이스의 event flow를 수행
		// Servlet Response 객체는 가짜(Mock)를 넣어줌
		controller.addContact(expected, new MockHttpServletResponse());
		// then - 예상결과 실제결과를 비교

		// 전체 목록에 추가한애를 가져옴
		List<Contact> contacts = controller.getContacts();
		Contact actual = contacts.get(0); // arrayList.get(인덱스);

		// 예상결과의 데어터와 실제 데이터를 비교함
		assertEquals(1, actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getPhone(), actual.getPhone());
		assertEquals(expected.getEmail(), actual.getEmail());
	}
	// test case: 할 말 삭제
	// event flow(처리흐림): 할 일 1건을 삭제함
	// pre-condition(사전조건): 할 일 데이터가 최소 1건이상 있어야함
	// expected result(예상결과): 할 일 목록에 삭제한 데어터가 존재하면 안 됨
	@Test
	void removeContact() {
		// given - 테스트 데어터 및 객체 준비
		// 사전 조건이 있다면 사전조건을 준비하는 단계
		// 여기서는 1건 추가가 되어있어야함
		ContactController controller = new ContactController();

		Contact testItem = Contact.builder().id(1).name("홍길동")
				.phone("010-1234-5678").email("google@naver.com").build();

		controller.addContact(testItem, new MockHttpServletResponse());

		List<Contact> beforecontacts = controller.getContacts();
		assertEquals(1, beforecontacts.size());

		// when - 테스트 케이스의 event flow를 수행
		// id가 1인 contact 1건을 삭제
		controller.removeContact(1, new MockHttpServletResponse());

		// then - 예상결과와 실제결과를 비교
		// 목록을 조회했을 때 목록의 크기가 0이어야함
		List<Contact> aftercontacts = controller.getContacts();
		assertEquals(0, aftercontacts.size()); // 삭제 후에는 목록 크기가 0
	}

	// test case: 할 일 수정
	// event flow(처리흐름): 할 일 1건에 대해서 메모값을 수정함
	// basic flow(정상흐름):
	// 1. 할 일 1건에 대해서 메모값을 수정함
	// alternative flow(예외흐름):
	// 1. 목록에 없는 id값으로 삭제를 실행 - 404
	// 2. 메모값을 빈 값 또는 데이터 객체를 안보냈음 - 400
	// pre-condition(사전조건): 할 일 데이터 최소 1건 이상 있어야함
	// expected result(예상결과): 할 일 목록에 수정한 메모값으로 출력되어야함
	@Test
	void modifyContact() {
		// given - 테스트 데어터 및 객체 준비
		// 사전 조건이 있다면 사전조건을 준비하는 단계
		// 여기서는 1건 추가가 되어있어야함
		ContactController controller = new ContactController();

		Contact testItem = Contact.builder().id(1).name("홍길동")
				.phone("010-1234-5678").email("google@naver.com").build();
		controller.addContact(testItem, new MockHttpServletResponse());

		// 변경할 테스트 데이터
		String expectedResult = "modify test memo";
		String expectedResult1 = "modify test memo1";
		String expectedResult2 = "modify test memo2";
		Contact modifyData = Contact.builder().name(expectedResult)
				.phone(expectedResult1).email(expectedResult2).build();

		HttpServletResponse res = new MockHttpServletResponse();

		// Basic flow - 정상적인 상황
		// when - 테스트 케이스의 event flow를 수행
		// id가 1인 contact에 memo를 수정
		controller.modifyContact(1, modifyData, res);

		// then - 예상결과와 실제결과를 비교
		// 목록을 조회했을 때 해당 메모값이 예상결과와 일치하여야함
		List<Contact> contacts = controller.getContacts();
		assertEquals(expectedResult, contacts.get(0).getName());
		assertEquals(expectedResult1, contacts.get(0).getPhone());
		assertEquals(expectedResult2, contacts.get(0).getEmail());

		// alternative flow - 1. id값이 없는 경우
		// when - id가 2로 수정해봄
		Contact resultContactId = controller.modifyContact(2, modifyData, res);

		// then - 예상결과와 실제결과를 비교
		// 반환 객체가 null, Status Code 404
		assertNull(resultContactId);
		assertEquals(HttpServletResponse.SC_NOT_FOUND, res.getStatus());

		// alternative flow - 2-1. memo값이 없거나 null인 경우
		// when
		Contact resultContactMemoNull = controller.modifyContact(1,
				new Contact(), res);

		// then - 예상결과와 실제결과를 비교
		// 반환 객체가 null, Status Code 400
		assertNull(resultContactMemoNull);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());

		// alternative flow - 2-2. memo값이 빈 값("")인 경우
		// when

		Contact resultContactMemoEmpty = controller.modifyContact(1,
				Contact.builder().name("").phone("").email("").build(), res);

		// then - 예상결과와 실제결과를 비교
		// 반환 객체가 null, Status Code 400
		assertNull(resultContactMemoEmpty);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());
	}
}
