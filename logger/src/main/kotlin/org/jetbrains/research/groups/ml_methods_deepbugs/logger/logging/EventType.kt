package org.jetbrains.research.groups.ml_methods_deepbugs.logger.logging

import org.jetbrains.research.groups.ml_methods_deepbugs.logger.logging.events.BugReport
import org.jetbrains.research.groups.ml_methods_deepbugs.logger.logging.events.LogData
import org.jetbrains.research.groups.ml_methods_deepbugs.logger.logging.events.ThresholdConfigured

enum class EventType(val eventName: String, val dataClass: Class<out LogData>) {
    BUG_REPORT("BUG_REPORT", BugReport::class.java),
    THRESHOLD_CONFIGURED("THRESHOLD_CONFIGURED", ThresholdConfigured::class.java)
}