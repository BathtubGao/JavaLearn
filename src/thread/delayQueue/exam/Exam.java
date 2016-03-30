package thread.delayQueue.exam;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.DelayQueue;

/**
 * DelayQueue实际运用示例 http://www.cnblogs.com/sunzhenchao/p/3515085.html
 */
public class Exam
{
	public static void main(String[] args) throws InterruptedException
	{
		int studentNumber = 20;
		CountDownLatch countDownLatch = new CountDownLatch(studentNumber + 1);
		DelayQueue<Student> students = new DelayQueue<Student>();
		Random random = new Random();
		for(int i=0;i<studentNumber;i++)
		{
			students.put(new Student("student"+(i+1),30+random.nextInt(120),countDownLatch));
		}
		//Teacher线程对DelayQueue进行监控
		Thread teacherThread = new Thread(new Teacher(students));
		students.put(new EndExam(students,120,countDownLatch,teacherThread));
		teacherThread.start();
		countDownLatch.await();
		System.out.println("考试时间到，全部交卷");
	}
}
