import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface ProcessImitator{
    fun addProcess(name: String, duration: Int)
    fun clearProcesses()
}

object RoundRobinImitator: ProcessImitator {
    private val _processes = MutableStateFlow<Map<String, Int>>(emptyMap())
    val avgFullTime = MutableStateFlow(0.0)
    val avgTime = MutableStateFlow(0.0)
    val avgReactiveTime = MutableStateFlow(0.0)
    val processes = _processes.asStateFlow()

    override fun addProcess(name: String, duration: Int) {
        val newMap = processes.value.plus(Pair(name, duration))
        _processes.value =  newMap
    }

    override fun clearProcesses() {
        _processes.value = emptyMap()
    }

    suspend fun startCalculating(quant: Int){
        val sysTime = mutableMapOf<String, Int>()
        val start = _processes.value.toMutableMap()
        while (_processes.value.isNotEmpty()){
            val entities = _processes.value.toMutableMap()
            val firstKey = _processes.value.keys.first()
            for (i in 1..quant){
                delay(1000)
                for (j in entities){
                    try {
                        sysTime[j.key] = sysTime[j.key]!!.plus(1)
                    } catch (e: Exception){
                        sysTime[j.key] = 1
                    }
                }
                if (entities[firstKey] != null){
                    if (entities[firstKey] != 0){
                        entities[firstKey] = entities[firstKey]!! - 1
                    } else {
                        entities.remove(firstKey)
                    }
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
            _processes.value = entities
        }
        var result = 0
        var reactiveResult = 0.0
        for (i in sysTime){
            result += i.value-start[i.key]!!
            reactiveResult += start[i.key]!!/i.value.toDouble()
        }
        println(sysTime)
        avgTime.emit(result/sysTime.size.toDouble())
        avgFullTime.emit(sysTime.values.sum()/sysTime.size.toDouble())
        avgReactiveTime.emit(reactiveResult/sysTime.size.toDouble())
    }

}

object RRSJFImitator: ProcessImitator {
    private val _processes = MutableStateFlow<Map<String, Int>>(emptyMap())
    val avgFullTime = MutableStateFlow(0.0)
    val avgTime = MutableStateFlow(0.0)
    val avgReactiveTime = MutableStateFlow(0.0)
    val processes = _processes.asStateFlow()

    override fun addProcess(name: String, duration: Int) {
        val newMap = processes.value.plus(Pair(name, duration)).toList().sortedBy { it.second }.toMap()
        _processes.value =  newMap
    }

    override fun clearProcesses() {
        _processes.value = emptyMap()
    }

    suspend fun startCalculating(quant: Int){
        val sysTime = mutableMapOf<String, Int>()
        val start = _processes.value.toMutableMap()
        while (_processes.value.isNotEmpty()){
            val entities = _processes.value.toMutableMap()
            val firstKey = _processes.value.keys.first()
            for (i in 1..quant){
                delay(1000)
                for (j in entities){
                    try {
                        sysTime[j.key] = sysTime[j.key]!!.plus(1)
                    } catch (e: Exception){
                        sysTime[j.key] = 1
                    }
                }
                if (entities[firstKey] != null){
                    if (entities[firstKey] != 0){
                        entities[firstKey] = entities[firstKey]!! - 1
                    } else {
                        entities.remove(firstKey)
                    }
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
            _processes.value = entities
        }
        var result = 0
        var reactiveResult = 0.0
        for (i in sysTime){
            result += i.value-start[i.key]!!
            reactiveResult += start[i.key]!!/i.value.toDouble()
        }
        println(sysTime)
        avgTime.emit(result/sysTime.size.toDouble())
        avgFullTime.emit(sysTime.values.sum()/sysTime.size.toDouble())
        avgReactiveTime.emit(reactiveResult/sysTime.size.toDouble())
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