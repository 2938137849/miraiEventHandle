package my.example.plugs

import my.example.annotation.Helper
import my.example.annotation.SendAuto
import my.miraiplus.annotation.EventHandle
import my.miraiplus.annotation.RegexAnn

/**
 *
 * @author bin
 * @since 1.0
 * @date 2022/1/13
 */
object CQBotCommonMsg {
	@EventHandle("ping")
	@RegexAnn("^[.．。]ping$", RegexOption.IGNORE_CASE)
	@Helper("测试bot是否连接正常")
	@SendAuto
	private val Ping = ".pong!"

	@EventHandle("data")
	@RegexAnn("^[.．。]data$", RegexOption.IGNORE_CASE)
	@Helper("开发者信息")
	@SendAuto(recall = 90 * 1000)
	private val Data = """
		|开发者QQ：2938137849
		|项目地址github：2938137849/KTBot
		|轮子github：mamoe/mirai
	""".trimMargin()

}
