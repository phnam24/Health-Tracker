package com.example.healthtracker.presentation.statistics.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.DailyCaloriePoint
import com.example.healthtracker.domain.model.RecentIntakeStats
import com.example.healthtracker.domain.model.WeeklyStats
import com.example.healthtracker.helper.toLocalizedDateString
import com.example.healthtracker.presentation.components.AppCard
import com.example.healthtracker.presentation.theme.dimensions
import com.example.healthtracker.presentation.theme.healthColors
import java.text.NumberFormat
import java.time.LocalDate
import kotlin.math.ceil
import kotlin.math.roundToInt
import java.time.format.TextStyle as JavaTextStyle

@Composable
fun StatisticsRecentIntakeCard(
    stats: RecentIntakeStats,
    isEmpty: Boolean,
    modifier: Modifier = Modifier,
) {
    StatisticsRecentIntakeCard(
        startDate = stats.startDate,
        endDate = stats.endDate,
        modifier = modifier,
        points = stats.points,
        isEmpty = isEmpty,
    )
}

@Composable
fun StatisticsRecentIntakeCard(
    startDate: LocalDate,
    endDate: LocalDate,
    modifier: Modifier = Modifier,
    points: List<DailyCaloriePoint> = emptyList(),
    isEmpty: Boolean = true,
) {
    AppCard(modifier = modifier.fillMaxWidth()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium),
        ) {
            StatisticsChartHeader(
                title = stringResource(R.string.statistics_intake_chart_title),
                range = statisticsRangeText(startDate, endDate),
                targetCalories = points.lastOrNull()?.goalCalories,
            )

            if (isEmpty) {
                StatisticsRecentEmptyContent()
            } else {
                WeeklyIntakeBarChart(points = points)
            }
        }
    }
}

