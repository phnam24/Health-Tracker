package com.example.healthtracker.presentation.onboarding.ui.step

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.Gender
import com.example.healthtracker.domain.model.OnboardingField
import com.example.healthtracker.presentation.components.AppDatePickerField
import com.example.healthtracker.presentation.components.DescriptionText
import com.example.healthtracker.presentation.components.OptionCard
import com.example.healthtracker.presentation.onboarding.state.OnboardingUiState
import com.example.healthtracker.presentation.onboarding.toStringRes
import com.example.healthtracker.presentation.onboarding.viewmodel.OnboardingEvent
import com.example.healthtracker.presentation.theme.dimensions

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PersonalInfoStep(
    uiState: OnboardingUiState,
    onEvent: (OnboardingEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        DescriptionText(
            label = stringResource(R.string.onboarding_personal_info_title),
            description = stringResource(R.string.onboarding_personal_info_description)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpacingMediumLarge))

        DatePickerSession(
            uiState = uiState,
            onDateSelected = onEvent
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpacingMediumLarge))

        GenderSelectSession(
            uiState = uiState,
            onGenderSelected = onEvent
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerSession(
    uiState: OnboardingUiState,
    onDateSelected: (OnboardingEvent) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        AppDatePickerField(
            value = uiState.birthDate,
            errorText = uiState.errors[OnboardingField.BIRTH_DATE]?.let { stringResource(it.toStringRes()) },
            onDateSelected = { selectedDate ->
                onDateSelected(OnboardingEvent.BirthDateSelected(selectedDate))
            },
            label = stringResource(R.string.onboarding_birth_date_label),
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpacingMedium))

        uiState.age?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                    .border(
                        width = MaterialTheme.dimensions.DividerThickness,
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    .padding(MaterialTheme.dimensions.SpacingMediumLarge),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Cake,
                    contentDescription = "Birthday cake icon",
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(MaterialTheme.dimensions.SpacingMediumLarge))

                Text(
                    text = stringResource(R.string.onboarding_age_value, it)
                )
            }
        }
    }
}

@Composable
fun GenderSelectSession(
    uiState: OnboardingUiState,
    onGenderSelected: (OnboardingEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        DescriptionText(
            label = stringResource(R.string.onboarding_gender_label),
            description = stringResource(R.string.onboarding_gender_description)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpacingMedium))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OptionCard(
                title = stringResource(R.string.gender_male),
                icon = Icons.Default.Male,
                selected = uiState.gender == Gender.MALE,
                onClick = { onGenderSelected(OnboardingEvent.GenderSelected(Gender.MALE)) },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(MaterialTheme.dimensions.SpacingSmall))

            OptionCard(
                title = stringResource(R.string.gender_female),
                icon = Icons.Default.Female,
                selected = uiState.gender == Gender.FEMALE,
                onClick = { onGenderSelected(OnboardingEvent.GenderSelected(Gender.FEMALE)) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpacingMedium))

        uiState.errors[OnboardingField.GENDER]?.let {
            Text(
                text = stringResource(it.toStringRes()),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
