package exercise;

public class MySqlDao implements DataAccessObject {

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		System.out.println("MySql DB에서 삭제");
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		System.out.println("MySql DB를 수정");
	}

	@Override
	public void insert() {
		// TODO Auto-generated method stub
		System.out.println("MySql DB에 삽입");
	}

	@Override
	public void select() {
		// TODO Auto-generated method stub
		System.out.println("MySql DB에서 검색");
	}

	@Override
	public String dbWork() {
		// TODO Auto-generated method stub
		return "";
	}

}