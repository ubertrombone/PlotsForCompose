package com.joshrose.plotsforcompose.axis.config.guidelines

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.theme.md_theme_dark_onBackground

/**
 * This class defines all available guidelines properties.
 *
 * @property lineColor the color of each guideline.
 * @property alpha the alpha to apply to the guidelines. This property must be of type [Multiplier].
 * To hide guidelines, set the axis config property showGuidelines to false.
 * @property strokeWidth the width of the lines.
 * @property pathEffect the pathEffect to apply to the guidelines.
 * @property padding the padding to be applied to the end of the guidelines closest to the axis.
 * @constructor Creates a configuration for axis guidelines.
 */
data class GuidelinesConfiguration(
    var lineColor: Color = md_theme_dark_onBackground,
    var alpha: Multiplier = Multiplier(.1f),
    var strokeWidth: Float = 2f,
    var pathEffect: PathEffect? = null,
    var padding: Float = 25f
) {
    override fun toString() = "GuidelinesConfiguration"
}

fun guidelinesConfiguration(init: GuidelinesConfiguration.() -> Unit = {}) = GuidelinesConfiguration().apply(init)