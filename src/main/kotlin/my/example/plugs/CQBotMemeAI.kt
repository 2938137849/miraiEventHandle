package my.example.plugs

import my.example.annotation.NeedAtBot
import my.example.annotation.SendAuto
import my.example.utils.ReplaceNode
import my.miraiplus.annotation.EventHandle
import my.miraiplus.annotation.Qualifier
import net.mamoe.mirai.event.EventPriority
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.data.MessageSource.Key.quote

/**
 *
 * @author bin
 * @since 1.0
 * @date 2022/1/14
 */
object CQBotMemeAI {

	@JvmStatic
	private val replaceNode = ReplaceNode() + mapOf(
		"不" to "很",
		"你" to "我",
		"我" to "你",
		"有" to "没有",
		"没有" to "有",
		"有没有" to "肯定有",
		"是" to "不是",
		"不是" to "是",
		"是不是" to "肯定是",
		"？" to "!",
		"?" to "!",
		"吗" to "",
	)

	@EventHandle("(玩梗用自动回复)", priority = EventPriority.LOWEST)
	@NeedAtBot
	@SendAuto
	private fun invoke(event: GroupMessageEvent, @Qualifier("atBot") at: At): Message {
		val message = event.message
		return buildMessageChain {
			+message.quote()
			+event.sender.at()
			for (it in message) {
				if (it == at) continue
				if (it !is PlainText) +it
				else +PlainText(replaceNode.replace(it.content))
			}
		}
	}

}
