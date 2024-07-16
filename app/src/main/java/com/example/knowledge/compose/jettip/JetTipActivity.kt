@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.knowledge.compose.jettip

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.knowledge.compose.jettip.components.InputField
import com.example.knowledge.compose.jettip.util.calculateTotalPerPerson
import com.example.knowledge.compose.jettip.util.calculateTotalTip
import com.example.knowledge.compose.jettip.widgets.RoundIconButton
import com.example.knowledge.compose.theme.ColorAccent
import com.example.knowledge.compose.theme.ColorPrimary
import com.example.knowledge.compose.theme.ColorPrimaryDark
import com.example.knowledge.compose.theme.ColorSecondary
import com.example.knowledge.compose.theme.ColorSecondaryLight
import com.example.knowledge.compose.theme.KnowledgeTheme
import com.example.knowledge.compose.theme.Typography

class JetTipActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                TipCalculator()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    KnowledgeTheme {
        content()
    }
}

@Composable
fun TipCalculator() {
    Surface(color = ColorPrimaryDark) {
        Column(modifier = Modifier.padding(8.dp)) {
            MainContent()
        }
    }
}

@Preview
@Composable
fun TopHeader(totalPerPerson: Double = 0.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .height(125.dp),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, color = ColorSecondary),
        color = ColorSecondaryLight
    ) {
        Column(
            modifier = Modifier.padding(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Total Per Person",
                style = Typography.h5,
                color = ColorPrimary,
                fontFamily = FontFamily.Cursive
            )
            val total = "%.2f".format(totalPerPerson)
            Text(
                text = "\$$total",
                style = Typography.h4,
                color = ColorPrimary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun MainContent() {
    val splitByState = remember {
        mutableStateOf(1)
    }
    val tipAmountState = remember {
        mutableStateOf(0.0)
    }
    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }

    BillForm(
        splitByState = splitByState,
        tipAmountState = tipAmountState,
        totalPerPersonState = totalPerPersonState
    )
}

@Composable
fun BillForm(
    range: IntRange = 1..100,
    splitByState: MutableState<Int>,
    tipAmountState: MutableState<Double>,
    totalPerPersonState: MutableState<Double>,
) {

    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    var sliderPositionState by remember {
        mutableStateOf(0f)
    }
    val tipPercentage = (sliderPositionState * 100).toInt()

    TopHeader(totalPerPerson = totalPerPersonState.value)

    Surface(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, color = ColorAccent),
        color = ColorPrimary
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Bill Input
            InputField(
                valueState = totalBillState,
                labelId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    totalPerPersonState.value = calculateTotalPerPerson(
                        totalBillState.value.toDouble(),
                        splitByState.value,
                        tipPercentage
                    )
                    keyboardController?.hide()
                }
            )
            if (validState) {
                // Split row
                Row(horizontalArrangement = Arrangement.Start) {
                    Text(
                        modifier = Modifier.align(alignment = CenterVertically),
                        text = "Split"
                    )

                    Spacer(modifier = Modifier.width(120.dp))

                    // Change split row
                    Row(
                        modifier = Modifier.padding(horizontal = 3.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        // Minus button
                        RoundIconButton(
                            imageVector = Icons.Default.Remove,
                            onClick = {
                                splitByState.value =
                                    if (splitByState.value > 1) splitByState.value - 1 else 1
                                totalPerPersonState.value = calculateTotalPerPerson(
                                    totalBillState.value.toDouble(),
                                    splitByState.value,
                                    tipPercentage
                                )
                            }
                        )

                        Text(
                            text = "${splitByState.value}",
                            modifier = Modifier
                                .align(CenterVertically)
                                .padding(start = 9.dp, end = 9.dp)
                        )

                        // Add button
                        RoundIconButton(
                            imageVector = Icons.Default.Add,
                            onClick = {
                                if (splitByState.value < range.last) splitByState.value++
                                totalPerPersonState.value = calculateTotalPerPerson(
                                    totalBillState.value.toDouble(),
                                    splitByState.value,
                                    tipPercentage
                                )
                            }
                        )
                    }
                }

                // Tip row
                Row {
                    Text(
                        text = "Tip",
                        modifier = Modifier.align(alignment = CenterVertically)
                    )

                    Spacer(modifier = Modifier.width(200.dp))

                    Text(
                        text = "$ ${tipAmountState.value}",
                        modifier = Modifier.align(alignment = CenterVertically)
                    )
                }

                // Tip percentage
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "$tipPercentage %")

                    Spacer(modifier = Modifier.height(10.dp))

                    Slider(
                        value = sliderPositionState,
                        onValueChange = { newVal ->
                            sliderPositionState = newVal
                            Log.d(
                                "BillForm",
                                "BillForm: ${totalBillState.value.toDouble()} - $tipPercentage"
                            )
                            tipAmountState.value = calculateTotalTip(
                                totalBill = totalBillState.value.toDouble(),
                                tipPercentage = tipPercentage
                            )
                            totalPerPersonState.value = calculateTotalPerPerson(
                                totalBillState.value.toDouble(),
                                splitByState.value,
                                tipPercentage
                            )
                        },
                        onValueChangeFinished = {
                            Log.d(
                                "BillForm",
                                "BillForm: 2 ${totalBillState.value.toDouble()} - $tipPercentage"
                            )
                        },
                        modifier = Modifier.padding(horizontal = 16.dp),
                        steps = 4,
                        colors = SliderDefaults.colors(
                            thumbColor = ColorSecondary,
                            activeTrackColor = ColorSecondary,
                            inactiveTrackColor = ColorSecondaryLight,
                            activeTickColor = ColorPrimaryDark,
                            inactiveTickColor = ColorPrimaryDark
                        )
                    )
                }

            } else {
                // Empty view
                Box {}
            }
        }
    }
}

@Preview()
@Composable
fun JetTipActivityPreview() {
    MyApp {
        TipCalculator()
    }
}