@Composable
fun StatisticsWeeklySummary(
    stats: WeeklyStats,
    modifier: Modifier = Modifier,
) {
    val locale = LocalConfiguration.current.locales[0]
    val numberFormat = NumberFormat.getIntegerInstance(locale)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall),
    ) {
        WeeklyStatCard(
            label = stringResource(R.string.statistics_average_eaten),
            value = numberFormat.format(stats.averageEatenCalories),
            supportingText = stringResource(R.string.common_kcal),
            icon = Icons.Outlined.Restaurant,
            accent = MaterialTheme.healthColors.caloriesConsumed,
            modifier = Modifier.weight(1f),
        )
        WeeklyStatCard(
            label = stringResource(R.string.statistics_average_burned),
            value = numberFormat.format(stats.averageBurnedCalories),
            supportingText = stringResource(R.string.common_kcal),
            icon = Icons.Outlined.LocalFireDepartment,
            accent = MaterialTheme.healthColors.caloriesBurned,
            modifier = Modifier.weight(1f),
        )
        WeeklyStatCard(
            label = stringResource(R.string.statistics_goal_days),
            value = stringResource(
                R.string.statistics_goal_days_value,
                stats.goalDaysHit,
                7,
            ),
            supportingText = stringResource(R.string.common_day),
            icon = Icons.Filled.TrackChanges,
            accent = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun WeeklyStatCard(
    label: String,
    value: String,
    supportingText: String?,
    icon: ImageVector,
    accent: Color,
    modifier: Modifier = Modifier,
) {
    AppCard(
        modifier = modifier.heightIn(
            min = MaterialTheme.dimensions.statisticsSummaryCardMinHeight,
        ),
        contentPadding = PaddingValues(
            MaterialTheme.dimensions.spacingMedium,
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingSmall),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(MaterialTheme.dimensions.standardIconSize),
                tint = accent,
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = accent,
            )
            supportingText?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
fun WeeklyTrendCard(
    stats: WeeklyStats,
    modifier: Modifier = Modifier,
) {
    AppCard(modifier = modifier.fillMaxWidth()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMedium),
        ) {
            StatisticsChartHeader(
                title = stringResource(R.string.statistics_trend_chart_title),
                range = statisticsRangeText(stats.startDate, stats.endDate),
            )
            WeeklyTrendLineChart(points = stats.points)
        }
    }
}

@Composable
private fun WeeklyIntakeBarChart(
    points: List<DailyCaloriePoint>,
    modifier: Modifier = Modifier,
) {
    val description = statisticsChartContentDescription(
        chartDescription = stringResource(R.string.cd_statistics_intake_chart),
        points = points,
    )
    val textMeasurer = rememberTextMeasurer()
    val locale = LocalConfiguration.current.locales[0]
    val numberFormat = remember(locale) { NumberFormat.getIntegerInstance(locale) }
    val dayLabels = remember(points, locale) {
        points.map { point ->
            point.date.dayOfWeek.getDisplayName(JavaTextStyle.SHORT, locale)
        }
    }
    val targetCalories = points.maxOfOrNull { it.goalCalories }?.toFloat() ?: 0f
    val maxPointValue = points.maxOfOrNull { it.eatenCalories }?.toFloat() ?: 0f
    val maxY = maxOf(targetCalories, maxPointValue, 1f) * 1.25f
    val barPastColor = MaterialTheme.healthColors.caloriesConsumedContainer
    val barTodayColor = MaterialTheme.healthColors.caloriesConsumed
    val valuePastColor = MaterialTheme.healthColors.onCaloriesConsumedContainer
    val valueTodayColor = MaterialTheme.healthColors.caloriesConsumed
    val targetLineColor = MaterialTheme.colorScheme.outline
    val axisTextColor = MaterialTheme.colorScheme.onSurfaceVariant
    val axisTextStyle = MaterialTheme.typography.labelSmall
    val valueTextStyle = MaterialTheme.typography.labelSmall
    val chartHeight = MaterialTheme.dimensions.chartHeight
    val barWidth = MaterialTheme.dimensions.chartBarWidth
    val labelGap = MaterialTheme.dimensions.spacingSmall
    val valueGap = MaterialTheme.dimensions.spacingExtraSmall
    val lineGap = MaterialTheme.dimensions.spacingSmall
    val targetStrokeWidth = MaterialTheme.dimensions.focusedBorderThickness
    val dashLength = MaterialTheme.dimensions.spacingSmall
    val dashGap = MaterialTheme.dimensions.spacingExtraSmall

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(chartHeight)
            .semantics { contentDescription = description },
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val sampleDayLayout = textMeasurer.measure(
            text = dayLabels.maxByOrNull(String::length).orEmpty(),
            style = axisTextStyle,
        )
        val plotBottom = canvasHeight - sampleDayLayout.size.height - labelGap.toPx()
        val plotHeight = plotBottom.coerceAtLeast(1f)
        val targetText = numberFormat.format(targetCalories.roundToInt())
        val targetTextLayout = textMeasurer.measure(
            text = targetText,
            style = axisTextStyle.copy(color = axisTextColor),
        )
        val targetY = plotBottom - (targetCalories / maxY * plotHeight)

        drawText(
            textLayoutResult = targetTextLayout,
            topLeft = Offset(
                x = 0f,
                y = targetY - targetTextLayout.size.height / 2f,
            ),
        )

        val lineStartX = targetTextLayout.size.width + lineGap.toPx()
        drawLine(
            color = targetLineColor,
            start = Offset(lineStartX, targetY),
            end = Offset(canvasWidth, targetY),
            strokeWidth = targetStrokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(dashLength.toPx(), dashGap.toPx()),
            ),
        )

        if (points.isNotEmpty()) {
            val barWidthPx = barWidth.toPx()
            val chartStartX = lineStartX
            val availableWidth = canvasWidth - chartStartX
            val spacePerBar = availableWidth / points.size

            points.forEachIndexed { index, point ->
                val isToday = index == points.lastIndex
                val xCenter = chartStartX + (index * spacePerBar) + (spacePerBar / 2)
                val barHeight = point.eatenCalories / maxY * plotHeight
                val yOffset = plotBottom - barHeight

                drawRoundRect(
                    color = if (isToday) barTodayColor else barPastColor,
                    topLeft = Offset(xCenter - barWidthPx / 2f, yOffset),
                    size = Size(barWidthPx, barHeight),
                    cornerRadius = CornerRadius(barWidthPx / 2f, barWidthPx / 2f),
                )

                if (point.eatenCalories > 0) {
                    val valueText = numberFormat.format(point.eatenCalories)
                    val valueTextLayout = textMeasurer.measure(
                        text = valueText,
                        style = valueTextStyle.copy(
                            color = if (isToday) valueTodayColor else valuePastColor,
                            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Medium,
                        ),
                    )
                    drawText(
                        textLayoutResult = valueTextLayout,
                        topLeft = Offset(
                            x = xCenter - valueTextLayout.size.width / 2f,
                            y = yOffset - valueTextLayout.size.height - valueGap.toPx(),
                        ),
                    )
                }

                val dayText = dayLabels[index]
                val dayTextLayout = textMeasurer.measure(
                    text = dayText,
                    style = axisTextStyle.copy(
                        color = if (isToday) valueTodayColor else axisTextColor,
                        fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                    ),
                )

                drawText(
                    textLayoutResult = dayTextLayout,
                    topLeft = Offset(
                        x = xCenter - dayTextLayout.size.width / 2f,
                        y = plotBottom + labelGap.toPx(),
                    ),
                )
            }
        }
    }
}

