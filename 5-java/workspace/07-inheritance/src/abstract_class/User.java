package abstract_class;

// 사용자 - 자식객체의 구조만 나타내는 클래스
// 자식객체의 기본 필드, 매서드와 추가 구현해야되는 메서드의 뼈대만 제공
// public abstract class 클래스명
public class User {
	private String id;
	private String name;
	private String phone;

	// final 메서드 : 재정의 불가능한 메서드
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