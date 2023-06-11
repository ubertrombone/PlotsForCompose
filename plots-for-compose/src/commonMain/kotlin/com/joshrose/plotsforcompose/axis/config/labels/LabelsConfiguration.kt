package com.joshrose.plotsforcompose.axis.config.labels

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.theme.Typography
import com.joshrose.plotsforcompose.theme.md_theme_dark_primary

// TODO: Update all docs here
/**
 * This class defines all available continuous label properties.
 *
 * @property fontColor the [Color] of the labels.
 * @property textStyle the [TextStyle] of the labels.
 * @property axisOffset value to offset each label from the respective axis.
 * @property rotation the number of degrees to rotate each label.
 * @property breaks the number of labels to apply to the axis.
 * @property minValueAdjustment the amount as a [Multiplier] to adjust the given data's minimum value.
 * @property maxValueAdjustment the amount as a [Multiplier] to adjust the given data's maximum value.
 * @property rangeAdjustment the amount as a [Multiplier] to adjust the given data's range and
 * offset labels from the adjacent axis.
 * @property format a string pattern to format numeric labels. E.g., "#.##" -- "1.23"
 * @constructor Creates a configuration for axis labels.
 */
// TODO: Labels cannot be greater than breaks
class LabelsConfiguration(
    var showLabels: Boolean = true,
    var fontColor: Color = md_theme_dark_primary,
    var textStyle: TextStyle = Typography.labelMedium,
    var axisOffset: Dp = 0.dp,
    var rotation: Float = 0f,
    var breaks: Int = 5,
    var labels: Int = 5,
    var minValueAdjustment: Multiplier = Multiplier(0f),
    var maxValueAdjustment: Multiplier = Multiplier(0f),
    var rangeAdjustment: Multiplier = Multiplier(0f),
    var format: String = "#.##"
) {
    override fun toString() = "LabelsConfiguration"

    companion object {
        fun labelsConfiguration(init: LabelsConfiguration.() -> Unit = {}) =
            LabelsConfiguration().apply(init)
    }
}

/** Contains default values used for implementations of [LabelsConfiguration] */
object LabelsConfigurationDefaults {

    /**
     * Creates a [LabelsConfiguration] for basic continuous label implementations.
     *
     * @param fontColor label font color.
     * @param textStyle style of label text. To use this default style but update one or more of the properties, use the copy method.
     * @param axisOffset value to offset each label from the respective axis.
     * @param rotation degrees to rotate the labels. When rotated, the axis will automatically
     *  center y-axis labels according to their bottom right point and
     *  x-axis labels according to their top left point (from 0 degrees).
     *  @param breaks the number of labels to apply to the axis.
     *  @param minValueAdjustment the amount as a [Multiplier] to adjust the given data's minimum value.
     *  @param maxValueAdjustment the amount as a [Multiplier] to adjust the given data's maximum value.
     *  @param rangeAdjustment the amount as a [Multiplier] to adjust the given data's range and
     *  offset labels from the adjacent axis.
     *  @param format a string pattern to format numeric labels. E.g., "#.##" -- "1.23"
     *  @return the resulting [LabelsConfiguration] to be used with an axis configuration.
     */
    fun labelsConfigurationDefault(
        showLabels: Boolean = true,
        fontColor: Color = md_theme_dark_primary,
        textStyle: TextStyle = Typography.labelMedium,
        axisOffset: Dp = 0.dp,
        rotation: Float = 0f,
        breaks: Int = 5,
        labels: Int = 5,
        minValueAdjustment: Multiplier = Multiplier(factor = 0f),
        maxValueAdjustment: Multiplier = Multiplier(factor = 0f),
        rangeAdjustment: Multiplier = Multiplier(factor = 0f),
        format: String = "#.##"
    ): LabelsConfiguration =
        LabelsConfiguration(
            showLabels = showLabels,
            fontColor = fontColor,
            textStyle = textStyle,
            axisOffset = axisOffset,
            rotation = rotation,
            breaks = breaks,
            labels = labels,
            minValueAdjustment = minValueAdjustment,
            maxValueAdjustment = maxValueAdjustment,
            rangeAdjustment = rangeAdjustment,
            format = format
        )
}
