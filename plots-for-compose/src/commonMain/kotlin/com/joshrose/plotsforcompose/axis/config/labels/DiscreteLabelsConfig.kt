package com.joshrose.plotsforcompose.axis.config.labels

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.theme.Typography
import com.joshrose.plotsforcompose.theme.md_theme_dark_primary

/**
 * This class defines all available discrete label properties.
 *
 * @property fontColor the [Color] of the labels.
 * @property textStyle the [TextStyle] of the labels.
 * @property axisOffset value to offset each label from the respective axis.
 * @property rotation the number of degrees to rotate each label.
 * @constructor Creates a configuration for axis labels.
 */
data class DiscreteLabelsConfig(
    val fontColor: Color,
    val textStyle: TextStyle,
    val axisOffset: Dp,
    val rotation: Float
)

/** Contains default values used for implementations of [DiscreteLabelsConfig] */
object DiscreteLabelsConfigDefaults {

    /**
     * Creates a [DiscreteLabelsConfig] for basic discrete label implementations.
     *
     * @param fontColor label font color.
     * @param textStyle style of label text. To use this default style but update one or more of the properties, use the copy method.
     * @param axisOffset value to offset each label from the respective axis.
     * @param rotation degrees to rotate the labels. When rotated, the axis will automatically
     *  center y-axis labels according to their bottom right point and
     *  x-axis labels according to their top left point (from 0 degrees).
     *  @return the resulting [DiscreteLabelsConfig] to be used with an axis configuration.
     */
    fun discreteLabelsConfigDefaults(
        fontColor: Color = md_theme_dark_primary,
        textStyle: TextStyle = Typography.labelMedium,
        axisOffset: Dp = 0.dp,
        rotation: Float = 0f
    ): DiscreteLabelsConfig =
        DiscreteLabelsConfig(
            fontColor = fontColor,
            textStyle = textStyle,
            axisOffset = axisOffset,
            rotation = rotation
        )
}
