package com.example.watchlistexample.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.watchlistexample.ui.viewmodel.ForexWatchlistViewModel
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun WatchlistScreen(viewModel: ForexWatchlistViewModel = hiltViewModel()) {
    val list = viewModel.fxListStateFlow.collectAsState()
    val equity = viewModel.equityFlow.collectAsState(null)
    val balance = viewModel.balanceFlow.collectAsState(null)
    val margin = viewModel.marginFlow.collectAsState(null)
    val usedValue = viewModel.usedValueFlow.collectAsState(null)
    val isLoading = viewModel.isLoading.collectAsState(false)
    val isError = viewModel.isError.collectAsState(false)
    Surface {
        Column(modifier = Modifier.background(Color(0xFF05033D))) {
            SummaryView(equity.value, balance.value, margin.value, usedValue.value)
            WatchlistHeaderView()
            Box(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()) {
                if (isLoading.value) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (isError.value) {
                    Button(onClick = {
                        viewModel.retryFetching()
                    }, modifier = Modifier.align(Alignment.Center)) {
                        Text(text = "Retry")
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxHeight()) {
                        items(list.value.size, key = { index -> list.value.get(index).symbol }) { index ->
                            WatchlistItemView(list.value.get(index))
                        }
                    }
                }
            }


        }
    }

}

@Preview
@Composable
fun WatchlistScreenPreview() {
    WatchlistScreen()
}


@Composable
fun SummaryViewItem(modifier: Modifier = Modifier,
    title1: String = "",
    title2: String = "",
    value1: String = "",
    value2: String = "") {
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .then(modifier)) {
        val (title1Ref, value1Ref, title2Ref, value2Ref) = createRefs()
        Text(
            overflow = TextOverflow.Ellipsis,
            text = title1,
            color = Color(0xFF0694B8),
            modifier = Modifier.constrainAs(title1Ref) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(value1Ref.start)
                bottom.linkTo(value1Ref.bottom)
                width = Dimension.preferredWrapContent
            }
        )
        Text(
            text = value1,
            color = Color.White,
            modifier = Modifier.constrainAs(value1Ref) {
                top.linkTo(title1Ref.top)
                start.linkTo(title1Ref.end)
                end.linkTo(parent.end)
                bottom.linkTo(title1Ref.bottom)
                width = Dimension.preferredWrapContent
            }
        )
        Text(
            text = title2,
            color = Color(0xFF0694B8),
            modifier = Modifier.constrainAs(title2Ref) {
                top.linkTo(title1Ref.bottom)
                start.linkTo(parent.start)
                width = Dimension.preferredWrapContent
            }
        )
        Text(
            text = value2,
            color = Color.White,
            modifier = Modifier.constrainAs(value2Ref) {
                top.linkTo(title2Ref.top)
                start.linkTo(value1Ref.start)
                end.linkTo(parent.end)
                width = Dimension.preferredWrapContent
            }
        )

        createHorizontalChain(
            title1Ref, value1Ref,
            chainStyle = ChainStyle.SpreadInside
        )
        createHorizontalChain(
            title2Ref, value2Ref,
            chainStyle = ChainStyle.SpreadInside
        )
    }

}


@Preview(showBackground = true)
@Composable
fun SummaryView(
    equity: BigDecimal? = null,
    balance: BigDecimal? = null,
    margin: BigDecimal? = null,
    usedValue: BigDecimal? = null) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .height(65.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(Color(0xFF274191))
    ) {
        Box(modifier = Modifier
            .weight(1f)
        ) {
            SummaryViewItem(title1 = "Equity", value1 = equity.toPriceString(),
                title2 = "Balance", value2 = balance.toPriceString())
        }
        Divider(modifier = Modifier
            .padding(4.dp)
            .fillMaxHeight()
            .width(1.dp)

            .background(Color.Gray))
        Box(modifier = Modifier
            .weight(1f)
        ) {
            SummaryViewItem(title1 = "Margin", value1 = margin.toPriceString(),
                title2 = "Used", value2 = usedValue.toPriceString())
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

@Preview(showBackground = false)
@Composable
fun WatchlistHeaderView() {
    Column {
        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)) {
            Text(modifier = Modifier
                .weight(0.25f)
                .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center, color = Color(0xFF0694B8), text = "Symbol")
            Text(modifier = Modifier
                .weight(0.25f)
                .align(Alignment.CenterVertically), textAlign = TextAlign.Center, color = Color(0xFF0694B8), text = "Change")
            Text(modifier = Modifier
                .weight(0.25f)
                .align(Alignment.CenterVertically), textAlign = TextAlign.Center, color = Color(0xFF0694B8), text = "Sell")
            Text(modifier = Modifier
                .weight(0.25f)
                .align(Alignment.CenterVertically), textAlign = TextAlign.Center, color = Color(0xFF0694B8), text = "Buy")
        }
        Divider(Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray))
    }
}

@Preview(showBackground = true)
@Composable
fun WatchlistItemView(item: WatchlistItem = WatchlistItem("EURUSD", BigDecimal(10.0), BigDecimal(11.0), BigDecimal(11.0), BigDecimal(10.0))) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(modifier = Modifier
                .weight(0.25f)
                .align(Alignment.CenterVertically), textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                text = item.symbol)
            Text(modifier = Modifier
                .weight(0.25f)
                .align(Alignment.CenterVertically), textAlign = TextAlign.Center, color =
            when (item.change.compareTo(BigDecimal.ZERO)) {
                -1 -> Color.Red
                else -> Color.Green
            }, text = "${item.change}%")
            Text(modifier = Modifier
                .weight(0.25f)
                .align(Alignment.CenterVertically), textAlign = TextAlign.Center, color = Color.White, text = item.sell.toFormatDecimal())

            Text(modifier = Modifier
                .weight(0.25f)
                .align(Alignment.CenterVertically), textAlign = TextAlign.Center, color = Color.White, text = item.buy.toFormatDecimal())

        }
        Divider(modifier = Modifier.background(Color.Gray))
    }

}

data class WatchlistItem(val symbol: String, val prev: BigDecimal, val sell: BigDecimal, val buy: BigDecimal, val price: BigDecimal) {
    val change: BigDecimal
        get() = ((price - prev) / prev).setScale(3, RoundingMode.CEILING)
}

fun BigDecimal?.toPriceString(textForEmpty: String = "-"): String {
    return this?.let {
        "\$${this.toFormatDecimal()}"
    } ?: textForEmpty

}

fun BigDecimal.toFormatDecimal(scale: Int = 2): String {
    return this.setScale(scale, RoundingMode.HALF_UP).toPlainString()
}
