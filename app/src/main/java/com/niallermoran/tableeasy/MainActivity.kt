package com.niallermoran.tableeasy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.niallermoran.tableeasy.ui.theme.TableEasyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TableEasyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    val fields = listOf(
        TableField(
            name = "id",
            title = "ID"
        ),
        TableField(
            name = "description",
            title = "Description"
        )
    )

    val data = listOf(
        MyData(
            id = 1,
            description = "A short description"
        ),
        MyData(
            id=2,
            description = "This is a longer description"
        )
    )

    Table<MyData>(
        fields= fields,
        data = data,
        onCellFormat = { item, field ->
            when(field.name)
            {
                "id" -> item.id.toString()
                "description" -> item.description
                else -> ""
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TableEasyTheme {
        Greeting("Android")
    }
}

data class MyData(val id:Int, val description: String)