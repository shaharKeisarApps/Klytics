package com.example.klytics.core

/**
 * Base sealed class for all analytics events, ensuring type safety.
 *
 * @property name The name of the event.
 * @property properties Common properties associated with this event.
 */
sealed class AnalyticsEvent(
    open val name: String,
    open val properties: EventProperties? = null
)

/**
 * Represents a screen view event.
 *
 * @param screenName The name of the screen viewed.
 * @param screenClass The class name of the screen component (optional).
 * @param properties Additional common properties.
 */
data class ScreenViewEvent(
    val screenName: String,
    val screenClass: String? = null,
    override val properties: EventProperties? = null
) : AnalyticsEvent(name = "screen_view", properties = properties)

/**
 * Represents a user action event.
 *
 * @param actionName A descriptive name for the user action (e.g., "button_click", "item_select").
 * @param category The category of the action (e.g., "engagement", "conversion").
 * @param label An optional label for the action (e.g., "cta_button", "product_sku").
 * @param value An optional numerical value associated with the action.
 * @param actionProperties Specific properties related to this user action.
 * @param properties Additional common properties.
 */
data class UserActionEvent(
    val actionName: String,
    val category: String? = null,
    val label: String? = null,
    val value: Long? = null,
    val actionProperties: Map<String, Any>? = null, // Specific to this action
    override val properties: EventProperties? = null
) : AnalyticsEvent(name = actionName, properties = properties)

/**
 * Represents an error event.
 *
 * @param errorType The type or class of the error (e.g., "network_error", "NullPointerException").
 * @param message A descriptive message for the error.
 * @param stackTrace The stack trace of the error, if available.
 * @param isFatal Indicates if the error was fatal.
 * @param properties Additional common properties.
 */
data class ErrorEvent(
    val errorType: String,
    val message: String?,
    val stackTrace: String? = null,
    val isFatal: Boolean = false,
    override val properties: EventProperties? = null
) : AnalyticsEvent(name = "error_occurred", properties = properties)

/**
 * Represents a generic custom event for flexibility.
 *
 * @param eventName The unique name for this custom event.
 * @param customData A map of key-value pairs specific to this custom event.
 * @param properties Additional common properties.
 */
data class CustomEvent(
    val eventName: String, // This will override the base 'name'
    val customData: Map<String, Any>,
    override val properties: EventProperties? = null
) : AnalyticsEvent(name = eventName, properties = properties)
