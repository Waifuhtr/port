package ludens.plugin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File

/**
 * Yüklü RPG Maker JS plugin'lerini listeleyen yönetim ekranı.
 * www/js/plugins/ klasöründeki .js dosyalarını gösterir.
 */

@Composable
fun PluginManagerScreen(wwwPath: String) {
    val pluginsDir = remember { File(wwwPath, "js/plugins") }
    var plugins by remember { mutableStateOf(listOf<File>()) }
    var totalSize by remember { mutableStateOf(0L) }

    LaunchedEffect(Unit) {
        refreshPlugins(pluginsDir) { list, size ->
            plugins = list
            totalSize = size
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("🔌 Plugin Yöneticisi", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Otomatik tanımlanan JS plugin'leri. ZIP'inizin www/js/plugins/ klasörüne attığınız tüm .js dosyaları build sırasında otomatik kaydedilir.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Toplam: ${plugins.size} plugin | ${totalSize / 1024} KB",
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (plugins.isEmpty()) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("📂 Plugin Bulunamadı", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "ZIP dosyanızın içinde www/js/plugins/ klasörü yok veya boş.\n" +
                        "RPG Maker plugin'lerinizi bu klasöre atıp yeniden build alın.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        } else {
            LazyColumn {
                items(plugins) { plugin ->
                    PluginCard(plugin)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                refreshPlugins(pluginsDir) { list, size ->
                    plugins = list
                    totalSize = size
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("🔄 Yenile") }
    }
}

@Composable
fun PluginCard(plugin: File) {
    val isLudens = plugin.nameWithoutExtension.startsWith("YDP_Ludens", ignoreCase = true)
    val isCheat = plugin.nameWithoutExtension.lowercase().contains("cheat") ||
                  plugin.nameWithoutExtension.lowercase().contains("hack") ||
                  plugin.nameWithoutExtension.lowercase().contains("mod")

    OutlinedCard(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    plugin.nameWithoutExtension,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "${plugin.length() / 1024} KB • ${plugin.extension.uppercase()}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row {
                if (isLudens) {
                    Badge(containerColor = MaterialTheme.colorScheme.primary) {
                        Text("Ludens", modifier = Modifier.padding(horizontal = 4.dp))
                    }
                }
                if (isCheat) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Badge(containerColor = MaterialTheme.colorScheme.tertiary) {
                        Text("Hile", modifier = Modifier.padding(horizontal = 4.dp))
                    }
                }
            }
        }
    }
}

private fun refreshPlugins(dir: File, callback: (List<File>, Long) -> Unit) {
    val list = dir.listFiles { f -> f.isFile && f.extension == "js" && f.name != "plugins.js" }
        ?.sortedBy { it.name }
        ?: emptyList()
    val size = list.sumOf { it.length() }
    callback(list, size)
}
