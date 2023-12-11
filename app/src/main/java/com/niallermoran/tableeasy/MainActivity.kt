package com.niallermoran.tableeasy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.niallermoran.tableeasy.ui.theme.TableEasyTheme
import java.util.Date
import kotlin.random.Random

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
                    Table()
                }
            }
        }
    }
}

@Composable
fun Table() {

    val fields = listOf(
        TableField(
            name = "date",
            title = "Date"
        ),
        TableField(
            name = "title",
            title = "Title"
        ),
        TableField(
            name = "description",
            title = "Description"
        )
        ,
        TableField(
            name = "count",
            title = "Count"
        )
    )
    
    val rowCount = 200
    val data = ArrayList<MyData>(rowCount)
    val epoch = Date().time
    for( i in 1..rowCount)
    {
        val randomDayCount = Random.nextLong(86400000, 8640000010)
        val randomCount = Random.nextInt(10, 200)
        val date = Date(epoch - randomDayCount)
        val title = "Title for $date"
        val description = "Description $date"


        data.add( MyData(
            date = date,
            count = randomCount,
            title = title,
            description = description
        ))
    }

    Table<MyData>(
        fields = fields,
        data = data,
        onCellFormat = { item, field ->
            when (field.name) {
                "date" -> item.date.toString()
                "count" -> item.count.toString()
                "title" -> item.title
                "description" -> item.description
                else -> ""
            }
        },
        showGridLines = true,
        headerBackgroundColor = Color.Cyan,
        alternatingRowBackgroundColor = Color.LightGray,
        rowBackgroundColor = Color.White
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TableEasyTheme {
        Table()
    }
}

data class MyData(val date: Date, val title:String, val description: String, val count:Int)