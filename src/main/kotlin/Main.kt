import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.loadXmlImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(

        title = "Лабораторная работа #2",
        onCloseRequest = ::exitApplication,
    ) {
        App()
    }
}

@Composable
fun App() {
    val screens = Screen.values().toList()
    val navController by rememberNavController(Screen.RR.name)
    val currentScreen by remember {
        navController.currentScreen
    }

    MaterialTheme {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            NavigationRail(backgroundColor = Color.LightGray) {
                screens.forEach {
                    NavigationRailItem(
                        selected = currentScreen == it.name,
                        icon = {
                            Icon(
                                painter = painterResource("process-svgrepo-com.svg"),
                                contentDescription = it.label
                            )
                        },
                        label = {
                            Text(it.label)
                        },
                        alwaysShowLabel = false,
                        onClick = {
                            navController.navigate(it.name)
                        }
                    )
                }
            }


                // This is how you can use
            CustomNavigationHost(navController = navController)
        }
    }
}

@Composable
fun BeautyButton(onClick: () -> Unit, content: @Composable() (RowScope.() -> Unit)){
    Button(onClick, content = content)
}

@Composable
fun CustomNavigationHost(
    navController: NavController
) {
    NavigationHost(navController) {
        composable(Screen.RR.name) {
            RoundRobin()
        }

        composable(Screen.SJF.name) {
            SJF()
        }

        composable(Screen.PSJF.name) {
            PSJF()
        }

        composable(Screen.RRSJF.name) {
            RRSJF()
        }

    }.build()
}

/**
 * NavigationHost class
 */
class NavigationHost(
    val navController: NavController,
    val contents: @Composable NavigationGraphBuilder.() -> Unit
) {

    @Composable
    fun build() {
        NavigationGraphBuilder().renderContents()
    }

    inner class NavigationGraphBuilder(
        val navController: NavController = this@NavigationHost.navController
    ) {
        @Composable
        fun renderContents() {
            this@NavigationHost.contents(this)
        }
    }
}


/**
 * Composable to build the Navigation Host
 */
@Composable
fun NavigationHost.NavigationGraphBuilder.composable(
    route: String,
    content: @Composable () -> Unit
) {
    if (navController.currentScreen.value == route) {
        content()
    }
}

enum class Screen(
    val label: String,
) {
    RR(
        label = "RR",
    ),
    SJF(
        label = "SJF",
    ),
    PSJF(
        label = "PSJF",
    ),
    RRSJF(
        label = "RRSJF",
    )
}
