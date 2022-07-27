@file:JvmName("Util")

package my.example.utils

import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.MiraiLogger

operator fun MatchResult.get(key: String): MatchGroup? {
	return groups[key]
}

fun Any?.toMessage(): Message? {
	return when (this) {
		null -> null
		Unit -> null
		is Message -> this
		is CharSequence -> if (isEmpty()) emptyMessageChain() else PlainText(this)
		is Array<*> -> buildMessageChain {
			addAll(this@toMessage.mapNotNull { it.toMessage() })
		}
		is Iterable<*> -> buildMessageChain {
			addAll(this@toMessage.mapNotNull { it.toMessage() })
		}
		else -> PlainText(toString())
	}
}

inline fun <reified T> createLogger(identity: String? = null) = MiraiLogger.Factory.create(T::class, identity)
