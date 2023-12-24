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
import kotlinx.coroutines.launch

@Composable
fun PSJF(){
    val composableScope = rememberCoroutineScope()
    var avgFullTime by remember { mutableStateOf("") }
    var avgTime by remember { mutableStateOf("") }
    var avgReactiveTime by remember { mutableStateOf("") }
    var processName by remember { mutableStateOf(1) }
    var processTime by remember { mutableStateOf("") }
    var processQuant = 1
    val processes = PSJFImitator.processes.collectAsState()

    Column(modifier = Modifier.fillMaxSize()){
        Row(Modifier.weight(0.9f)) {
            Column(Modifier.weight(0.3f)) {
                Text("PSJF")
                TextField(label = { Text("Длительность процесса") },value = processTime, onValueChange = {
                    processTime = it
                })

                BeautyButton(onClick = {
                    PSJFImitator.addProcess("p${processName++}", processTime.toInt())
                    val process = PSJFImitator.processes.value.values.toList()
                    avgFullTime = calculateFullTime(process).toString()
                    avgTime = calculateTime(process).toString()
                    avgReactiveTime = calculateReactiveTime(process).toString()
                }){
                    Text("Добавить процесс")
                }
                BeautyButton(onClick = {
                    composableScope.launch{
                        PSJFImitator.startCalculating()
                    }
                }){
                    Text("Запустить процессы")
                }
                Text("Среднее время в системе: ${avgFullTime}")
                Text("Среднее потерянное время: $avgTime")
                Text("Среднее отношение реактивности: $avgReactiveTime")
            }
            LazyHorizontalScrollable(processes.value, Modifier.weight(0.7f).padding(10.dp))
        }
    }
}

