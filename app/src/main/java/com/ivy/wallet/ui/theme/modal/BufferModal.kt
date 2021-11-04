package com.ivy.wallet.ui.theme.modal

import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ivy.wallet.ui.theme.IvyTheme
import com.ivy.wallet.ui.theme.components.BufferBattery
import com.ivy.wallet.ui.theme.modal.edit.AmountModal
import java.util.*

data class BufferModalData(
    val balance: Double,
    val buffer: Double,
    val currency: String,
    val id: UUID = UUID.randomUUID()
)

@Composable
fun BoxWithConstraintsScope.BufferModal(
    modal: BufferModalData?,
    dismiss: () -> Unit,
    onBufferChanged: (Double) -> Unit
) {
    var newBufferAmount by remember(modal) {
        mutableStateOf(modal?.buffer ?: 0.0)
    }

    var amountModalVisible by remember { mutableStateOf(false) }

    IvyModal(
        id = modal?.id,
        visible = modal != null,
        dismiss = dismiss,
        PrimaryAction = {
            ModalSave {
                onBufferChanged(newBufferAmount)
                dismiss()
            }
        }
    ) {
        Spacer(Modifier.height(16.dp))

        BufferBattery(
            modifier = Modifier.padding(horizontal = 16.dp),
            buffer = newBufferAmount,
            balance = modal?.balance ?: 0.0,
            currency = modal?.currency ?: "",
            backgroundNotFilled = IvyTheme.colors.medium,
        )

        Spacer(Modifier.height(24.dp))

        ModalAmountSection(
            label = "Edit Savings goal",
            currency = modal?.currency ?: "",
            amount = newBufferAmount
        ) {
            amountModalVisible = true
        }
    }

    AmountModal(
        visible = amountModalVisible,
        currency = modal?.currency ?: "",
        initialAmount = newBufferAmount,
        dismiss = { amountModalVisible = false }
    ) {
        newBufferAmount = it
    }
}