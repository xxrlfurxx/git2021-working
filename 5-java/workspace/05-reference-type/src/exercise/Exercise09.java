package exercise;

import java.util.Scanner;

public class Exercise09 {

	public static void main(String[] args) {
		boolean run = true;
		int studentNum = 0;
		int[] scores = null;
		Scanner scanner = new Scanner(System.in);

		while (run) {
			System.out.println("----------------------------------------------");
			System.out.println("1.�л��� | 2.�����Է� | 3. ��������Ʈ | 4.�м� | 5.����");
			System.out.println("----------------------------------------------");
			System.out.println("����>");

			int selectNo = scanner.nextInt();

			switch (selectNo) {
			case 1:
				System.out.println("�л���>");
				studentNum = scanner.nextInt();
				// �л����� �Է¹���
				// �Է��� �л��� ��ŭ �迭ũ�⸦ �ʱ�ȭ
				break;
			case 2:
				scores = new int[studentNum];
				for (int i = 0; i < studentNum; i++) {
					System.out.println("scores[" + i + "]");
					scores[i] = scanner.nextInt();
				}
				// �迭ũ�⸸ŭ �ݺ��ؼ� ������ �Է� ����
				break;
			case 3:
				for (int i = 0; i < studentNum; i++) {
					System.out.println("scores[" + i + "]= " + scores[i]);
				}
				// �迭ũ�⸸ŭ �ݺ��ؼ� ���� ����� ���
				break;
			case 4:
				int sum = 0;
				for (int score : scores) {
					sum += score;
				}
				System.out.println("�ְ� ����:" + sum);
				System.out.println("�������:" + sum * 1.0 / scores.length);
				// �ְ������� ������� ���
				break;
			case 5:
				System.out.println("����>");
				// run = false�� �ݺ��� Ż��
				run = false;
				break;
			}
		}
		System.out.println("���α׷� ����");

	}
}
