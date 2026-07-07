package ludens.gamepad

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Gamepad tuş eşleme ekranı.
 * Kullanıcı fiziksel gamepad butonlarını RPG Maker tuşlarına yeniden atayabilir.
 */

data class ButtonMapping(
    val button: String,
    val defaultKey: String,
    var mapped: String
)

@Composable
fun GamepadMapperScreen(
    onSave: (List<ButtonMapping>) -> Unit,
    onReset: () -> Unit
) {
    val mappings = remember {
        mutableStateListOf(
            ButtonMapping("A (Cross)", "ok", "Enter / Z"),
            ButtonMapping("B (Circle)", "cancel", "Esc / X / Num 0"),
            ButtonMapping("X (Square)", "shift", "Shift"),
            ButtonMapping("Y (Triangle)", "menu", "Esc / X / Num 0"),
            ButtonMapping("L1 (LB)", "pageup", "Q / PageUp"),
            ButtonMapping("R1 (RB)", "pagedown", "W / PageDown"),
            ButtonMapping("L2 (LT)", "tab", "Tab"),
            ButtonMapping("R2 (RT)", "debug", "F9"),
            ButtonMapping("D-Pad Up", "up", "↑ / Num 8"),
            ButtonMapping("D-Pad Down", "down", "↓ / Num 2"),
            ButtonMapping("D-Pad Left", "left", "← / Num 4"),
            ButtonMapping("D-Pad Right", "right", "→ / Num 6"),
            ButtonMapping("Start (Options)", "menu", "Esc"),
            ButtonMapping("Select (Share)", "cancel", "Esc")
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("🎮 Gamepad Tuş Eşleme", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Fiziksel gamepad butonlarını RPG Maker tuşlarına yeniden atayabilirsiniz. " +
            "Değişiklikler oyun yeniden başlatıldığında aktif olur.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(mappings) { mapping ->
                GamepadMappingRow(
                    mapping = mapping,
                    onValueChange = { newValue ->
                        val idx = mappings.indexOf(mapping)
                        if (idx >= 0) mappings[idx] = mapping.copy(mapped = newValue)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { onSave(mappings.toList()) },
                modifier = Modifier.weight(1f)
            ) { Text("💾 Kaydet") }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(
                onClick = {
                    mappings.clear()
                    mappings.addAll(listOf(
                        ButtonMapping("A (Cross)", "ok", "Enter / Z"),
                        ButtonMapping("B (Circle)", "cancel", "Esc / X / Num 0"),
                        ButtonMapping("X (Square)", "shift", "Shift"),
                        ButtonMapping("Y (Triangle)", "menu", "Esc / X / Num 0"),
                        ButtonMapping("L1 (LB)", "pageup", "Q / PageUp"),
                        ButtonMapping("R1 (RB)", "pagedown", "W / PageDown"),
                        ButtonMapping("L2 (LT)", "tab", "Tab"),
                        ButtonMapping("R2 (RT)", "debug", "F9"),
                        ButtonMapping("D-Pad Up", "up", "↑ / Num 8"),
                        ButtonMapping("D-Pad Down", "down", "↓ / Num 2"),
                        ButtonMapping("D-Pad Left", "left", "← / Num 4"),
                        ButtonMapping("D-Pad Right", "right", "→ / Num 6"),
                        ButtonMapping("Start (Options)", "menu", "Esc"),
                        ButtonMapping("Select (Share)", "cancel", "Esc")
                    ))
                    onReset()
                },
                modifier = Modifier.weight(1f)
            ) { Text("🔄 Sıfırla") }
        }
    }
}

@Composable
fun GamepadMappingRow(
    mapping: ButtonMapping,
    onValueChange: (String) -> Unit
) {
    OutlinedCard(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1.5f)) {
                Text(mapping.button, style = MaterialTheme.typography.titleMedium)
                Text("RPG Key: ${mapping.defaultKey}", style = MaterialTheme.typography.bodySmall)
            }
            OutlinedTextField(
                value = mapping.mapped,
                onValueChange = onValueChange,
                label = { Text("Eşleşme") },
                modifier = Modifier.weight(2f),
                singleLine = true
            )
        }
    }
}
