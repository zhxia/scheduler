package com.zhxia.quartz.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TaskQueue {
	private Queue<Task> queue;

	public TaskQueue() {
		queue = new ConcurrentLinkedQueue<Task>();
	}

	public boolean enQueue(Task task) {
		return queue.add(task);
	}

	public Task deQueue() {
		return queue.poll();
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}
}
