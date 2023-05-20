package com.joshrose.plotsforcompose.axis.x.util

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfigurationDefaults
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.random.Random
import kotlin.test.assertEquals

class DSXExtensionsKtTest {
    private val random = Random
    private val size = Size(width = 100f, height = 100f)
    private val config = LabelsConfigurationDefaults.labelsConfigurationDefault().copy(
        axisOffset = random.nextInt(0, 50).dp,
        rotation = random.nextInt(-90, 90).toFloat()
    )
    private val labelSize =
        Size(width = random.nextInt(50, 100).toFloat(), height = random.nextInt(10, 30).toFloat())

    @Test
    fun testLabelYPosition() {
        val posList = listOf(Bottom, Center, Top)
        posList.forEach { xPos ->
            val y = getY(xPos)

            val offsetY =
                if (xPos == Top) y.minus(labelSize.height.div(2f)).minus(config.axisOffset.value)
                else y.plus(labelSize.height.div(2f)).plus(config.axisOffset.value)

            val xLabels = adjustXLabelCoordinates(
                x = 0f,
                y = offsetY,
                rotation = config.rotation,
                yOffset = labelSize.height,
                xOffset = labelSize.width
            )

            val offsetPoint =
                if (xPos == Top) xLabels.second.plus(labelSize.height) else xLabels.second

            assertEquals(config.axisOffset.value, abs(y.minus(offsetPoint)))
        }
    }

    @Test
    fun testLabelXPosition() {
        val rotation = config.rotation

        val xLabels = adjustXLabelCoordinates(
            y = 0f,
            x = size.width.div(2f),
            rotation = rotation,
            yOffset = labelSize.height,
            xOffset = labelSize.width
        )

        val expectedValue = when {
            rotation < 0f -> size.width.div(2f).minus(labelSize.width)
            rotation == 0f -> size.width.div(2f).minus(labelSize.width.div(2f))
            else -> size.width.div(2f)
        }

        assertEquals(expectedValue, xLabels.first)
    }

    private fun getY(xPos: XAxis) = when (xPos) {
        Top -> 0f
        Bottom -> size.height
        Center -> size.height.div(2f)
        else -> throw IllegalStateException("xAxisPosition must be of type AxisPosition.XAxis. Current state: $xPos")
    }
}