package callback;

public class Work implements DoHomeWork {

	public static void main(String arg[]) {
//		String que = "1+1=?";
//		RoomMate roommate = new RoomMate();
//		roommate.getAnswer(que, new Work());
		String homework="��x������0,sin(x)/x=?";
		new Work().ask(homework,new RoomMate());
	}

	public void doHomeWork(String question, String answer) {
		// TODO Auto-generated method stub
		System.out.println("��ҵ��");
		if (answer !=null) {
			System.out.println(question + answer);
		} else {
			System.out.println(question + "null");
		}
	}

	public void ask(final String homework, final RoomMate roomMate) {
		new Thread(new Runnable() {

			public void run() {
				roomMate.getAnswer(homework, Work.this);
			}
		}).start();
		goHome();
	}

	public void goHome() {
		System.out.println("�һؼ��ˡ������������ѣ�����д����ҵ");
	}
}
