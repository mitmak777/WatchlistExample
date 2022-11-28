package com.example.watchlistexample.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import java.math.BigDecimal

@Composable
fun WatchlistScreen() {
    Surface {
        Column {
            SummaryView()
            WatchlistHeaderCellView()
            LazyColumn {

                items(20) {

                    WatchlistItemView()
                }

            }
        }
    }

}

@Preview
@Composable
fun WatchlistScreenPreview(){
    WatchlistScreen()
}


@Composable
fun SummaryViewItem(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .then(modifier)) {
        val (title1, value1, title2, value2) = createRefs()
        Text(
            overflow = TextOverflow.Ellipsis,
            text = "Label 1:",
            modifier = Modifier.constrainAs(title1) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(value1.start)
                bottom.linkTo(value1.bottom)
                width = Dimension.preferredWrapContent
            }
        )
        Text(
            text = "Value 1",
            modifier = Modifier.constrainAs(value1) {
                top.linkTo(title1.top)
                start.linkTo(title1.end)
                end.linkTo(parent.end)
                bottom.linkTo(title1.bottom)
                width = Dimension.preferredWrapContent
            }
        )
        Text(
            text = "Label 2:",
            modifier = Modifier.constrainAs(title2) {
                top.linkTo(title1.bottom)
                start.linkTo(parent.start)
                width = Dimension.preferredWrapContent
            }
        )
        Text(
            text = "Value 2",
            modifier = Modifier.constrainAs(value2) {
                top.linkTo(title2.top)
                start.linkTo(value1.start)
                end.linkTo(parent.end)
                width = Dimension.preferredWrapContent
            }
        )

        createHorizontalChain(
            title1, value1,
            chainStyle = ChainStyle.SpreadInside
        )
        createHorizontalChain(
            title2, value2,
            chainStyle = ChainStyle.SpreadInside
        )
    }

}


@Preview(showBackground = true)
@Composable
fun SummaryView() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .height(40.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(Color.Blue)) {
        Box(modifier = Modifier
            .weight(1f)
            .background(Color.Red)) {
            SummaryViewItem()
        }
        Divider(modifier = Modifier
            .padding(4.dp)
            .fillMaxHeight()
            .width(1.dp)

            .background(Color.Gray))
        Box(modifier = Modifier
            .weight(1f)
            .background(Color.Green)) {
            SummaryViewItem()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SummaryViewItemPreview() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(Color.White)) {
        SummaryViewItem()
    }
}


@Preview(showBackground = true)
@Composable
fun SummaryViewPreview2() {
    Card(shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.Blue)) {
        Row(modifier = Modifier.height(150.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .background(Color.Red)) {

            }
            Divider(modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .padding(4.dp)
                .background(Color.Gray))
            Column(modifier = Modifier
                .weight(1f)
                .background(Color.Green)) {

            }
        }
    }
}


@Preview(showBackground = false)
@Composable
fun WatchlistHeaderCellView() {
    Column {
        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.White)) {
            Text(modifier = Modifier
                .weight(0.25f)
                .fillMaxHeight(), textAlign = TextAlign.Center, color = Color.Black,text = "Symbol")
            Text(modifier = Modifier
                .weight(0.25f)
                .fillMaxHeight(), textAlign = TextAlign.Center, color = Color.Black,text = "Change")
            Text(modifier = Modifier
                .weight(0.25f)
                .fillMaxHeight(), textAlign = TextAlign.Center, color = Color.Black,text = "Sell")
            Text(modifier = Modifier
                .weight(0.25f)
                .fillMaxHeight(), textAlign = TextAlign.Center, color = Color.Black,text = "Buy")
        }
        Divider(Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray))
    }
}

@Preview(showBackground = true)
@Composable
fun WatchlistItemView(item: WatchlistItem = WatchlistItem("AAPL", BigDecimal(10.0), BigDecimal(11.0), BigDecimal(11.0), BigDecimal(10.0))) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.White)) {
            Text(modifier = Modifier
                .weight(0.25f)
                .fillMaxHeight(), textAlign = TextAlign.Center, color = Color.Black, text = item.symbol)
            Text(modifier = Modifier
                .weight(0.25f)
                .fillMaxHeight(), textAlign = TextAlign.Center, color = Color.Black, text = item.getChange().toString())
            Text(modifier = Modifier
                .weight(0.25f)
                .fillMaxHeight(), textAlign = TextAlign.Center, color = Color.Black, text = item.sell.toString())
            Text(modifier = Modifier
                .weight(0.25f)
                .fillMaxHeight(), textAlign = TextAlign.Center, color = Color.Black, text = item.buy.toString())
        }
    }
}

data class WatchlistItem(val symbol: String, val prev: BigDecimal, val sell: BigDecimal, val buy: BigDecimal, val price: BigDecimal)

fun WatchlistItem.getChange(): BigDecimal {
    return (price - prev) / prev
}