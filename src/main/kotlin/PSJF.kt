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
    var processName by remember { mutableStateOf("") }
    var processTime by remember { mutableStateOf("") }
    var processQuant by remember { mutableStateOf("") }
    val processes = PSJFImitator.processes.collectAsState()

    Column(modifier = Modifier.fillMaxSize()){
        Row(Modifier.weight(0.9f)) {
            Text("PSJF")
            LazyHorizontalScrollable(processes.value, Modifier.weight(0.7f).padding(10.dp))
            Column(Modifier.weight(0.3f)) {
                TextField(label = { Text("Квант времени") }, value = processQuant, onValueChange = {
                    processQuant = it
                })
                TextField(label = { Text("Имя процесса") }, value = processName, onValueChange = {
                    processName = it
                })
                TextField(label = { Text("Длительность процесса") },value = processTime, onValueChange = {
                    processTime = it
                })

                Button(onClick = {
                    PSJFImitator.addProcess(processName, processTime.toInt())
                }){
                    Text("Добавить процесс")
                }
                Button(onClick = {
                    composableScope.launch{
                        PSJFImitator.startCalculating()
                    }
                }){
                    Text("Запустить процессы")
                }
            }
        }
    }
}