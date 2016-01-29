package thread;

import java.util.concurrent.Semaphore;

/**
 * 	java 并发编程学习：Semaphore
 * 	参考：http://www.cnblogs.com/dolphin0520/p/3920397.html
 * Bathtub 2016-01-29
 */
public class SemaphoreDemo
{
	public static void main(String[] args)
	{
		int N = 8;
		Semaphore semaphore = new Semaphore(5);
		for (int i = 0;i < N; i++)
		{
			new Worker(i,semaphore).start();
		}
	}
	
	static class Worker extends Thread 
	{
		private int num;
		private Semaphore semaphore;
		public Worker(int num,Semaphore semaphore)
		{
			this.num = num;
			this.semaphore = semaphore;
		}
		
		public void run() 
		{
			try
			{
				semaphore.acquire();
				System.out.println("工人" + this.num + "占用一个机器在生产。。。");
				Thread.sleep(2000);
				System.out.println("工人" + this.num + "释放出机器");
				semaphore.release();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
