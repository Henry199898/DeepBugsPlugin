package org.jetbrains.research.deepbugs.javascript

import com.intellij.openapi.components.*
import org.jetbrains.research.deepbugs.common.DeepBugsConfig
import org.jetbrains.research.deepbugs.common.DeepBugsConfigHandler

@State(name = "DeepBugsJS", storages = [Storage("deepbugs.js.xml")])
class JSDeepBugsConfig : PersistentStateComponent<DeepBugsConfig.State>, DeepBugsConfig(default) {
    companion object : DeepBugsConfigHandler() {
        override val instance: JSDeepBugsConfig by lazy { ServiceManager.getService(JSDeepBugsConfig::class.java) }

        override val default = State(
            binOperatorThreshold = 0.8f,
            binOperandThreshold = 0.8f,
            swappedArgsThreshold = 0.8f
        )
    }
}
