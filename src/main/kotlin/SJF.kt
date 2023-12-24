import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SJF(){
    var avgFullTime by remember { mutableStateOf("") }
    var avgTime by remember { mutableStateOf("") }
    var avgReactiveTime by remember { mutableStateOf("") }
    val composableScope = rememberCoroutineScope()
    var processName by remember { mutableStateOf(1) }
    var processTime by remember { mutableStateOf("") }
    val processes = SJFImitator.processes.collectAsState()

    Column(modifier = Modifier.fillMaxSize()){
        Text("SJF")
        Row(Modifier.weight(0.9f)) {
            Column(Modifier.weight(0.3f)) {
                TextField(label = {Text("Длительность процесса")},value = processTime, onValueChange = {
                    processTime = it
                })
                BeautyButton(onClick = {
                    SJFImitator.addProcess("p${processName++}", processTime.toInt())
                    val process = SJFImitator.processes.value.values.toList()
                    avgFullTime = calculateFullTime(process).toString()
                    avgTime = calculateTime(process).toString()
                    avgReactiveTime = calculateReactiveTime(process).toString()
                }){
                    Text("Добавить процесс")
                }
                BeautyButton(onClick = {
                    SJFImitator.clearProcesses()
                }){
                    Text("Сброс процессов")
                }
                Text("Среднее время в системе: ${avgFullTime}")
                Text("Среднее потерянное время: $avgTime")
                Text("Среднее отношение реактивности: $avgReactiveTime")
            }
            LazyHorizontalScrollable(processes.value, Modifier.weight(0.7f).padding(10.dp))
        }
    }
}


fun calculateFullTime(processes: List<Int>):Double{
    var avTime = 0.0
    for (i in processes.indices){
        avTime += processes.subList(0, i).sum()+processes[i]
    }
    return (avTime/(processes.size.toDouble()))
}
fun calculateTime(processes: List<Int>): Double{
    var avTime = 0.0
    for (i in processes.indices){
        avTime += processes.subList(0, i).sum()
    }
    avTime = avTime/(processes.size.toDouble())
    return avTime
}

fun calculateReactiveTime(processes: List<Int>): Double{
    var avTime = 0.0
    for (i in 1..processes.size-1){
        avTime += (processes[i].toDouble())/(processes.subList(0, i).sum()+processes[i])
    }
    avTime = avTime/((processes.size-1).toDouble())
    return avTime
}