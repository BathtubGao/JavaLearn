package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 	java 并发编程学习：CountDownLatch
 * 	参考：http://www.cnblogs.com/dolphin0520/p/3920397.html
 * Bathtub 2016-01-29
 */
public class CountDownLatchDemo
{
	public static void main(String[] args)
	{
		final CountDownLatch latch = new CountDownLatch(2);
		
		new Thread() 
		{
			public void run() 
			{
				try
				{
					System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
					Thread.sleep(5000);
					System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
					latch.countDown();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
		
		
		new Thread() 
		{
			public void run()
			{
				try
				{
					System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
					Thread.sleep(5000);
					System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
					latch.countDown();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				
				
			}
		}.start();
		
		try
		{
			System.out.println("等待2个子线程执行完毕。。。。");
			
			//latch.await(2000,TimeUnit.MILLISECONDS);
			//两个都实现时 latch.await();有效
			latch.await();
			System.out.println("2个子线程已经执行完毕");
			System.out.println("继续执行主线程");
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
