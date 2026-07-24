package com.example.healthtracker.presentation.components

fun normalizeDecimalInput(value: String): String =
    value.replace(',', '.')
        .filterIndexed { index, char ->
            char.isDigit() || (char == '.' && index > 0)
        }
        .let { filtered ->
            val firstDot = filtered.indexOf('.')
            if (firstDot == -1) {
                filtered
            } else {
                filtered.take(firstDot + 1) +
                        filtered.drop(firstDot + 1).replace(".", "")
            }
        }

fun Double.toEditableDecimal(): String =
    toBigDecimal()
        .stripTrailingZeros()
        .toPlainString()