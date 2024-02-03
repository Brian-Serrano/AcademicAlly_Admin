package api

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