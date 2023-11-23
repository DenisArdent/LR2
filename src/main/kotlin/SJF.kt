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
    val composableScope = rememberCoroutineScope()
    var processName by remember { mutableStateOf("") }
    var processTime by remember { mutableStateOf("") }
    val processes = SJFImitator.processes.collectAsState()

    Column(modifier = Modifier.fillMaxSize()){
        Text("SJF")
        Row(Modifier.weight(0.9f)) {
            LazyHorizontalScrollable(processes.value, Modifier.weight(0.7f).padding(10.dp))
            Column(Modifier.weight(0.3f)) {
                TextField(label = {Text("Имя процесса")}, value = processName, onValueChange = {
                    processName = it
                })
                TextField(label = {Text("Длительность процесса")},value = processTime, onValueChange = {
                    processTime = it
                })

                Button(onClick = {
                    SJFImitator.addProcess(processName, processTime.toInt())
                }){
                    Text("Добавить процесс")
                }
                Button(onClick = {
                    SJFImitator.clearProcesses()
                }){
                    Text("Сброс процессов")
                }
            }
        }
    }
}