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
fun RRSJF() {
    val composableScope = rememberCoroutineScope()
    var processName by remember { mutableStateOf(1) }
    val avgFullTime = RRSJFImitator.avgFullTime.collectAsState()
    val avgTime = RRSJFImitator.avgTime.collectAsState()
    val avgReactiveTime = RRSJFImitator.avgReactiveTime.collectAsState()
    var processTime by remember { mutableStateOf("") }
    var processQuant by remember { mutableStateOf("1") }
    val processes = RRSJFImitator.processes.collectAsState()

    Column(modifier = Modifier.fillMaxSize()){
        Text("RRSJF")
        Row(Modifier.weight(0.9f)) {
            Column(Modifier.weight(0.3f)) {
                TextField(label = { Text("Квант времени") }, value = processQuant, onValueChange = {
                    processQuant = it
                })
                TextField(label = { Text("Длительность процесса") },value = processTime, onValueChange = {
                    processTime = it
                })
                BeautyButton(onClick = {
                    RRSJFImitator.addProcess("P${processName++}", processTime.toInt())
                }){
                    Text("Добавить процесс")
                }
                BeautyButton(onClick = {
                    composableScope.launch{
                        RRSJFImitator.startCalculating(processQuant.toInt())
                    }
                }){
                    Text("Запустить процессы")
                }
                Text("Среднее время в системе: ${avgFullTime.value}")
                Text("Среднее потерянное время: ${avgTime.value}")
                Text("Среднее отношение реактивности: ${avgReactiveTime.value}")
            }
            LazyHorizontalScrollableForRR(processes.value, Modifier.weight(0.7f).padding(10.dp))

        }
    }
}