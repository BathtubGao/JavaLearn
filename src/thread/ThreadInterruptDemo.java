package thread;

/**
 * 	java 并发编程学习：Thread  nterrupt
 * 	参考：http://www.cnblogs.com/dolphin0520/p/3923737.html
 * Bathtub 2016-01-29
 */
public class ThreadInterruptDemo
{
	public static void main(String[] args)
	{
		ThreadInterruptDemo test = new ThreadInterruptDemo();
		MyThread thread = test.new MyThread();
		thread.start();
		try
		{
			Thread.currentThread().sleep(2000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		thread.interrupt();
	}
	
	class MyThread extends Thread 
	{
		public void run() 
		{
			int i = 0;
			while(!isInterrupted() && i < Integer.MAX_VALUE)
			{
				System.out.println(i + " while循环");
				i ++;
			}
		}
	}
}
