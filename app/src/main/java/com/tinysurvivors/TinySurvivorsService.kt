package com.tinysurvivors

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.preference.PreferenceManager

class TinySurvivorsService : AccessibilityService() {

    private var botEnabled = true
    private val sequence = listOf(
        Action.ClickByText("Reclamar"),
        Action.ClickByText("Claim"),
        Action.ClickByText("Atacar"),
        Action.ClickByText("Attack")
    )

    override fun onServiceConnected() {
        super.onServiceConnected()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        botEnabled = prefs.getBoolean("bot_enabled", true)

        val info = serviceInfo ?: AccessibilityServiceInfo()
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        info.flags = AccessibilityServiceInfo.DEFAULT
        serviceInfo = info
        Log.i(TAG, "Servicio conectado. Bot activo: $botEnabled")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (!botEnabled) return
        val root = rootInActiveWindow ?: return
        runSequence(root, sequence)
    }

    override fun onInterrupt() {
        Log.i(TAG, "Servicio interrumpido")
    }

    private fun runSequence(root: AccessibilityNodeInfo, seq: List<Action>) {
        for (a in seq) {
            when (a) {
                is Action.ClickByText -> tryClickByText(root, a.text)
            }
        }
    }

    private fun tryClickByText(root: AccessibilityNodeInfo, text: String): Boolean {
        val nodes = root.findAccessibilityNodeInfosByText(text) ?: return false
        for (n in nodes) {
            val target = findClickableAncestor(n)
            if (target != null && target.isEnabled) {
                val ok = target.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                Log.d(TAG, "Click \"$text\": $ok en ${target.className}")
                if (ok) return true
            }
        }
        return false
    }

    private fun findClickableAncestor(node: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        var cur = node
        while (cur != null) {
            if (cur.isClickable) return cur
            cur = cur.parent
        }
        return null
    }

    sealed class Action {
        data class ClickByText(val text: String) : Action()
    }

    companion object {
        private const val TAG = "TinySurvivorsService"
    }
}