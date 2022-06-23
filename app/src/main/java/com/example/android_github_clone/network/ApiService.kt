package com.example.android_github_clone.network

import com.example.android_github_clone.model.AccessToken
import com.example.android_github_clone.model.User
import com.example.android_github_clone.model.UserRepositoriesResponseItem
import com.example.android_github_clone.model.UsersResponse
import com.example.android_github_clone.utils.ApiConstans
import com.example.githubclone.models.repositories_search_response.RepositoriesResponse
import retrofit2.http.*

interface ApiService {

    companion object {
        const val BASE_URl = ApiConstans.apiUrl
        const val IS_TEST_SERVER: Boolean = true
    }
    /*get access token  for github auth*/

    @POST(ApiConstans.doaminUrl + "login/oauth/access_token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
    ): AccessToken

    /*get user data */
    @GET("user")
    suspend fun getUserData(): User


    // get user repos'
    @GET("users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") username: String
    ): List<UserRepositoriesResponseItem>

    // get users by searching
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
    ): UsersResponse

    // get repos by searching
    @GET("search/repositories")
    suspend fun searchRepositories(@Query("query") query: String): RepositoriesResponse

}