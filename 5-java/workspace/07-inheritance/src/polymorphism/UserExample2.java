package polymorphism;

public class UserExample2 {
	public static void main(String[] args) {

		// �Ϲ� �����
		User user = new User();
		user.setId("hong");
		user.setName("ȫ�浿");
		user.setPhone("01012345678");
		user.printUserInfo();

		// �θ� Ŭ���� ��ü�� �ڽ� Ŭ���� ��ü�� ������ �� ����
		// �߿��� ���� �θ� Ŭ������ �ʵ�, �޼��常 ��밡����.

		// ������
		Admin admin = new Admin();
		// ��ӹ��� User�� �޼��� �� �ʵ带 �״�� ��밡����
		admin.setId("john");
		admin.setName("John Smith");
		admin.setPhone("0212345678");
		admin.printUserInfo();
		// �ڽ� ��ü�� �޼���, �ʵ�� ��� �Ұ���
//		admin.setDeptNo("10001");

		// ����� ���
		Member member = new Member();
		// ��ӹ��� User�� �޼��� �� �ʵ带 �״�� ��밡����
		member.setId("kim");
		member.setName("������");
		member.setPhone("01009876543");
		// �ڽ� ��ü�� �޼���, �ʵ�� ��� �Ұ���
//		member.setPoint(100000);

		// **�ڽ� ��ü�� ������ �޼��尡 ȣ���
		member.printUserInfo();
	}
}
