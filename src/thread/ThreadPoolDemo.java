package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * java 并发编程学习：线程池学习
 * 参考：http://www.cnblogs.com/dolphin0520/p/3932921.html
 * Bathtub 2016-02-17
 */
public class ThreadPoolDemo
{

	class MyTask implements Runnable 
	{
		private int taskNum;
		
		public MyTask(int num)
		{
			this.taskNum = num;
		}
		
		public void run()
		{
			System.out.println("正在执行task " + taskNum);
			try
			{
				Thread.currentThread().sleep(4000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			System.out.println("task " + taskNum + "执行完毕");
		}
	}
	
	public static void main(String[] args)
	{
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10,200,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(5));
		ThreadPoolDemo demo = new ThreadPoolDemo();
		for(int i=0;i<15;i++)
		{
			MyTask myTask = demo.new MyTask(i);
			executor.execute(myTask);
			System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" + executor.getQueue().size() +
					"，已执行完的任务数目：" + executor.getCompletedTaskCount());
		}
		executor.shutdown();
	}
}
