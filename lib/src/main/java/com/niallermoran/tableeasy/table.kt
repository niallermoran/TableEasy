package com.niallermoran.tableeasy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun <T> Table(fields:List<TableField>, data: List<T>, onCellFormat:(item:T, field:TableField)->String) {

    LazyColumn(modifier = Modifier.padding(8.dp)) {

        // header row
        item {

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){

                fields.forEach {
                    HeaderCell(
                        text = it.title
                    )
                }

            }

            Divider(
                color = Color.LightGray,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()
            )
        }
        
        itemsIndexed(items = data)
        {index, item:T->

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                fields.forEach {
                    TableCell(
                        text = onCellFormat( item, it)
                    )
                }
            }
        }

    }
}

@Composable
private fun TableCell(
    text: String,
    weight: Float = 1f,
    alignment: TextAlign = TextAlign.Center
) {

    Text(
        text = text,
        modifier = Modifier.padding(10.dp),
        textAlign = alignment
    )
}

@Composable
private fun HeaderCell(
    text: String,
    weight: Float = 1f,
    alignment: TextAlign = TextAlign.Center
) {
    Text(
        text = text,
        modifier = Modifier.padding(10.dp),
        textAlign = alignment,
        fontWeight = FontWeight.Bold
    )
}

data class TableField(val name: String, val title: String)