package ludens.cheat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ludens.bridge.JsBridge

@Composable
fun CheatMenuScreen(jsBridge: JsBridge?) {
    var gold by remember { mutableStateOf("999999") }
    var hp by remember { mutableStateOf("9999") }
    var mp by remember { mutableStateOf("999") }
    var level by remember { mutableStateOf("99") }
    var itemId by remember { mutableStateOf("1") }
    var itemCount by remember { mutableStateOf("99") }
    var actorId by remember { mutableStateOf("1") }
    var saveSlot by remember { mutableStateOf("1") }
    var mapId by remember { mutableStateOf("1") }
    var posX by remember { mutableStateOf("8") }
    var posY by remember { mutableStateOf("8") }
    var log by remember { mutableStateOf("Hile menüsü hazır.\n") }

    fun logMsg(msg: String) {
        log += "[${System.currentTimeMillis() % 10000}] $msg\n"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("🎮 Hile Menüsü", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Aktör Seçimi (Tüm hileler için ortak)
        OutlinedCard(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("👤 Hedef Aktör", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = actorId,
                    onValueChange = { actorId = it },
                    label = { Text("Aktör ID (1=Kahraman)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ALTIN
        CheatCard(title = "💰 Altın") {
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                OutlinedTextField(
                    value = gold,
                    onValueChange = { gold = it },
                    label = { Text("Miktar") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    jsBridge?.setGold(gold.toIntOrNull() ?: 999999)
                    logMsg("Altın $gold yapıldı.")
                }) { Text("Uygula") }
            }
        }

        // HP / MP
        CheatCard(title = "❤️ HP & MP") {
            Row {
                OutlinedTextField(
                    value = hp,
                    onValueChange = { hp = it },
                    label = { Text("HP") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = mp,
                    onValueChange = { mp = it },
                    label = { Text("MP") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Button(onClick = {
                    jsBridge?.setHp(actorId.toIntOrNull() ?: 1, hp.toIntOrNull() ?: 9999)
                    jsBridge?.setMp(actorId.toIntOrNull() ?: 1, mp.toIntOrNull() ?: 999)
                    logMsg("Aktör $actorId -> HP:$hp MP:$mp")
                }, modifier = Modifier.weight(1f)) { Text("Uygula") }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    jsBridge?.recoverAllParty()
                    logMsg("Tüm party iyileştirildi.")
                }, modifier = Modifier.weight(1f)) { Text("Full Heal") }
            }
        }

        // SEVİYE
        CheatCard(title = "📈 Seviye & Stat") {
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                OutlinedTextField(
                    value = level,
                    onValueChange = { level = it },
                    label = { Text("Level") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    jsBridge?.setLevel(actorId.toIntOrNull() ?: 1, level.toIntOrNull() ?: 99)
                    logMsg("Aktör $actorId Level:$level")
                }) { Text("Uygula") }
            }
        }

        // ITEM
        CheatCard(title = "🎒 Item Ekle") {
            Row {
                OutlinedTextField(
                    value = itemId,
                    onValueChange = { itemId = it },
                    label = { Text("Item ID") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = itemCount,
                    onValueChange = { itemCount = it },
                    label = { Text("Adet") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Button(onClick = {
                    jsBridge?.addItem(itemId.toIntOrNull() ?: 1, itemCount.toIntOrNull() ?: 99)
                    logMsg("Item $itemId x${itemCount.toIntOrNull() ?: 99} eklendi.")
                }, modifier = Modifier.weight(1f)) { Text("Ekle") }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    jsBridge?.addAllItems()
                    logMsg("Tüm item'ler eklendi!")
                }, modifier = Modifier.weight(1f)) { Text("Tüm Itemler") }
            }
        }

        // TELEPORT
        CheatCard(title = "🗺️ Teleport") {
            Row {
                OutlinedTextField(value = mapId, onValueChange = { mapId = it }, label = { Text("Map ID") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f))
                OutlinedTextField(value = posX, onValueChange = { posX = it }, label = { Text("X") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f))
                OutlinedTextField(value = posY, onValueChange = { posY = it }, label = { Text("Y") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f))
            }
            Button(onClick = {
                jsBridge?.teleport(mapId.toIntOrNull() ?: 1, posX.toIntOrNull() ?: 8, posY.toIntOrNull() ?: 8)
                logMsg("Teleport -> Map:$mapId X:$posX Y:$posY")
            }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) { Text("Işınlan") }
        }

        // HIZLI İŞLEMLER
        CheatCard(title = "⚡ Hızlı İşlemler") {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = { jsBridge?.toggleGodMode(); logMsg("God Mode toggled.") }) { Text("God Mode") }
                Button(onClick = { jsBridge?.toggleNoClip(); logMsg("NoClip toggled.") }) { Text("Duvar İçi") }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = { jsBridge?.setGameSpeed(3.0f); logMsg("Hız x3") }) { Text("Hız x3") }
                Button(onClick = { jsBridge?.setGameSpeed(1.0f); logMsg("Hız normal") }) { Text("Normal") }
                Button(onClick = { jsBridge?.freezeEnemies(); logMsg("Düşmanlar dondu") }) { Text("Dondur") }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                OutlinedTextField(value = saveSlot, onValueChange = { saveSlot = it }, label = { Text("Slot") }, modifier = Modifier.width(80.dp), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                Button(onClick = { jsBridge?.saveGame(saveSlot.toIntOrNull() ?: 1); logMsg("Slot $saveSlot kaydedildi.") }) { Text("Kaydet") }
                Button(onClick = { jsBridge?.loadGame(saveSlot.toIntOrNull() ?: 1); logMsg("Slot $saveSlot yüklendi.") }) { Text("Yükle") }
            }
        }

        // LOG
        OutlinedCard(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("📝 Log", style = MaterialTheme.typography.titleMedium)
                Text(log, style = MaterialTheme.typography.bodySmall, modifier = Modifier.height(150.dp).verticalScroll(rememberScrollState()))
            }
        }
    }
}

@Composable
fun CheatCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    OutlinedCard(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}
