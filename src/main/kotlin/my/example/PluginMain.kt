package my.example

import my.example.annotation.*
import my.example.plugs.*
import my.miraiplus.MiraiEventHandle
import my.miraiplus.annotation.RegexAnn
import net.mamoe.mirai.console.extension.PluginComponentStorage
import net.mamoe.mirai.console.plugin.jvm.JvmPlugin
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin

/**
 * 插件入口
 */
object PluginMain : KotlinPlugin(
	JvmPluginDescription(
		id = "my.ktbot.binbot",
		version = "0.1"
	) {
		author("bin.qq=2938137849")
		info("这是一个测试插件,在这里描述插件的功能和用法等.")
	}
), JvmPlugin {
	private val myEventHandle = MiraiEventHandle(this)

	override fun PluginComponentStorage.onLoad() {
		PlugConfig.reload()
		myEventHandle.injector + SendAuto + NeedAdmin + RegexAnn.Inject() +
			SendGroup + SendAdmin + NeedAtBot
	}

	override fun onEnable() {
		myEventHandle += arrayOf(
			CQBotRepeat,
			CQBotCommonMsg, CQBotMemeAI,
			BotEventHandle
		)
	}

	override fun onDisable() {
		myEventHandle.unregisterAll()
	}

	inline fun <T> catch(block: () -> T): T? {
		return try {
			block()
		}
		catch (e: Exception) {
			logger.error(e); null
		}
	}
}
