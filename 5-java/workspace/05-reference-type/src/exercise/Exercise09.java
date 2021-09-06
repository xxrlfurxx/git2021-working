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
			System.out.println("1.학생수 | 2.점수입력 | 3. 점수리스트 | 4.분석 | 5.종료");
			System.out.println("----------------------------------------------");
			System.out.println("선택>");

			int selectNo = scanner.nextInt();

			switch (selectNo) {
			case 1:
				System.out.println("학생수>");
				studentNum = scanner.nextInt();
				// 학생수를 입력받음
				// 입력한 학생수 만큼 배열크기를 초기화
				break;
			case 2:
				scores = new int[studentNum];
				for (int i = 0; i < studentNum; i++) {
					System.out.println("scores[" + i + "]");
					scores[i] = scanner.nextInt();
				}
				// 배열크기만큼 반복해서 점수를 입력 받음
				break;
			case 3:
				for (int i = 0; i < studentNum; i++) {
					System.out.println("scores[" + i + "]= " + scores[i]);
				}
				// 배열크기만큼 반복해서 점수 목록을 출력
				break;
			case 4:
				int sum = 0;
				for (int score : scores) {
					sum += score;
				}
				System.out.println("최고 점수:" + sum);
				System.out.println("평균점수:" + sum * 1.0 / scores.length);
				// 최고점수와 평균점수 출력
				break;
			case 5:
				System.out.println("선택>");
				// run = false로 반복문 탈출
				run = false;
				break;
			}
		}
		System.out.println("프로그램 종료");

	}
}
