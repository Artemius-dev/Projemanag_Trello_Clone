package com.projemanag.testHelpers

import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicBoolean

class Executor private constructor(
    tasks: List<Runnable>,
    private val callback: Callback?
) : Thread() {
    private val workers: ConcurrentLinkedQueue<Worker>
    private val latch: CountDownLatch
    fun execute() {
        start()
    }

    override fun run() {
        while (true) {
            val worker: Worker = workers.poll() ?: break
            worker.start()
        }
        try {
            latch.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        callback?.onComplete()
    }

    class Builder {
        private val tasks: ArrayList<Runnable> = ArrayList()
        private var callback: Callback? = null
        fun add(task: Runnable?): Builder {
            if (task != null) {
                tasks.add(task)
            }
            return this
        }

        fun callback(callback: Callback?): Builder {
            this.callback = callback
            return this
        }

        fun build(): Executor {
            return Executor(tasks, callback)
        }
    }

    interface Callback {
        fun onComplete()
    }

    init {
        workers = ConcurrentLinkedQueue()
        latch = CountDownLatch(tasks.size)
        for (task in tasks) {
            workers.add(Worker(task, latch))
        }
    }
}

class Worker(task: Runnable, latch: CountDownLatch) : Runnable {
    private val started: AtomicBoolean
    private val task: Runnable
    private val thread: Thread
    private val latch: CountDownLatch
    fun start() {
        if (!started.getAndSet(true)) {
            thread.start()
        }
    }

    override fun run() {
        task.run()
        latch.countDown()
    }

    init {
        this.latch = latch
        this.task = task
        started = AtomicBoolean(false)
        thread = Thread(this)
    }
}