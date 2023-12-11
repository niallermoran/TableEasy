package com.niallermoran.tableeasy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Hashtable


@Composable
fun <T> Table(
    fields: List<TableField>,
    data: List<T>,
    tableCellPadding: PaddingValues = PaddingValues(12.dp),
    showGridLines: Boolean = true,
    headerBackgroundColor: Color = Color.LightGray,
    rowBackgroundColor: Color = Color.White,
    alternatingRowBackgroundColor: Color = Color.LightGray,
    headerTextStyle: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        textAlign = TextAlign.Left
    ),
    cellTextStyle: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        textAlign = TextAlign.Left
    ),
    onCellFormat: (item: T, field: TableField) -> String
) {

    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()

    // calculate column widths based
    val columnWidths = Hashtable<String, Dp>()
    fields.forEach { field ->

        columnWidths[field.name] = with(density) {
            textMeasurer.measure(
                text = field.title,
                style = headerTextStyle,
                overflow = TextOverflow.Visible,
                maxLines = 1
            ).size.width.toDp().plus(tableCellPadding.calculateLeftPadding(LayoutDirection.Ltr))
                .plus(tableCellPadding.calculateEndPadding(LayoutDirection.Ltr)).plus(10.dp)
        }

        data.forEach { item ->

            // get the text
            val text = onCellFormat(item, field)

            // measure text
            val width = with(density) {
                textMeasurer.measure(
                    text = text,
                    style = cellTextStyle,
                    overflow = TextOverflow.Visible,
                    maxLines = 1
                ).size.width.toDp().plus(tableCellPadding.calculateLeftPadding(LayoutDirection.Ltr))
                    .plus(tableCellPadding.calculateEndPadding(LayoutDirection.Ltr).plus(10.dp))
            }

            val maxWidth = columnWidths[field.name]
            if (maxWidth == null || width > maxWidth)
                columnWidths[field.name] = width

        }
    }

        LazyRow()
        {
            item() {
                LazyColumn(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {

                    // header row
                    item {

                        Row(
                            modifier = Modifier.height(IntrinsicSize.Min),
                            horizontalArrangement = Arrangement.Start
                        ) {

                            fields.forEach {

                                val width = columnWidths[it.name]

                                HeaderCell(
                                    text = it.title,
                                    padding = tableCellPadding,
                                    width = width,
                                    textStyle = headerTextStyle,
                                    background = headerBackgroundColor
                                )

                                if (showGridLines)
                                    Divider(
                                        color = Color.LightGray,
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(1.dp)
                                    )

                            }

                        }

                        if (showGridLines) {
                            Divider(
                                color = Color.LightGray,
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }

                    itemsIndexed(items = data)
                    { index, item: T ->

                        Row(
                            modifier = Modifier.height(IntrinsicSize.Min),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            fields.forEach {
                                val width = columnWidths[it.name]


                                TableCell(
                                    text = onCellFormat(item, it),
                                    padding = tableCellPadding,
                                    textStyle = cellTextStyle,
                                    width = width,
                                    alignment = TextAlign.Left,
                                    background = if (index % 2 == 0) rowBackgroundColor else alternatingRowBackgroundColor
                                )

                                if (showGridLines)
                                    Divider(
                                        color = Color.LightGray,
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(1.dp)
                                    )
                            }
                        }

                        if (showGridLines) {
                            Divider(
                                color = Color.LightGray,
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }

                }
            }
        }

}

@Composable
private fun TableCell(
    text: String,
    padding: PaddingValues,
    alignment: TextAlign = TextAlign.Center,
    width: Dp?,
    textStyle: TextStyle,
    background: Color
) {

    Box(modifier = Modifier
        .width(width ?: 300.dp)
        .background(background)) {

        Text(
            text = text,
            modifier = Modifier.padding(padding),
            textAlign = alignment,
            maxLines = 2,
            overflow = TextOverflow.Visible,
            style = textStyle
        )
    }
}

@Composable
private fun HeaderCell(
    text: String,
    padding: PaddingValues,
    alignment: TextAlign = TextAlign.Center,
    width: Dp? = 300.dp,
    textStyle: TextStyle,
    background: Color
) {
    Box(modifier = Modifier
        .width(width ?: 300.dp)
        .background(background)) {
        Text(
            text = text,
            modifier = Modifier.padding(padding),
            textAlign = alignment,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Visible,
            style = textStyle
        )
    }
}

data class TableField(val name: String, val title: String)