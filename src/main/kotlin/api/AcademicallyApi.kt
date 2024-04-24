package api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AcademicallyApi {

    @POST("/admin_assessment_routes/approve_multiple_choice_question")
    suspend fun approveMultipleChoiceQuestion(@Body multipleChoiceBody: MultipleChoiceBody): Resource<Success>

    @POST("/admin_assessment_routes/approve_identification_question")
    suspend fun approveIdentificationQuestion(@Body identificationBody: IdentificationBody): Resource<Success>

    @POST("/admin_assessment_routes/approve_true_or_false_question")
    suspend fun approveTrueOrFalseQuestion(@Body trueOrFalseBody: TrueOrFalseBody): Resource<Success>

    @POST("/admin_assessment_routes/disapprove_pending_multiple_choice_assessment")
    suspend fun disapprovePendingMultipleChoiceAssessment(@Body unapprovedBody: UnapprovedBody): Resource<Success>

    @POST("/admin_assessment_routes/disapprove_pending_identification_assessment")
    suspend fun disapprovePendingIdentificationAssessment(@Body unapprovedBody: UnapprovedBody): Resource<Success>

    @POST("/admin_assessment_routes/disapprove_pending_true_or_false_assessment")
    suspend fun disapprovePendingTrueOrFalseAssessment(@Body unapprovedBody: UnapprovedBody): Resource<Success>

    @GET("/admin_assessment_routes/get_pending_multiple_choice_questions")
    suspend fun getPendingMultipleChoiceQuestions(): Resource<List<PendingAssessment>>

    @GET("/admin_assessment_routes/get_pending_identification_questions")
    suspend fun getPendingIdentificationQuestions(): Resource<List<PendingAssessment>>

    @GET("/admin_assessment_routes/get_pending_true_or_false_questions")
    suspend fun getPendingTrueOrFalseQuestions(): Resource<List<PendingAssessment>>

    @GET("/admin_assessment_routes/get_pending_multiple_choice_question")
    suspend fun getPendingMultipleChoiceQuestion(@Query("assessment_id") assessmentId: Int): Resource<MultipleChoice>

    @GET("/admin_assessment_routes/get_pending_identification_question")
    suspend fun getPendingIdentificationQuestion(@Query("assessment_id") assessmentId: Int): Resource<Identification>

    @GET("/admin_assessment_routes/get_pending_true_or_false_question")
    suspend fun getPendingTrueOrFalseQuestion(@Query("assessment_id") assessmentId: Int): Resource<TrueOrFalse>

    @POST("/admin_auth_routes/admin_login")
    @Unauthorized
    suspend fun adminLogin(@Body loginBody: LoginBody): AuthResource<User>

    @POST("/admin_auth_routes/admin_signup")
    @Unauthorized
    suspend fun adminSignup(@Body signupBody: SignupBody): AuthResource<User>

    @POST("/admin_routes/ban_user")
    suspend fun banUser(@Body userBanBody: UserBanBody): Resource<Success>

    @GET("/admin_routes/search_users")
    suspend fun searchUser(@Query("search_query") searchQuery: String): Resource<List<Info>>

    @GET("/admin_routes/get_support_messages_from_admin")
    suspend fun getSupportMessagesFromAdmin(): Resource<List<Chat>>

    @GET("/admin_routes/get_support_message_with_user")
    suspend fun getSupportMessageWithUser(@Query("user_id")  userId: Int): Resource<Chat>

    @POST("/admin_routes/send_support_message")
    suspend fun sendSupportMessage(@Body chatBody: ChatBody): Resource<Success>

    @POST("/admin_routes/close_support_chat")
    suspend fun closeSupportChat(@Body closeChatBody: CloseChatBody): Resource<Success>

    companion object {

        fun getInstance(preferences: User): AcademicallyApi {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(preferences))
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            val gson = GsonBuilder()
                .registerTypeAdapter(Resource::class.java, ResponseDeserializer())
                .registerTypeAdapter(AuthResource::class.java, AuthResponseDeserializer())
                .create()

            return Retrofit.Builder()
                .baseUrl("https://BrianSerrano.pythonanywhere.com")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(AcademicallyApi::class.java)
        }
    }
}