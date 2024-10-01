package com.example.knowledge.compose.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 96.sp,
        letterSpacing = (-1.5).sp,
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 60.sp,
        letterSpacing = (-0.5).sp,
    ),
    h3 = TextStyle(
        fontSize = 48.sp,
        letterSpacing = 0.sp,
    ),
    h4 = TextStyle(
        fontSize = 34.sp,
        letterSpacing = 0.25.sp,
    ),
    h5 = TextStyle(
        fontSize = 24.sp,
        letterSpacing = 0.sp,
    ),
    h6 = getTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp,
    ),
    subtitle1 = getTextStyle(
        fontSize = 16.sp,
        letterSpacing = 0.15.sp,
    ),
    subtitle2 = getTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp,
    ),
    body1 = getTextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    body2 = getTextStyle(
        fontSize = 14.sp,
        letterSpacing = 0.25.sp,
    ),
    button = getTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp,
    ),
    caption = getTextStyle(
        fontSize = 12.sp,
        letterSpacing = 0.4.sp,
    ),
    overline = getTextStyle(
        fontSize = 10.sp,
        letterSpacing = 1.5.sp,
    )
)

private fun getTextStyle(
    fontFamily: FontFamily = FontFamily.Default,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit = 10.sp,
    letterSpacing: TextUnit = 1.15.sp,
    color: Color = ColorSecondary,
    lineHeight: TextUnit = TextUnit.Unspecified,
) = TextStyle(
    fontFamily = fontFamily,
    fontWeight = fontWeight,
    fontSize = fontSize,
    letterSpacing = letterSpacing,
    color = color,
    lineHeight = lineHeight,
)