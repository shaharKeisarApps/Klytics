package com.example.klytics.core

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Interface for an event queue that stores analytics events.
 */
interface EventQueue {
    /**
     * Adds an event to the queue.
     * @param event The [AnalyticsEvent] to add.
     * @return True if the event was added successfully (typically always true for unbounded in-memory queue).
     */
    suspend fun enqueue(event: AnalyticsEvent): Boolean

    /**
     * Retrieves and removes the next event from the queue.
     * @return The next [AnalyticsEvent], or null if the queue is empty.
     */
    suspend fun dequeue(): AnalyticsEvent?

    /**
     * Retrieves, but does not remove, the next event from the queue.
     * @return The next [AnalyticsEvent], or null if the queue is empty.
     */
    suspend fun peek(): AnalyticsEvent?

    /**
     * Gets the current number of events in the queue.
     * @return The size of the queue.
     */
    suspend fun size(): Int

    /**
     * Checks if the queue is empty.
     * @return True if the queue is empty, false otherwise.
     */
    suspend fun isEmpty(): Boolean

    /**
     * Clears all events from the queue.
     */
    suspend fun clear()
}

/**
 * A basic in-memory implementation of the [EventQueue] using a [MutableList] protected by a [Mutex].
 * This implementation is thread-safe.
 *
 * @param capacity The maximum capacity of the queue. If -1, queue is unbounded. (Not strictly enforced in this basic version yet)
 */
class InMemoryEventQueue(private val capacity: Int = -1) : EventQueue {
    private val queue = mutableListOf<AnalyticsEvent>()
    private val mutex = Mutex()

    override suspend fun enqueue(event: AnalyticsEvent): Boolean {
        mutex.withLock {
            if (capacity == -1 || queue.size < capacity) {
                queue.add(event)
                return true
            }
            return false // Capacity reached
        }
    }

    override suspend fun dequeue(): AnalyticsEvent? {
        return mutex.withLock {
            if (queue.isNotEmpty()) {
                queue.removeAt(0)
            } else {
                null
            }
        }
    }

    override suspend fun peek(): AnalyticsEvent? {
        return mutex.withLock {
            queue.firstOrNull()
        }
    }

    override suspend fun size(): Int {
        return mutex.withLock {
            queue.size
        }
    }

    override suspend fun isEmpty(): Boolean {
        return mutex.withLock {
            queue.isEmpty()
        }
    }

    override suspend fun clear() {
        mutex.withLock {
            queue.clear()
        }
    }
}
