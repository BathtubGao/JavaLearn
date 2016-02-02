package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * java 并发编程学习：lockInterruptibly
 * 参考：http://www.cnblogs.com/dolphin0520/p/3923167.html 
 * Bathtub  2016-02-01
 */
public class LockInterruptiblyDemo
{
	private Lock lock = new ReentrantLock();
	
	public static void main(String[] args)
	{
		LockInterruptiblyDemo test = new LockInterruptiblyDemo();
		MyThread thread1 = test.new MyThread(test);
		MyThread thread2 = test.new MyThread(test);
		thread1.start();
		thread2.start();
		
		try
		{
			Thread.sleep(2000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		thread2.interrupt();
	}
	
	public void insert(Thread thread) throws InterruptedException 
	{
		lock.lockInterruptibly();//注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将InterruptedException抛出
		try
		{
			long startTime = System.currentTimeMillis();
			for(;;)
			{
				System.out.println("业务操作");
				if(System.currentTimeMillis() - startTime >= 50)
					break;
			}
		}
		finally
		{
			System.out.println(Thread.currentThread().getName() + "执行finally");
			lock.unlock();
			System.out.println(thread.getName() + "释放了锁");
		}
	}
	
	class MyThread extends Thread
	{
		private LockInterruptiblyDemo test = null;
		
		public MyThread(LockInterruptiblyDemo test)
		{
			this.test = test;
		}
		
		public void run() 
		{
			try
			{
				test.insert(Thread.currentThread());
			}
			catch (InterruptedException e)
			{
				System.out.println(Thread.currentThread().getName() + "被中断");
			}
		}
	}
}
