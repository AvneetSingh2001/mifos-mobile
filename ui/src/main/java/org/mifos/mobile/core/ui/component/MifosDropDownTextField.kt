package org.mifos.mobile.core.ui.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

@Composable
fun MifosDropDownTextField(
    optionsList: List<String> = listOf(),
    selectedOption: String? = null,
    labelResId: Int = 0,
    isEnabled: Boolean = true,
    supportingText: String? = null,
    error: Boolean = false,
    onClick: (Int, String) -> Unit
) {

    var expanded by rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier.alpha(if (!isEnabled) 0.4f else 1f),
    ) {
        OutlinedTextField(
            value = selectedOption ?: "",
            onValueChange = {  },
            label = { Text(stringResource(id = labelResId)) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    expanded = !expanded && isEnabled
                },
            enabled = false,
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = if(expanded) OutlinedTextFieldDefaults.colors().focusedTextColor
                        else OutlinedTextFieldDefaults.colors().unfocusedTextColor,
                disabledContainerColor = if(expanded) OutlinedTextFieldDefaults.colors().focusedContainerColor
                else OutlinedTextFieldDefaults.colors().unfocusedContainerColor,
                disabledLabelColor = if(expanded) MaterialTheme.colorScheme.primary
                else if(error) Color.Red
                else OutlinedTextFieldDefaults.colors().unfocusedLabelColor,
                disabledBorderColor = if(expanded) MaterialTheme.colorScheme.primary
                else if(error) Color.Red
                else OutlinedTextFieldDefaults.colors().unfocusedIndicatorColor,
                disabledSupportingTextColor = if(expanded) MaterialTheme.colorScheme.primary
                else if(error) Color.Red
                else OutlinedTextFieldDefaults.colors().unfocusedSupportingTextColor,
            ),
            supportingText = { if (error) Text(text = supportingText ?: "") },
            isError = error,
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ArrowDropUp
                    else Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown",
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp),
            onDismissRequest = { expanded = false },
        ) {
            optionsList.forEachIndexed { index, item ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onClick(index, item)
                    },
                    text = { Text(text = item) }
                )
            }
        }
    }
}