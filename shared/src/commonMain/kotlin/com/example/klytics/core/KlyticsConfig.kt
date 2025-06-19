package com.example.klytics.core

// Forward declaration for AnalyticsProvider, which will be defined later
interface AnalyticsProvider // Placeholder

/**
 * Configuration class for initializing the Klytics library.
 *
 * @property providers A list of [AnalyticsProvider] instances to be used for tracking events.
 * @property isDebugMode Indicates whether the library should operate in debug mode (e.g., more logging).
 * @property defaultEventProperties Default [EventProperties] to be merged with every event.
 * @property sessionTimeoutMillis Duration in milliseconds after which a new session is started.
 */
data class KlyticsConfig(
    val providers: List<AnalyticsProvider> = emptyList(),
    val isDebugMode: Boolean = false,
    val defaultEventProperties: EventProperties? = null,
    val sessionTimeoutMillis: Long = 30 * 60 * 1000 // Default to 30 minutes
) {
    /**
     * Builder class for constructing [KlyticsConfig] instances in a more readable way.
     */
    class Builder {
        private var providers: MutableList<AnalyticsProvider> = mutableListOf()
        private var isDebugMode: Boolean = false
        private var defaultEventProperties: EventProperties? = null
        private var sessionTimeoutMillis: Long = 30 * 60 * 1000 // Default to 30 minutes

        /**
         * Adds an [AnalyticsProvider] to the list of providers.
         */
        fun addProvider(provider: AnalyticsProvider): Builder = apply {
            this.providers.add(provider)
        }

        /**
         * Adds multiple [AnalyticsProvider] instances to the list of providers.
         */
        fun addProviders(providers: List<AnalyticsProvider>): Builder = apply {
            this.providers.addAll(providers)
        }

        /**
         * Sets the debug mode for the library.
         * @param isDebug True to enable debug mode, false otherwise.
         */
        fun debug(isDebug: Boolean): Builder = apply {
            this.isDebugMode = isDebug
        }

        /**
         * Sets the default [EventProperties] to be merged with every event.
         */
        fun defaultProperties(properties: EventProperties): Builder = apply {
            this.defaultEventProperties = properties
        }

        /**
         * Sets the session timeout duration.
         * @param millis The duration in milliseconds.
         */
        fun sessionTimeout(millis: Long): Builder = apply {
            this.sessionTimeoutMillis = millis
        }

        /**
         * Builds the [KlyticsConfig] instance.
         */
        fun build(): KlyticsConfig = KlyticsConfig(
            providers = providers,
            isDebugMode = isDebugMode,
            defaultEventProperties = defaultEventProperties,
            sessionTimeoutMillis = sessionTimeoutMillis
        )
    }
}

// Example of how a top-level Klytics object might look later (not part of this file yet)
/*
object Klytics {
    private var currentConfig: KlyticsConfig? = null
    private val eventQueue: EventQueue = InMemoryEventQueue() // Assuming we have one

    fun configure(block: KlyticsConfig.Builder.() -> Unit) {
        currentConfig = KlyticsConfig.Builder().apply(block).build()
        // Initialize providers, etc.
    }

    fun track(event: AnalyticsEvent) {
        if (currentConfig == null) {
            // Log error or throw exception: Klytics not configured
            return
        }
        // Add to queue, process with providers, etc.
        // CoroutineScope(Dispatchers.Default).launch {
        //     eventQueue.enqueue(event)
        // }
    }
}
*/
