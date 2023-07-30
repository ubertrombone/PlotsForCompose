package com.joshrose.common.components.linegraph.data

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.joshrose.common.components.linegraph.models.DataValues
import kotlinx.coroutines.*
import kotlin.random.Random

class DataModelImpl(initialState: DataValues) : InstanceKeeper.Instance, DataModel {
    override val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override val dataValues = MutableValue(initialState)
    private val initState = initialState

    override fun updateData() {
        scope.launch {
            val rand = Random.nextInt(from = 1, until = 4)
            dataValues.update {
                when (rand) {
                    1 -> DataValues(
                        mapOf(
                            "Independent" to List(20) { (0..20).random() },
                            "Dependent" to List(20) { (0..20).random() }
                        )
                    )
                    2 -> DataValues(
                        mapOf(
                            "Independent" to List(20) { (-20..20).random() },
                            "Dependent" to List(20) { (-20..20).random() }
                        )
                    )
                    else -> DataValues(
                        mapOf(
                            "Independent" to List(20) { (-20..0).random() },
                            "Dependent" to List(20) { (-20..0).random() }
                        )
                    )
                }
            }
        }
    }

    override fun resetData() {
        scope.launch {
            dataValues.update { initState }
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}