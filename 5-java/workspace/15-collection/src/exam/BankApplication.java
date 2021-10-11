package exam;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankApplication {

	// Map �������� ������ Map ������ Ÿ��(Inteface)
	// = HashMap
	// = HashTable
	// = TreeMap
	//
	// �����ϴ� �ڷᱸ���� ���� ���� �޼��带 ȣ���ϴ���
	// �������� ó������� �ٸ�

	// ���¸�� Map ��ü
	// Map<ŰŸ��, ��Ÿ��> ������ = new HashMap<ŰŸ��, ��Ÿ��>();
	private static Map<String, Account> accounts = new HashMap<String, Account>();

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		boolean run = true;
		while (run) {
			System.out.println("----------------------------------------------------------");
			System.out.println("1.���»��� | 2.���¸�� | 3.���� | 4.��� | 5.����");
			System.out.println("----------------------------------------------------------");
			System.out.print("����> ");

			int selectNo = scanner.nextInt();

			if (selectNo == 1) {
				createAccount();
			} else if (selectNo == 2) {
				accountList();
			} else if (selectNo == 3) {
				deposit();
			} else if (selectNo == 4) {
				withdraw();
			} else if (selectNo == 5) {
				run = false;
			}
		}
		System.out.println("���α׷� ����");
	}

	// ���»����ϱ�(�����߰��ϱ�)
	private static void createAccount() {

		System.out.println("---------");
		System.out.println("���»���");
		System.out.println("---------");
		System.out.println("���¹�ȣ:");
		System.out.println("������:");
		System.out.println("�ʱ��Աݾ�:");
		Account account = new Account(scanner.next(), scanner.next(), scanner.nextInt());
		accounts.put(scanner.next(), account);
		System.out.println("���: ���°� �����Ǿ����ϴ�.");

	}

	// ���¸�Ϻ���
	private static void accountList() {

		System.out.println("---------");
		System.out.println("���¸��");
		System.out.println("---------");

		for (Account account : accounts.values()) {
			String ano = account.getAno();
			String owner = account.getOwner();
			int balance = account.getBalance();

			System.out.println(ano + "\t" + owner + "\t" + balance);
		}
	}

	// �����ϱ�(�ʵ尪����)
	private static void deposit() {

		System.out.println("---------");
		System.out.println("����");
		System.out.println("---------");

		System.out.println("���¹�ȣ:");
		Account a = accounts.get(scanner.next());

		System.out.println("���ݾ�:");
		a.setBalance(a.getBalance() + scanner.nextInt());
		System.out.println("���: ������ �Ϸ�Ǿ����ϴ�.");
	}

	// ����ϱ�(�ʵ尪����)
	private static void withdraw() {

		System.out.println("---------");
		System.out.println("���");
		System.out.println("---------");

		System.out.println("���¹�ȣ:");
		Account a = accounts.get(scanner.next());

		System.out.println("��ݾ�: ");
		a.setBalance(a.getBalance() - scanner.nextInt());
		System.out.println("���: ����� �Ϸ�Ǿ����ϴ�.");

	}
}
