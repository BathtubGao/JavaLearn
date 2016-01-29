package thread;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 	java 并发编程学习：ReentrantLock
 * 	参考：http://www.cnblogs.com/dolphin0520/p/3923167.html
 * Bathtub 2016-01-29
 */
public class ReentrantLockDemo
{
	private ArrayList<Integer> arrayList = new ArrayList<Integer>();
	private Lock lock = new ReentrantLock();
	
	public static void main(String[] args)
	{
		final ReentrantLockDemo test = new ReentrantLockDemo();
		
		new Thread() {
			public void run() 
			{
				test.insert(Thread.currentThread());
			}
		}.start();
		
		new Thread() {
			public void run() 
			{
				test.insert(Thread.currentThread());
			}
		}.start();
	}
	
	public void insert(Thread thread)
	{
//		Lock lock = new ReentrantLock();
		lock.lock();
		try
		{
			System.out.println(thread.getName() + "得到了锁");
			for (int i = 0;i < 5; i++)
			{
				arrayList.add(i);
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
		} finally
		{
			System.out.println(thread.getName() + "释放了锁");
			lock.unlock();
		}
		
	}
}
