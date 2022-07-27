package my.example

import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value
import net.mamoe.mirai.contact.Friend
import net.mamoe.mirai.event.events.MessageEvent

object PlugConfig : AutoSavePluginConfig("config") {
	val adminId: Long by value(2938137849L)

	fun getAdmin(bot: Bot): Friend {
		return bot.getFriendOrFail(adminId)
	}

	fun isAdmin(e: MessageEvent): Boolean = e.sender.id == adminId

}
