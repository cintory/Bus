package www.cintroy.com.bus.base

import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleEndedException

/**
 * Created by Cintory on 2019/10/10 16:16
 * Email：Cintory@gmail.com
 */
enum class ActivityEvent {
    CREATE, START, RESUME, PAUSE, STOP, DESTROY;

    companion object {
        val LIFECYCLE = CorrespondingEventsFunction { lastEvent: ActivityEvent ->
            return@CorrespondingEventsFunction when (lastEvent) {
                CREATE -> DESTROY
                START -> STOP
                RESUME -> PAUSE
                PAUSE -> STOP
                STOP -> DESTROY
                DESTROY -> throw LifecycleEndedException(
                    "Cannot bind to Activity lifecycle after it's been destroyed."
                )
            }
        }
    }
}
