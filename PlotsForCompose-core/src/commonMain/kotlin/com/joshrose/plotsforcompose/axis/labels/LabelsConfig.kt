package com.joshrose.plotsforcompose.axis.labels

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.axis.util.Multiplier
import com.joshrose.plotsforcompose.theme.Typography
import com.joshrose.plotsforcompose.theme.md_theme_dark_primary
import com.joshrose.plotsforcompose.theme.md_theme_light_primary

/**
 * This class defines all available label properties.
 *
 * @property fontColor the [Color] of the labels.
 * @property textStyle the [TextStyle] of the labels.
 * @property xOffset the amount in [Dp] to offset each label along the x-axis.
 * @property yOffset the amount in [Dp] to offset each label along the y-axis.
 * @property rotation the number of degrees to rotate each label.
 * @property breaks the number of labels to apply to the axis.
 * @property isBaseZero if true, the axis' minimum value will be 0.
 * @property minValueAdjustment the amount as a [Multiplier] to adjust the given data's minimum value.
 * @property maxValueAdjustment the amount as a [Multiplier] to adjust the given data's maximum value.
 * @property rangeAdjustment the amount as a [Multiplier] to adjust the given data's range and
 * offset labels from the adjacent axis.
 * @constructor Creates a configuration for axis labels.
 */
data class LabelsConfig(
    val fontColor: Color,
    val textStyle: TextStyle,
    val xOffset: Dp,
    val yOffset: Dp,
    val rotation: Int,
    val breaks: Int,
    val isBaseZero: Boolean,
    val minValueAdjustment: Multiplier,
    val maxValueAdjustment: Multiplier,
    val rangeAdjustment: Multiplier
)

/** Contains default values used for implementations of [LabelsConfig] */
object LabelsConfigDefaults {

    /**
     * Creates a [LabelsConfig] for basic label implementations.
     *
     * @param isDarkTheme true if labels should be a dark theme.
     * @param fontColor label font color.
     * @param textStyle style of label text. To use this default style but update one or more of the properties, use the copy method.
     * @param xOffset value to offset each label along the x-axis.
     * @param yOffset value to offset each label along the y-axis.
     * @param rotation degrees to rotate the labels. When rotated, the axis will automatically
     *  center y-axis labels according to their bottom right point and
     *  x-axis labels according to their top left point (from 0 degrees).
     *  Using negatives values is recommended when rotating y labels.
     *  @param breaks the number of labels to apply to the axis.
     *  @param isBaseZero true if axis should start at 0. Defaults to false.
     *  @param minValueAdjustment the amount as a [Multiplier] to adjust the given data's minimum value.
     *  @param maxValueAdjustment the amount as a [Multiplier] to adjust the given data's maximum value.
     *  @param rangeAdjustment the amount as a [Multiplier] to adjust the given data's range and
     *  offset labels from the adjacent axis.
     *  @return the resulting [LabelsConfig] to be used with an axis configuration.
     */
    fun labelsConfigDefaults(
        isDarkTheme: Boolean,
        fontColor: Color = md_theme_dark_primary,
        textStyle: TextStyle = Typography.headlineSmall,
        xOffset: Dp = 0.dp,
        yOffset: Dp = 0.dp,
        rotation: Int = 0,
        breaks: Int = 5,
        isBaseZero: Boolean = false,
        minValueAdjustment: Multiplier = Multiplier(factor = 0f),
        maxValueAdjustment: Multiplier = Multiplier(factor = 0f),
        rangeAdjustment: Multiplier = Multiplier(factor = 0f)
    ): LabelsConfig =
        LabelsConfig(
            fontColor = if (isDarkTheme) fontColor else md_theme_light_primary,
            textStyle = textStyle,
            xOffset = xOffset,
            yOffset = yOffset,
            rotation = rotation,
            breaks = breaks,
            isBaseZero = isBaseZero,
            minValueAdjustment = minValueAdjustment,
            maxValueAdjustment = maxValueAdjustment,
            rangeAdjustment = rangeAdjustment
        )
}
