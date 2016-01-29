package thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 	java 并发编程学习：CyclicBarrier
 * 	参考：http://www.cnblogs.com/dolphin0520/p/3920397.html
 * Bathtub 2016-01-29
 */
public class CyclicBarrierDemo
{
	static class Writer extends Thread 
	{
		private CyclicBarrier cyclicBarrier;
		public Writer(CyclicBarrier cyclicBarrier) 
		{
			this.cyclicBarrier = cyclicBarrier;
		}
		
		public void run() 
		{
			System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据。。。。");
			try
			{
				Thread.sleep(5000);
				System.out.println("线程" + Thread.currentThread().getName() + "写入数据完毕，等待其他线程写入完毕");
				cyclicBarrier.await();
//				cyclicBarrier.await(2000,TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			catch (BrokenBarrierException e)
			{
				e.printStackTrace();
			}
//			catch (TimeoutException e)
//			{
//				e.printStackTrace();
//			}
			System.out.println("所有线程写入完毕，继续处理其他任务...");
		}
	}
	
	public static void main(String[] args)
	{
		int N = 4;
//		CyclicBarrier barrier = new CyclicBarrier(N,new Runnable() {
//			@Override
//			public void run() 
//			{
//				System.out.println("当前进程" + Thread.currentThread().getName());
//			}
//		});
		
		CyclicBarrier barrier = new CyclicBarrier(N);
		
		for (int i = 0;i < N;i++)
		{
//			if (i < N-1)
//			{
//				new Writer(barrier).start();
//			}
//			else
//			{
//				try
//				{
//					Thread.sleep(5000);
//				}
//				catch (InterruptedException e)
//				{
//					e.printStackTrace();
//				}
//				new Writer(barrier).start();
//			}
			
			new Writer(barrier).start();
		}
		
//		try
//		{
//			Thread.sleep(25000);
//		}
//		catch (InterruptedException e)
//		{
//			e.printStackTrace();
//		}
		
		System.out.println("CyclicBarrier重用");
         
        for(int i=0;i<N;i++) 
        {
            new Writer(barrier).start();
        }
	}
}
