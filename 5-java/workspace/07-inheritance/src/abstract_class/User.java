package abstract_class;

// ����� - �ڽİ�ü�� ������ ��Ÿ���� Ŭ����
// �ڽİ�ü�� �⺻ �ʵ�, �ż���� �߰� �����ؾߵǴ� �޼����� ���븸 ����
// public abstract class Ŭ������
public class User {
	private String id;
	private String name;
	private String phone;

	// final �޼��� : ������ �Ұ����� �޼���
//	public final void printUserInfo() {
	public void printUserInfo() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}