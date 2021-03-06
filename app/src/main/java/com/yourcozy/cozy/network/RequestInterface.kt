package com.yourcozy.cozy.network

import com.yourcozy.cozy.network.requestData.*
import com.yourcozy.cozy.network.responseData.*
import com.yourcozy.cozy.network.responseData.ResponseCategoryActivity
import com.yourcozy.cozy.network.responseData.ResponseBookmarkUpdate
import com.yourcozy.cozy.network.responseData.ResponseInterest
import com.yourcozy.cozy.network.responseData.ResponseMap
import com.yourcozy.cozy.network.responseData.ResponseSignin
import com.yourcozy.cozy.network.responseData.ResponseRecent
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RequestInterface {

    //회원가입 https://github.com/yourCozy/cozy-Server/wiki/%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85
    @POST("/user/signup")
    fun requestSignup(@Body body: RequestSignup) : Call<ResponseSignup>
    //이메일 중복확인 https://github.com/yourCozy/cozy-Server/wiki/%EC%9D%B4%EB%A9%94%EC%9D%BC-%EC%A4%91%EB%B3%B5-%ED%99%95%EC%9D%B8
    @POST("/user/checkemail")
    fun requestCheckEmail(@Body body: RequestCheckEmail) : Call<ResponseCheckEmail>
    //이메일로그인 https://github.com/yourCozy/cozy-Server/wiki/%EB%A1%9C%EA%B7%B8%EC%9D%B8
    @POST("/user/signin")
    fun requestEmailLogin(@Body body: RequestEmailLogin) : Call<ResponseEmailLogin>
    //소셜로그인 https://github.com/yourCozy/cozy-Server/wiki/%EC%86%8C%EC%85%9C%EB%A1%9C%EA%B7%B8%EC%9D%B8(%EC%82%AC%EC%9A%A9%EC%9E%90-%EC%A0%95%EB%B3%B4-%EC%A0%80%EC%9E%A5)
    @POST("/auth/social")
    fun requestLogin(@Body body: RequestLogin) : Call<ResponseSignin>
    //비밀번호 찾기 https://github.com/yourCozy/cozy-Server/wiki/%EB%B9%84%EB%B0%80%EB%B2%88%ED%98%B8-%EC%B0%BE%EA%B8%B0
    @POST("/user/findpw")
    fun requestFindPW(@Body body : RequestFindPW) : Call<ResponseFindPW>

    //추천
    //책방 8개 추천 https://github.com/yourCozy/cozy-Server/wiki/%EC%B1%85%EB%B0%A9-8%EA%B0%9C-%EC%B6%94%EC%B2%9C
    @GET("/bookstore/recommendation")
    fun requestRecommendationUser(@HeaderMap headers: Map<String, String?>) : Call<ResponseRecommendData>
    //로그인 안한 사용자
    @GET("/bookstore/recommendation")
    fun requestRecommendation() : Call<ResponseRecommendData>

    //책방 상세 https://github.com/yourCozy/cozy-Server/wiki/%EC%B1%85%EB%B0%A9-%EC%83%81%EC%84%B8
    //로그인 안한 사용자
    @GET("/bookstore/detail/{bookstoreIdx}")
    fun requestBookstoreDatail(@HeaderMap headers: Map<String, String?>, @Path("bookstoreIdx") bookstoreIdx: Int)  : Call<ResponseBookstoreDetailData>


    //책방 상세_책방 피드 https://github.com/yourCozy/cozy-Server/wiki/%EC%B1%85%EB%B0%A9-%EC%83%81%EC%84%B8_%EC%B1%85%EB%B0%A9-%ED%94%BC%EB%93%9C
    @GET("/bookstore/feed/{bookstoreIdx}")
    fun requestBookstoreFeed(@Path("bookstoreIdx") bookstoreIdx: Int) : Call<ResponseBookstoreFeedData>


    //책방 상세_활동 피드 https://github.com/yourCozy/cozy-Server/wiki/%EC%B1%85%EB%B0%A9-%EC%83%81%EC%84%B8_%ED%99%9C%EB%8F%99-%ED%94%BC%EB%93%9C
    @GET("/activity/{bookstoreIdx}")
    fun requestBookstoreActivity(@Path("bookstoreIdx") bookstoreIdx: Int) : Call<ResponseBookstoreActivityData>


    //책방 상세_간단 후기 조회 https://github.com/yourCozy/cozy-Server/wiki/%EA%B0%84%EB%8B%A8%ED%9B%84%EA%B8%B0-%EC%A1%B0%ED%9A%8C
    @GET("review/simple/{bookstoreIdx}")
    fun requestSimpleReview(@Path("bookstoreIdx") bookstoreIdx: Int) : Call<ResponseSimpleReview>

    //책방 상세_간단 후기 작성 https://github.com/yourCozy/cozy-Server/wiki/%EA%B0%84%EB%8B%A8%ED%9B%84%EA%B8%B0-%EC%9E%91%EC%84%B1
    @POST("review/simple/{bookstoreIdx}")
    fun requestSimpleReviewWrite(@HeaderMap headers: Map<String, String?>,@Path("bookstoreIdx") bookstoreIdx: Int, @Body body: RequestSimpleReview) : Call<ResponseSimpleReviewWrite>


    //지역
    //지역별 책방 조회 https://github.com/yourCozy/cozy-Server/wiki/%EC%A7%80%EC%97%AD%EB%B3%84-%EC%B1%85%EB%B0%A9-%EC%A1%B0%ED%9A%8C
    @GET("/bookstore/section/{sectionIdx}")
    fun requestMapLogin(@Path("sectionIdx") sectionIdx: Int, @HeaderMap headers: Map<String, String?>) : Call<ResponseMap>
    //로그인하지 않았을 때
    @GET("/bookstore/section/{sectionIdx}")
    fun requestMap(@Path("sectionIdx") sectionIdx: Int) : Call<ResponseMap>

    //지역별 책방 갯수 https://github.com/yourCozy/cozy-Server/wiki/%EC%A7%80%EC%97%AD%EB%B3%84-%EC%B1%85%EB%B0%A9-%EA%B0%AF%EC%88%98-%EB%B3%B4%EA%B8%B0
    @GET("/bookstore/count/section")
    fun requestCount(@HeaderMap headers: Map<String, String?>) : Call<ResponseCount>



    //활동
    //활동 상세 조회 https://github.com/yourCozy/cozy-Server/wiki/%ED%99%9C%EB%8F%99-%EC%83%81%EC%84%B8-%EC%A1%B0%ED%9A%8C
    @GET("/activity/detail/{activityIdx}")
    fun requestEventDetailLogin(@Path("activityIdx") activityIdx: Int, @HeaderMap headers: Map<String, String?>) : Call<ResponseEventDetail>
    //로그인 하지 않았을 때
    @GET("/activity/detail/{activityIdx}")
    fun requestEventDetail(@Path("activityIdx") activityIdx: Int) : Call<ResponseEventDetail>

    //카테고리별 활동 조회(마감 임박순) https://github.com/yourCozy/cozy-Server/wiki/%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC%EB%B3%84-%ED%99%9C%EB%8F%99-%EC%A1%B0%ED%9A%8C(%EB%A7%88%EA%B0%90-%EC%9E%84%EB%B0%95%EC%88%9C)
    @GET("/activity/category/deadline/{categoryIdx}")
    fun requestCategoryActivity(@Path("categoryIdx") categoryIdx: Int, @HeaderMap headers: Map<String, String?>) : Call<ResponseCategoryActivity>

    //카테고리별 활동 조회(최신순)


    //내정보
    //내 정보 조회 https://github.com/yourCozy/cozy-Server/wiki/%EB%82%B4-%EC%A0%95%EB%B3%B4-%ED%8E%98%EC%9D%B4%EC%A7%80-%EC%A1%B0%ED%9A%8C
    @GET("/mypage/myinfo")
    fun requestMypage(@HeaderMap headers: Map<String, String?>): Call<ResponseMypage>

    //내 정보 수정 클릭 https://github.com/yourCozy/cozy-Server/wiki/%EB%82%B4-%EC%A0%95%EB%B3%B4-%EC%88%98%EC%A0%95-%ED%81%B4%EB%A6%AD
    @GET("/mypage/update/myinfo")
    fun requestMypageDetail(@HeaderMap headers: Map<String, String?>) : Call<ResponseMypageDetail>

    //닉네임 변경 https://github.com/yourCozy/cozy-Server/wiki/%EB%82%B4-%EC%A0%95%EB%B3%B4-%EB%8B%89%EB%84%A4%EC%9E%84-%EC%88%98%EC%A0%95
    @POST("/mypage/update/nickname")
    fun requestNickChanged(@Body body: RequestNick, @HeaderMap headers: Map<String, String?>): Call<ResponseNickChanged>

    //비밀번호 변경-1 https://github.com/yourCozy/cozy-Server/wiki/%EB%82%B4-%EC%A0%95%EB%B3%B4-%EB%B9%84%EB%B0%80%EB%B2%88%ED%98%B8-%EC%88%98%EC%A0%951
    @POST("/mypage/update/password/1")
    fun requestPwdChangeEmail(@Body body: RequestEmailPC, @HeaderMap headers: Map<String, String?>): Call<ResponsePwdChangeEmail>

    //비밀번호 변경-2 https://github.com/yourCozy/cozy-Server/wiki/%EB%82%B4-%EC%A0%95%EB%B3%B4-%EB%B9%84%EB%B0%80%EB%B2%88%ED%98%B8-%EC%88%98%EC%A0%952
    @POST("/mypage/update/password/2")
    fun requestPwdChange(@Body body: RequestNewPwd, @HeaderMap headers: Map<String, String?>) : Call<ResponsePwdChange>

    //관심 책방 조회 https://github.com/yourCozy/cozy-Server/wiki/%EA%B4%80%EC%8B%AC%EC%B1%85%EB%B0%A9-%EC%A1%B0%ED%9A%8C
    @GET("/mypage/interest")
    fun requestInterest(@HeaderMap headers: Map<String, String?>) : Call<ResponseInterest>


    //북마크 업데이트 https://github.com/yourCozy/cozy-Server/wiki/%EB%B6%81%EB%A7%88%ED%81%AC-%EC%97%85%EB%8D%B0%EC%9D%B4%ED%8A%B8
    @PUT("/mypage/interest/{bookstoreIdx}")
    fun requestBookmarkUpdate(@Path("bookstoreIdx") bookstoreIdx : Int, @HeaderMap headers: Map<String, String?>) : Call<ResponseBookmarkUpdate>


    //최근 본 책방 조회 https://github.com/yourCozy/cozy-Server/wiki/%EC%B5%9C%EA%B7%BC-%EB%B3%B8-%EC%B1%85%EB%B0%A9-%EC%A1%B0%ED%9A%8C
    @GET("/mypage/recent")
    fun requestRecentlySeen(@HeaderMap headers: Map<String, String?>) : Call<ResponseRecent>

    //프로필 사진 업데이트 https://github.com/yourCozy/cozy-Server/wiki/%ED%94%84%EB%A1%9C%ED%95%84-%EC%82%AC%EC%A7%84-%EC%97%85%EB%8D%B0%EC%9D%B4%ED%8A%B8
    @Multipart
    @POST("/user/profile")
    fun requestProfile(@Body body : RequestProfilePic, @HeaderMap headers: Map<String, String?>) : Call<ResponseProfile>

//    취향 등록 https://github.com/yourCozy/cozy-Server/wiki/%EC%B7%A8%ED%96%A5-%EB%93%B1%EB%A1%9D
    @POST("/mypage/recommendation")
    fun requestPreference(@HeaderMap headers: Map<String, String?>, @Query("opt") opt1: String, @Query("opt") opt2: String, @Query("opt") opt3: String, @Query("opt") opt4: String, @Query("opt") opt5: String, @Query("opt") opt6: String) : Call<ResponsePreference>


    //취향 수정 https://github.com/yourCozy/cozy-Server/wiki/%EC%B7%A8%ED%96%A5-%EC%88%98%EC%A0%95
    //@PUT("/mypage/recommendation?opt=%&opt=% ...")

    //검색 https://github.com/yourCozy/cozy-Server/wiki/%EA%B2%80%EC%83%89-%ED%8E%98%EC%9D%B4%EC%A7%80
    @GET("/bookstore/search/{keyword}")
    fun requestSearch(@Path("keyword") keyword : String, @HeaderMap headers: Map<String, String?>) : Call<ResponseSearch>

    //댓글 조회 https://github.com/yourCozy/cozy-Server/wiki/%ED%99%9C%EB%8F%99-%EB%8C%93%EA%B8%80-%EC%A1%B0%ED%9A%8C
    @GET("comment/activity/{activityIdx}")
    fun requestComment(@Path("activityIdx") activityIdx: Int, @HeaderMap headers: Map<String, String?>) : Call<ResponseComment>
    //댓글 작성 https://github.com/yourCozy/cozy-Server/wiki/%ED%99%9C%EB%8F%99-%EB%8C%93%EA%B8%80-%EC%9E%91%EC%84%B1
    @POST("comment/{activityIdx}")
    fun requestCommentWrite(@Body body: RequestCommentWrite, @HeaderMap headers: Map<String, String?>, @Path("activityIdx") activityIdx: Int) : Call<ResponseCommentWrite>
    //댓글 수정
    @PUT("comment/{commentIdx}")
    fun requestCommentChange(@Path("commentIdx") commentIdx: Int, @Body body: RequestCommentWrite, @HeaderMap headers: Map<String, String?>) : Call<ResponseCommentChange>
    //댓글 삭제 https://github.com/yourCozy/cozy-Server/wiki/%ED%99%9C%EB%8F%99-%EB%8C%93%EA%B8%80-%EC%82%AD%EC%A0%9C
    @DELETE("/comment/{commentIdx}")
    fun requestCommentDel(@Path("commentIdx") commentIdx: Int, @HeaderMap headers: Map<String, String?>) : Call<ResponseCommentDel>
}



