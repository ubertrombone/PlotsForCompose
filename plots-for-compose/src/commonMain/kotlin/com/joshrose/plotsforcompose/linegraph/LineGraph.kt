package com.joshrose.plotsforcompose.linegraph

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration.Companion.xConfiguration
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration.Companion.yConfiguration
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration.XConfiguration
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration.YConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration.Companion.guidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration.Companion.labelsConfiguration
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*
import com.joshrose.plotsforcompose.axis.util.floatLabels
import com.joshrose.plotsforcompose.common.maxValue
import com.joshrose.plotsforcompose.common.minValue
import com.joshrose.plotsforcompose.common.range
import com.joshrose.plotsforcompose.exception.InvalidRangeException
import com.joshrose.plotsforcompose.linegraph.config.LineGraphConfig
import com.joshrose.plotsforcompose.linegraph.config.LineGraphConfigDefaults
import com.joshrose.plotsforcompose.linegraph.model.NumberData
import com.joshrose.plotsforcompose.linegraph.model.NumberData.Companion.maxXValue
import com.joshrose.plotsforcompose.linegraph.model.NumberData.Companion.maxYValue
import com.joshrose.plotsforcompose.linegraph.model.NumberData.Companion.minXValue
import com.joshrose.plotsforcompose.linegraph.model.NumberData.Companion.minYValue
import com.joshrose.plotsforcompose.util.LoadingState
import com.joshrose.plotsforcompose.util.LoadingState.*

//@Composable
//fun LineGraph(
//    data: List<StringData>,
//    modifier: Modifier = Modifier,
//    xAxisConfig: ContinuousAxisConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults(),
//    yAxisConfig: ContinuousAxisConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults(),
//    lineGraphConfig: LineGraphConfig = LineGraphConfigDefaults.lineGraphConfigDefaults(),
//) {
//
//}

@ExperimentalTextApi
@Composable
fun LineGraph(
    realData: Map<String, Any> = emptyMap(),
    data: List<NumberData>,
    modifier: Modifier = Modifier,
    xLabelConfiguration: LabelsConfiguration = labelsConfiguration(),
    xGuidelinesConfiguration: GuidelinesConfiguration = guidelinesConfiguration(),
    xAxisLineConfiguration: XConfiguration = xConfiguration(),
    yLabelConfiguration: LabelsConfiguration = labelsConfiguration(),
    yGuidelinesConfiguration: GuidelinesConfiguration = guidelinesConfiguration(),
    yAxisLineConfiguration: YConfiguration = yConfiguration(),
    lineGraphConfig: LineGraphConfig = LineGraphConfigDefaults.lineGraphConfigDefaults(),
) {
    var state by remember { mutableStateOf<LoadingState>(Success) }
    val yTextMeasurer = rememberTextMeasurer()
    val xTextMeasurer = rememberTextMeasurer()

    if (data.isEmpty()) state = Empty

    val minXValue by remember { derivedStateOf { data.minXValue() } }
    val maxXValue by remember { derivedStateOf { data.maxXValue() } }
    val maxYValue by remember { derivedStateOf { maxValue(data.maxYValue(), yLabelConfiguration.maxValueAdjustment) } }
    val minYValue by remember { derivedStateOf { minValue(data.minYValue(), maxYValue, yLabelConfiguration.minValueAdjustment) } }
    val yRange by remember { derivedStateOf { range(minYValue, maxYValue, yLabelConfiguration.rangeAdjustment) } }

    var yLabels by remember { mutableStateOf(listOf(0f, 100f)) }
    try {
        yLabels = floatLabels(
            breaks = yLabelConfiguration.breaks,
            minValue = minYValue,
            maxValue = maxYValue
        )
    } catch (e: InvalidRangeException) { state = Error }

    val xLabels by remember { mutableStateOf(data.map { it.x }) }

    val xAxisPosition = xAxisLineConfiguration.axisPosition ?: when {
        maxYValue <= 0 -> Top
        minYValue < 0 -> Center
        else -> Bottom
    }

    val yAxisPosition = yAxisLineConfiguration.axisPosition ?: when {
        maxXValue <= 0 -> End
        minXValue < 0 -> Center
        else -> Start
    }

//    val plot = composePlot(data = realData) {
//        x = realData["X"]
//        y = realData["Y"]
//    }
//        .plus(unboundXAxis(
//            config = xAxisConfig,
//            breaks = xLabels,
//            labels = xLabels.map { it.toString() },
//            naValue = Float.NaN,
//            reverse = true
//        ))
//        .plus(unboundXAxis(config = xAxisConfig))
//        .plus(plotSize(width = 20.dp, height = 20.dp))
//
//    println(plot)
//    println(plot.scales())
//    plot.show()

    Canvas(modifier = modifier) {
//        if (yAxisConfig.showAxis) {
//            unboundYAxis(
//                config = yAxisConfig,
//                labels = yLabels.reversed(),
//                range = yRange,
//                yRangeValues = Range(min = minYValue, max = maxYValue),
//                yAxisPosition = yAxisPosition,
//                xAxisPosition = xAxisPosition,
//                drawXAxis = false,
//                drawZero = true,
//                textMeasurer = yTextMeasurer
//            )
//        }
//
//        if (xAxisConfig.showAxis) {
//            boundXAxis(
//                config = xAxisConfig,
//                labels = xLabels,
//                xAxisPosition = xAxisPosition,
//                yAxisPosition = yAxisPosition,
//                drawYAxis = yAxisConfig.showAxis && yAxisConfig.showAxisLine,
//                axisAlignment = xAxisConfig.axisLine.axisAlignment,
//                textMeasurer = xTextMeasurer
//            )
//        }
    }
}