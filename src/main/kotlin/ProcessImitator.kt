import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.naming.Name
import kotlin.coroutines.CoroutineContext

interface ProcessImitator{
    fun addProcess(name: String, duration: Int)
    fun clearProcesses()
}

object RoundRobinImitator: ProcessImitator {
    private val _processes = MutableStateFlow<Map<String, Int>>(emptyMap())
    val processes = _processes.asStateFlow()

    override fun addProcess(name: String, duration: Int) {
        val newMap = processes.value.plus(Pair(name, duration))
        _processes.value =  newMap
    }

    override fun clearProcesses() {
        _processes.value = emptyMap()
    }

    suspend fun startCalculating(quant: Int){
        while (_processes.value.isNotEmpty()){
            val entities = _processes.value.toMutableMap()
            val firstKey = _processes.value.keys.first()
            for (i in 1..quant){
                delay(1000)
                if (entities[firstKey] != 0){
                    entities[firstKey] = entities[firstKey]!! - 1
                } else {
                    entities.remove(firstKey)
                }
            }
            try {
                val firstValue = entities[firstKey]!!
                if (firstValue != 0) {
                    entities.remove(firstKey)
                    entities.put(firstKey, firstValue)
                } else {
                    entities.remove(firstKey)
                }
            } catch (e: Exception){

            }
            println(entities)
            _processes.value = entities
        }
    }

}

object RRSJFImitator: ProcessImitator {
    private val _processes = MutableStateFlow<Map<String, Int>>(emptyMap())
    val processes = _processes.asStateFlow()

    override fun addProcess(name: String, duration: Int) {
        val newMap = processes.value.plus(Pair(name, duration)).toList().sortedBy { it.second }.toMap()
        _processes.value =  newMap
    }

    override fun clearProcesses() {
        _processes.value = emptyMap()
    }

    suspend fun startCalculating(quant: Int){
        while (_processes.value.isNotEmpty()){
            val entities = _processes.value.toMutableMap()
            val firstKey = _processes.value.keys.first()
            for (i in 1..quant){
                delay(1000)
                if (entities[firstKey] != 0){
                    entities[firstKey] = entities[firstKey]!! - 1
                } else {
                    entities.remove(firstKey)
                }
            }
            try {
                val firstValue = entities[firstKey]!!
                if (firstValue != 0) {
                    entities.remove(firstKey)
                    entities.put(firstKey, firstValue)
                } else {
                    entities.remove(firstKey)
                }
            } catch (e: Exception){

            }
            println(entities)
            _processes.value = entities
        }
    }

}

object PSJFImitator: ProcessImitator {
    private val _processes = MutableStateFlow<Map<String, Int>>(emptyMap())
    val processes = _processes.asStateFlow()

    override fun addProcess(name: String, duration: Int) {
        val newMap = processes.value.plus(Pair(name, duration)).toList().sortedBy { it.second }.toMap()
        _processes.value =  newMap
    }

    override fun clearProcesses() {
        _processes.value = emptyMap()
    }

    suspend fun startCalculating(){

        while (_processes.value.isNotEmpty()){

            delay(1000)
            val entities = _processes.value.toMutableMap()
            val firstKey = _processes.value.keys.first()
            if (entities[firstKey] != 0){
                entities[firstKey] = entities[firstKey]!! - 1
            } else {
                entities.remove(firstKey)
            }
            val firstValue = entities[firstKey]!!
            if (firstValue == 0) {
                entities.remove(firstKey)
            }
            println(entities)
            _processes.value = entities
        }
    }

}

object SJFImitator: ProcessImitator{
    private val _processes = MutableStateFlow<Map<String, Int>>(emptyMap())
    val processes = _processes.asStateFlow()


    override fun addProcess(name: String, duration: Int){
        val newMap = processes.value.plus(Pair(name, duration)).toList().sortedBy { it.second }.toMap()
        _processes.value =  newMap
    }

    override fun clearProcesses(){
        _processes.value = emptyMap()
    }
}