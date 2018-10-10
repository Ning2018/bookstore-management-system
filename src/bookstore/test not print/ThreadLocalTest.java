package bookstore.test;

public class ThreadLocalTest implements Runnable {

	ThreadLocal<String> threadLocal = new ThreadLocal<>();
	String name;
			int i= 0;
			
	@Override
	public void run() {
		
		for(;i<10;i++) {
			name = Thread.currentThread().getName();
			    threadLocal.set(name);
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+": "+threadLocal.get());
			}
	}
	
	public static void main(String[] args) {
		
		ThreadLocalTest tlt= new ThreadLocalTest();
		new Thread(tlt, "Tom").start();
		new Thread(tlt, "Mot").start();
	}
}
