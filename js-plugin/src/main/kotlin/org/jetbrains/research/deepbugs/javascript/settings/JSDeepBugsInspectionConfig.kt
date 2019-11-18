package org.jetbrains.research.deepbugs.javascript.settings

import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil
import org.jetbrains.research.deepbugs.services.settings.DeepBugsInspectionConfig

//FIXME-review should not be it a global stored configuration?
@State(name = "deepbugs_js_config", storages = [Storage("deepbugs_js_config.xml")])
class JSDeepBugsInspectionConfig : PersistentStateComponent<JSDeepBugsInspectionConfig>, DeepBugsInspectionConfig {
    override val configId: String = "JSInspectionConfig"

    override var curBinOperatorThreshold = JSDeepBugsInspectionConfigurable.JS_DEFAULT_BIN_OPERATOR_CONFIG
    override var curBinOperandThreshold = JSDeepBugsInspectionConfigurable.JS_DEFAULT_BIN_OPERAND_CONFIG
    override var curSwappedArgsThreshold = JSDeepBugsInspectionConfigurable.JS_DEFAULT_SWAPPED_ARGS_CONFIG

    override fun getState(): JSDeepBugsInspectionConfig = this

    override fun loadState(state: JSDeepBugsInspectionConfig) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance(): JSDeepBugsInspectionConfig = ServiceManager.getService(JSDeepBugsInspectionConfig::class.java)
    }
}
