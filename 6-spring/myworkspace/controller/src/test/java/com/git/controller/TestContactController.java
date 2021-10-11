package com.git.controller;

// ���� �������� ���ϴ� static �޼���
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.git.controller.contact.Contact;
import com.git.controller.contact.ContactController;

// ���� �׽�Ʈ(Unit Test)
// - �ڹ� �������� ���� �� Ŭ������ �޼������ �׽�Ʈ�ϴ� ��
//
// integration test
//- �鿣��������� ���� ���� API�� �׽�Ʈ �ϴ� ��
//- ������ ����, ��Ʈ��ũ�� �����͸� ������ ó�� ������� Ȯ��

@SpringBootTest
public class TestContactController {

	// test case: �� �� �߰�
	// event flow(ó���帲): �� �� 1���� �߰���
	// pre-condition(��������): ���� ����..
	// expected result(������): �� �� ��Ͽ� �߰��� �����Ͱ� �����ؾ���

	@Test
	void addContact() {
		// given - �׽�Ʈ ������ �� ��ü �غ�
		ContactController controller = new ContactController();
		Contact expected = Contact.builder().id(1).name("ȫ�浿")
				.phone("010-1234-5678").email("google@naver.com").build();

		// when - �׽�Ʈ ���̽��� event flow�� ����
		// Servlet Response ��ü�� ��¥(Mock)�� �־���
		controller.addContact(expected, new MockHttpServletResponse());
		// then - ������ ��������� ��

		// ��ü ��Ͽ� �߰��Ѿָ� ������
		List<Contact> contacts = controller.getContacts();
		Contact actual = contacts.get(0); // arrayList.get(�ε���);

		// �������� �����Ϳ� ���� �����͸� ����
		assertEquals(1, actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getPhone(), actual.getPhone());
		assertEquals(expected.getEmail(), actual.getEmail());
	}
	// test case: �� �� ����
	// event flow(ó���帲): �� �� 1���� ������
	// pre-condition(��������): �� �� �����Ͱ� �ּ� 1���̻� �־����
	// expected result(������): �� �� ��Ͽ� ������ �����Ͱ� �����ϸ� �� ��
	@Test
	void removeContact() {
		// given - �׽�Ʈ ������ �� ��ü �غ�
		// ���� ������ �ִٸ� ���������� �غ��ϴ� �ܰ�
		// ���⼭�� 1�� �߰��� �Ǿ��־����
		ContactController controller = new ContactController();

		Contact testItem = Contact.builder().id(1).name("ȫ�浿")
				.phone("010-1234-5678").email("google@naver.com").build();

		controller.addContact(testItem, new MockHttpServletResponse());

		List<Contact> beforecontacts = controller.getContacts();
		assertEquals(1, beforecontacts.size());

		// when - �׽�Ʈ ���̽��� event flow�� ����
		// id�� 1�� contact 1���� ����
		controller.removeContact(1, new MockHttpServletResponse());

		// then - �������� ��������� ��
		// ����� ��ȸ���� �� ����� ũ�Ⱑ 0�̾����
		List<Contact> aftercontacts = controller.getContacts();
		assertEquals(0, aftercontacts.size()); // ���� �Ŀ��� ��� ũ�Ⱑ 0
	}

	// test case: �� �� ����
	// event flow(ó���帧): �� �� 1�ǿ� ���ؼ� �޸��� ������
	// basic flow(�����帧):
	// 1. �� �� 1�ǿ� ���ؼ� �޸��� ������
	// alternative flow(�����帧):
	// 1. ��Ͽ� ���� id������ ������ ���� - 404
	// 2. �޸��� �� �� �Ǵ� ������ ��ü�� �Ⱥ����� - 400
	// pre-condition(��������): �� �� ������ �ּ� 1�� �̻� �־����
	// expected result(������): �� �� ��Ͽ� ������ �޸����� ��µǾ����
	@Test
	void modifyContact() {
		// given - �׽�Ʈ ������ �� ��ü �غ�
		// ���� ������ �ִٸ� ���������� �غ��ϴ� �ܰ�
		// ���⼭�� 1�� �߰��� �Ǿ��־����
		ContactController controller = new ContactController();

		Contact testItem = Contact.builder().id(1).name("ȫ�浿")
				.phone("010-1234-5678").email("google@naver.com").build();
		controller.addContact(testItem, new MockHttpServletResponse());

		// ������ �׽�Ʈ ������
		String expectedResult = "modify test memo";
		String expectedResult1 = "modify test memo1";
		String expectedResult2 = "modify test memo2";
		Contact modifyData = Contact.builder().name(expectedResult)
				.phone(expectedResult1).email(expectedResult2).build();

		HttpServletResponse res = new MockHttpServletResponse();

		// Basic flow - �������� ��Ȳ
		// when - �׽�Ʈ ���̽��� event flow�� ����
		// id�� 1�� contact�� memo�� ����
		controller.modifyContact(1, modifyData, res);

		// then - �������� ��������� ��
		// ����� ��ȸ���� �� �ش� �޸��� �������� ��ġ�Ͽ�����
		List<Contact> contacts = controller.getContacts();
		assertEquals(expectedResult, contacts.get(0).getName());
		assertEquals(expectedResult1, contacts.get(0).getPhone());
		assertEquals(expectedResult2, contacts.get(0).getEmail());

		// alternative flow - 1. id���� ���� ���
		// when - id�� 2�� �����غ�
		Contact resultContactId = controller.modifyContact(2, modifyData, res);

		// then - �������� ��������� ��
		// ��ȯ ��ü�� null, Status Code 404
		assertNull(resultContactId);
		assertEquals(HttpServletResponse.SC_NOT_FOUND, res.getStatus());

		// alternative flow - 2-1. memo���� ���ų� null�� ���
		// when
		Contact resultContactMemoNull = controller.modifyContact(1,
				new Contact(), res);

		// then - �������� ��������� ��
		// ��ȯ ��ü�� null, Status Code 400
		assertNull(resultContactMemoNull);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());

		// alternative flow - 2-2. memo���� �� ��("")�� ���
		// when

		Contact resultContactMemoEmpty = controller.modifyContact(1,
				Contact.builder().name("").phone("").email("").build(), res);

		// then - �������� ��������� ��
		// ��ȯ ��ü�� null, Status Code 400
		assertNull(resultContactMemoEmpty);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());
	}
}
