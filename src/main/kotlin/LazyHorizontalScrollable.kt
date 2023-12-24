import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun LazyHorizontalScrollable(
    processes: Map<String, Int>,
    modifier: Modifier
) {
    Box(
        modifier = modifier
    ) {
        val state = rememberLazyListState()
        val composableScope = rememberCoroutineScope()

        LazyRow(
            Modifier.fillMaxSize()
                .padding(end = 12.dp)
                .background(Color.LightGray), state) {
            item{
                Column {
                        for (i in 0..processes.size){
                            if (i==0){
                                TableCell("Процессы", width = 100.dp)
                            } else{
                                TableCell(processes.keys.toList()[i-1], width = 100.dp)
                            }

                    }
                }
            }
            items(processes.values.size) { x ->
                for (i in 1..processes.values.toList()[x]){
                    Column{
                        if (x==0){
                            TableCell(text = "$i")
                        }else{
                            TableCell(text = "${i+processes.values.toList().subList(0,x).sum()}")
                        }
                        for (j in 0 until x){
                            TableCell(text = "")
                        }
                        for (j in x until processes.size){
                            if (j==x){
                                TableCell(text = "И")
                            } else{
                                TableCell(text = "Г")
                            }
                        }
                    }
                }
            }
        }

        HorizontalScrollbar(
            modifier = Modifier.align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(end = 12.dp),
            adapter = rememberScrollbarAdapter(
                scrollState = state
            )
        )
    }
}

@Composable
fun LazyHorizontalScrollableForRR(
    processes: Map<String, Int>,
    modifier: Modifier
) {
    Box(
        modifier = modifier
    ) {
        val state = rememberLazyListState()
        val composableScope = rememberCoroutineScope()

        LazyRow(
            Modifier.fillMaxSize()
                .padding(end = 12.dp)
                .background(Color.LightGray), state) {
            item{
                Column {
                    for (i in 0..processes.size){
                        if (i==0){
                            TableCell("Процессы", width = 100.dp)
                        } else{
                            TableCell(processes.keys.toList()[i-1], width = 100.dp)
                        }
                    }
                }
            }
            items(processes.values.size) { x ->
                for (i in 1..processes.values.toList()[x]){
                    val time = i+processes.values.toList().subList(0,x).sum()
                    Column{
                        if (x==0){
                            TableCell(text = "$i")
                        }else{
                            TableCell(text = "$time")
                        }
                        for (j in 0 until x){
                            TableCell(text = "")
                        }
                        for (j in x until processes.size){
                            if (i==1 && j==0){
                                TableCell(text = "И")
                            } else{
                                TableCell(text = "Г")
                            }
                        }
                    }
                }
            }
        }

        HorizontalScrollbar(
            modifier = Modifier.align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(end = 12.dp),
            adapter = rememberScrollbarAdapter(
                scrollState = state
            )
        )
    }
}

@Composable
fun ColumnScope.TableCell(
    text: String,
    width: Dp = 30.dp,
    height: Dp = 30.dp
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .background(Color.White)
            .size(width, height)
            .padding(8.dp),
        fontSize = 12.sp
    )
}

@Composable
fun RowScope.RowTableCell(
    text: String,
    width: Dp = 30.dp,
    height: Dp = 30.dp
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .background(Color.White)
            .size(width, height)
            .padding(8.dp),
        fontSize = 12.sp
    )
}