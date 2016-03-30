package thread.Interrupt;

import java.util.Timer;
import java.util.TimerTask;

/**
 * interrupt 的使用
 * http://www.blogjava.net/fhtdy2004/archive/2009/08/22/292181.html
 */
public class CheckInterrupt
{
	static class CanStop extends Thread
	{
		private int counter = 0;

		public void run()
		{
			boolean done = false;
			try
			{
				// 设置成100比主线程的500要小
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			while (counter < 100000 && !done)
			{
				System.out.println(counter++);
				// 在主线程中调用stoppable.interrupt()之前为false,假如之后没有调用Thread.interrupted()则一直为true,
				// 否则为第一次为true,调用Thread.interrupted之后为false
				System.out.println("in thread stoppable.isInterrupted() " + isInterrupted());
				
				//在主线程中调用stoppable.interrupt()之前为false,之后只有第一个会显示为true,之后全为false
				//System.out.println("stoppable.isInterrupted() " + Thread.interrupted());
				
				if(Thread.interrupted() == true)
				{
					try
					{
						//Thread.interrupted()会清除中断标志位,显然这里面只会调用一次
						System.out.println("in thread after Thread.interrupted() " + isInterrupted());
						sleep(10000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		final CanStop stoppable = new CanStop();
		stoppable.start();
		new Timer(true).schedule(new TimerTask()
		{
			public void run()
			{
				System.out.println("Requesting Interrupt");
				//不会中断正在执行的线程,原因是因为interrupt()方法只设置中断状态标志位为true
				stoppable.interrupt();
				System.out.println("in timer stoppable.isInterrupted() "+stoppable.isInterrupted());
			}
		},500);
	}
}
