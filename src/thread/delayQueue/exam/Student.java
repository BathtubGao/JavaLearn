package thread.delayQueue.exam;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Student implements Runnable, Delayed
{

	private String name;

	private long workTime;

	private long submitTime;

	private boolean isForce = false;

	private CountDownLatch countDownLatch;

	public Student()
	{
	}

	public Student(String name, long workTime, CountDownLatch countDownLatch)
	{
		this.name = name;
		this.workTime = workTime;
		this.submitTime = TimeUnit.NANOSECONDS.convert(workTime, TimeUnit.NANOSECONDS) + System.nanoTime();
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run()
	{
		if (isForce)
		{
			System.out.println(name + "交卷，希望用时" + workTime + "分钟，实际用时120分钟");
		}
		else
		{
			System.out.println(name + "交卷，希望用时" + workTime + "分钟，实际用时" + workTime + "分钟");
		}
		countDownLatch.countDown();
	}

	@Override
	public long getDelay(TimeUnit unit)
	{
		return unit.convert(submitTime - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	@Override
	public int compareTo(Delayed delayed)
	{
		if (delayed == null || !(delayed instanceof Student))
			return 1;
		if (delayed == this)
			return 0;
		Student s = (Student) delayed;
		if (this.workTime > s.workTime)
		{
			return 1;
		}
		else if (this.workTime == s.workTime)
		{
			return 0;
		}
		else
		{
			return -1;
		}
	}

	public boolean isForce()
	{
		return isForce;
	}

	public void setForce(boolean isForce)
	{
		this.isForce = isForce;
	}

}
