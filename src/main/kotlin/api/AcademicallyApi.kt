package api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AcademicallyApi {

    @POST("/approve_multiple_choice_question")
    fun approveMultipleChoiceQuestion(@Body multipleChoiceBody: MultipleChoiceBody): String

    @POST("/approve_identification_question")
    fun approveIdentificationQuestion(@Body identificationBody: IdentificationBody): String

    @POST("/approve_true_or_false_question")
    fun approveTrueOrFalseQuestion(@Body trueOrFalseBody: TrueOrFalseBody): String

    @POST("/disapprove_pending_multiple_choice_assessment")
    fun disapprovePendingMultipleChoiceAssessment(@Body unapprovedBody: UnapprovedBody): String

    @POST("/disapprove_pending_identification_assessment")
    fun disapprovePendingIdentificationAssessment(@Body unapprovedBody: UnapprovedBody): String

    @POST("/disapprove_pending_true_or_false_assessment")
    fun disapprovePendingTrueOrFalseAssessment(@Body unapprovedBody: UnapprovedBody): String

    @GET("/get_pending_multiple_choice_questions")
    fun getPendingMultipleChoiceQuestions(): List<PendingAssessment>

    @GET("/get_pending_identification_questions")
    fun getPendingIdentificationQuestions(): List<PendingAssessment>

    @GET("/get_pending_true_or_false_questions")
    fun getPendingTrueOrFalseQuestions(): List<PendingAssessment>

    @GET("/get_pending_multiple_choice_question")
    fun getPendingMultipleChoiceQuestion(): MultipleChoice

    @GET("/get_pending_identification_question")
    fun getPendingIdentificationQuestion(): Identification

    @GET("/get_pending_true_or_false_question")
    fun getPendingTrueOrFalseQuestion(): TrueOrFalse

}