@Composable
private fun WeeklyTrendLineChart(
    points: List<DailyCaloriePoint>,
    modifier: Modifier = Modifier,
) {
    val description = statisticsChartContentDescription(
        chartDescription = stringResource(R.string.cd_statistics_trend_chart),
        points = points,
    )
    val textMeasurer = rememberTextMeasurer()
    val locale = LocalConfiguration.current.locales[0]
    val numberFormat = remember(locale) { NumberFormat.getIntegerInstance(locale) }
    val dayLabels = remember(points, locale) {
        points.map { point ->
            point.date.dayOfWeek.getDisplayName(JavaTextStyle.SHORT, locale)
        }
    }
    val visiblePoints = points.filterNot(DailyCaloriePoint::isFuture)
    val maxDataValue = visiblePoints.maxOfOrNull { point ->
        maxOf(point.eatenCalories, point.burnedCalories)
    } ?: 0
    val maxY = ceil(maxDataValue.coerceAtLeast(1) / 1_000f)
        .coerceAtLeast(1f) * 1_000f
    val gridSteps = (maxY / 1000).toInt()
    val intakeColor = MaterialTheme.healthColors.caloriesConsumed
    val burnedColor = MaterialTheme.healthColors.caloriesBurned
    val axisTextColor = MaterialTheme.colorScheme.onSurfaceVariant
    val gridColor = MaterialTheme.colorScheme.outlineVariant
    val markerBackgroundColor = MaterialTheme.healthColors.cardContainer
    val emptyMarkerColor = MaterialTheme.colorScheme.outline
    val axisTextStyle = MaterialTheme.typography.labelSmall
    val valueTextStyle = MaterialTheme.typography.labelSmall
    val chartHeight = MaterialTheme.dimensions.chartHeight
    val yAxisLabelWidth = MaterialTheme.dimensions.spacingDoubleExtraLarge +
            MaterialTheme.dimensions.spacingSmall
    val xAxisLabelHeight = MaterialTheme.dimensions.spacingExtraLarge
    val valueGap = MaterialTheme.dimensions.spacingSmall
    val markerRadius = MaterialTheme.dimensions.chartPointSize / 2
    val lineWidth = MaterialTheme.dimensions.focusedBorderThickness
    val gridWidth = MaterialTheme.dimensions.dividerThickness
    val dashLength = MaterialTheme.dimensions.spacingSmall
    val dashGap = MaterialTheme.dimensions.spacingExtraSmall

    Column(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = description },
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(chartHeight),
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val yAxisWidthPx = yAxisLabelWidth.toPx()
            val chartBottom = canvasHeight - xAxisLabelHeight.toPx()
            val chartTop = valueTextStyle.lineHeight.toPx() + valueGap.toPx()
            val chartRight = canvasWidth
            val chartPlotHeight = (chartBottom - chartTop).coerceAtLeast(1f)
            val availableWidth = chartRight - yAxisWidthPx

            for (i in 0..gridSteps) {
                val value = i * 1000
                val yPos = chartBottom - value / maxY * chartPlotHeight
                val textLayout = textMeasurer.measure(
                    text = numberFormat.format(value),
                    style = axisTextStyle.copy(color = axisTextColor),
                )
                drawText(
                    textLayoutResult = textLayout,
                    topLeft = Offset(
                        x = 0f,
                        y = yPos - textLayout.size.height / 2f,
                    ),
                )

                if (i > 0) {
                    drawLine(
                        color = gridColor,
                        start = Offset(yAxisWidthPx, yPos),
                        end = Offset(chartRight, yPos),
                        strokeWidth = gridWidth.toPx(),
                        pathEffect = PathEffect.dashPathEffect(
                            floatArrayOf(dashLength.toPx(), dashGap.toPx()),
                        ),
                    )
                }
            }

            if (points.isEmpty()) return@Canvas

            val spacePerPoint = availableWidth / points.size
            val intakePath = Path()
            val burnedPath = Path()
            var intakeStarted = false
            var burnedStarted = false
            val markerRadiusPx = markerRadius.toPx()
            val pointCoordinates = mutableListOf<Triple<DailyCaloriePoint, Offset, Offset>>()

            points.forEachIndexed { index, point ->
                val xCenter = yAxisWidthPx + index * spacePerPoint + spacePerPoint / 2f
                val dayText = dayLabels[index]
                val dayTextLayout = textMeasurer.measure(
                    text = dayText,
                    style = axisTextStyle.copy(color = axisTextColor),
                )
                drawText(
                    textLayoutResult = dayTextLayout,
                    topLeft = Offset(
                        x = xCenter - dayTextLayout.size.width / 2f,
                        y = canvasHeight - dayTextLayout.size.height,
                    ),
                )

                if (point.isFuture) return@forEachIndexed

                val intakeY = chartBottom - point.eatenCalories / maxY * chartPlotHeight
                val burnedY = chartBottom - point.burnedCalories / maxY * chartPlotHeight
                val intakeOffset = Offset(xCenter, intakeY)
                val burnedOffset = Offset(xCenter, burnedY)
                pointCoordinates += Triple(point, intakeOffset, burnedOffset)

                if (!intakeStarted) {
                    intakePath.moveTo(xCenter, intakeY)
                    intakeStarted = true
                } else {
                    intakePath.lineTo(xCenter, intakeY)
                }
                if (!burnedStarted) {
                    burnedPath.moveTo(xCenter, burnedY)
                    burnedStarted = true
                } else {
                    burnedPath.lineTo(xCenter, burnedY)
                }

                if (point.hasData) {
                    val intakeValText = numberFormat.format(point.eatenCalories)
                    val intakeValLayout = textMeasurer.measure(
                        text = intakeValText,
                        style = valueTextStyle.copy(
                            color = intakeColor,
                            fontWeight = FontWeight.Medium,
                        ),
                    )
                    drawText(
                        textLayoutResult = intakeValLayout,
                        topLeft = Offset(
                            x = xCenter - intakeValLayout.size.width / 2f,
                            y = intakeY - intakeValLayout.size.height - valueGap.toPx(),
                        ),
                    )

                    val burnedValText = numberFormat.format(point.burnedCalories)
                    val burnedValLayout = textMeasurer.measure(
                        text = burnedValText,
                        style = valueTextStyle.copy(
                            color = burnedColor,
                            fontWeight = FontWeight.Medium,
                        ),
                    )
                    drawText(
                        textLayoutResult = burnedValLayout,
                        topLeft = Offset(
                            x = xCenter - burnedValLayout.size.width / 2f,
                            y = burnedY - burnedValLayout.size.height - valueGap.toPx(),
                        ),
                    )
                }
            }

            drawPath(
                path = intakePath,
                color = intakeColor,
                style = Stroke(
                    width = lineWidth.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round,
                ),
            )
            drawPath(
                path = burnedPath,
                color = burnedColor,
                style = Stroke(
                    width = lineWidth.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round,
                ),
            )

            pointCoordinates.forEach { (point, intakeOffset, burnedOffset) ->
                if (!point.hasData) {
                    drawCircle(
                        color = emptyMarkerColor,
                        radius = markerRadiusPx,
                        center = intakeOffset,
                        style = Stroke(
                            width = gridWidth.toPx(),
                            pathEffect = PathEffect.dashPathEffect(
                                floatArrayOf(dashLength.toPx(), dashGap.toPx()),
                            ),
                        ),
                    )
                    return@forEach
                }

                drawCircle(
                    color = markerBackgroundColor,
                    radius = markerRadiusPx,
                    center = intakeOffset,
                )
                drawCircle(
                    color = intakeColor,
                    radius = markerRadiusPx,
                    center = intakeOffset,
                    style = Stroke(width = lineWidth.toPx()),
                )
                drawDiamondMarker(
                    center = burnedOffset,
                    radius = markerRadiusPx,
                    color = burnedColor,
                    backgroundColor = markerBackgroundColor,
                    strokeWidth = lineWidth.toPx(),
                )
            }
        }

        ChartLegend(
            intakeColor = intakeColor,
            burnedColor = burnedColor,
        )
    }
}

