package com.yoimerdr.compose.ludens.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.inter
import ludens.composeapp.generated.resources.notosans_sc
import ludens.composeapp.generated.resources.plusjakartasans
import org.jetbrains.compose.resources.Font

@Suppress("ObjectPropertyName")
private var _Inter: FontFamily? = null

@Suppress("ObjectPropertyName")
private var _PlusJakartaSans: FontFamily? = null


val Inter: FontFamily
    @Stable
    @Composable
    get() {
        if (_Inter == null) {
            _Inter = FontFamily(
                Font(
                    Res.font.inter,
                    weight = FontWeight.Light
                ),
                Font(
                    Res.font.inter,
                    weight = FontWeight.Normal
                ),
                Font(
                    Res.font.inter,
                    weight = FontWeight.Medium
                ),
                Font(
                    Res.font.inter,
                    weight = FontWeight.Bold
                ),
                Font(
                    Res.font.inter,
                    weight = FontWeight.Black
                ),
                Font(
                    Res.font.inter,
                    weight = FontWeight.SemiBold
                ),
                Font(
                    Res.font.notosans_sc,
                    weight = FontWeight.Light
                ),
                Font(
                    Res.font.notosans_sc,
                    weight = FontWeight.Normal
                ),
                Font(
                    Res.font.notosans_sc,
                    weight = FontWeight.Medium
                ),
                Font(
                    Res.font.notosans_sc,
                    weight = FontWeight.SemiBold
                ),
                Font(
                    Res.font.notosans_sc,
                    weight = FontWeight.Bold
                ),
                Font(
                    Res.font.notosans_sc,
                    weight = FontWeight.Black
                ),
            )
        }

        return _Inter!!
    }

val PlusJakartaSans: FontFamily
    @Stable
    @Composable
    get() {
        if (_PlusJakartaSans == null) {
            _PlusJakartaSans = FontFamily(
                Font(
                    Res.font.plusjakartasans,
                    weight = FontWeight.Light
                ),
                Font(
                    Res.font.plusjakartasans,
                    weight = FontWeight.Normal
                ),
                Font(
                    Res.font.plusjakartasans,
                    weight = FontWeight.Medium
                ),
                Font(
                    Res.font.plusjakartasans,
                    weight = FontWeight.Bold
                ),
                Font(
                    Res.font.plusjakartasans,
                    weight = FontWeight.SemiBold
                ),
                Font(
                    Res.font.notosans_sc,
                    weight = FontWeight.Light
                ),
                Font(
                    Res.font.notosans_sc,
                    weight = FontWeight.Normal
                ),
                Font(
                    Res.font.notosans_sc,
                    weight = FontWeight.Medium
                ),
                Font(
                    Res.font.notosans_sc,
                    weight = FontWeight.SemiBold
                ),
                Font(
                    Res.font.notosans_sc,
                    weight = FontWeight.Bold
                ),
                Font(
                    Res.font.notosans_sc,
                    weight = FontWeight.Black
                )
            )
        }
        return _PlusJakartaSans!!
    }

val DisplayFontFamily: FontFamily
    @Stable
    @Composable
    get() = PlusJakartaSans

val BodyFontFamily: FontFamily
    @Stable
    @Composable
    get() = Inter
