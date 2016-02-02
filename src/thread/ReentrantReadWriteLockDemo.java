package thread;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * java 并发编程学习：ReentrantReadWriteLock
 * 参考：http://www.cnblogs.com/dolphin0520/p/3923167.html Bathtub
 * 2016-02-01
 */
public class ReentrantReadWriteLockDemo
{
	private ReentrantReadWriteLock rw1 = new ReentrantReadWriteLock();
	
	public static void main(String[] args)
	{
		final ReentrantReadWriteLockDemo test = new ReentrantReadWriteLockDemo();
		
		new Thread() {
			public void run()
			{
				test.get(Thread.currentThread());
			}
		}.start();
		
		new Thread() {
			public void run()
			{
				test.get(Thread.currentThread());
			}
		}.start();	
	}
	
	public void get(Thread thread)
	{
		rw1.readLock().lock();
		try 
		{
			long start = System.currentTimeMillis();
			while(System.currentTimeMillis() - start <= 1)
			{
				System.out.println(thread.getName() + "正在进行读操作");
			}
			System.out.println(thread.getName() + "读操作完毕");
		}
		finally
		{
			rw1.readLock().unlock();
		}
	}
}
