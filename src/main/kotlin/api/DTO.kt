package api

annotation class Unauthorized

sealed class ProcessState {
    data object Loading : ProcessState()
    data object Success : ProcessState()
    data class Error(val message: String) : ProcessState()
}

sealed class Resource<T>(val data: T?, val error: String?) {
    class Success<T>(data: T): Resource<T>(data, null)
    class Error<T>(error: String): Resource<T>(null, error)
}

sealed class AuthResource<T>(val data: T?, val message: String?, val error: String?) {
    class Success<T>(data: T): AuthResource<T>(data, null, null)
    class ValidationError<T>(message: String): AuthResource<T>(null, message, null)
    class Error<T>(error: String): AuthResource<T>(null, null, error)
}

data class MultipleChoiceBody(
    val assessmentId: Int,
    val courseId: Int,
    val module: String,
    val question: String,
    val letterA: String,
    val letterB: String,
    val letterC: String,
    val letterD: String,
    val answer: String,
    val creator: String
)

data class IdentificationBody(
    val assessmentId: Int,
    val courseId: Int,
    val module: String,
    val question: String,
    val answer: String,
    val creator: String
)

data class TrueOrFalseBody(
    val assessmentId: Int,
    val courseId: Int,
    val module: String,
    val question: String,
    val answer: Boolean,
    val creator: String
)

data class MultipleChoice(
    val assessmentId: Int,
    val courseId: Int,
    val courseName: String,
    val module: String,
    val question: String,
    val letterA: String,
    val letterB: String,
    val letterC: String,
    val letterD: String,
    val answer: String,
    val creator: String
)

data class Identification(
    val assessmentId: Int,
    val courseId: Int,
    val courseName: String,
    val module: String,
    val question: String,
    val answer: String,
    val creator: String
)

data class TrueOrFalse(
    val assessmentId: Int,
    val courseId: Int,
    val courseName: String,
    val module: String,
    val question: String,
    val answer: Boolean,
    val creator: String
)

data class UnapprovedBody(
    val assessmentId: Int
)

data class PendingAssessment(
    val assessmentId: Int,
    val courseId: Int,
    val courseName: String,
    val module: String,
    val creator: String
)

data class Success(
    val message: String
)

data class User(
    val token: String = "",
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val password: String = ""
)

data class LoginBody(
    val email: String,
    val password: String
)

data class SignupBody(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)

data class UserBanBody(
    val value: Boolean,
    val userId: Int
)

data class Info(
    val id: Int,
    val name: String,
    val role: String,
    val email: String,
    val degree: String,
    val age: Int,
    val address: String,
    val contactNumber: String,
    val summary: String,
    val educationalBackground: String,
    val image: String,
    val freeTutoringTime: String,
    val isBanned: Boolean
)

data class Chat(
    val messages: List<Message> = emptyList(),
    val userId: Int = 0,
    val userName: String = "",
    val userRole: String = "",
    val userEmail: String = "",
    val userImage: String = ""
)

data class Message(
    val message: String,
    val sentDate: String,
    val isSender: Boolean
)

data class ChatBody(
    val message: String,
    val fromId: Int,
    val toId: Int
)

data class CloseChatBody(
    val userId: Int
)