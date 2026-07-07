package ludens.bridge

import android.webkit.WebView

/**
 * WebView üzerinden RPG Maker MV/MZ JavaScript fonksiyonlarını çalıştıran bridge.
 * Bu sınıf sayesinde native Kotlin UI'dan oyunun iç verilerine müdahale edebiliriz.
 */
class JsBridge(private val webView: WebView?) {

    fun exec(js: String, onResult: ((String?) -> Unit)? = null) {
        webView?.post {
            webView.evaluateJavascript(js) { result ->
                onResult?.invoke(result)
            }
        }
    }

    // ─── EKONOMİ ───
    fun setGold(amount: Int) = exec("window.\$gameParty._gold = $amount;")
    fun addGold(amount: Int) = exec("window.\$gameParty.gainGold($amount);")

    // ─── KARAKTER / PARTY ───
    fun setHp(actorId: Int, hp: Int) = exec("window.\$gameActors.actor($actorId).setHp($hp);")
    fun setMp(actorId: Int, mp: Int) = exec("window.\$gameActors.actor($actorId).setMp($mp);")
    fun setMaxHp(actorId: Int, value: Int) = exec("window.\$gameActors.actor($actorId).paramPlus(0, $value);")
    fun setMaxMp(actorId: Int, value: Int) = exec("window.\$gameActors.actor($actorId).paramPlus(1, $value);")
    fun setLevel(actorId: Int, level: Int) = exec("window.\$gameActors.actor($actorId).changeLevel($level, false);")
    fun recoverAll(actorId: Int) = exec("window.\$gameActors.actor($actorId).recoverAll();")
    fun recoverAllParty() = exec("window.\$gameParty.members().forEach(function(a){a.recoverAll();});")

    // ─── GOD MODE ───
    fun toggleGodMode() = exec("""
        if(!window.__godMode){window.__godMode=true;window.__godInterval=setInterval(function(){
            window.\$gameParty.members().forEach(function(a){a.setHp(a.mhp);a.setMp(a.mmp);});
        },500);}else{window.__godMode=false;clearInterval(window.__godInterval);}
    """.trimIndent())

    // ─── NO CLIP (Duvar İçi) ───
    fun toggleNoClip() = exec("""
        if(!window.__noClip){window.__noClip=true;window.\$gamePlayer.setThrough(true);}
        else{window.__noClip=false;window.\$gamePlayer.setThrough(false);}
    """.trimIndent())

    // ─── ITEM / SİLAH / ZIRH ───
    fun addItem(itemId: Int, count: Int) = exec("window.\$gameParty.gainItem(window.\$dataItems[$itemId], $count);")
    fun addWeapon(weaponId: Int, count: Int) = exec("window.\$gameParty.gainItem(window.\$dataWeapons[$weaponId], $count);")
    fun addArmor(armorId: Int, count: Int) = exec("window.\$gameParty.gainItem(window.\$dataArmors[$armorId], $count);")
    fun addAllItems() = exec("""
        for(var i=1;i<window.\$dataItems.length;i++){
            if(window.\$dataItems[i])window.\$gameParty.gainItem(window.\$dataItems[i],99);
        }
    """.trimIndent())

    // ─── HIZ / ZAMAN ───
    fun setGameSpeed(rate: Float) = exec("window.Graphics._gameSpeed = $rate;")
    fun freezeEnemies() = exec("""
        window.\$gameMap.events().forEach(function(e){if(e.isType()!=='player')e._moveType=0;});
    """.trimIndent())

    // ─── KAYDET / YÜKLE ───
    fun saveGame(slot: Int) = exec("window.\$gameSystem.onBeforeSave();window.DataManager.saveGame($slot);")
    fun loadGame(slot: Int) = exec("window.DataManager.loadGame($slot);window.\$gameSystem.onAfterLoad();")
    fun quickSave() = exec("window.DataManager.saveGame(99);")
    fun quickLoad() = exec("window.DataManager.loadGame(99);")

    // ─── DİĞER ───
    fun openMenu() = exec("window.SceneManager.push(window.Scene_Menu);")
    fun teleport(mapId: Int, x: Int, y: Int) = exec("window.\$gamePlayer.reserveTransfer($mapId, $x, $y);")
    fun showFps() = exec("window.Graphics._showFps = true;")
    fun hideFps() = exec("window.Graphics._showFps = false;")

    // ─── DEĞER OKUMA (Callback ile) ───
    fun getGold(callback: (Int) -> Unit) {
        exec("window.\$gameParty.gold();") { result ->
            callback(result?.toIntOrNull() ?: 0)
        }
    }
    fun getHp(actorId: Int, callback: (Int) -> Unit) {
        exec("window.\$gameActors.actor($actorId).hp;") { result ->
            callback(result?.toIntOrNull() ?: 0)
        }
    }
}
