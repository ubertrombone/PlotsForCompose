package com.joshrose.plotsforcompose.axis.config.guidelines

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.theme.md_theme_dark_onBackground

/**
 * This class defines all available guidelines properties.
 *
 * @property lineColor the color of each guideline.
 * @property alpha the alpha to apply to the guidelines. This property must be of type [Multiplier].
 * To hide guidelines, set the axis config property showLabels to false.
 * @property strokeWidth the width of the lines.
 * @property pathEffect the pathEffect to apply to the guidelines.
 * @property padding the padding to be applied to the end of the guidelines closest to the axis.
 * @constructor Creates a configuration for axis guidelines.
 */
data class GuidelinesConfig(
    val lineColor: Color,
    val alpha: Multiplier,
    val strokeWidth: Dp,
    val pathEffect: PathEffect?,
    val padding: Dp
)

/** Contains default values used for implementations of [GuidelinesConfig] */
object GuidelinesConfigDefaults {

    /**
     * Creates a [GuidelinesConfig] for basic guideline implementations.
     *
     * @param lineColor the guideline color.
     * @param alpha the guideline alpha.
     * @param strokeWidth the guideline line width.
     * @param pathEffect the pathEffect to apply to the guidelines.
     * @param padding the padding to offset the start of the guideline from the axis.
     * @return the resulting [GuidelinesConfig] to be used with an axis configuration.
     */
    fun guidelinesConfigDefaults(
        lineColor: Color = md_theme_dark_onBackground,
        alpha: Multiplier = Multiplier(factor = .1f),
        strokeWidth: Dp = 2.dp,
        pathEffect: PathEffect? = null,
        padding: Dp = 25.dp
    ): GuidelinesConfig =
        GuidelinesConfig(
            lineColor = lineColor,
            alpha = alpha,
            strokeWidth = strokeWidth,
            pathEffect = pathEffect,
            padding = padding
        )
}
