package thread;

/**
 * java 并发编程学习：ThreadLocal
 * 参考：http://www.cnblogs.com/dolphin0520/p/3920407.html Bathtub
 * 2016-02-15
 */

public class ThreadLocalDemo
{
	ThreadLocal<Long> longLocal = new ThreadLocal<Long>();

	ThreadLocal<String> stringLocal = new ThreadLocal<String>();

	public void set()
	{
		longLocal.set(Thread.currentThread().getId());
		stringLocal.set(Thread.currentThread().getName());
	}

	public long getLong()
	{
		return longLocal.get();
	}
	
	public String getString()
	{
		return stringLocal.get();
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		final ThreadLocalDemo test = new ThreadLocalDemo();
		test.set();
		System.out.println(test.getLong());
		System.out.println(test.getString());
		
		Thread thread1 = new Thread() {
			public void run()
			{
				test.set();
				System.out.println(test.getLong());
				System.out.println(test.getString());
			}
		};
		
		thread1.start();
		thread1.join();
		System.out.println(test.getLong());
		System.out.println(test.getString());
	}
}
