package org.jetbrains.research.groups.ml_methods.deepbugs.python.settings

import com.intellij.openapi.options.Configurable
import org.jetbrains.research.groups.ml_methods.deepbugs.services.logger.collectors.SettingsInfoCollector
import org.jetbrains.research.groups.ml_methods.deepbugs.python.utils.DeepBugsPythonService
import org.jetbrains.research.groups.ml_methods.deepbugs.services.settings.DeepBugsInspectionConfigurable
import org.jetbrains.research.groups.ml_methods.deepbugs.services.ui.DeepBugsUI
import org.jetbrains.research.groups.ml_methods.deepbugs.python.utils.DeepBugsPythonService.EVENT_LOG_PREFIX

class PyDeepBugsInspectionConfigurable(settings: PyDeepBugsInspectionConfig) : DeepBugsInspectionConfigurable(settings), Configurable {
    companion object {
        const val PY_DEFAULT_BIN_OPERATOR_CONFIG: Float = 0.94f
        const val PY_DEFAULT_BIN_OPERAND_CONFIG: Float = 0.95f
        const val PY_DEFAULT_SWAPPED_ARGS_CONFIG: Float = 0.96f
    }

    override fun logSettings() {
        return SettingsInfoCollector.logNewSettings(EVENT_LOG_PREFIX, settings, deepBugsUI!!)
    }

    override fun createUI(): DeepBugsUI {
        SettingsInfoCollector.settingsInvoked(EVENT_LOG_PREFIX)
        return PyDeepBugsUI()
    }

    override fun getDisplayName() = DeepBugsPythonService.PY_PLUGIN_NAME
}