@Composable
private fun statisticsChartContentDescription(
    chartDescription: String,
    points: List<DailyCaloriePoint>,
): String {
    val context = LocalContext.current
    val locale = LocalConfiguration.current.locales[0]
    val datePattern = stringResource(R.string.date_format_short)
    val rangeDescription = points.firstOrNull()?.let { firstPoint ->
        points.lastOrNull()?.let { lastPoint ->
            stringResource(
                R.string.cd_statistics_week_range,
                firstPoint.date.toLocalizedDateString(datePattern, locale),
                lastPoint.date.toLocalizedDateString(datePattern, locale),
            )
        }
    }
    val pointDescriptions = points.map { point ->
        val day = point.date.dayOfWeek.getDisplayName(JavaTextStyle.FULL, locale)
        if (point.isFuture) {
            stringResource(R.string.cd_statistics_future_day, day)
        } else {
            stringResource(
                R.string.cd_statistics_day_point,
                day,
                point.eatenCalories,
                point.burnedCalories,
                point.goalCalories,
            )
        }
    }

    return buildList {
        add(chartDescription)
        rangeDescription?.let(::add)
        addAll(pointDescriptions)
    }.joinToString(separator = ". ")
}

private fun DrawScope.drawDiamondMarker(
    center: Offset,
    radius: Float,
    color: Color,
    backgroundColor: Color,
    strokeWidth: Float,
) {
    val path = Path().apply {
        moveTo(center.x, center.y - radius)
        lineTo(center.x + radius, center.y)
        lineTo(center.x, center.y + radius)
        lineTo(center.x - radius, center.y)
        close()
    }
    drawPath(path = path, color = backgroundColor)
    drawPath(
        path = path,
        color = color,
        style = Stroke(width = strokeWidth, join = StrokeJoin.Round),
    )
}

