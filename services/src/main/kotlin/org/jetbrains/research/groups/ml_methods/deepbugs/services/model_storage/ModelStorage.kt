package org.jetbrains.research.groups.ml_methods.deepbugs.services.model_storage

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.intellij.openapi.application.PathManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.ProjectManager
import org.jetbrains.research.groups.ml_methods.deepbugs.services.downloader.DownloadClient
import org.jetbrains.research.groups.ml_methods.deepbugs.services.utils.DeepBugsServicesBundle
import org.jetbrains.research.groups.ml_methods.deepbugs.services.utils.Mapping
import org.tensorflow.SavedModelBundle
import org.tensorflow.Session
import java.nio.file.Files
import java.nio.file.Paths

class ModelStorage(private val pluginName: String) {
    private val modelPath = Paths.get(PathManager.getPluginsPath(), pluginName, "models").toString()

    var nodeTypeMapping: Mapping? = null
        private set
    var typeMapping: Mapping? = null
        private set
    var operatorMapping: Mapping? = null
        private set
    var tokenMapping: Mapping? = null
        private set
    var binOperandModel: Session? = null
        private set
    var binOperatorModel: Session? = null
        private set
    var swappedArgsModel: Session? = null
        private set

    init {
        val client = DownloadClient(pluginName) { initModels() }
        client.checkRepos()
    }

    private fun initModels() {
        ProgressManager.getInstance().run(object : Task.Backgroundable(ProjectManager.getInstance().defaultProject,
            DeepBugsServicesBundle.message("initialize.task.title", pluginName), false) {
            override fun run(indicator: ProgressIndicator) {
                indicator.isIndeterminate = true
                binOperandModel = loadModel("binOperandDetectionModel", indicator)
                binOperatorModel = loadModel("binOperatorDetectionModel", indicator)
                swappedArgsModel = loadModel("swappedArgsDetectionModel", indicator)
                nodeTypeMapping = loadMapping("nodeTypeToVector.json", indicator)
                typeMapping = loadMapping("typeToVector.json", indicator)
                operatorMapping = loadMapping("operatorToVector.json", indicator)
                tokenMapping = loadMapping("tokenToVector.json", indicator)
            }
        })
    }

    private fun loadMapping(mappingName: String, progress: ProgressIndicator): Mapping? {
        val loadMappingPath = Paths.get(modelPath, mappingName)
        if (Files.exists(loadMappingPath)) {
            progress.text = DeepBugsServicesBundle.message("init.model.file", mappingName)
            return Mapping(Parser().parse(loadMappingPath.toString()) as JsonObject)
        }
        return null
    }

    private fun loadModel(modelName: String, progress: ProgressIndicator): Session? {
        val loadModelPath = Paths.get(modelPath, modelName)
        if (Files.exists(loadModelPath)) {
            progress.text = DeepBugsServicesBundle.message("init.model.file", modelName)
            return SavedModelBundle.load(loadModelPath.toString(), "serve").session()
        }
        return null
    }
}