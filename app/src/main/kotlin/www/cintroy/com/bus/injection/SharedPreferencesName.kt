package www.cintroy.com.bus.injection

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY_GETTER
import kotlin.annotation.AnnotationTarget.PROPERTY_SETTER
import kotlin.annotation.AnnotationTarget.VALUE_PARAMETER

/**
 * Created by Cintory on 2019/10/9 17:50
 * Email：Cintory@gmail.com
 */
@Qualifier
@Target(FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER, VALUE_PARAMETER)
@Retention(BINARY)
annotation class SharedPreferencesName