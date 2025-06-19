package com.example.klytics.core

/**
 * Represents common properties associated with an analytics event.
 *
 * @property userId A unique identifier for the user.
 * @property sessionId A unique identifier for the current session.
 * @property customProperties A map of additional custom key-value pairs.
 */
data class EventProperties(
    val userId: String? = null,
    val sessionId: String? = null,
    val customProperties: Map<String, Any>? = null
)
