package utils

data class AssessmentState(
    val assessmentId: Int = 0,
    val courseId: Int = 0,
    val assessment: List<String> = emptyList(),
    val approveButtonEnabled: Boolean = true,
    val disapproveButtonEnabled: Boolean = true
)

data class LoginState(
    val email: String = "",
    val password: String = "",
    val passwordVisibility: Boolean = false,
    val buttonEnabled: Boolean = true
)

data class SignupState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val passwordVisibility: Boolean = false,
    val confirmPassword: String = "",
    val confirmPasswordVisibility: Boolean = false,
    val buttonEnabled: Boolean = true
)

data class SearchInfo(
    val searchQuery: String = "",
    val isActive: Boolean = false,
    val history: List<String> = emptyList()
)

data class AboutSupportChatState(
    val message: String = "",
    val closeButtonEnabled: Boolean = true,
    val sendButtonEnabled: Boolean = true
)