@Composable
private fun ChartLegend(
    intakeColor: Color,
    burnedColor: Color,
) {
    val markerSize = MaterialTheme.dimensions.standardIconSize
    val lineWidth = MaterialTheme.dimensions.focusedBorderThickness
    val markerRadius = MaterialTheme.dimensions.chartPointSize / 2
    val markerBackgroundColor = MaterialTheme.healthColors.cardContainer

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = MaterialTheme.dimensions.spacingMedium),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Canvas(modifier = Modifier.size(markerSize)) {
            drawLine(
                color = intakeColor,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = lineWidth.toPx(),
            )
            drawCircle(
                color = markerBackgroundColor,
                radius = markerRadius.toPx(),
                center = center,
            )
            drawCircle(
                color = intakeColor,
                radius = markerRadius.toPx(),
                center = center,
                style = Stroke(width = lineWidth.toPx()),
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.dimensions.spacingExtraSmall))
        Text(
            text = stringResource(R.string.statistics_eaten_legend),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.width(MaterialTheme.dimensions.spacingExtraLarge))

        Canvas(modifier = Modifier.size(markerSize)) {
            drawLine(
                color = burnedColor,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = lineWidth.toPx(),
            )
            drawDiamondMarker(
                center = center,
                radius = markerRadius.toPx(),
                color = burnedColor,
                backgroundColor = markerBackgroundColor,
                strokeWidth = lineWidth.toPx(),
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.dimensions.spacingExtraSmall))
        Text(
            text = stringResource(R.string.statistics_burned_legend),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun StatisticsChartHeader(
    title: String,
    range: String,
    targetCalories: Int? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.dimensions.spacingExtraSmall,
            ),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = range,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        targetCalories?.let {
            StatisticsGoalChip(
                targetCalories = it,
                modifier = Modifier.padding(
                    start = MaterialTheme.dimensions.spacingSmall,
                ),
            )
        }
    }
}

@Composable
private fun StatisticsGoalChip(
    targetCalories: Int,
    modifier: Modifier = Modifier,
) {
    val locale = LocalConfiguration.current.locales[0]
    val formattedTarget = remember(targetCalories, locale) {
        NumberFormat.getIntegerInstance(locale).format(targetCalories)
    }

    Surface(
        modifier = modifier.heightIn(min = MaterialTheme.dimensions.statChipHeight),
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.healthColors.subtleContainer,
        contentColor = MaterialTheme.healthColors.onSubtleContainer,
        border = BorderStroke(
            width = MaterialTheme.dimensions.dividerThickness,
            color = MaterialTheme.colorScheme.outlineVariant,
        ),
    ) {
        Text(
            text = stringResource(R.string.statistics_goal_chip, formattedTarget),
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimensions.spacingSmall,
                vertical = MaterialTheme.dimensions.spacingExtraSmall,
            ),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
        )
    }
}
